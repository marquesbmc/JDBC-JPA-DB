package br.edu.ibmec.livraria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JPACrudTest {
    private Path bancoTemporario;

    @BeforeEach
    void prepararBanco() throws Exception {
        // O CRUD acontece em uma copia para que o teste seja repetivel.
        bancoTemporario = Files.createTempFile("livraria-jpa-", ".db");
        Files.copy(Path.of("livraria.db"), bancoTemporario, StandardCopyOption.REPLACE_EXISTING);
        System.setProperty("livraria.db", bancoTemporario.toString());
    }

    @AfterEach
    void descartarBanco() throws Exception {
        // Limpa configuracao e arquivo mesmo depois de cada metodo de teste.
        System.clearProperty("livraria.db");
        Files.deleteIfExists(bancoTemporario);
    }

    @Test
    void deveExecutarCrudCompleto() {
        try (JPAUtil jpa = new JPAUtil();
             EntityManager entityManager = jpa.criarEntityManager()) {
            // CREATE: objeto novo, ainda com ID nulo.
            Livro livro = new Livro(
                    "Teste JPA",
                    "9789999000021",
                    new BigDecimal("20.00"),
                    2,
                    1L);

            executarTransacao(entityManager, () -> entityManager.persist(livro));
            assertNotNull(livro.getId());

            // READ: clear evita devolver a mesma instancia que ja estava em memoria.
            entityManager.clear();
            Livro encontrado = entityManager.find(Livro.class, livro.getId());

            // UPDATE: o metodo muda o objeto; o commit produz SQL por dirty checking.
            executarTransacao(entityManager, () -> encontrado.atualizar(
                    "Teste atualizado",
                    new BigDecimal("25.00"),
                    3));
            assertEquals("Teste atualizado", encontrado.getTitulo());

            // DELETE: remove exige que a entidade esteja gerenciada.
            executarTransacao(entityManager, () -> entityManager.remove(encontrado));
            assertNull(entityManager.find(Livro.class, livro.getId()));
        }
    }

    private void executarTransacao(EntityManager entityManager, Runnable operacao) {
        // Este helper deixa o teste focado no CRUD e garante rollback em falhas.
        EntityTransaction transacao = entityManager.getTransaction();
        try {
            transacao.begin();
            operacao.run();
            transacao.commit();
        } catch (RuntimeException exception) {
            if (transacao.isActive()) {
                transacao.rollback();
            }
            throw exception;
        }
    }
}
