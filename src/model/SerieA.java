package model;

import java.io.Serializable;

/**
 * Classe model.SerieA - Subclasse que representa um campeonato da Série A
 * Estende a classe model.Clube e implementa Serializable para permitir a serialização
 */
public class SerieA extends Clube implements Serializable, Partidas {
    private static final long serialVersionUID = 1L;
    
    private int ano;
    private int numeroEquipes;
    private String campeao;
    private String viceCampeao;
    private int rodadaAtual;
    
    /**
     * Construtor padrão
     */
    public SerieA() {
        super();
    }
    
    /**
     * Construtor com parâmetros
     * @param nome Nome do campeonato
     * @param cidade Cidade sede da organização
     * @param anoFundacao Ano de fundação do campeonato
     * @param estadio Estádio principal
     * @param numeroTitulos Número de edições
     * @param ano Ano da edição atual
     * @param numeroEquipes Número de equipes participantes
     * @param campeao Último campeão
     * @param viceCampeao Último vice-campeão
     * @param rodadaAtual Rodada atual do campeonato
     */
    public SerieA(String nome, String cidade, int anoFundacao, String estadio, int numeroTitulos,
                 int ano, int numeroEquipes, String campeao, String viceCampeao, int rodadaAtual) {
        super(nome, cidade, anoFundacao, estadio, numeroTitulos);
        this.ano = ano;
        this.numeroEquipes = numeroEquipes;
        this.campeao = campeao;
        this.viceCampeao = viceCampeao;
        this.rodadaAtual = rodadaAtual;
    }
    
    // Getters e Setters
    public int getAno() {
        return ano;
    }
    
    public void setAno(int ano) {
        this.ano = ano;
    }
    
    public int getNumeroEquipes() {
        return numeroEquipes;
    }
    
    public void setNumeroEquipes(int numeroEquipes) {
        this.numeroEquipes = numeroEquipes;
    }
    
    public String getCampeao() {
        return campeao;
    }
    
    public void setCampeao(String campeao) {
        this.campeao = campeao;
    }
    
    public String getViceCampeao() {
        return viceCampeao;
    }
    
    public void setViceCampeao(String viceCampeao) {
        this.viceCampeao = viceCampeao;
    }
    
    public int getRodadaAtual() {
        return rodadaAtual;
    }
    
    public void setRodadaAtual(int rodadaAtual) {
        this.rodadaAtual = rodadaAtual;
    }
    
    /**
     * Método para exibir informações do campeonato
     * @return String com informações do campeonato
     */
    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + 
               "\nAno: " + ano + 
               "\nNúmero de Equipes: " + numeroEquipes + 
               "\nCampeão: " + campeao + 
               "\nVice-Campeão: " + viceCampeao + 
               "\nRodada Atual: " + rodadaAtual;
    }
    
    // Implementação dos métodos da interface model.Partidas
    @Override
    public int registrarPartida(String mandante, String visitante, String data, String local) {
        // Implementação do registro de partida para Série A
        System.out.println("Partida de Série A registrada: " + mandante + " x " + visitante);
        return (mandante + visitante + data).hashCode();
    }
    
    @Override
    public boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante) {
        // Implementação do registro de resultado para Série A
        System.out.println("Resultado registrado para partida " + idPartida + ": " + golsMandante + " x " + golsVisitante);
        return true;
    }
    
    @Override
    public String obterInformacoesPartida(int idPartida) {
        // Implementação da obtenção de informações de partida para Série A
        return "Informações da partida " + idPartida + " do campeonato Série A";
    }

    /**
     * Classe model.SerieA.Libertadores - Subclasse que representa o campeonato da model.SerieA.Libertadores
     * Estende a classe model.Clube e implementa Serializable para permitir a serialização
     */
    public static class Libertadores extends Clube implements Serializable, Partidas {
        private static final long serialVersionUID = 1L;

        private int ano;
        private int numeroEquipes;
        private String[] paisesParticipantes;
        private String campeao;
        private String viceCampeao;
        private String faseAtual;

        /**
         * Construtor padrão
         */
        public Libertadores() {
            super();
            this.paisesParticipantes = new String[10];
        }

        /**
         * Construtor com parâmetros
         * @param nome Nome do campeonato
         * @param cidade Cidade sede da organização
         * @param anoFundacao Ano de fundação do campeonato
         * @param estadio Estádio da final
         * @param numeroTitulos Número de edições
         * @param ano Ano da edição atual
         * @param numeroEquipes Número de equipes participantes
         * @param paisesParticipantes Países com equipes participantes
         * @param campeao Último campeão
         * @param viceCampeao Último vice-campeão
         * @param faseAtual Fase atual do campeonato
         */
        public Libertadores(String nome, String cidade, int anoFundacao, String estadio, int numeroTitulos,
                           int ano, int numeroEquipes, String[] paisesParticipantes, String campeao,
                           String viceCampeao, String faseAtual) {
            super(nome, cidade, anoFundacao, estadio, numeroTitulos);
            this.ano = ano;
            this.numeroEquipes = numeroEquipes;
            this.paisesParticipantes = paisesParticipantes;
            this.campeao = campeao;
            this.viceCampeao = viceCampeao;
            this.faseAtual = faseAtual;
        }

        // Getters e Setters
        public int getAno() {
            return ano;
        }

        public void setAno(int ano) {
            this.ano = ano;
        }

        public int getNumeroEquipes() {
            return numeroEquipes;
        }

        public void setNumeroEquipes(int numeroEquipes) {
            this.numeroEquipes = numeroEquipes;
        }

        public String[] getPaisesParticipantes() {
            return paisesParticipantes;
        }

        public void setPaisesParticipantes(String[] paisesParticipantes) {
            this.paisesParticipantes = paisesParticipantes;
        }

        public String getCampeao() {
            return campeao;
        }

        public void setCampeao(String campeao) {
            this.campeao = campeao;
        }

        public String getViceCampeao() {
            return viceCampeao;
        }

        public void setViceCampeao(String viceCampeao) {
            this.viceCampeao = viceCampeao;
        }

        public String getFaseAtual() {
            return faseAtual;
        }

        public void setFaseAtual(String faseAtual) {
            this.faseAtual = faseAtual;
        }

        /**
         * Método para exibir informações do campeonato
         * @return String com informações do campeonato
         */
        @Override
        public String exibirInformacoes() {
            StringBuilder info = new StringBuilder(super.exibirInformacoes());
            info.append("\nAno: ").append(ano);
            info.append("\nNúmero de Equipes: ").append(numeroEquipes);
            info.append("\nCampeão: ").append(campeao);
            info.append("\nVice-Campeão: ").append(viceCampeao);
            info.append("\nFase Atual: ").append(faseAtual);

            info.append("\nPaíses Participantes: ");
            for (String pais : paisesParticipantes) {
                if (pais != null) {
                    info.append(pais).append(", ");
                }
            }

            return info.toString();
        }

        // Implementação dos métodos da interface model.Partidas
        @Override
        public int registrarPartida(String mandante, String visitante, String data, String local) {
            // Implementação do registro de partida para model.SerieA.Libertadores
            System.out.println("Partida de model.SerieA.Libertadores registrada: " + mandante + " x " + visitante);
            return (mandante + visitante + data).hashCode();
        }

        @Override
        public boolean registrarResultado(int idPartida, int golsMandante, int golsVisitante) {
            // Implementação do registro de resultado para model.SerieA.Libertadores
            System.out.println("Resultado registrado para partida " + idPartida + ": " + golsMandante + " x " + golsVisitante);
            return true;
        }

        @Override
        public String obterInformacoesPartida(int idPartida) {
            // Implementação da obtenção de informações de partida para model.SerieA.Libertadores
            return "Informações da partida " + idPartida + " do campeonato model.SerieA.Libertadores";
        }
    }
}
