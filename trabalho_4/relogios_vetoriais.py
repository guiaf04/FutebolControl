#!/usr/bin/env python3
"""
Parte 3 - Relógios Vetoriais
Demonstra como relógios vetoriais permitem determinar a concorrência entre eventos.
"""

import threading
import time
import random
import queue
from datetime import datetime

class ProcessoVetorial:
    def __init__(self, id_processo, num_processos):
        self.id = id_processo
        self.num_processos = num_processos
        self.filas_entrada = [queue.Queue() for _ in range(num_processos)]
        self.eventos = []
        self.relogio_vetorial = [0] * num_processos
        self.running = True
        
    def log_evento(self, tipo, detalhes=""):
        """Loga um evento com timestamp vetorial e físico"""
        timestamp_fisico = int(time.time() * 1000)
        evento = {
            'processo': self.id,
            'relogio_vetorial': list(self.relogio_vetorial), # Copia para evitar referência
            'timestamp_fisico': timestamp_fisico,
            'tipo': tipo,
            'detalhes': detalhes,
            'hora_legivel': datetime.fromtimestamp(timestamp_fisico/1000).strftime('%H:%M:%S.%f')[:-3]
        }
        self.eventos.append(evento)
        print(f"P{self.id} [V={self.relogio_vetorial}] [{evento['hora_legivel']}] {tipo}: {detalhes}")
        
    def evento_interno(self, descricao):
        """Simula um evento interno do processo, incrementando seu componente no relógio vetorial"""
        self.relogio_vetorial[self.id] += 1
        self.log_evento("EVENTO_INTERNO", descricao)
    
    def enviar_mensagem(self, destino, conteudo):
        """Envia mensagem para outro processo, incluindo o relógio vetorial"""
        self.relogio_vetorial[self.id] += 1 # Incrementa antes de enviar
        if destino != self.id and destino < self.num_processos:
            mensagem = {
                'origem': self.id,
                'relogio_vetorial_envio': list(self.relogio_vetorial), # Envia cópia
                'conteudo': conteudo
            }
            
            # Simula latência de rede variável
            latencia = random.uniform(0.01, 0.1)
            time.sleep(latencia)
            
            self.filas_entrada[destino].put(mensagem)
            self.log_evento("ENVIO", f"para P{destino}: '{conteudo}' (V_envio={self.relogio_vetorial}, latência: {latencia:.3f}s)")
    
    def processar_mensagens(self):
        """Processa mensagens recebidas, ajustando o relógio vetorial"""
        try:
            while not self.filas_entrada[self.id].empty():
                mensagem = self.filas_entrada[self.id].get_nowait()
                
                # Regra de Relógio Vetorial para recepção:
                # V[i][j] = max(V[i][j], Vmsg[j]) para todo j
                for j in range(self.num_processos):
                    self.relogio_vetorial[j] = max(self.relogio_vetorial[j], mensagem['relogio_vetorial_envio'][j])
                
                # Depois, V[i][i]++
                self.relogio_vetorial[self.id] += 1
                
                detalhes = f"de P{mensagem['origem']}: '{mensagem['conteudo']}' " + \
                          f"(V_recebido={mensagem['relogio_vetorial_envio']})"
                
                self.log_evento("RECEBIMENTO", detalhes)
                
        except queue.Empty:
            pass
    
    def executar(self):
        """Loop principal do processo"""
        print(f"\n=== Processo P{self.id} iniciado (Vetorial) ===")
        
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
        
        print(f"\n=== Processo P{self.id} finalizado (Vetorial) ===")

def simular_relogios_vetoriais():
    """Executa a simulação com relógios vetoriais"""
    print("=" * 60)
    print("SIMULAÇÃO DE RELÓGIOS VETORIAIS")
    print("=" * 60)
    print("Observação: Relógios vetoriais permitem determinar concorrência.\n")
    
    num_processos = 3
    processos = []
    threads = []
    
    # Cria os processos
    for i in range(num_processos):
        processo = ProcessoVetorial(i, num_processos)
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
    
    # Ordena por timestamp físico para visualização, mas a análise é pelo vetor
    todos_eventos.sort(key=lambda x: x['timestamp_fisico'])
    
    print("\n" + "=" * 60)
    print("LINHA DO TEMPO GLOBAL (ordenada por timestamp físico para visualização)")
    print("=" * 60)
    
    for evento in todos_eventos:
        print(f"P{evento['processo']} [V={evento['relogio_vetorial']}] [{evento['hora_legivel']}] {evento['tipo']}: {evento['detalhes']}")
    
    print("\n" + "=" * 60)
    print("ANÁLISE DOS RESULTADOS")
    print("=" * 60)
    print("Com relógios vetoriais:")
    print("1. Se V(A) < V(B) (componente a componente), então A -> B (A causou B).")
    print("2. Se V(A) não é menor nem maior que V(B), então A e B são concorrentes.")
    print("   (Ex: V(A) = [2,1,0], V(B) = [1,2,0] -> concorrentes)")
    
    return todos_eventos

if __name__ == "__main__":
    eventos = simular_relogios_vetoriais()

