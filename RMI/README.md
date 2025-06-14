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
* **Gson (Biblioteca de JSON do Google)**
* **Apache Maven** (Gerenciador de Dependências e Build)

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

**Pré-requisitos:** É necessário ter o **Java JDK** e o **Apache Maven** instalados e configurados no seu sistema.

**1. Compilar e Empacotar o Projeto:**
Abra um terminal na raiz do projeto (onde o arquivo `pom.xml` está localizado) e execute o comando do Maven para compilar o código e baixar as dependências (como o Gson).
```bash
mvn package
```
Este comando criará um diretório `target` com os arquivos compilados.

---
**2. Iniciar o Servidor:**
No mesmo terminal, execute o comando abaixo para iniciar o servidor RMI.
```bash
mvn exec:java -Dexec.mainClass="org.example.server.Server"
```
Você verá a mensagem: `Servidor RMI está pronto.` Deixe este terminal aberto.

---
**3. Iniciar o Cliente:**
Abra um **novo terminal**, navegue até a raiz do projeto e execute o comando para iniciar o cliente:
```bash
mvn exec:java -Dexec.mainClass="org.example.client.Client"
```
O cliente irá se conectar ao servidor e um menu interativo será exibido para interação.

## Atendimento aos Requisitos do Trabalho

A seguir, é detalhado como o projeto cumpre cada requisito especificado no documento `Trabalho_2_RMI.pdf`.

### 1. Comunicação via RMI e Protocolo Requisição-Resposta

* **Implementação RMI**: A comunicação é inteiramente baseada em Java RMI, conforme solicitado. O projeto não utiliza sockets diretamente, abstraindo a comunicação de baixo nível através das classes `UnicastRemoteObject`, `LocateRegistry` e o serviço de nomes do RMI.

* **Protocolo Requisição-Resposta**: O trabalho descreve um protocolo com métodos como `doOperation`, `getRequest`, `sendReply` e um formato de mensagem específico. Este requisito é atendido de forma **abstrata** pelo RMI:
  * O **Stub** do RMI no cliente realiza o papel do `doOperation`, empacotando a chamada do método e seus argumentos.
  * O **RMI Runtime** no servidor atua como `getRequest` (ouvindo por requisições) e `sendReply` (enviando a resposta).
  * O mecanismo de serialização (seja nativo do Java ou via JSON) empacota os dados "como mostra a figura", convertendo objetos em um formato de bytes para transmissão. A proibição do uso de sockets  reforça que essa abordagem de alto nível é a correta.

### 2. Requisitos Adicionais da Aplicação

* **Mínimo de 4 classes de entidade**: O projeto utiliza `Clube.java`, `EstatisticasClube.java`, `Partida.java`, `SerieA.java` e `Libertadores.java`, superando o requisito.
* **Mínimo de 2 composições (agregação "tem-um")**: Cumprido. A classe `Clube` possui uma lista de `EstatisticasClube` e as classes `SerieA` e `Libertadores` possuem um mapa de `EstatisticasClube`.
* **Mínimo de 2 composições (extensão "é-um")**: Cumprido. As classes `SerieA` e `Libertadores` implementam a interface `Campeonato`.
* **Mínimo de 4 métodos para invocação remota**: A interface `CampeonatoManager` define 5 métodos remotos, atendendo ao requisito.

### 3. Passagem de Parâmetros e Dados

* **Passagem por Referência para Objetos Remotos**: Cumprido. Este é o comportamento padrão do RMI para objetos remotos. O objeto `CampeonatoManager` existe apenas no servidor. O cliente recebe um **Stub**, que é uma referência remota. Todas as chamadas de método no Stub são executadas no objeto real no servidor.
* **Passagem por Valor para Objetos Locais**: Cumprido. Objetos que não são remotos (como `Clube` e `Partida`) são passados por valor.
* **Representação Externa de Dados**: O requisito é atendido através do **JSON**. Quando um objeto é passado por valor, ele é convertido em uma string JSON pela biblioteca Gson. Ao receber os dados, o receptor converte a string JSON de volta para um objeto Java, permitindo a manipulação dos dados de forma estruturada e atendendo à sugestão de usar um formato como JSON, XML ou Protocol Buffers.