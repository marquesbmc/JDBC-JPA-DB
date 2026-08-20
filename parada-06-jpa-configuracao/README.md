# Parada 6: Configuracao JPA

Apresenta JPA, Hibernate, `persistence.xml`, `EntityManagerFactory`,
`EntityManager` e o primeiro mapeamento da tabela `livro`.

JPA nao faz parte do Java 17. Os JARs necessarios ja estao em `.lib/jpa`, por
isso Maven nao e necessario.

Execute no PowerShell a partir desta pasta:

```powershell
New-Item -ItemType Directory -Force target\classes | Out-Null
& "..\.tools\jdk-17.0.20+8\bin\javac.exe" -cp "..\.lib\jpa\*" -d target\classes (Get-ChildItem src\main\java -Recurse -Filter *.java).FullName
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
```

O Hibernate apenas utiliza o banco existente. Ele nao cria nem altera tabelas.
