# FutebolControl – Trabalho 3

**Gerenciamento de clubes de futebol com servidor em Python e dois clientes: Java e JavaScript.**

---

## 🎥 Demonstração

Assista à demonstração do projeto no YouTube: [📺 Gerenciador de Clubes](https://youtu.be/jPSNxorAY6U)

---

## 📁 Estrutura do Projeto

O projeto está organizado em três principais componentes:

- **servidor_api/**: Servidor central em Python, responsável pelo gerenciamento dos dados e comunicação com os clientes.
- **cliente_java/**: Cliente em Java, com interface de terminal para interação com o servidor.
- **cliente_javascript/**: Cliente Web, desenvolvido em HTML, CSS e JavaScript, para acesso via navegador.

Além disso, há uma pasta de documentação com diagramas e especificações.

```
trabalho_3/
├── servidor_api/
│   ├── main.py                # Código principal do servidor Python
│   ├── requirements.txt       # Dependências do servidor
│   └── models/                # Modelos de dados (campeonato, clube, partida)
├── cliente_java/
│   ├── src/                   # Código-fonte Java
│   ├── pom.xml                # Gerenciador de dependências Maven
│   └── README.md              # Instruções do cliente Java
├── cliente_javascript/
│   ├── index.html             # Página principal
│   ├── styles.css             # Estilos da interface
│   ├── js/                    # Scripts JS e modelos
│   └── README.md              # Instruções do cliente JS
├── documentação/              # Diagramas UML e materiais extras
└── README.md                  # Este arquivo
```

---

## ⚙️ Funcionamento Geral

O sistema segue o padrão cliente-servidor:

- O **servidor** centraliza e gerencia os dados dos clubes, campeonatos e partidas.
- Os **clientes** (Java e JavaScript) enviam comandos ao servidor, recebem respostas e exibem os dados ao usuário.
- A comunicação é feita via API HTTP (usando JSON) ou sockets, dependendo da implementação.

---

## 🧠 Servidor (Python)

- Implementado em Python, localizado em `servidor_api/`.
- Utiliza Flask para expor uma API REST.
- Gerencia dados de clubes, campeonatos e partidas em memória ou arquivos locais.
- Recebe requisições dos clientes para listar, cadastrar, editar e remover dados.
- Retorna respostas em formato JSON.
- Estrutura dos modelos em `servidor_api/models/`.

### Principais arquivos:
- `main.py`: Inicializa o servidor e define as rotas da API.
- `models/`: Contém as classes de dados (campeonato, clube, partida).
- `requirements.txt`: Lista as dependências necessárias (Flask, etc).

---

## 💻 Cliente Java

- Localizado em `cliente_java/`.
- Aplicação de terminal (console) escrita em Java.
- Utiliza Maven para gerenciamento de dependências.
- Envia comandos para o servidor via HTTP (usando bibliotecas como HttpClient) ou TCP.
- Exibe menus interativos para o usuário:
    - Listar clubes/campeonatos
    - Cadastrar novo clube/campeonato
    - Editar/remover dados
- Facilita a interação do usuário com o sistema.

### Principais arquivos:
- `src/`: Código-fonte Java organizado em pacotes (modelos, serviços, UI).
- `pom.xml`: Gerenciador de dependências Maven.

---

## 🌐 Cliente JavaScript (Web)

- Localizado em `cliente_javascript/`.
- Interface web simples feita com HTML, CSS e JavaScript.
- Utiliza `fetch` para se comunicar com o servidor Python via API HTTP.
- Permite ao usuário visualizar e modificar dados dos clubes e campeonatos.
- Apresenta os dados em tabelas, formulários ou cartões.

### Principais arquivos:
- `index.html`: Página principal da interface.
- `styles.css`: Estilos visuais.
- `js/`: Scripts JavaScript, modelos e serviços para comunicação com a API.

---

## 📥 Como Baixar e Executar o Projeto

### 1. Clonar o Repositório

```bash
git clone https://github.com/guiaf04/FutebolControl.git
cd FutebolControl/trabalho_3
```

### 2. Executar o Servidor (Python)

```bash
cd servidor_api
python3 -m venv venv
source venv/bin/activate  # Windows: venv\Scripts\activate
pip install -r requirements.txt
uvicorn main:app --reload --port:8000
```
O servidor será iniciado e escutará conexões, geralmente em `localhost:5000`.

### 3. Executar o Cliente Java

```bash
cd ../cliente_java
mvn install        # ou: gradle build
mvn exec:java      # ou: java -jar target/ClienteJava.jar
```
Você verá um menu interativo no terminal para interagir com o servidor.

### 4. Executar o Cliente Web (JavaScript)

#### Opção 1: Sem Node.js

Abra o arquivo `cliente_javascript/index.html` diretamente no navegador.

#### Opção 2: Com Node.js

```bash
cd ../cliente_javascript
npm install
npm run start   # ou npm run dev
```
Acesse via: [http://localhost:3000](http://localhost:3000)

---

## 📂 Organização dos Arquivos

| Caminho                        | Descrição                                 |
|-------------------------------|-------------------------------------------|
| servidor_api/main.py           | Lógica principal do servidor Python       |
| servidor_api/requirements.txt  | Dependências do servidor                  |
| servidor_api/models/           | Modelos de dados do sistema               |
| cliente_java/src/              | Código-fonte da aplicação Java            |
| cliente_javascript/            | Arquivos HTML, CSS e JS para cliente Web  |
| documentação/                  | Diagramas UML e materiais complementares  |

---

## 🔁 Fluxo de Comunicação

1. Cliente Java/JS envia comando para o servidor Python.
2. Servidor interpreta, executa e responde com os dados.
3. Cliente exibe o resultado ao usuário.
4. Operações podem ser repetidas indefinidamente.

---

## 💡 Observações e Possíveis Extensões

- Valide os dados nos clientes antes de enviá-los ao servidor.
- O projeto pode ser estendido com:
    - Banco de dados real (SQLite, PostgreSQL, etc).
    - Autenticação de usuários.
    - Interface Web mais moderna (React, Vue, etc).
