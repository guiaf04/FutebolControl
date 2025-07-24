/**
 * Serviço para comunicação com a API do servidor
 */
class ApiService {
    constructor(baseUrl = 'http://localhost:8000') {
        this.baseUrl = baseUrl;
    }

    /**
     * Realiza uma requisição HTTP
     * @param {string} endpoint - Endpoint da API
     * @param {Object} options - Opções da requisição (method, body, etc.)
     * @returns {Promise<Object>} Resposta da API
     */
    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const config = {
            headers: {
                'Content-Type': 'application/json',
                ...options.headers
            },
            ...options
        };

        try {
            const response = await fetch(url, config);
            const data = await response.json();
            
            if (!response.ok) {
                throw new Error(data.detail || `HTTP error! status: ${response.status}`);
            }
            
            return data;
        } catch (error) {
            console.error('Erro na requisição:', error);
            throw error;
        }
    }

    // ==================== CLUBES ====================

    /**
     * Cria um novo clube
     * @param {Clube} clube - Dados do clube
     * @returns {Promise<Object>} Resposta da API
     */
    async criarClube(clube) {
        return this.request('/clubes', {
            method: 'POST',
            body: JSON.stringify(clube.toJSON())
        });
    }

    /**
     * Lista todos os clubes
     * @returns {Promise<Clube[]>} Lista de clubes
     */
    async listarClubes() {
        const data = await this.request('/clubes');
        return data.map(clubeData => Clube.fromJSON(clubeData));
    }

    /**
     * Obtém estatísticas de um clube
     * @param {string} sigla - Sigla do clube
     * @returns {Promise<Clube>} Dados do clube
     */
    async obterEstatisticasClube(sigla) {
        const data = await this.request(`/clubes/estatisticas?sigla=${sigla}`);
        return Clube.fromJSON(data);
    }

    /**
     * Atualiza um clube
     * @param {string} siglaOriginal - Sigla original do clube
     * @param {Clube} clube - Novos dados do clube
     * @returns {Promise<Object>} Resposta da API
     */
    async atualizarClube(siglaOriginal, clube) {
        return this.request(`/clubes?sigla_original=${siglaOriginal}`, {
            method: 'PUT',
            body: JSON.stringify(clube.toJSON())
        });
    }

    /**
     * Deleta um clube
     * @param {string} sigla - Sigla do clube
     * @returns {Promise<Object>} Resposta da API
     */
    async deletarClube(sigla) {
        return this.request(`/clubes?sigla=${sigla}`, {
            method: 'DELETE'
        });
    }

    /**
     * Obtém histórico de partidas de um clube
     * @param {string} sigla - Sigla do clube
     * @returns {Promise<Partida[]>} Lista de partidas
     */
    async obterHistoricoClube(sigla) {
        const data = await this.request(`/clubes/partidas?sigla=${sigla}`);
        return data.map(partidaData => Partida.fromJSON(partidaData));
    }

    // ==================== CAMPEONATOS ====================

    /**
     * Cria um novo campeonato
     * @param {Campeonato} campeonato - Dados do campeonato
     * @returns {Promise<Object>} Resposta da API
     */
    async criarCampeonato(campeonato) {
        return this.request('/campeonatos', {
            method: 'POST',
            body: JSON.stringify(campeonato.toJSON())
        });
    }

    /**
     * Lista todos os campeonatos
     * @returns {Promise<Campeonato[]>} Lista de campeonatos
     */
    async listarCampeonatos() {
        const data = await this.request('/campeonatos');
        return data.map(campeonatoData => Campeonato.fromJSON(campeonatoData));
    }

    /**
     * Atualiza um campeonato
     * @param {string} nomeOriginal - Nome original do campeonato
     * @param {Campeonato} campeonato - Novos dados do campeonato
     * @returns {Promise<Object>} Resposta da API
     */
    async atualizarCampeonato(nomeOriginal, campeonato) {
        return this.request(`/campeonatos?nome_original=${nomeOriginal}`, {
            method: 'PUT',
            body: JSON.stringify(campeonato.toJSON())
        });
    }

    /**
     * Deleta um campeonato
     * @param {string} nome - Nome do campeonato
     * @returns {Promise<Object>} Resposta da API
     */
    async deletarCampeonato(nome) {
        return this.request(`/campeonatos?nome=${nome}`, {
            method: 'DELETE'
        });
    }

    /**
     * Adiciona um clube a um campeonato
     * @param {string} nomeCampeonato - Nome do campeonato
     * @param {string} siglaClube - Sigla do clube
     * @returns {Promise<Object>} Resposta da API
     */
    async adicionarClubeAoCampeonato(nomeCampeonato, siglaClube) {
        return this.request(`/campeonatos/adicionar_clube?nome_campeonato=${nomeCampeonato}&sigla_clube=${siglaClube}`, {
            method: 'POST'
        });
    }

    /**
     * Lista clubes participantes de um campeonato
     * @param {string} nome - Nome do campeonato
     * @returns {Promise<Clube[]>} Lista de clubes participantes
     */
    async listarClubesCampeonato(nome) {
        const data = await this.request(`/campeonatos/clubes_participantes?nome=${nome}`);
        return data.map(clubeData => Clube.fromJSON(clubeData));
    }

    // ==================== PARTIDAS ====================

    /**
     * Registra uma nova partida
     * @param {Partida} partida - Dados da partida
     * @returns {Promise<Object>} Resposta da API
     */
    async registrarPartida(partida) {
        return this.request('/partidas', {
            method: 'POST',
            body: JSON.stringify(partida.toJSON())
        });
    }
}
