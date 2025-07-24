/**
 * Aplicação principal - Futebol Control Cliente Web
 */

// Instância do serviço de API
const apiService = new ApiService();

// ==================== FUNÇÕES DE CLUBES ====================

async function criarClube() {
    try {
        const nome = document.getElementById("clubeNome").value;
        const sigla = document.getElementById("clubeSigla").value;
        
        if (!nome || !sigla) {
            mostrar({ erro: "Nome e sigla são obrigatórios" });
            return;
        }

        const clube = new Clube({ nome, sigla });
        const resultado = await apiService.criarClube(clube);
        mostrar(resultado);
        
        // Limpar campos
        document.getElementById("clubeNome").value = '';
        document.getElementById("clubeSigla").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function verEstatisticas() {
    try {
        const sigla = document.getElementById("estatSigla").value;
        
        if (!sigla) {
            mostrar({ erro: "Sigla é obrigatória" });
            return;
        }

        const clube = await apiService.obterEstatisticasClube(sigla);
        mostrar({
            clube: clube.toJSON(),
            estatisticas: {
                total_jogos: clube.getTotalJogos(),
                pontos: clube.getPontos(),
                saldo_gols: clube.getSaldoGols()
            }
        });
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function atualizarClube() {
    try {
        const siglaOriginal = document.getElementById("putSiglaOriginal").value;
        const nome = document.getElementById("putNome").value;
        const novaSigla = document.getElementById("putNovaSigla").value;
        
        if (!siglaOriginal) {
            mostrar({ erro: "Sigla original é obrigatória" });
            return;
        }

        // Obter dados atuais do clube
        const clubeAtual = await apiService.obterEstatisticasClube(siglaOriginal);
        
        // Criar clube com dados atualizados
        const clubeAtualizado = new Clube({
            ...clubeAtual.toJSON(),
            nome: nome || clubeAtual.nome,
            sigla: novaSigla || clubeAtual.sigla
        });

        const resultado = await apiService.atualizarClube(siglaOriginal, clubeAtualizado);
        mostrar(resultado);
        
        // Limpar campos
        document.getElementById("putSiglaOriginal").value = '';
        document.getElementById("putNome").value = '';
        document.getElementById("putNovaSigla").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function deletarClube() {
    try {
        const sigla = document.getElementById("delSigla").value;
        
        if (!sigla) {
            mostrar({ erro: "Sigla é obrigatória" });
            return;
        }

        const resultado = await apiService.deletarClube(sigla);
        mostrar(resultado);
        
        // Limpar campo
        document.getElementById("delSigla").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function verHistorico() {
    try {
        const sigla = document.getElementById("histSigla").value;
        
        if (!sigla) {
            mostrar({ erro: "Sigla é obrigatória" });
            return;
        }

        const partidas = await apiService.obterHistoricoClube(sigla);
        const historico = partidas.map(partida => ({
            partida: partida.toString(),
            resultado: partida.getResultado(),
            vencedor: partida.getVencedor(),
            total_gols: partida.getTotalGols()
        }));
        
        mostrar({ historico });
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

// ==================== FUNÇÕES DE CAMPEONATOS ====================

async function criarCampeonato() {
    try {
        const nome = document.getElementById("campNome").value;
        const ano = parseInt(document.getElementById("campAno").value);
        
        if (!nome || !ano) {
            mostrar({ erro: "Nome e ano são obrigatórios" });
            return;
        }

        const campeonato = new Campeonato({ nome, ano });
        const resultado = await apiService.criarCampeonato(campeonato);
        mostrar(resultado);
        
        // Limpar campos
        document.getElementById("campNome").value = '';
        document.getElementById("campAno").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function atualizarCampeonato() {
    try {
        const nomeOriginal = document.getElementById("putCampNomeOriginal").value;
        const novoNome = document.getElementById("putCampNovoNome").value;
        const novoAno = document.getElementById("putCampNovoAno").value;
        
        if (!nomeOriginal) {
            mostrar({ erro: "Nome original é obrigatório" });
            return;
        }

        // Obter dados atuais do campeonato
        const campeonatos = await apiService.listarCampeonatos();
        const campeonatoAtual = campeonatos.find(c => c.nome === nomeOriginal);
        
        if (!campeonatoAtual) {
            mostrar({ erro: "Campeonato não encontrado" });
            return;
        }

        // Criar campeonato com dados atualizados
        const campeonatoAtualizado = new Campeonato({
            nome: novoNome || campeonatoAtual.nome,
            ano: novoAno ? parseInt(novoAno) : campeonatoAtual.ano,
            clubes: campeonatoAtual.clubes
        });

        const resultado = await apiService.atualizarCampeonato(nomeOriginal, campeonatoAtualizado);
        mostrar(resultado);
        
        // Limpar campos
        document.getElementById("putCampNomeOriginal").value = '';
        document.getElementById("putCampNovoNome").value = '';
        document.getElementById("putCampNovoAno").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function deletarCampeonato() {
    try {
        const nome = document.getElementById("delCampNome").value;
        
        if (!nome) {
            mostrar({ erro: "Nome é obrigatório" });
            return;
        }

        const resultado = await apiService.deletarCampeonato(nome);
        mostrar(resultado);
        
        // Limpar campo
        document.getElementById("delCampNome").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function adicionarClubeAoCampeonato() {
    try {
        const nomeCampeonato = document.getElementById("addCampNome").value;
        const siglaClube = document.getElementById("addClubeSigla").value;
        
        if (!nomeCampeonato || !siglaClube) {
            mostrar({ erro: "Nome do campeonato e sigla do clube são obrigatórios" });
            return;
        }

        const resultado = await apiService.adicionarClubeAoCampeonato(nomeCampeonato, siglaClube);
        mostrar(resultado);
        
        // Limpar campos
        document.getElementById("addCampNome").value = '';
        document.getElementById("addClubeSigla").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

async function verClubesCampeonato() {
    try {
        const nome = document.getElementById("listCampClubesNome").value;
        
        if (!nome) {
            mostrar({ erro: "Nome do campeonato é obrigatório" });
            return;
        }

        const clubes = await apiService.listarClubesCampeonato(nome);
        const clubesInfo = clubes.map(clube => ({
            info: clube.toString(),
            estatisticas: {
                pontos: clube.getPontos(),
                saldo_gols: clube.getSaldoGols(),
                jogos: clube.getTotalJogos()
            }
        }));
        
        mostrar({ 
            campeonato: nome,
            clubes_participantes: clubesInfo 
        });
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

// ==================== FUNÇÕES DE PARTIDAS ====================

async function registrarPartida() {
    try {
        const sigla_clube1 = document.getElementById("pClube1").value;
        const sigla_clube2 = document.getElementById("pClube2").value;
        const gols1 = parseInt(document.getElementById("pGols1").value);
        const gols2 = parseInt(document.getElementById("pGols2").value);
        const campeonato = document.getElementById("pCamp").value;
        
        if (!sigla_clube1 || !sigla_clube2 || isNaN(gols1) || isNaN(gols2) || !campeonato) {
            mostrar({ erro: "Todos os campos são obrigatórios" });
            return;
        }

        if (sigla_clube1 === sigla_clube2) {
            mostrar({ erro: "Um clube não pode jogar contra si mesmo" });
            return;
        }

        const partida = new Partida({
            sigla_clube1,
            sigla_clube2,
            gols1,
            gols2,
            campeonato
        });

        const resultado = await apiService.registrarPartida(partida);
        mostrar({
            ...resultado,
            resumo_partida: {
                jogo: partida.toString(),
                resultado: partida.getResultado(),
                vencedor: partida.getVencedor(),
                total_gols: partida.getTotalGols(),
                empate: partida.isEmpate()
            }
        });
        
        // Limpar campos
        document.getElementById("pClube1").value = '';
        document.getElementById("pClube2").value = '';
        document.getElementById("pGols1").value = '';
        document.getElementById("pGols2").value = '';
        document.getElementById("pCamp").value = '';
    } catch (error) {
        mostrar({ erro: error.message });
    }
}

// ==================== FUNÇÕES UTILITÁRIAS ====================

/**
 * Exibe dados na área de saída
 * @param {Object} obj - Objeto a ser exibido
 */
function mostrar(obj) {
    const saida = document.getElementById("saida");
    saida.value = JSON.stringify(obj, null, 2);
}

/**
 * Limpa a área de saída
 */
function limparSaida() {
    document.getElementById("saida").value = '';
}

// ==================== INICIALIZAÇÃO ====================

document.addEventListener('DOMContentLoaded', function() {
    console.log('Futebol Control Cliente Web carregado!');
    console.log('API Service inicializado para:', apiService.baseUrl);
});
