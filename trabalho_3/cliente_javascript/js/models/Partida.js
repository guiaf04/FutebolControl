/**
 * Classe que representa uma partida de futebol
 */
class Partida {
    constructor(data = {}) {
        this.sigla_clube1 = data.sigla_clube1 || '';
        this.sigla_clube2 = data.sigla_clube2 || '';
        this.gols1 = data.gols1 || 0;
        this.gols2 = data.gols2 || 0;
        this.campeonato = data.campeonato || '';
    }

    /**
     * Cria uma instância de Partida a partir de dados JSON do servidor
     * @param {Object} jsonData - Dados JSON do servidor
     * @returns {Partida} Nova instância de Partida
     */
    static fromJSON(jsonData) {
        return new Partida(jsonData);
    }

    /**
     * Converte a instância para um objeto JSON para envio ao servidor
     * @returns {Object} Objeto JSON
     */
    toJSON() {
        return {
            sigla_clube1: this.sigla_clube1,
            sigla_clube2: this.sigla_clube2,
            gols1: this.gols1,
            gols2: this.gols2,
            campeonato: this.campeonato
        };
    }

    /**
     * Determina o resultado da partida
     * @returns {string} 'vitoria_clube1', 'vitoria_clube2' ou 'empate'
     */
    getResultado() {
        if (this.gols1 > this.gols2) {
            return 'vitoria_clube1';
        } else if (this.gols2 > this.gols1) {
            return 'vitoria_clube2';
        } else {
            return 'empate';
        }
    }

    /**
     * Retorna o clube vencedor (se houver)
     * @returns {string|null} Sigla do clube vencedor ou null em caso de empate
     */
    getVencedor() {
        const resultado = this.getResultado();
        if (resultado === 'vitoria_clube1') {
            return this.sigla_clube1;
        } else if (resultado === 'vitoria_clube2') {
            return this.sigla_clube2;
        }
        return null;
    }

    /**
     * Retorna o clube perdedor (se houver)
     * @returns {string|null} Sigla do clube perdedor ou null em caso de empate
     */
    getPerdedor() {
        const resultado = this.getResultado();
        if (resultado === 'vitoria_clube1') {
            return this.sigla_clube2;
        } else if (resultado === 'vitoria_clube2') {
            return this.sigla_clube1;
        }
        return null;
    }

    /**
     * Verifica se a partida foi um empate
     * @returns {boolean} True se foi empate, false caso contrário
     */
    isEmpate() {
        return this.gols1 === this.gols2;
    }

    /**
     * Retorna o total de gols da partida
     * @returns {number} Total de gols
     */
    getTotalGols() {
        return this.gols1 + this.gols2;
    }

    /**
     * Verifica se um clube participou da partida
     * @param {string} siglaClube - Sigla do clube
     * @returns {boolean} True se o clube participou, false caso contrário
     */
    temClube(siglaClube) {
        return this.sigla_clube1 === siglaClube || this.sigla_clube2 === siglaClube;
    }

    /**
     * Retorna uma representação em string da partida
     * @returns {string} String formatada com informações da partida
     */
    toString() {
        return `${this.sigla_clube1} ${this.gols1} x ${this.gols2} ${this.sigla_clube2} (${this.campeonato})`;
    }
}
