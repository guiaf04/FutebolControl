/**
 * Classe que representa um campeonato
 */
class Campeonato {
    constructor(data = {}) {
        this.nome = data.nome || '';
        this.ano = data.ano || new Date().getFullYear();
        this.clubes = data.clubes || [];
    }

    /**
     * Cria uma instância de Campeonato a partir de dados JSON do servidor
     * @param {Object} jsonData - Dados JSON do servidor
     * @returns {Campeonato} Nova instância de Campeonato
     */
    static fromJSON(jsonData) {
        return new Campeonato(jsonData);
    }

    /**
     * Converte a instância para um objeto JSON para envio ao servidor
     * @returns {Object} Objeto JSON
     */
    toJSON() {
        return {
            nome: this.nome,
            ano: this.ano,
            clubes: this.clubes
        };
    }

    /**
     * Adiciona um clube ao campeonato
     * @param {string} siglaClube - Sigla do clube a ser adicionado
     * @returns {boolean} True se adicionado com sucesso, false se já existia
     */
    adicionarClube(siglaClube) {
        if (!this.clubes.includes(siglaClube)) {
            this.clubes.push(siglaClube);
            return true;
        }
        return false;
    }

    /**
     * Remove um clube do campeonato
     * @param {string} siglaClube - Sigla do clube a ser removido
     * @returns {boolean} True se removido com sucesso, false se não existia
     */
    removerClube(siglaClube) {
        const index = this.clubes.indexOf(siglaClube);
        if (index > -1) {
            this.clubes.splice(index, 1);
            return true;
        }
        return false;
    }

    /**
     * Verifica se um clube participa do campeonato
     * @param {string} siglaClube - Sigla do clube
     * @returns {boolean} True se o clube participa, false caso contrário
     */
    temClube(siglaClube) {
        return this.clubes.includes(siglaClube);
    }

    /**
     * Retorna o número de clubes participantes
     * @returns {number} Número de clubes
     */
    getNumeroParticipantes() {
        return this.clubes.length;
    }

    /**
     * Retorna uma representação em string do campeonato
     * @returns {string} String formatada com informações do campeonato
     */
    toString() {
        return `${this.nome} (${this.ano}) - ${this.getNumeroParticipantes()} clubes`;
    }
}
