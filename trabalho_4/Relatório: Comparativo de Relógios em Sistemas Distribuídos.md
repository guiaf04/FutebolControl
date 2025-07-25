# Relatório: Comparativo de Relógios em Sistemas Distribuídos

## Introdução
Este relatório apresenta a implementação e análise comparativa de três tipos de relógios em sistemas distribuídos: relógios físicos, relógios lógicos de Lamport e relógios vetoriais. O objetivo é demonstrar as diferenças fundamentais entre eles, suas aplicações, vantagens e limitações na garantia da ordem de eventos e detecção de concorrência.

## Metodologia
Foram desenvolvidos três scripts Python independentes, cada um simulando um tipo de relógio em um ambiente distribuído com três processos (threads). A comunicação entre os processos envolveu a troca de mensagens com latência variável para simular condições de rede realistas. Cada evento (interno, envio ou recebimento de mensagem) foi logado com informações relevantes para cada tipo de relógio.

### Relógios Físicos
- **Implementação:** Cada processo manteve seu próprio relógio físico (baseado em `time.time()` do Python). Eventos foram logados com o timestamp físico.
- **Objetivo:** Observar como a falta de sincronização perfeita entre relógios físicos pode levar a inconsistências na ordem percebida dos eventos.

### Relógios Lógicos de Lamport
- **Implementação:** Cada processo manteve um contador escalar (`relogio_lamport`). O contador foi incrementado a cada evento interno e antes do envio de mensagens. Ao receber uma mensagem, o relógio do processo foi atualizado para `max(relogio_local, relogio_mensagem) + 1`.
- **Objetivo:** Demonstrar a garantia da ordem causal (se A acontece antes de B, então L(A) < L(B)).

### Relógios Vetoriais
- **Implementação:** Cada processo manteve um vetor de contadores, onde cada componente representava o conhecimento do processo sobre o estado dos outros processos. Eventos internos incrementaram o componente local. Mensagens incluíram uma cópia do vetor do remetente. Na recepção, o vetor local foi atualizado com o máximo componente a componente, seguido de um incremento no componente local.
- **Objetivo:** Ilustrar a capacidade de determinar a ordem causal total e identificar eventos concorrentes.

## Resultados e Análise
As simulações foram executadas e os logs de eventos foram analisados para cada tipo de relógio. Abaixo, um resumo das observações:

### Relógios Físicos
- **Características:** Baseados no tempo real do sistema. Simples de implementar.
- **Problemas Observados:** A saída da simulação demonstrou claramente que, sem uma sincronização de tempo extremamente precisa (que é difícil de alcançar em sistemas distribuídos), a ordem dos eventos pode parecer incorreta. Mensagens podem ser registradas como recebidas antes de serem enviadas, e a ordem causal real não é garantida. Não há mecanismo para determinar concorrência.

### Relógios Lógicos de Lamport
- **Características:** Atribuem um valor escalar (inteiro) a cada evento. Garantem a propriedade 


'aconteceu antes' (causalidade): se um evento A acontece antes de um evento B, então o timestamp de Lamport de A será menor que o de B (L(A) < L(B)).
- **Observações:** A simulação de Lamport mostrou que a ordem causal é preservada. Por exemplo, uma mensagem enviada sempre terá um timestamp de Lamport menor ou igual ao timestamp de Lamport do evento de recebimento (após o ajuste `max(L_recebido, L_local) + 1`). No entanto, a recíproca não é verdadeira: se L(A) < L(B), não significa necessariamente que A causou B; A e B podem ser eventos concorrentes. Relógios de Lamport não fornecem informações sobre concorrência.

### Relógios Vetoriais
- **Características:** Cada processo mantém um vetor de contadores, com uma entrada para cada processo no sistema. Este vetor captura o conhecimento do processo sobre o progresso de todos os outros processos. Permitem determinar a ordem causal total e identificar concorrência.
- **Observações:** A simulação com relógios vetoriais demonstrou sua capacidade de capturar relações causais mais precisas. Se o vetor de um evento A é menor ou igual (componente a componente) ao vetor de um evento B, então A causou B. Mais importante, se os vetores de A e B não são comparáveis (nem A < B, nem B < A), então A e B são concorrentes. Isso é uma vantagem significativa sobre os relógios de Lamport, pois permite uma análise mais rica das relações entre eventos.

## Conclusão
A escolha do tipo de relógio em um sistema distribuído depende diretamente dos requisitos de consistência e ordenação de eventos:

- **Relógios Físicos:** São inadequados para garantir a ordem causal em sistemas distribuídos sem mecanismos de sincronização de tempo global extremamente robustos. Sua principal utilidade é para timestamps de eventos locais ou para depuração, mas não para inferir causalidade em um ambiente distribuído.

- **Relógios Lógicos de Lamport:** São uma solução eficaz e simples para garantir a ordem causal parcial. São ideais para cenários onde a detecção de concorrência não é um requisito, mas a preservação da ordem 'aconteceu antes' é crucial (e.g., ordenação de logs, replicação de dados simples).

- **Relógios Vetoriais:** Oferecem a capacidade mais completa para sistemas distribuídos, permitindo não apenas a preservação da ordem causal total, mas também a detecção de concorrência. Embora introduzam um overhead maior (o tamanho do vetor cresce com o número de processos), são indispensáveis em aplicações que exigem um entendimento preciso das relações entre eventos, como sistemas de controle de concorrência otimista, detecção de deadlocks distribuídos ou replicação de dados com forte consistência.

Em resumo, enquanto os relógios físicos fornecem uma visão do tempo real, os relógios lógicos (Lamport e vetoriais) fornecem uma visão do tempo lógico, que é fundamental para entender e gerenciar a causalidade e a concorrência em sistemas distribuídos. A complexidade e o overhead aumentam do relógio físico para o de Lamport e, finalmente, para o vetorial, mas também aumenta a riqueza das informações sobre a ordem dos eventos.

