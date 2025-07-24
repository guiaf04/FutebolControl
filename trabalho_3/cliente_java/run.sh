#!/bin/bash

# Script de execução para o cliente Java refatorado
# Futebol Control - Cliente Java

echo "=== Executando Futebol Control - Cliente Java ==="

# Verificar se as classes foram compiladas
if [ ! -d "build" ]; then
    echo "Classes não encontradas. Compilando primeiro..."
    ./compile.sh
fi

if [ -d "build" ]; then
    echo "Iniciando aplicação..."
    cd build
    java ui.FutebolClientUI
else
    echo "❌ Erro: Não foi possível encontrar as classes compiladas."
    echo "Execute primeiro: ./compile.sh"
    exit 1
fi
