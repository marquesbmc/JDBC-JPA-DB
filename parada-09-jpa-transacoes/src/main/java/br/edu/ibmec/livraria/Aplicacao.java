package br.edu.ibmec.livraria;

public class Aplicacao {
    public static void main(String[] args) {
        // A fabrica permanece aberta durante todas as operacoes do roteiro.
        try (JPAUtil jpa = new JPAUtil()) {
            VendaService servico = new VendaService(jpa);

            // Leitura inicial usada como referencia para commit e rollback.
            int estoqueInicial = servico.consultarEstoque(1L);
            System.out.println("Estoque inicial: " + estoqueInicial);

            // Primeira tentativa: todos os comandos terminam em commit.
            VendaResultado resultado = servico.registrarVenda(1L, 2);
            System.out.println("Venda confirmada: " + resultado.vendaId());
            System.out.println("Item criado: " + resultado.itemVendaId());
            System.out.println("Estoque apos commit: " + resultado.estoqueRestante());

            try {
                // Segunda tentativa: o service executa flush e lanca uma falha.
                servico.demonstrarRollback(1L, 1);
            } catch (IllegalStateException exception) {
                System.out.println("Rollback: " + exception.getMessage());
            }

            // INSERTs e UPDATE da tentativa com falha nao podem permanecer no banco.
            System.out.println("Estoque apos rollback: " + servico.consultarEstoque(1L));
        }
    }
}
