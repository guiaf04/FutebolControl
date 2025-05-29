# Sistema de Controle de Clubes de Futebol - Documentação

## Descrição do Projeto
Este projeto implementa um Sistema de Controle de Clubes de Futebol em Java, seguindo os requisitos de comunicação entre processos conforme solicitado no trabalho. O sistema inclui classes para representar clubes, campeonatos, sócios torcedores e confederações, além de implementar comunicação via sockets, serialização e streams personalizados.

## Estrutura de Classes

### Superclasse
- **model.Clube**: Representa um clube de futebol com atributos básicos como nome, cidade, ano de fundação, estádio e número de títulos.

### Subclasses
- **model.SerieA**: Representa o campeonato da Série A, estendendo model.Clube e implementando a interface model.Partidas.
- **model.SerieB**: Representa o campeonato da Série B, estendendo model.Clube e implementando a interface model.Partidas.
- **model.SerieA.Libertadores**: Representa o campeonato da model.SerieA.Libertadores, estendendo model.Clube e implementando a interface model.Partidas.
- **model.SocioTorcedor**: Representa um sócio torcedor de um clube, estendendo model.Clube.

### Agregação
- **model.Partidas.Confederacao**: Representa uma confederação que agrega vários clubes.

### Interface
- **model.Partidas**: Define métodos para registrar partidas, resultados e obter informações.

### Streams Personalizados
- **ClubeOutputStream**: Estende OutputStream para enviar dados de clubes.
- **ClubeInputStream**: Estende InputStream para ler dados de clubes.

### Comunicação Cliente-Servidor
- **TCP.ServidorClube**: Implementa um servidor para o sistema de clubes.
- **TCP.ClienteClube**: Implementa um cliente para o sistema de clubes.

### Sistema de Votação
- **SistemaVotacao**: Implementa um sistema de votação para clubes, utilizando comunicação unicast (TCP) e multicast (UDP).

## Funcionalidades Implementadas

1. **Classes POJO**: Criação de classes que representam as informações do serviço escolhido.
2. **Streams Personalizados**: Implementação de subclasses de OutputStream e InputStream para enviar e receber dados de clubes.
3. **Serialização**: Implementação de comunicação cliente-servidor com serialização de objetos.
4. **Sistema de Votação**: Implementação de um sistema de votação com comunicação unicast e multicast.

## Como Executar

### Teste de Streams
1. Compile todas as classes: `javac *.java`
2. Execute a classe TesteStreams: `java TesteStreams`

### Servidor e Cliente
1. Inicie o servidor: `java TCP.ServidorClube`
2. Em outro terminal, inicie o cliente: `java TCP.ClienteClube`

### Sistema de Votação
1. Execute a classe SistemaVotacao: `java SistemaVotacao`

## Observações
- O sistema implementa todos os requisitos solicitados no trabalho.
- As classes estão documentadas com comentários JavaDoc.
- A comunicação entre processos é feita via sockets TCP e UDP.
- A serialização é utilizada para transmitir objetos entre cliente e servidor.
