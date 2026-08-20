# Parada 8: Relacionamentos JPA

Substitui o campo `autorId` por uma associacao entre objetos usando
`@ManyToOne` e `@OneToMany`. A navegacao demonstra o carregamento lazy sem
usar JPQL.

Execute no PowerShell a partir desta pasta:

```powershell
New-Item -ItemType Directory -Force target\classes | Out-Null
& "..\.tools\jdk-17.0.20+8\bin\javac.exe" -cp "..\.lib\jpa\*" -d target\classes (Get-ChildItem src\main\java -Recurse -Filter *.java).FullName
& "..\.tools\jdk-17.0.20+8\bin\java.exe" -cp "target\classes;src\main\resources;..\.lib\jpa\*" br.edu.ibmec.livraria.Aplicacao
```
