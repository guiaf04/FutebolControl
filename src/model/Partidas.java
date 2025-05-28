package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Interface model.Partidas - Define o contrato para operações relacionadas a partidas de futebol
 */
public interface Partidas {
    /**
     * Registra uma nova partida no sistema
     * @param mandante Nome do clube mandante
     * @param visitante Nome do clube visitante
     * @param data Data da partida
     * @param local Local da partida
     * @return ID da partida registrada
     */
    int registrarPartida(String mandante, String visitante, String data, String local);
    
    /**
     * Registra o resultado de uma partida
     * @param idPartida ID da partida
     * @param golsMandante Gols marcados pelo mandante
     * @param golsVisitante Gols marcados pelo visitante
     * @return true se o registro foi bem-sucedido, false caso contrário
     */
    boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante);
    
    /**
     * Obtém informações sobre uma partida específica
     * @param idPartida ID da partida
     * @return String contendo informações da partida
     */
    String obterInformacoesPartida(int idPartida);

    /**
     * Classe model.Partidas.Confederacao - Representa uma confederação que agrega clubes
     * Implementa Serializable para permitir a serialização
     */
    class Confederacao implements Serializable {
        private static final long serialVersionUID = 1L;

        private String nome;
        private String pais;
        private int anoFundacao;
        private String presidente;
        private List<Clube> clubesFiliados;

        /**
         * Construtor padrão
         */
        public Confederacao() {
            this.clubesFiliados = new ArrayList<>();
        }

        /**
         * Construtor com parâmetros
         * @param nome Nome da confederação
         * @param pais País da confederação
         * @param anoFundacao Ano de fundação
         * @param presidente Nome do presidente
         */
        public Confederacao(String nome, String pais, int anoFundacao, String presidente) {
            this.nome = nome;
            this.pais = pais;
            this.anoFundacao = anoFundacao;
            this.presidente = presidente;
            this.clubesFiliados = new ArrayList<>();
        }

        // Getters e Setters
        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getPais() {
            return pais;
        }

        public void setPais(String pais) {
            this.pais = pais;
        }

        public int getAnoFundacao() {
            return anoFundacao;
        }

        public void setAnoFundacao(int anoFundacao) {
            this.anoFundacao = anoFundacao;
        }

        public String getPresidente() {
            return presidente;
        }

        public void setPresidente(String presidente) {
            this.presidente = presidente;
        }

        public List<Clube> getClubesFiliados() {
            return clubesFiliados;
        }

        /**
         * Método para adicionar um clube à confederação
         * @param clube model.Clube a ser adicionado
         * @return true se o clube foi adicionado com sucesso, false caso contrário
         */
        public boolean adicionarClube(Clube clube) {
            return clubesFiliados.add(clube);
        }

        /**
         * Método para remover um clube da confederação
         * @param clube model.Clube a ser removido
         * @return true se o clube foi removido com sucesso, false caso contrário
         */
        public boolean removerClube(Clube clube) {
            return clubesFiliados.remove(clube);
        }

        /**
         * Método para buscar um clube pelo nome
         * @param nome Nome do clube a ser buscado
         * @return model.Clube encontrado ou null se não encontrado
         */
        public Clube buscarClube(String nome) {
            for (Clube clube : clubesFiliados) {
                if (clube.getNome().equalsIgnoreCase(nome)) {
                    return clube;
                }
            }
            return null;
        }

        /**
         * Método para exibir informações da confederação
         * @return String com informações da confederação
         */
        public String exibirInformacoes() {
            StringBuilder info = new StringBuilder();
            info.append("Confederação: ").append(nome);
            info.append("\nPaís: ").append(pais);
            info.append("\nAno de Fundação: ").append(anoFundacao);
            info.append("\nPresidente: ").append(presidente);
            info.append("\nNúmero de Clubes Filiados: ").append(clubesFiliados.size());

            return info.toString();
        }

        /**
         * Método para listar todos os clubes filiados
         * @return String com a lista de clubes filiados
         */
        public String listarClubesFiliados() {
            StringBuilder lista = new StringBuilder("Clubes Filiados à ").append(nome).append(":\n");

            for (Clube clube : clubesFiliados) {
                lista.append("- ").append(clube.getNome()).append(" (").append(clube.getCidade()).append(")\n");
            }

            return lista.toString();
        }
    }
}
