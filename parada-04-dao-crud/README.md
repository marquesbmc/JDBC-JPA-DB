# Parada 1: Conexao JDBC

Executa a abertura e o fechamento de uma `Connection` para o banco `livraria.db` ja existente.

```powershell
mvn test
mvn exec:java -Dexec.mainClass=br.edu.ibmec.livraria.Aplicacao
```

Em drivers JDBC modernos nao usamos `Class.forName`. O driver SQLite e encontrado automaticamente pelo mecanismo Java Service Provider.