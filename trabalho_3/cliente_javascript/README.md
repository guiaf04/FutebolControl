# Futebol Control - Cliente JavaScript

Este projeto foi refatorado para separar o código JavaScript do HTML e criar uma arquitetura mais organizada com classes para representar os dados.

## Estrutura do Projeto

```
cliente_javascript/
├── index_novo.html          # Arquivo HTML principal (refatorado)
├── index.html              # Arquivo HTML original (mantido para referência)
├── styles.css              # Estilos CSS separados
├── js/                     # Diretório JavaScript
│   ├── app.js              # Aplicação principal
│   ├── models/             # Classes de modelo
│   │   ├── Clube.js        # Classe Clube
│   │   ├── Campeonato.js   # Classe Campeonato
│   │   └── Partida.js      # Classe Partida
│   └── services/           # Serviços
│       └── ApiService.js   # Serviço de comunicação com API
```

## Classes Criadas

### Clube
- Representa um clube de futebol
- Métodos para calcular pontos, saldo de gols, total de jogos
- Métodos `fromJSON()` e `toJSON()` para conversão de/para API

### Campeonato
- Representa um campeonato
- Métodos para gerenciar clubes participantes
- Métodos `fromJSON()` e `toJSON()` para conversão de/para API

### Partida
- Representa uma partida de futebol
- Métodos para determinar resultado, vencedor, perdedor
- Métodos `fromJSON()` e `toJSON()` para conversão de/para API

### ApiService
- Centraliza toda comunicação com a API
- Métodos para todas as operações CRUD
- Tratamento de erros padronizado
- Conversão automática entre objetos JavaScript e JSON

## Melhorias Implementadas

1. **Separação de Responsabilidades**: HTML, CSS e JavaScript em arquivos separados
2. **Classes de Modelo**: Representação tipada dos dados da API
3. **Serviço de API**: Centralização da comunicação com o servidor
4. **Tratamento de Erros**: Melhor feedback para o usuário
5. **Validação de Dados**: Verificação de campos obrigatórios
6. **Funcionalidades Extras**: Cálculos automáticos (pontos, saldo de gols, etc.)
7. **Limpeza de Formulários**: Campos são limpos após operações bem-sucedidas

## Como Usar

1. Use o arquivo `index_novo.html` ao invés do `index.html` original
2. Certifique-se de que o servidor API esteja rodando em `http://localhost:8000`
3. Todas as funcionalidades do cliente original estão preservadas e melhoradas

## Funcionalidades

- ✅ Criar, listar, atualizar e deletar clubes
- ✅ Ver estatísticas detalhadas dos clubes
- ✅ Criar, listar, atualizar e deletar campeonatos
- ✅ Adicionar clubes aos campeonatos
- ✅ Registrar partidas com atualização automática de estatísticas
- ✅ Ver histórico de partidas dos clubes
- ✅ Interface limpa e responsiva
