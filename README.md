# Livraria JDBC e JPA

Projeto didatico incremental com Java 17, JDBC, JPA, Hibernate e SQLite. Cada `parada-*` e independente e possui seu proprio banco SQLite previamente criado. As aplicacoes nao criam tabelas, migrations ou dados iniciais.

## Downloads

- [Baixar os slides da aula (PPTX)](https://github.com/marquesbmc/JDBC-JPA-DB/releases/download/v1.0.0/JDBC_JPA_Apresentacao.pptx)
- [Baixar o pacote completo e pronto para executar (ZIP)](https://github.com/marquesbmc/JDBC-JPA-DB/releases/download/v1.0.0/JDBC-JPA-DB-completo-v1.0.0.zip)

## Sequencia recomendada

### JDBC

1. Conexao: DriverManager e Connection.
2. PreparedStatement: parametros, consultas, insercao e chave gerada.
3. ResultSet: mapeamento, Optional e List.
4. DAO: CRUD de Livro.
5. Transacoes: commit, rollback e Savepoint.

### JPA

6. Configuracao: Hibernate, persistence.xml, EntityManager e primeira entidade.
7. CRUD: ciclo de vida, persist, find, dirty checking e remove.
8. Relacionamentos: ManyToOne, OneToMany e carregamento lazy.
9. Transacoes: cascade, commit, rollback e atualizacao de estoque.

O driver e `org.xerial:sqlite-jdbc`. Drivers JDBC modernos sao descobertos pelo mecanismo Service Provider; nao usamos `Class.forName`.

## Como executar

Abra o PowerShell na raiz deste projeto. Os comandos abaixo usam somente o Java 17 extraido em `.tools` e os JARs de `.lib`; Maven nao e necessario.

O Java 17 inclui JDBC, mas nao inclui JPA. Por isso, Hibernate, Jakarta Persistence e suas dependencias estao previamente baixados em `.lib/jpa`.

Cada bloco abaixo e independente: copie e execute o bloco inteiro da parada desejada.

```powershell
# Parada 1 - conexao
New-Item -ItemType Directory -Force parada-01-conexao\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\sqlite-jdbc-3.46.1.3.jar" -d parada-01-conexao\target\classes (Get-ChildItem parada-01-conexao\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-01-conexao
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;..\.lib\sqlite-jdbc-3.46.1.3.jar" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 2 - PreparedStatement
New-Item -ItemType Directory -Force parada-02-prepared-statement\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\sqlite-jdbc-3.46.1.3.jar" -d parada-02-prepared-statement\target\classes (Get-ChildItem parada-02-prepared-statement\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-02-prepared-statement
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;..\.lib\sqlite-jdbc-3.46.1.3.jar" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 3 - ResultSet e mapeamento
New-Item -ItemType Directory -Force parada-03-resultset-mapeamento\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\sqlite-jdbc-3.46.1.3.jar" -d parada-03-resultset-mapeamento\target\classes (Get-ChildItem parada-03-resultset-mapeamento\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-03-resultset-mapeamento
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;..\.lib\sqlite-jdbc-3.46.1.3.jar" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 4 - DAO e CRUD
New-Item -ItemType Directory -Force parada-04-dao-crud\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\sqlite-jdbc-3.46.1.3.jar" -d parada-04-dao-crud\target\classes (Get-ChildItem parada-04-dao-crud\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-04-dao-crud
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;..\.lib\sqlite-jdbc-3.46.1.3.jar" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 5 - transacoes
New-Item -ItemType Directory -Force parada-05-transacoes\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\sqlite-jdbc-3.46.1.3.jar" -d parada-05-transacoes\target\classes (Get-ChildItem parada-05-transacoes\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-05-transacoes
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;..\.lib\sqlite-jdbc-3.46.1.3.jar" br.edu.ibmec.livraria.Aplicacao
Pop-Location
```

As paradas JPA usam o mesmo `javac`, acrescentando os JARs de `.lib/jpa` e a pasta de recursos que contem o `persistence.xml`:

```powershell
# Parada 6 - configuracao JPA
New-Item -ItemType Directory -Force parada-06-jpa-configuracao\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\jpa\*" -d parada-06-jpa-configuracao\target\classes (Get-ChildItem parada-06-jpa-configuracao\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-06-jpa-configuracao
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 7 - CRUD JPA
New-Item -ItemType Directory -Force parada-07-jpa-crud\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\jpa\*" -d parada-07-jpa-crud\target\classes (Get-ChildItem parada-07-jpa-crud\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-07-jpa-crud
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 8 - relacionamentos JPA
New-Item -ItemType Directory -Force parada-08-jpa-relacionamentos\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\jpa\*" -d parada-08-jpa-relacionamentos\target\classes (Get-ChildItem parada-08-jpa-relacionamentos\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-08-jpa-relacionamentos
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
Pop-Location

# Parada 9 - transacoes JPA
New-Item -ItemType Directory -Force parada-09-jpa-transacoes\target\classes | Out-Null
& ".\.tools\jdk-17.0.20+8\bin\javac.exe" -cp ".\.lib\jpa\*" -d parada-09-jpa-transacoes\target\classes (Get-ChildItem parada-09-jpa-transacoes\src\main\java -Recurse -Filter *.java).FullName
Push-Location parada-09-jpa-transacoes
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
Pop-Location
```

Cada execucao que inclui insercao, CRUD ou venda altera somente o `livraria.db` daquela parada. Para recomecar a demonstracao, restaure a copia original:

```powershell
Copy-Item -Force banco\livraria-base.db parada-05-transacoes\livraria.db
```

Troque `parada-05-transacoes` pelo nome da parada que deseja restaurar.
