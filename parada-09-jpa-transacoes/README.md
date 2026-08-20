# Parada 9: Transacoes JPA

Registra uma venda, cria o item por cascade e reduz o estoque por dirty checking.
Todos os passos pertencem a mesma `EntityTransaction`. Uma falha controlada depois
de `flush()` demonstra o rollback desfazendo comandos que ja chegaram ao SQLite.

Execute no PowerShell a partir desta pasta:

```powershell
New-Item -ItemType Directory -Force target\classes | Out-Null
& "..\.tools\jdk-17.0.20+8\bin\javac.exe" -cp "..\.lib\jpa\*" -d target\classes (Get-ChildItem src\main\java -Recurse -Filter *.java).FullName
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
```

Esta aplicacao altera o banco. Para repetir a demonstracao, restaure-o na raiz:

```powershell
Copy-Item -Force banco\livraria-base.db parada-09-jpa-transacoes\livraria.db
```
