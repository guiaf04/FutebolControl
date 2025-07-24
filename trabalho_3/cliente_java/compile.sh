#!/bin/bash

# Script de compilação para o cliente Java refatorado
# Futebol Control - Cliente Java

echo "=== Compilando Futebol Control - Cliente Java ==="

# Criar diretório de build se não existir
mkdir -p build

echo "Compilando classes de modelo..."
javac -d build src/models/*.java

echo "Compilando utilitários..."
javac -cp build -d build src/utils/*.java

echo "Compilando serviços..."
javac -cp build -d build src/services/*.java

echo "Compilando interface gráfica..."
javac -cp build -d build src/ui/*.java

if [ $? -eq 0 ]; then
    echo "✅ Compilação concluída com sucesso!"
    echo ""
    echo "Para executar a aplicação:"
    echo "  cd build"
    echo "  java ui.FutebolClientUI"
    echo ""
    echo "Ou use o script run.sh"
else
    echo "❌ Erro na compilação!"
    exit 1
fi
