# Parada 2: PreparedStatement

Acrescenta consultas parametrizadas, insercao e chave gerada. O SQL permanece fixo; os valores sao vinculados pelos marcadores `?` antes de `executeQuery` ou `executeUpdate`.

```powershell
mvn test
mvn exec:java -Dexec.mainClass=br.edu.ibmec.livraria.Aplicacao
```

O driver SQLite e descoberto automaticamente via Java Service Provider; nao ha `Class.forName`.# Parada 1: Conexao JDBC

Executa a abertura e o fechamento de uma `Connection` para o banco `livraria.db` ja existente.

```powershell
mvn test
mvn exec:java -Dexec.mainClass=br.edu.ibmec.livraria.Aplicacao
```

Em drivers JDBC modernos nao usamos `Class.forName`. O driver SQLite e encontrado automaticamente pelo mecanismo Java Service Provider.