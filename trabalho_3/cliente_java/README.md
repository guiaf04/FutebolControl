# Futebol Control - Cliente Java Refatorado

Este projeto foi refatorado para seguir melhores práticas de desenvolvimento Java, com separação clara de responsabilidades e arquitetura organizada.

## Estrutura do Projeto

```
cliente_java/
├── FutebolClient.java          # Arquivo original (mantido para referência)
├── FutebolClient.class         # Classe compilada original
├── src/                        # Código fonte refatorado
│   ├── models/                 # Classes de modelo
│   │   ├── Clube.java          # Modelo de Clube
│   │   ├── Campeonato.java     # Modelo de Campeonato
│   │   └── Partida.java        # Modelo de Partida
│   ├── services/               # Serviços
│   │   └── ApiService.java     # Serviço de comunicação com API
│   ├── utils/                  # Utilitários
│   │   └── JsonUtils.java      # Utilitários para JSON
│   └── ui/                     # Interface gráfica
│       └── FutebolClientUI.java # Interface principal refatorada
```

## Classes Criadas

### 📦 **Models (Modelos de Dados)**

#### **Clube.java**
- Representa um clube de futebol
- **Atributos**: nome, sigla, vitórias, empates, derrotas, gols pró, gols contra
- **Métodos Úteis**:
  - `getTotalJogos()` - Calcula total de jogos
  - `getPontos()` - Calcula pontos (3 por vitória, 1 por empate)
  - `getSaldoGols()` - Calcula saldo de gols
  - `toJson()` / `fromJson()` - Conversão para/de JSON

#### **Campeonato.java**
- Representa um campeonato
- **Atributos**: nome, ano, lista de clubes participantes
- **Métodos Úteis**:
  - `adicionarClube()` / `removerClube()` - Gestão de participantes
  - `temClube()` - Verifica se clube participa
  - `getNumeroParticipantes()` - Conta participantes
  - `toJson()` / `fromJson()` - Conversão para/de JSON

#### **Partida.java**
- Representa uma partida de futebol
- **Atributos**: clubes, gols, campeonato
- **Métodos Úteis**:
  - `getResultado()` - Determina resultado (vitória/empate)
  - `getVencedor()` / `getPerdedor()` - Identifica vencedor/perdedor
  - `getTotalGols()` - Soma total de gols
  - `toJson()` / `fromJson()` - Conversão para/de JSON

### 🔧 **Services (Serviços)**

#### **ApiService.java**
- Centraliza toda comunicação com a API REST
- **Funcionalidades**:
  - Métodos HTTP (GET, POST, PUT, DELETE)
  - Tratamento de erros padronizado
  - Conversão automática entre objetos Java e JSON
  - Classe interna `ApiResponse<T>` para respostas tipadas
- **Métodos por Entidade**:
  - Clubes: criar, listar, atualizar, deletar, obter estatísticas, histórico
  - Campeonatos: criar, listar, atualizar, deletar, adicionar clubes, listar participantes
  - Partidas: registrar

### 🛠️ **Utils (Utilitários)**

#### **JsonUtils.java**
- Utilitários para parsing básico de JSON
- **Nota**: Implementação simplificada para evitar dependências externas
- **Métodos**:
  - `extractStringValue()` - Extrai valores string
  - `extractIntValue()` - Extrai valores inteiros
  - `extractStringArray()` - Extrai arrays de strings
  - `extractJsonObjects()` - Extrai objetos de arrays JSON

### 🖥️ **UI (Interface Gráfica)**

#### **FutebolClientUI.java**
- Interface gráfica principal refatorada
- **Melhorias**:
  - Código organizado em métodos específicos
  - Validação aprimorada de entrada
  - Confirmações para operações de exclusão
  - Exibição formatada de resultados
  - Limpeza automática de campos após sucesso
  - Tratamento de erros com diálogos informativos

## Principais Melhorias

### 🏗️ **Arquitetura**
- **Separação de Responsabilidades**: Models, Services, Utils, UI
- **Baixo Acoplamento**: Cada classe tem uma responsabilidade específica
- **Reutilização**: Classes podem ser reutilizadas em outros contextos

### 🔒 **Robustez**
- **Validação de Entrada**: Verificação de campos obrigatórios
- **Tratamento de Erros**: Mensagens claras e específicas
- **Confirmações**: Diálogos de confirmação para operações críticas

### 💡 **Usabilidade**
- **Interface Melhorada**: Melhor organização visual
- **Feedback Claro**: Respostas formatadas e informativas
- **Limpeza Automática**: Campos limpos após operações bem-sucedidas

### 📊 **Funcionalidades Extras**
- **Cálculos Automáticos**: Pontos, saldo de gols, estatísticas
- **Resumos Detalhados**: Informações formatadas sobre partidas e clubes
- **Histórico Completo**: Visualização organizada do histórico de partidas

## Como Compilar e Executar

### Compilação
```bash
# Navegar para o diretório do projeto
cd cliente_java

# Compilar todas as classes
javac -d . src/**/*.java

# Ou compilar arquivo por arquivo
javac -cp . src/models/*.java
javac -cp . src/utils/*.java
javac -cp . src/services/*.java
javac -cp . src/ui/*.java
```

### Execução
```bash
# Executar a aplicação refatorada
java ui.FutebolClientUI

# Ou executar o arquivo original (para comparação)
java FutebolClient
```

## Dependências
- **Java 8+** (para lambdas e features modernas)
- **Swing** (incluído no JDK)
- **Nenhuma dependência externa** (JSON parsing implementado manualmente)

## Compatibilidade
- ✅ Mantém 100% de compatibilidade com a API existente
- ✅ Todas as funcionalidades do cliente original preservadas
- ✅ Interface similar para facilitar transição
- ✅ Funciona com o mesmo servidor Python/FastAPI
