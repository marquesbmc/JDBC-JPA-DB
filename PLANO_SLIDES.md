# Plano mestre dos slides: JDBC e JPA

## Objetivo geral

Ao final da sequencia, o aluno deve compreender como uma aplicacao Java acessa
um banco relacional primeiro com JDBC explicito e depois com JPA/Hibernate,
conseguindo relacionar cada conceito ao codigo executavel das paradas.

## Estrutura didatica comum

Cada apresentacao deve seguir esta progressao:

1. Situacao ou problema que torna o conceito necessario.
2. Definicoes essenciais, com poucos termos por slide.
3. Fluxo mental ou relacao entre os componentes.
4. Leitura guiada de pequenos trechos do codigo fonte.
5. SQL ou saida observavel produzida pela aplicacao.
6. Pergunta, experimento ou exercicio de fechamento.

Regras para os futuros decks:

- Um slide deve ensinar uma ideia principal.
- Slides de codigo devem mostrar de 8 a 16 linhas, nunca o arquivo inteiro.
- Destaques de codigo devem acompanhar a ordem da explicacao oral.
- Use diagramas apenas para ciclo de vida, relacionamentos e transacoes.
- A saida do console deve aparecer depois do codigo que a produz.
- Perguntas ao aluno ficam no ultimo slide ou nas notas do professor.
- Nao terminar com um slide generico de agradecimento.

## Quantidade planejada

| Parada | Tema | Duracao | Slides |
|---|---|---:|---:|
| 01 | Conexao JDBC | 15 min | 6 |
| 02 | PreparedStatement | 25 min | 8 |
| 03 | ResultSet e mapeamento | 25 min | 8 |
| 04 | DAO e CRUD | 30 min | 9 |
| 05 | Transacoes JDBC | 35 min | 10 |
| 06 | Configuracao JPA | 30 min | 9 |
| 07 | CRUD e ciclo de vida JPA | 35 min | 10 |
| 08 | Relacionamentos JPA | 40 min | 10 |
| 09 | Transacoes JPA | 40 min | 11 |

Total: 81 slides para aproximadamente 4 horas e 50 minutos de conteudo.

---

## Parada 01: Conexao JDBC

### Slide 1 - Java encontra o banco por meio do JDBC

Tipo: abertura.

- Nome da parada e objetivo: abrir e fechar uma conexao SQLite.
- Resultado esperado: driver, URL e estado da conexao no console.
- Arquivos centrais: `ConnectionFactory.java` e `Aplicacao.java`.

### Slide 2 - JDBC padroniza a conversa entre Java e bancos diferentes

Tipo: definicoes.

- JDBC: API padrao de acesso a bancos relacionais.
- Driver JDBC: implementacao especifica do banco.
- URL JDBC: identifica driver, banco e localizacao.
- SQLite: banco embutido armazenado em um arquivo.

### Slide 3 - Uma Connection representa uma sessao aberta

Tipo: modelo mental.

- Fluxo visual: Aplicacao -> DriverManager -> driver SQLite -> `livraria.db`.
- `DriverManager` escolhe o driver pelo prefixo `jdbc:sqlite:`.
- `Connection` existe ate ser fechada.
- Uma conexao aberta e um recurso que precisa de ciclo de vida controlado.

### Slide 4 - ConnectionFactory concentra caminho, URL e abertura

Tipo: codigo fonte.

- Mostrar constantes da propriedade, arquivo padrao e prefixo JDBC.
- Mostrar `localizarArquivoBanco()` e a validacao com `Files.isRegularFile`.
- Mostrar a composicao da URL.
- Mostrar `DriverManager.getConnection(url)`.

### Slide 5 - try-with-resources garante o fechamento

Tipo: codigo fonte.

- Mostrar o bloco `try (Connection connection = ...)` de `Aplicacao`.
- Explicar que `Connection` implementa `AutoCloseable`.
- Mostrar acesso a `getMetaData()`, driver e URL.
- Mostrar o caminho de erro pelo `catch (SQLException)`.

### Slide 6 - O fechamento automatico evita conexoes abandonadas

Tipo: execucao e pratica.

- Exibir a saida esperada da aplicacao.
- Pergunta: quem chama `connection.close()`?
- Experimento: executar fora da pasta da parada e interpretar o erro de caminho.
- Transicao: uma conexao aberta ainda nao envia comandos SQL.

---

## Parada 02: PreparedStatement

### Slide 1 - A conexao agora envia comandos parametrizados

Tipo: abertura.

- Objetivo: consultar e inserir sem concatenar valores no SQL.
- Arquivos: `ConsultaLivro`, `CadastroLivro` e `Aplicacao`.
- Resultado: consultas, insercao e ID gerado.

### Slide 2 - Concatenar valores mistura dados com o comando

Tipo: problema.

- Contrastar SQL concatenado com SQL que usa `?`.
- Riscos: aspas, tipos incorretos e injecao de SQL.
- Regra: o texto SQL permanece fixo; os valores seguem separados.

### Slide 3 - PreparedStatement associa cada marcador a um valor

Tipo: definicoes.

- `PreparedStatement`: comando SQL preparado e parametrizado.
- Indices comecam em 1.
- `setLong`, `setString`, `setBigDecimal` e `setInt` preservam tipos.
- `executeQuery` le dados; `executeUpdate` altera dados.

### Slide 4 - Uma busca por ID retorna zero ou uma linha

Tipo: codigo fonte.

- Mostrar SQL de `buscarPorId` com `WHERE id = ?`.
- Destacar `statement.setLong(1, id)`.
- Destacar `executeQuery()`.
- Explicar `Optional` como representacao da ausencia.

### Slide 5 - LIKE recebe os curingas como parte do parametro

Tipo: codigo fonte.

- Mostrar SQL de `buscarPorParteDoTitulo`.
- Mostrar `"%" + titulo + "%"` em `setString`.
- Mostrar `while (resultSet.next())`.
- Contrastar resultado unico com lista de resultados.

### Slide 6 - INSERT usa os mesmos marcadores e setters tipados

Tipo: codigo fonte.

- Mostrar o text block do INSERT.
- Relacionar cinco colunas, cinco marcadores e cinco setters.
- Mostrar `executeUpdate()`.
- Explicar por que preco usa `BigDecimal`.

### Slide 7 - A chave gerada completa a nova linha

Tipo: codigo fonte.

- Mostrar `Statement.RETURN_GENERATED_KEYS`.
- Mostrar `statement.getGeneratedKeys()`.
- Mostrar `chaves.next()` antes de `getLong(1)`.
- Explicar por que o ID so existe depois do INSERT.

### Slide 8 - Restricoes do banco continuam valendo

Tipo: execucao e pratica.

- Exibir consultas e ID inserido no console.
- Mostrar o erro de ISBN duplicado e identificar `SQLITE_CONSTRAINT_UNIQUE`.
- Experimento: remover um setter ou repetir o ISBN.
- Transicao: ainda estamos montando textos; a proxima parada cria objetos.

---

## Parada 03: ResultSet e mapeamento

### Slide 1 - Linhas do banco passam a ser objetos Java

Tipo: abertura.

- Objetivo: transformar `ResultSet` em `Livro`.
- Arquivos: `Livro`, `LivroMapper`, `ConsultaLivro` e `Aplicacao`.
- Resultado: `Optional<Livro>` e `List<Livro>`.

### Slide 2 - ResultSet e um cursor, nao uma colecao pronta

Tipo: definicoes.

- `ResultSet` representa o resultado de um SELECT.
- O cursor nasce antes da primeira linha.
- `next()` avanca e informa se existe outra linha.
- Os getters leem somente a linha atual.

### Slide 3 - Cada tipo SQL precisa de uma leitura compativel

Tipo: definicoes.

- `getLong`, `getString`, `getBigDecimal` e `getInt`.
- Leitura por nome de coluna versus leitura por indice.
- `autor_id` no SQL se torna `autorId` no Java.
- Preco permanece `BigDecimal`.

### Slide 4 - O record Livro representa uma linha completa

Tipo: codigo fonte.

- Mostrar a declaracao do `record Livro`.
- Explicar componentes, construtor e acessores gerados.
- Explicar imutabilidade.
- Relacionar os seis componentes as colunas da tabela.

### Slide 5 - LivroMapper tem uma unica responsabilidade

Tipo: codigo fonte.

- Mostrar `mapear(ResultSet)`.
- O mapper nao abre conexao, nao executa SQL e nao chama `next()`.
- Ele apenas le a linha atual e cria `Livro`.
- Beneficio: o mesmo mapeamento atende varias consultas.

### Slide 6 - Optional modela uma busca que pode nao encontrar

Tipo: codigo fonte.

- Mostrar `buscarUm`.
- Destacar `resultSet.next()`.
- Comparar `Optional.of(livro)` e `Optional.empty()`.
- Mostrar os IDs 1 e 999 usados na aplicacao.

### Slide 7 - List acumula todas as linhas mapeadas

Tipo: codigo fonte.

- Mostrar `buscarLista`.
- Destacar lista inicialmente vazia.
- Destacar `while (resultSet.next())` e `livros.add`.
- Explicar por que lista vazia e melhor que `null`.

### Slide 8 - O console confirma objetos, ausencia e listas

Tipo: execucao e pratica.

- Exibir livro encontrado, `Optional.empty` e listas.
- Pergunta: o que acontece se o mapper for chamado antes de `next()`?
- Experimento: consultar outro ID inexistente.
- Transicao: consultas e mapeamento agora precisam de uma organizacao unica.

---

## Parada 04: DAO e CRUD

### Slide 1 - O DAO cria uma fronteira para o acesso a dados

Tipo: abertura.

- Objetivo: concentrar SQL e JDBC em `LivroDAO`.
- Resultado: CRUD completo sem SQL na aplicacao.
- Arquivos: `LivroDAO`, `LivroMapper`, `Livro` e `Aplicacao`.

### Slide 2 - CRUD descreve as quatro operacoes basicas

Tipo: definicoes.

- Create: inserir.
- Read: buscar e listar.
- Update: atualizar.
- Delete: excluir.
- Cada operacao corresponde a uma intencao da aplicacao.

### Slide 3 - DAO esconde detalhes de persistencia

Tipo: definicoes e arquitetura.

- Fluxo visual: Aplicacao -> LivroDAO -> JDBC -> SQLite.
- A aplicacao conhece metodos e objetos, nao SQL.
- O DAO conhece SQL, conexoes, statements e mapper.
- O DAO nao deve manter uma `Connection` aberta como atributo.

### Slide 4 - INSERT devolve um novo Livro com identidade

Tipo: codigo fonte.

- Mostrar SQL e `RETURN_GENERATED_KEYS` em `inserir`.
- Mostrar `preencherDadosDoLivro`.
- Mostrar criacao do novo `Livro` com o ID gerado.
- Explicar o ID zero do objeto de entrada.

### Slide 5 - READ combina Optional, List e mapper

Tipo: codigo fonte.

- Mostrar `buscarPorId` e retorno `Optional`.
- Mostrar `listarTodos` e `buscarPorTitulo`.
- Mostrar reutilizacao de `buscarLista`.
- Reforcar que nenhuma linha produz colecao vazia.

### Slide 6 - UPDATE usa o ID no WHERE

Tipo: codigo fonte.

- Mostrar SQL de `atualizar`.
- Cinco parametros de dados e o sexto parametro para ID.
- Mostrar `executeUpdate() == 1`.
- Explicar retorno `false` quando nenhuma linha e encontrada.

### Slide 7 - DELETE tambem confirma linhas afetadas

Tipo: codigo fonte.

- Mostrar SQL de `excluir`.
- Vincular ID com `setLong`.
- Mostrar retorno booleano.
- Comparar exclusao bem-sucedida com ID inexistente.

### Slide 8 - A aplicacao percorre o CRUD sem conhecer JDBC

Tipo: codigo fonte e fluxo.

- Mostrar sequencia de chamadas em `Aplicacao`.
- Inserir -> buscar -> listar -> atualizar -> excluir -> buscar novamente.
- Explicar por que atualizar cria outro record.
- Mostrar `Optional.empty` depois da exclusao.

### Slide 9 - Separar responsabilidades torna o codigo evolutivo

Tipo: execucao e pratica.

- Exibir a saida completa do CRUD.
- Pergunta: qual classe mudaria se o SQL de livro fosse alterado?
- Experimento: atualizar ou excluir um ID inexistente.
- Transicao: uma venda precisa coordenar varios DAOs ou comandos juntos.

---

## Parada 05: Transacoes JDBC

### Slide 1 - Uma venda precisa ser concluida por inteiro

Tipo: abertura.

- Objetivo: proteger venda, item e estoque como uma unidade.
- Arquivo central: `VendaService`.
- Resultado: commit no sucesso e rollback na falha.

### Slide 2 - Operacoes isoladas podem deixar dados inconsistentes

Tipo: problema.

- Cenario: venda inserida, item inserido, atualizacao de estoque falha.
- Sem transacao, parte dos dados permanece.
- Pergunta central: o que significa uma venda incompleta?
- Necessidade de atomicidade.

### Slide 3 - Uma transacao termina em commit ou rollback

Tipo: definicoes.

- Transacao: conjunto de comandos tratado como uma unidade.
- `commit`: confirma todas as alteracoes.
- `rollback`: desfaz alteracoes ainda nao confirmadas.
- `autoCommit=true`: cada comando confirma sozinho.

### Slide 4 - Todos os comandos devem usar a mesma Connection

Tipo: modelo mental.

- Fluxo visual dentro de uma unica conexao.
- `setAutoCommit(false)` inicia o controle manual.
- Abrir outra conexao cria outra fronteira transacional.
- Guardar e restaurar o estado original de `autoCommit`.

### Slide 5 - VendaService define a fronteira da unidade de trabalho

Tipo: codigo fonte.

- Mostrar abertura da `Connection`.
- Mostrar `autoCommitOriginal` e `setAutoCommit(false)`.
- Mostrar bloco `try/catch/finally` interno.
- Destacar que metodos auxiliares recebem a mesma conexao.

### Slide 6 - A ordem dos comandos respeita as chaves estrangeiras

Tipo: codigo fonte e sequencia.

- Buscar livro e validar estoque.
- Calcular total com `BigDecimal`.
- Inserir venda e recuperar `vendaId`.
- Inserir item e depois baixar estoque.

### Slide 7 - commit torna as tres alteracoes definitivas

Tipo: codigo fonte.

- Mostrar `connection.commit()`.
- Mostrar criacao de `VendaResultado` somente depois do commit.
- Relacionar venda, item e estoque restante.
- Explicar por que o retorno nao leva a Connection.

### Slide 8 - rollback devolve o banco ao estado anterior

Tipo: codigo fonte.

- Mostrar `catch (SQLException | RuntimeException)`.
- Mostrar `connection.rollback()` e relancamento.
- Mostrar `EstoqueInsuficienteException`.
- Mostrar restauracao de `autoCommit` no `finally`.

### Slide 9 - Savepoint permite rollback parcial

Tipo: definicao e codigo fonte.

- Savepoint marca uma posicao dentro da transacao.
- `rollback(ponto)` preserva comandos anteriores.
- `rollback()` sem argumento desfaz toda a transacao.
- Explicar que o exemplo usa um UPDATE seguro e isolado.

### Slide 10 - Consistencia e o resultado observavel

Tipo: execucao e pratica.

- Mostrar estoque inicial, venda confirmada e novo estoque.
- Mostrar falha por estoque insuficiente.
- Confirmar que a tentativa invalida nao altera os dados.
- Experimento: provocar uma falha entre item e estoque.

---

## Parada 06: Configuracao JPA

### Slide 1 - JPA inicia uma nova camada sobre o JDBC

Tipo: abertura.

- Objetivo: configurar o provedor e executar o primeiro `find`.
- Arquivos: `persistence.xml`, `JPAUtil`, `Livro` e `Aplicacao`.
- Resultado: objeto carregado e SQL gerado pelo Hibernate.

### Slide 2 - JPA e especificacao; Hibernate e implementacao

Tipo: definicoes.

- JPA/Jakarta Persistence define interfaces e anotacoes.
- Hibernate implementa a especificacao.
- Hibernate continua usando JDBC internamente.
- SQLiteDialect adapta o SQL ao SQLite.

### Slide 3 - JPA nao faz parte do Java 17

Tipo: contexto tecnico.

- Java 17 inclui JDBC no modulo `java.sql`.
- API JPA e provedor sao dependencias externas.
- JARs locais em `.lib/jpa` eliminam a necessidade de Maven na aula.
- O codigo usa `jakarta.persistence`, nao `javax.persistence`.

### Slide 4 - persistence.xml descreve a unidade de persistencia

Tipo: codigo fonte.

- Mostrar nome `livraria` e `RESOURCE_LOCAL`.
- Mostrar provedor Hibernate e classe `Livro`.
- Mostrar driver, URL e SQLiteDialect.
- Mostrar `schema-generation=none` e SQL visivel.

### Slide 5 - Uma entidade conecta classe, tabela e identidade

Tipo: definicoes e codigo fonte.

- Mostrar `@Entity` e `@Table(name="livro")`.
- Mostrar `@Id` e `@GeneratedValue(IDENTITY)`.
- Mostrar `@Column` e restricoes.
- Explicar construtor protegido sem argumentos.

### Slide 6 - EntityManagerFactory e cara; EntityManager e descartavel

Tipo: definicoes.

- `EntityManagerFactory`: configuracao compartilhada da aplicacao.
- `EntityManager`: contexto de uma unidade de trabalho.
- Um factory cria varios managers.
- EntityManager nao deve ser compartilhado entre threads.

### Slide 7 - JPAUtil inicializa e encerra o provedor

Tipo: codigo fonte.

- Mostrar validacao do arquivo SQLite.
- Mostrar sobrescrita da URL por propriedades.
- Mostrar `Persistence.createEntityManagerFactory`.
- Mostrar `criarEntityManager()` e `close()`.

### Slide 8 - find busca uma entidade pela chave primaria

Tipo: codigo fonte.

- Mostrar `entityManager.find(Livro.class, 1L)`.
- Primeiro argumento: tipo; segundo: identidade.
- Entidade encontrada fica gerenciada.
- ID ausente retorna `null`.

### Slide 9 - O SQL aparece mesmo quando nao foi escrito na aplicacao

Tipo: execucao e pratica.

- Exibir os dois SELECTs gerados.
- Relacionar colunas selecionadas aos atributos da entidade.
- Mostrar livro encontrado e resultado nulo.
- Transicao: a proxima parada altera o estado das entidades.

---

## Parada 07: CRUD e ciclo de vida JPA

### Slide 1 - O EntityManager acompanha o ciclo de vida dos objetos

Tipo: abertura.

- Objetivo: executar CRUD observando estados da entidade.
- Arquivos: `Livro` e `Aplicacao`.
- Resultado: INSERT, SELECT, UPDATE e DELETE gerados.

### Slide 2 - Entidades atravessam quatro estados principais

Tipo: definicoes e diagrama.

- Novo/transiente: criado com `new`, ainda sem contexto.
- Gerenciado: acompanhado pelo EntityManager.
- Destacado/detached: existe, mas nao e mais acompanhado.
- Removido: agendado para DELETE.

### Slide 3 - ID nulo identifica um Livro ainda novo

Tipo: codigo fonte.

- Mostrar `Long id` e `@GeneratedValue`.
- Explicar wrapper `Long` em vez de `long`.
- Mostrar construtor de negocio sem ID.
- Mostrar ausencia de `setId`.

### Slide 4 - persist leva o objeto ao estado gerenciado

Tipo: codigo fonte.

- Mostrar criacao com `new Livro(...)`.
- Mostrar `entityManager.contains` antes e depois.
- Mostrar `transacao.begin()` e `persist`.
- Mostrar ID disponivel apos o INSERT.

### Slide 5 - Escritas JPA exigem uma transacao ativa

Tipo: codigo fonte.

- Mostrar `EntityTransaction`.
- Sequencia begin -> operacao -> commit.
- Catch com `rollback` quando ainda ativa.
- Explicar por que leitura simples e escrita tem tratamentos diferentes.

### Slide 6 - clear destaca objetos do contexto

Tipo: codigo fonte.

- Mostrar `entityManager.clear()`.
- Mostrar `contains(livro)` retornando falso.
- Alteracoes futuras no objeto destacado nao sao sincronizadas.
- `find` devolve outra instancia gerenciada.

### Slide 7 - Dirty checking produz UPDATE sem metodo update

Tipo: codigo fonte.

- Mostrar `encontrado.atualizar(...)`.
- O Hibernate compara estado inicial e estado atual.
- O UPDATE aparece no commit.
- Nao existe `entityManager.update()` em JPA.

### Slide 8 - remove agenda a exclusao

Tipo: codigo fonte.

- Mostrar transacao e `entityManager.remove(encontrado)`.
- A entidade precisa estar gerenciada.
- DELETE ocorre na sincronizacao/commit.
- `find` posterior retorna `null`.

### Slide 9 - O SQL revela cada transicao de estado

Tipo: execucao.

- Mostrar INSERT e `last_insert_rowid()`.
- Mostrar SELECT depois de `clear`.
- Mostrar UPDATE produzido pelo dirty checking.
- Mostrar DELETE e busca final.

### Slide 10 - O contexto de persistencia e a ideia central

Tipo: pratica e sintese.

- Pergunta: por que alterar o objeto destacado nao atualiza o banco?
- Experimento: remover o commit da atualizacao.
- Pedir que o aluno classifique o estado do objeto em cada linha.
- Transicao: a proxima parada conecta entidades entre si.

---

## Parada 08: Relacionamentos JPA

### Slide 1 - Chaves estrangeiras viram referencias entre objetos

Tipo: abertura.

- Objetivo: navegar entre `Autor` e `Livro`.
- Arquivos: `Autor`, `Livro` e `Aplicacao`.
- Resultado: navegacao nos dois sentidos sem JPQL.

### Slide 2 - O banco guarda IDs; o modelo Java guarda objetos

Tipo: problema e comparacao.

- Antes: `Long autorId`.
- Depois: `Autor autor`.
- A coluna `autor_id` continua existindo no SQLite.
- O JPA converte referencia de objeto em chave estrangeira.

### Slide 3 - Cardinalidade descreve quantos objetos se relacionam

Tipo: definicoes e diagrama.

- Um autor possui muitos livros.
- Muitos livros pertencem a um autor.
- `@OneToMany` representa colecao.
- `@ManyToOne` representa referencia unica.

### Slide 4 - Livro controla a coluna autor_id

Tipo: codigo fonte.

- Mostrar `@ManyToOne(fetch=LAZY, optional=false)`.
- Mostrar `@JoinColumn(name="autor_id")`.
- Explicar lado dono da relacao.
- `optional=false` corresponde ao NOT NULL.

### Slide 5 - Autor declara o lado inverso com mappedBy

Tipo: codigo fonte.

- Mostrar `List<Livro> livros`.
- Mostrar `@OneToMany(mappedBy="autor")`.
- `mappedBy` referencia o nome do atributo Java, nao a coluna.
- Nenhuma tabela intermediaria e criada.

### Slide 6 - Os dois lados descrevem a mesma relacao

Tipo: modelo mental.

- Fluxo visual `Autor.livros <-> Livro.autor`.
- `Livro.autor` controla a chave estrangeira.
- `Autor.livros` permite navegacao inversa.
- Evitar confundir lado dono com cardinalidade.

### Slide 7 - LAZY adia a consulta ate o primeiro uso

Tipo: definicoes.

- Proxy ou colecao representa dados ainda nao carregados.
- `PersistenceUtil.isLoaded` inspeciona o estado.
- O primeiro acesso pode disparar SELECT adicional.
- EntityManager precisa permanecer aberto.

### Slide 8 - A aplicacao torna o carregamento lazy visivel

Tipo: codigo fonte.

- Mostrar `find(Autor.class, 1L)`.
- Mostrar `isLoaded` antes da colecao.
- Mostrar o `for` sobre `autor.getLivros()`.
- Mostrar `isLoaded` depois do acesso.

### Slide 9 - A navegacao inversa tambem pode carregar sob demanda

Tipo: codigo fonte e execucao.

- Mostrar `find(Livro.class, 3L)`.
- Mostrar `livro.getAutor().getNome()`.
- Relacionar cada acesso aos SELECTs do console.
- Contar quantos comandos foram executados.

### Slide 10 - Relacionamentos exigem cuidado fora do contexto

Tipo: pratica e sintese.

- Evitar recursao `Autor -> Livro -> Autor` em `toString`.
- Fechar EntityManager antes do lazy pode causar falha.
- Experimento: mover o acesso a colecao para depois do try.
- Transicao: venda combina relacionamentos, cascade e transacao.

---

## Parada 09: Transacoes JPA

### Slide 1 - JPA coordena venda, item e estoque como uma unidade

Tipo: abertura.

- Objetivo: registrar uma venda atomica com entidades relacionadas.
- Arquivos: `VendaService`, `Venda`, `ItemVenda`, `Livro` e converter.
- Resultado: commit e rollback observaveis no SQL.

### Slide 2 - A venda e a raiz de um pequeno agregado

Tipo: definicoes e modelo mental.

- Venda controla seus itens.
- Item referencia venda e livro.
- Livro fornece preco e estoque.
- O service controla a unidade de trabalho.

### Slide 3 - O grafo de objetos corresponde as chaves estrangeiras

Tipo: diagrama.

- `Venda 1 -> N ItemVenda`.
- `ItemVenda N -> 1 Livro`.
- `Livro N -> 1 Autor`.
- Mostrar `venda_id`, `livro_id` e `autor_id` no esquema.

### Slide 4 - Cascade persiste os itens a partir da venda

Tipo: codigo fonte.

- Mostrar `@OneToMany(mappedBy="venda", cascade=ALL)`.
- Mostrar `Venda.adicionarItem` mantendo os dois lados.
- Mostrar `ItemVenda.venda` como lado dono.
- Explicar por que apenas `Venda` recebe `persist`.

### Slide 5 - ItemVenda preserva o preco historico

Tipo: modelagem e codigo fonte.

- `precoUnitario` copia o preco no momento da venda.
- Alterar `Livro.preco` no futuro nao altera vendas passadas.
- Quantidade e preco calculam o total.
- `BigDecimal` preserva precisao monetaria.

### Slide 6 - O converter adapta LocalDateTime a coluna TEXT

Tipo: codigo fonte.

- Mostrar `@Convert` em `Venda.dataHora`.
- Mostrar `AttributeConverter<LocalDateTime, String>`.
- Escrita em formato ISO-8601.
- Leitura com `LocalDateTime.parse`.

### Slide 7 - VendaService abre uma unica unidade de trabalho

Tipo: codigo fonte.

- Mostrar criacao de EntityManager e EntityTransaction.
- Mostrar `begin()`.
- Buscar livro com `find`.
- Validar existencia, quantidade e estoque.

### Slide 8 - Livro gerenciado gera UPDATE por dirty checking

Tipo: codigo fonte.

- Mostrar `livro.retirarDoEstoque`.
- A regra impede quantidade invalida e estoque negativo.
- O objeto veio de `find` e ja esta gerenciado.
- Nenhum `persist` ou `merge` e chamado para Livro.

### Slide 9 - persist percorre o cascade e prepara o commit

Tipo: codigo fonte.

- Criar `Venda` e adicionar `ItemVenda`.
- Mostrar `entityManager.persist(venda)`.
- INSERT de venda precede INSERT de item por causa do ID.
- Commit confirma INSERTs e UPDATE.

### Slide 10 - flush envia SQL; commit confirma a transacao

Tipo: definicoes e demonstracao.

- `flush`: sincroniza memoria e banco sem confirmar.
- `commit`: torna a transacao definitiva.
- Falha controlada ocorre depois de `flush`.
- `rollback` desfaz comandos que ja chegaram ao SQLite.

### Slide 11 - O estoque comprova commit e rollback

Tipo: execucao, pratica e fechamento.

- Mostrar estoque inicial, venda confirmada e estoque 10.
- Mostrar falha simulada depois do flush.
- Mostrar estoque ainda 10 depois do rollback.
- Experimento: remover o flush e comparar o SQL exibido.

---

## Proximo passo depois da revisao

Quando este roteiro estiver aprovado:

1. Criar `ROTEIRO_SLIDES.md` dentro de cada pasta `parada-*`.
2. Acrescentar a cada slide o trecho exato de codigo e as notas do professor.
3. Definir um tema visual comum para JDBC e outro tratamento de destaque para JPA.
4. Gerar um `.pptx` por parada.
5. Renderizar e revisar todos os slides em desktop antes da entrega.
