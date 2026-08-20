package br.edu.ibmec.livraria;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class CadastroLivroTest {
    @Test
    void deveInserirLivroERetornarChaveGerada() {
        // Arrange: monta o objeto que executa o INSERT.
        CadastroLivro cadastro = new CadastroLivro(new ConnectionFactory());

        // Act: insere dados validos e captura o ID devolvido pelo SQLite.
        long id = cadastro.inserir("Teste PreparedStatement", "9789999000002", new BigDecimal("10.00"), 1, 1);

        // Assert: IDs AUTOINCREMENT validos sao positivos.
        assertTrue(id > 0);
    }
}
