# Parada 7: CRUD e ciclo de vida JPA

Demonstra `persist`, `find`, dirty checking, `remove` e os estados novo,
gerenciado, destacado e removido de uma entidade.

Execute no PowerShell a partir desta pasta:

```powershell
New-Item -ItemType Directory -Force target\classes | Out-Null
& "..\.tools\jdk-17.0.20+8\bin\javac.exe" -cp "..\.lib\jpa\*" -d target\classes (Get-ChildItem src\main\java -Recurse -Filter *.java).FullName
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
```

A aplicacao exclui o livro demonstrativo ao final, permitindo novas execucoes.
