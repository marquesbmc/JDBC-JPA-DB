# Parada 5: Transacoes JDBC

Uma venda usa a mesma `Connection` para consultar estoque, inserir venda e item, atualizar livro e chamar `commit`. Falhas provocam `rollback`; ha tambem um exemplo pequeno de `Savepoint`.

O SQLite JDBC e descoberto automaticamente por Service Provider. Nenhuma classe cria tabelas.