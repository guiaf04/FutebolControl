package models;

/**
 * Classe que representa um clube de futebol
 */
public class Clube {
    private String nome;
    private String sigla;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;

    // Construtores
    public Clube() {
        this("", "", 0, 0, 0, 0, 0);
    }

    public Clube(String nome, String sigla) {
        this(nome, sigla, 0, 0, 0, 0, 0);
    }

    public Clube(String nome, String sigla, int vitorias, int empates, int derrotas, int golsPro, int golsContra) {
        this.nome = nome;
        this.sigla = sigla;
        this.vitorias = vitorias;
        this.empates = empates;
        this.derrotas = derrotas;
        this.golsPro = golsPro;
        this.golsContra = golsContra;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getSigla() { return sigla; }
    public void setSigla(String sigla) { this.sigla = sigla; }

    public int getVitorias() { return vitorias; }
    public void setVitorias(int vitorias) { this.vitorias = vitorias; }

    public int getEmpates() { return empates; }
    public void setEmpates(int empates) { this.empates = empates; }

    public int getDerrotas() { return derrotas; }
    public void setDerrotas(int derrotas) { this.derrotas = derrotas; }

    public int getGolsPro() { return golsPro; }
    public void setGolsPro(int golsPro) { this.golsPro = golsPro; }

    public int getGolsContra() { return golsContra; }
    public void setGolsContra(int golsContra) { this.golsContra = golsContra; }

    // Métodos de cálculo
    public int getTotalJogos() {
        return vitorias + empates + derrotas;
    }

    public int getPontos() {
        return (vitorias * 3) + empates;
    }

    public int getSaldoGols() {
        return golsPro - golsContra;
    }

    // Métodos de conversão JSON
    public String toJson() {
        return String.format(
            "{\"nome\":\"%s\", \"sigla\":\"%s\", \"vitorias\":%d, \"empates\":%d, \"derrotas\":%d, \"gols_pro\":%d, \"gols_contra\":%d}",
            nome, sigla, vitorias, empates, derrotas, golsPro, golsContra
        );
    }

    /**
     * Cria um objeto Clube a partir de uma string JSON simples
     * Nota: Esta é uma implementação simplificada para evitar dependências externas
     */
    public static Clube fromJson(String json) {
        Clube clube = new Clube();
        
        // Parse simples do JSON (sem biblioteca externa)
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            String[] pairs = json.split(",");
            
            for (String pair : pairs) {
                String[] keyValue = pair.split(":", 2);
                if (keyValue.length == 2) {
                    String key = keyValue[0].trim().replaceAll("\"", "");
                    String value = keyValue[1].trim().replaceAll("\"", "");
                    
                    switch (key) {
                        case "nome":
                            clube.setNome(value);
                            break;
                        case "sigla":
                            clube.setSigla(value);
                            break;
                        case "vitorias":
                            clube.setVitorias(Integer.parseInt(value));
                            break;
                        case "empates":
                            clube.setEmpates(Integer.parseInt(value));
                            break;
                        case "derrotas":
                            clube.setDerrotas(Integer.parseInt(value));
                            break;
                        case "gols_pro":
                            clube.setGolsPro(Integer.parseInt(value));
                            break;
                        case "gols_contra":
                            clube.setGolsContra(Integer.parseInt(value));
                            break;
                    }
                }
            }
        }
        
        return clube;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d pts, %d jogos", 
                           nome, sigla, getPontos(), getTotalJogos());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Clube clube = (Clube) obj;
        return sigla != null ? sigla.equals(clube.sigla) : clube.sigla == null;
    }

    @Override
    public int hashCode() {
        return sigla != null ? sigla.hashCode() : 0;
    }
}
