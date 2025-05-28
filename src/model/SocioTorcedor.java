package model;

import java.io.Serializable;

/**
 * Classe model.SocioTorcedor - Subclasse que representa um sócio torcedor de um clube
 * Estende a classe model.Clube e implementa Serializable para permitir a serialização
 */
public class SocioTorcedor extends Clube implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String nome;
    private String cpf;
    private String plano;
    private double mensalidade;
    private String dataAdesao;
    private boolean ativo;
    
    /**
     * Construtor padrão
     */
    public SocioTorcedor() {
        super();
    }
    
    /**
     * Construtor com parâmetros
     * @param clubeNome Nome do clube
     * @param clubeCidade Cidade sede do clube
     * @param clubeAnoFundacao Ano de fundação do clube
     * @param clubeEstadio Estádio do clube
     * @param clubeNumeroTitulos Número de títulos do clube
     * @param nome Nome do sócio torcedor
     * @param cpf CPF do sócio torcedor
     * @param plano Plano de adesão
     * @param mensalidade Valor da mensalidade
     * @param dataAdesao Data de adesão ao plano
     * @param ativo Status de atividade do sócio
     */
    public SocioTorcedor(String clubeNome, String clubeCidade, int clubeAnoFundacao, 
                        String clubeEstadio, int clubeNumeroTitulos, String nome, 
                        String cpf, String plano, double mensalidade, 
                        String dataAdesao, boolean ativo) {
        super(clubeNome, clubeCidade, clubeAnoFundacao, clubeEstadio, clubeNumeroTitulos);
        this.nome = nome;
        this.cpf = cpf;
        this.plano = plano;
        this.mensalidade = mensalidade;
        this.dataAdesao = dataAdesao;
        this.ativo = ativo;
    }
    
    // Getters e Setters
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    
    public String getPlano() {
        return plano;
    }
    
    public void setPlano(String plano) {
        this.plano = plano;
    }
    
    public double getMensalidade() {
        return mensalidade;
    }
    
    public void setMensalidade(double mensalidade) {
        this.mensalidade = mensalidade;
    }
    
    public String getDataAdesao() {
        return dataAdesao;
    }
    
    public void setDataAdesao(String dataAdesao) {
        this.dataAdesao = dataAdesao;
    }
    
    public boolean isAtivo() {
        return ativo;
    }
    
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
    /**
     * Método para exibir informações do sócio torcedor
     * @return String com informações do sócio torcedor
     */
    @Override
    public String exibirInformacoes() {
        return super.exibirInformacoes() + 
               "\nNome do Sócio: " + nome + 
               "\nCPF: " + cpf + 
               "\nPlano: " + plano + 
               "\nMensalidade: R$ " + mensalidade + 
               "\nData de Adesão: " + dataAdesao + 
               "\nStatus: " + (ativo ? "Ativo" : "Inativo");
    }
    
    /**
     * Método para renovar a adesão do sócio torcedor
     * @param novaData Nova data de adesão
     * @return true se a renovação foi bem-sucedida, false caso contrário
     */
    public boolean renovarAdesao(String novaData) {
        if (!ativo) {
            this.ativo = true;
        }
        this.dataAdesao = novaData;
        return true;
    }
    
    /**
     * Método para cancelar a adesão do sócio torcedor
     * @return true se o cancelamento foi bem-sucedido, false caso contrário
     */
    public boolean cancelarAdesao() {
        this.ativo = false;
        return true;
    }
}
