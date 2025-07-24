/**
 * Classe que representa um clube de futebol
 */
class Clube {
    constructor(data = {}) {
        this.nome = data.nome || '';
        this.sigla = data.sigla || '';
        this.vitorias = data.vitorias || 0;
        this.empates = data.empates || 0;
        this.derrotas = data.derrotas || 0;
        this.gols_pro = data.gols_pro || 0;
        this.gols_contra = data.gols_contra || 0;
    }

    /**
     * Cria uma instância de Clube a partir de dados JSON do servidor
     * @param {Object} jsonData - Dados JSON do servidor
     * @returns {Clube} Nova instância de Clube
     */
    static fromJSON(jsonData) {
        return new Clube(jsonData);
    }

    /**
     * Converte a instância para um objeto JSON para envio ao servidor
     * @returns {Object} Objeto JSON
     */
    toJSON() {
        return {
            nome: this.nome,
            sigla: this.sigla,
            vitorias: this.vitorias,
            empates: this.empates,
            derrotas: this.derrotas,
            gols_pro: this.gols_pro,
            gols_contra: this.gols_contra
        };
    }

    /**
     * Calcula o total de jogos
     * @returns {number} Total de jogos
     */
    getTotalJogos() {
        return this.vitorias + this.empates + this.derrotas;
    }

    /**
     * Calcula os pontos (3 por vitória, 1 por empate)
     * @returns {number} Total de pontos
     */
    getPontos() {
        return (this.vitorias * 3) + this.empates;
    }

    /**
     * Calcula o saldo de gols
     * @returns {number} Saldo de gols
     */
    getSaldoGols() {
        return this.gols_pro - this.gols_contra;
    }

    /**
     * Retorna uma representação em string do clube
     * @returns {string} String formatada com informações do clube
     */
    toString() {
        return `${this.nome} (${this.sigla}) - ${this.getPontos()} pts, ${this.getTotalJogos()} jogos`;
    }
}
