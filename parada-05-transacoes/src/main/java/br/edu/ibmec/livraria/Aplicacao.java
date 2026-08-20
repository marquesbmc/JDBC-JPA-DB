package br.edu.ibmec.livraria;

public class Aplicacao {
    public static void main(String[] args) {
        // Service executa gravacoes; ConsultaLivro observa o estado do banco.
        VendaService servico = new VendaService(new ConnectionFactory());
        ConsultaLivro consulta = new ConsultaLivro(new ConnectionFactory());

        // Primeiro executamos o caminho feliz, que termina com commit.
        // orElseThrow deixa claro que o livro 1 deve existir no banco preparado.
        System.out.println("Estoque inicial: " + consulta.buscarPorId(1).orElseThrow().estoque());
        VendaResultado resultado = servico.registrarVenda(1, 2);
        System.out.println("Venda confirmada: " + resultado.venda().id());
        System.out.println("Novo estoque: " + resultado.estoqueRestante());

        // Depois forçamos uma falha de negocio para observar o rollback.
        // A quantidade e maior que qualquer estoque disponivel na base.
        try {
            servico.registrarVenda(1, 10_000);
        } catch (EstoqueInsuficienteException exception) {
            System.out.println("Rollback: " + exception.getMessage());
        }

        // Savepoint e apresentado em um exemplo menor e independente da venda.
        servico.demonstrarSavepoint();
    }
}
