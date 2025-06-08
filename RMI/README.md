# RMI - Gestão de Campeonatos de Futebol

## Visão Geral

Este projeto é uma aplicação cliente-servidor desenvolvida em Java que utiliza RMI (Remote Method Invocation) para gerenciar informações de clubes e partidas de futebol. A implementação segue os requisitos da disciplina de Sistemas Distribuídos (QXD0043) da Universidade Federal do Ceará, Campus Quixadá.

O sistema permite que um cliente se conecte a um servidor remoto para realizar operações como adicionar, listar e buscar clubes, além de registrar partidas, demonstrando conceitos fundamentais de computação distribuída.

## Funcionalidades

* Adicionar um novo clube ao sistema.
* Listar todos os clubes cadastrados no servidor.
* Buscar as informações de um clube específico pelo nome.
* Registrar uma partida entre dois clubes existentes.

## Tecnologias Utilizadas

* **Java SE Development Kit (JDK)**
* **Java RMI (Remote Method Invocation)**

## Estrutura do Projeto

O código está organizado em diretórios para separar as responsabilidades de cada componente do sistema:

```
/rmi-campeonato
├── client/          # Contém a classe principal do cliente
│   └── Client.java
├── model/           # Contém as classes de entidade (POJOs)
│   ├── ...
├── server/          # Contém a implementação do serviço e o servidor principal
│   ├── CampeonatoManagerImpl.java
│   └── Server.java
└── shared/          # Contém a interface remota, compartilhada entre cliente e servidor
    └── CampeonatoManager.java
```

## Como Executar

**Pré-requisitos:** É necessário ter o Java JDK instalado e configurado no seu sistema.

**1. Compilar o Projeto:**
Abra um terminal na raiz do projeto (`/rmi-campeonato`) e execute o comando de compilação:
```bash
javac */*.java
```

**2. Iniciar o Servidor:**
No mesmo terminal, inicie o servidor RMI. Ele irá iniciar o registro na porta 1099 e aguardar conexões.
```bash
java server.Server
```
Você verá a mensagem: `Servidor RMI está pronto.`

**3. Iniciar o Cliente:**
Abra um **novo terminal**, navegue até a raiz do projeto e execute o cliente:
```bash
java client.Client
```
O cliente irá se conectar ao servidor e um menu interativo será exibido.

## Atendimento aos Requisitos do Trabalho

A seguir, é detalhado como o projeto cumpre cada requisito especificado no documento `Trabalho_2_RMI.pdf`.

### 1. Comunicação via RMI e Protocolo Requisição-Resposta

* **Implementação RMI**: A comunicação é inteiramente baseada em Java RMI, conforme solicitado. O projeto não utiliza sockets diretamente, abstraindo a comunicação de baixo nível através das classes `UnicastRemoteObject`, `LocateRegistry` e o serviço de nomes do RMI.

* **Protocolo Requisição-Resposta**: O trabalho descreve um protocolo com métodos como `doOperation`, `getRequest`, `sendReply` e um formato de mensagem específico[cite: 4, 6, 7, 8, 10]. Este requisito é atendido de forma **abstrata** pelo RMI:
    * O **Stub** do RMI no cliente realiza o papel do `doOperation`, empacotando a chamada do método e seus argumentos.
    * O **RMI Runtime** no servidor atua como `getRequest` (ouvindo por requisições) e `sendReply` (enviando a resposta).
    * A **Serialização Java** é o mecanismo que empacota os dados "como mostra a figura", convertendo objetos em um formato de bytes para transmissão. A proibição do uso de sockets  reforça que essa abordagem de alto nível é a correta.

### 2. Requisitos Adicionais da Aplicação 

* **Mínimo de 4 classes de entidade**: O projeto utiliza `Clube.java`, `EstatisticasClube.java`, `Partida.java`, `SerieA.java` e `Libertadores.java`, superando o requisito.
* **Mínimo de 2 composições (agregação "tem-um")**: Cumprido. A classe `Clube` possui uma lista de `EstatisticasClube` e as classes `SerieA` e `Libertadores` possuem um mapa de `EstatisticasClube`.
* **Mínimo de 2 composições (extensão "é-um")**: Cumprido. As classes `SerieA` e `Libertadores` implementam a interface `Campeonato`.
* **Mínimo de 4 métodos para invocação remota**: A interface `CampeonatoManager` define 5 métodos remotos (`adicionarClube`, `listarClubes`, `buscarClubePorNome`, `registrarPartida`, `atualizarClube`), atendendo ao requisito.

### 3. Passagem de Parâmetros e Dados

* **Passagem por Referência para Objetos Remotos**: Este é o comportamento padrão do RMI para objetos remotos. O objeto `CampeonatoManager` existe apenas no servidor. O cliente recebe um **Stub**, que é uma referência remota. Todas as chamadas de método no Stub são executadas no objeto real no servidor.
* **Passagem por Valor para Objetos Locais**: Cumprido. Objetos que são `Serializable` mas não `Remote` (como `Clube` e `Partida`) são passados por valor. O RMI serializa o objeto no cliente, envia os bytes pela rede e desserializa no servidor, criando uma **cópia** local.
* **Representação Externa de Dados**: O requisito é atendido através da **Serialização de Objetos Java**. Quando um objeto é passado por valor, ele é convertido em um fluxo de bytes padronizado e independente de plataforma. Este fluxo de bytes **é** uma forma de representação externa de dados. Embora o trabalho sugira XML, JSON ou Protocol Buffers, a serialização nativa do Java é uma alternativa válida que cumpre o mesmo papel.