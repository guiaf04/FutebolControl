#!/usr/bin/env python3
"""
Parte 1 - Simulação de Eventos Distribuídos com Relógios Físicos
Demonstra como a ordem dos eventos pode parecer incorreta sem controle lógico.
"""

import threading
import time
import random
import queue
from datetime import datetime

class ProcessoFisico:
    def __init__(self, id_processo, num_processos):
        self.id = id_processo
        self.num_processos = num_processos
        self.filas_entrada = [queue.Queue() for _ in range(num_processos)]
        self.eventos = []
        self.running = True
        
    def timestamp_fisico(self):
        """Retorna timestamp físico atual em milissegundos"""
        return int(time.time() * 1000)
    
    def log_evento(self, tipo, detalhes=""):
        """Loga um evento com timestamp físico"""
        timestamp = self.timestamp_fisico()
        evento = {
            'processo': self.id,
            'timestamp': timestamp,
            'tipo': tipo,
            'detalhes': detalhes,
            'hora_legivel': datetime.fromtimestamp(timestamp/1000).strftime('%H:%M:%S.%f')[:-3]
        }
        self.eventos.append(evento)
        print(f"P{self.id} [{evento['hora_legivel']}] {tipo}: {detalhes}")
        
    def enviar_mensagem(self, destino, conteudo):
        """Envia mensagem para outro processo"""
        if destino != self.id and destino < self.num_processos:
            timestamp = self.timestamp_fisico()
            mensagem = {
                'origem': self.id,
                'timestamp_envio': timestamp,
                'conteudo': conteudo
            }
            
            # Simula latência de rede variável
            latencia = random.uniform(0.01, 0.1)
            time.sleep(latencia)
            
            self.filas_entrada[destino].put(mensagem)
            self.log_evento("ENVIO", f"para P{destino}: '{conteudo}' (latência: {latencia:.3f}s)")
    
    def processar_mensagens(self):
        """Processa mensagens recebidas"""
        try:
            while not self.filas_entrada[self.id].empty():
                mensagem = self.filas_entrada[self.id].get_nowait()
                timestamp_recebimento = self.timestamp_fisico()
                
                detalhes = f"de P{mensagem['origem']}: '{mensagem['conteudo']}' " + \
                          f"(enviada em {datetime.fromtimestamp(mensagem['timestamp_envio']/1000).strftime('%H:%M:%S.%f')[:-3]})"
                
                self.log_evento("RECEBIMENTO", detalhes)
                
        except queue.Empty:
            pass
    
    def evento_interno(self, descricao):
        """Simula um evento interno do processo"""
        self.log_evento("EVENTO_INTERNO", descricao)
    
    def executar(self):
        """Loop principal do processo"""
        print(f"\\n=== Processo P{self.id} iniciado ===")
        
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
        
        print(f"\\n=== Processo P{self.id} finalizado ===")

def simular_relogios_fisicos():
    """Executa a simulação com relógios físicos"""
    print("=" * 60)
    print("SIMULAÇÃO DE RELÓGIOS FÍSICOS")
    print("=" * 60)
    print("Observação: A ordem dos eventos pode parecer incorreta")
    print("devido à falta de sincronização lógica.\\n")
    
    num_processos = 3
    processos = []
    threads = []
    
    # Cria os processos
    for i in range(num_processos):
        processo = ProcessoFisico(i, num_processos)
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
    
    # Ordena por timestamp físico
    todos_eventos.sort(key=lambda x: x['timestamp'])
    
    print("\\n" + "=" * 60)
    print("LINHA DO TEMPO GLOBAL (ordenada por timestamp físico)")
    print("=" * 60)
    
    for evento in todos_eventos:
        print(f"P{evento['processo']} [{evento['hora_legivel']}] {evento['tipo']}: {evento['detalhes']}")
    
    print("\\n" + "=" * 60)
    print("ANÁLISE DOS RESULTADOS")
    print("=" * 60)
    print("Problemas observados com relógios físicos:")
    print("1. Mensagens podem parecer ser recebidas antes de serem enviadas")
    print("2. A ordem causal dos eventos não é preservada")
    print("3. Eventos concorrentes não são distinguíveis")
    print("4. Dependência da sincronização dos relógios físicos")
    
    return todos_eventos

if __name__ == "__main__":
    eventos = simular_relogios_fisicos()

