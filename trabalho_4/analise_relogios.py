#!/usr/bin/env python3
"""
Análise Comparativa de Relógios Distribuídos
Compara os resultados das simulações de relógios físicos, de Lamport e vetoriais.
"""

from relogios_fisicos import simular_relogios_fisicos
from relogios_lamport import simular_relogios_lamport
from relogios_vetoriais import simular_relogios_vetoriais

def main():
    print("\n" + "#" * 70)
    print("### INICIANDO ANÁLISE COMPARATIVA DE RELÓGIOS DISTRIBUÍDOS ###")
    print("#" * 70)

    # Executa a simulação de relógios físicos
    print("\n" + "-" * 60)
    print("EXECUTANDO SIMULAÇÃO DE RELÓGIOS FÍSICOS")
    print("-" * 60)
    eventos_fisicos = simular_relogios_fisicos()

    # Executa a simulação de relógios de Lamport
    print("\n" + "-" * 60)
    print("EXECUTANDO SIMULAÇÃO DE RELÓGIOS DE LAMPORT")
    print("-" * 60)
    eventos_lamport = simular_relogios_lamport()

    # Executa a simulação de relógios vetoriais
    print("\n" + "-" * 60)
    print("EXECUTANDO SIMULAÇÃO DE RELÓGIOS VETORIAIS")
    print("-" * 60)
    eventos_vetoriais = simular_relogios_vetoriais()

    print("\n" + "#" * 70)
    print("### RESUMO DA ANÁLISE ###")
    print("#" * 70)

    print("\n" + "=" * 60)
    print("RELÓGIOS FÍSICOS")
    print("=" * 60)
    print("Características:")
    print("- Baseados no tempo real (System.currentTimeMillis()).")
    print("- Simples de implementar.")
    print("Problemas:")
    print("- Não garantem ordem causal de eventos em sistemas distribuídos devido à falta de sincronização perfeita.")
    print("- Podem apresentar inconsistências na ordem dos eventos (e.g., mensagem recebida antes de ser enviada).")
    print("- Não conseguem determinar concorrência entre eventos.")

    print("\n" + "=" * 60)
    print("RELÓGIOS LÓGICOS DE LAMPORT")
    print("=" * 60)
    print("Características:")
    print("- Atribuem um valor escalar (inteiro) a cada evento.")
    print("- Garantem a propriedade 'aconteceu antes' (causalidade): se A -> B, então L(A) < L(B).")
    print("- Simples de implementar e entender.")
    print("Limitações:")
    print("- Não conseguem determinar concorrência: se L(A) < L(B), não implica que A -> B; A e B podem ser concorrentes.")
    print("- Não capturam a relação causal completa entre todos os eventos.")

    print("\n" + "=" * 60)
    print("RELÓGIOS VETORIAIS")
    print("=" * 60)
    print("Características:")
    print("- Cada processo mantém um vetor de contadores, um para cada processo no sistema.")
    print("- Capturam a relação 'aconteceu antes' de forma mais precisa que Lamport.")
    print("- Permitem determinar concorrência: se V(A) não é menor nem maior que V(B) (componente a componente), então A e B são concorrentes.")
    print("Vantagens:")
    print("- Preservam a causalidade e permitem identificar concorrência.")
    print("Limitações:")
    print("- O tamanho do vetor cresce com o número de processos (N), o que pode ser um problema em sistemas muito grandes.")
    print("- A comparação de vetores é mais complexa que a de escalares.")

    print("\n" + "=" * 60)
    print("CONCLUSÃO")
    print("=" * 60)
    print("A escolha do tipo de relógio depende dos requisitos do sistema distribuído:")
    print("- Relógios Físicos: Para sistemas onde a sincronização de tempo real é crítica e pode ser garantida (e.g., NTP/GPS), mas não para ordem causal em eventos distribuídos.")
    print("- Relógios de Lamport: Suficientes quando apenas a ordem causal parcial é necessária e a detecção de concorrência não é um requisito.")
    print("- Relógios Vetoriais: Essenciais quando a detecção de concorrência e a ordem causal total são cruciais, apesar do overhead de armazenamento e processamento.")

if __name__ == "__main__":
    main()

