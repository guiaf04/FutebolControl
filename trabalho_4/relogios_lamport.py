#!/usr/bin/env python3
"""
Parte 2 - Relógio Lógico de Lamport
Demonstra como o algoritmo de Lamport estabelece uma ordem causal entre eventos.
"""

import threading
import time
import random
import queue
from datetime import datetime

class ProcessoLamport:
    def __init__(self, id_processo, num_processos):
        self.id = id_processo
        self.num_processos = num_processos
        self.filas_entrada = [queue.Queue() for _ in range(num_processos)]
        self.eventos = []
        self.relogio_lamport = 0
        self.running = True
        
    def log_evento(self, tipo, detalhes=""):
        """Loga um evento com timestamp lógico de Lamport e físico"""
        timestamp_fisico = int(time.time() * 1000)
        evento = {
            'processo': self.id,
            'relogio_lamport': self.relogio_lamport,
            'timestamp_fisico': timestamp_fisico,
            'tipo': tipo,
            'detalhes': detalhes,
            'hora_legivel': datetime.fromtimestamp(timestamp_fisico/1000).strftime('%H:%M:%S.%f')[:-3]
        }
        self.eventos.append(evento)
        print(f"P{self.id} [L={self.relogio_lamport}] [{evento['hora_legivel']}] {tipo}: {detalhes}")
        
    def evento_interno(self, descricao):
        """Simula um evento interno do processo, incrementando o relógio de Lamport"""
        self.relogio_lamport += 1
        self.log_evento("EVENTO_INTERNO", descricao)
    
    def enviar_mensagem(self, destino, conteudo):
        """Envia mensagem para outro processo, incluindo o relógio de Lamport"""
        self.relogio_lamport += 1 # Incrementa antes de enviar
        if destino != self.id and destino < self.num_processos:
            mensagem = {
                'origem': self.id,
                'relogio_lamport_envio': self.relogio_lamport,
                'conteudo': conteudo
            }
            
            # Simula latência de rede variável
            latencia = random.uniform(0.01, 0.1)
            time.sleep(latencia)
            
            self.filas_entrada[destino].put(mensagem)
            self.log_evento("ENVIO", f"para P{destino}: '{conteudo}' (L_envio={self.relogio_lamport}, latência: {latencia:.3f}s)")
    
    def processar_mensagens(self):
        """Processa mensagens recebidas, ajustando o relógio de Lamport"""
        try:
            while not self.filas_entrada[self.id].empty():
                mensagem = self.filas_entrada[self.id].get_nowait()
                
                # Regra de Lamport: L(p) = max(L(recebido), L(p)) + 1
                self.relogio_lamport = max(self.relogio_lamport, mensagem['relogio_lamport_envio']) + 1
                
                detalhes = f"de P{mensagem['origem']}: '{mensagem['conteudo']}' " + \
                          f"(L_recebido={mensagem['relogio_lamport_envio']})"
                
                self.log_evento("RECEBIMENTO", detalhes)
                
        except queue.Empty:
            pass
    
    def executar(self):
        """Loop principal do processo"""
        print(f"\n=== Processo P{self.id} iniciado (Lamport) ===")
        
        for i in range(5):  # Cada processo faz 5 iterações
            # Evento interno
            self.evento_interno(f"Operação local {i+1}")
            
            # Sleep variável para simular processamento
            sleep_time = random.uniform(0.05, 0.2)
            time.sleep(sleep_time)
            
            # Processa mensagens recebidas
            self.processar_mensagens()
            
            # Envia mensagem aleatória
            if random.random() > 0.3:  # 70% de chance de enviar mensagem
                destino = random.choice([p for p in range(self.num_processos) if p != self.id])
                conteudo = f"Msg_{i+1}_de_P{self.id}"
                self.enviar_mensagem(destino, conteudo)
            
            # Sleep adicional
            time.sleep(random.uniform(0.02, 0.08))
        
        # Processa mensagens restantes
        time.sleep(0.1)
        self.processar_mensagens()
        
        print(f"\n=== Processo P{self.id} finalizado (Lamport) ===")

def simular_relogios_lamport():
    """Executa a simulação com relógios de Lamport"""
    print("=" * 60)
    print("SIMULAÇÃO DE RELÓGIOS DE LAMPORT")
    print("=" * 60)
    print("Observação: A ordem causal dos eventos deve ser preservada.\n")
    
    num_processos = 3
    processos = []
    threads = []
    
    # Cria os processos
    for i in range(num_processos):
        processo = ProcessoLamport(i, num_processos)
        processos.append(processo)
    
    # Compartilha as filas entre os processos
    for i in range(num_processos):
        for j in range(num_processos):
            processos[i].filas_entrada[j] = processos[j].filas_entrada[j]
    
    # Inicia as threads
    for processo in processos:
        thread = threading.Thread(target=processo.executar)
        threads.append(thread)
        thread.start()
    
    # Aguarda todas as threads terminarem
    for thread in threads:
        thread.join()
    
    # Coleta todos os eventos
    todos_eventos = []
    for processo in processos:
        todos_eventos.extend(processo.eventos)
    
    # Ordena por timestamp lógico de Lamport, depois por timestamp físico para desempate
    todos_eventos.sort(key=lambda x: (x['relogio_lamport'], x['timestamp_fisico']))
    
    print("\n" + "=" * 60)
    print("LINHA DO TEMPO GLOBAL (ordenada por relógio de Lamport)")
    print("=" * 60)
    
    for evento in todos_eventos:
        print(f"P{evento['processo']} [L={evento['relogio_lamport']}] [{evento['hora_legivel']}] {evento['tipo']}: {evento['detalhes']}")
    
    print("\n" + "=" * 60)
    print("ANÁLISE DOS RESULTADOS")
    print("=" * 60)
    print("Com relógios de Lamport, a ordem causal é preservada:")
    print("1. Se evento A acontece antes de B, então L(A) < L(B).")
    print("2. No entanto, se L(A) < L(B), não significa necessariamente que A aconteceu antes de B (concorrência).")
    
    return todos_eventos

if __name__ == "__main__":
    eventos = simular_relogios_lamport()

