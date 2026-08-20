package br.edu.ibmec.livraria;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CadastroLivroTest {
    @Test
    void deveInserirLivroERetornarChaveGerada() {
        // Arrange: prepara o colaborador responsavel pelo INSERT.
        CadastroLivro cadastro = new CadastroLivro(new ConnectionFactory());

        // Act: cadastra uma linha com todos os parametros exigidos.
        long id = cadastro.inserir("Teste PreparedStatement", "9789999000002", new BigDecimal("10.00"), 1, 1);

        // Assert: a chave confirma que o SQLite aceitou e identificou a linha.
        assertTrue(id > 0);
    }
}
