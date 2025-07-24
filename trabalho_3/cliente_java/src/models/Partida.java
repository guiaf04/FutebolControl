package models;

/**
 * Classe que representa uma partida de futebol
 */
public class Partida {
    private String siglaClube1;
    private String siglaClube2;
    private int gols1;
    private int gols2;
    private String campeonato;

    // Enums para resultado
    public enum Resultado {
        VITORIA_CLUBE1, VITORIA_CLUBE2, EMPATE
    }

    // Construtores
    public Partida() {
        this("", "", 0, 0, "");
    }

    public Partida(String siglaClube1, String siglaClube2, int gols1, int gols2, String campeonato) {
        this.siglaClube1 = siglaClube1;
        this.siglaClube2 = siglaClube2;
        this.gols1 = gols1;
        this.gols2 = gols2;
        this.campeonato = campeonato;
    }

    // Getters e Setters
    public String getSiglaClube1() { return siglaClube1; }
    public void setSiglaClube1(String siglaClube1) { this.siglaClube1 = siglaClube1; }

    public String getSiglaClube2() { return siglaClube2; }
    public void setSiglaClube2(String siglaClube2) { this.siglaClube2 = siglaClube2; }

    public int getGols1() { return gols1; }
    public void setGols1(int gols1) { this.gols1 = gols1; }

    public int getGols2() { return gols2; }
    public void setGols2(int gols2) { this.gols2 = gols2; }

    public String getCampeonato() { return campeonato; }
    public void setCampeonato(String campeonato) { this.campeonato = campeonato; }

    // Métodos de análise da partida
    public Resultado getResultado() {
        if (gols1 > gols2) {
            return Resultado.VITORIA_CLUBE1;
        } else if (gols2 > gols1) {
            return Resultado.VITORIA_CLUBE2;
        } else {
            return Resultado.EMPATE;
        }
    }

    public String getVencedor() {
        Resultado resultado = getResultado();
        switch (resultado) {
            case VITORIA_CLUBE1:
                return siglaClube1;
            case VITORIA_CLUBE2:
                return siglaClube2;
            default:
                return null; // Empate
        }
    }

    public String getPerdedor() {
        Resultado resultado = getResultado();
        switch (resultado) {
            case VITORIA_CLUBE1:
                return siglaClube2;
            case VITORIA_CLUBE2:
                return siglaClube1;
            default:
                return null; // Empate
        }
    }

    public boolean isEmpate() {
        return gols1 == gols2;
    }

    public int getTotalGols() {
        return gols1 + gols2;
    }

    public boolean temClube(String siglaClube) {
        return siglaClube1.equals(siglaClube) || siglaClube2.equals(siglaClube);
    }

    // Métodos de conversão JSON
    public String toJson() {
        return String.format(
            "{\"sigla_clube1\":\"%s\", \"sigla_clube2\":\"%s\", \"gols1\":%d, \"gols2\":%d, \"campeonato\":\"%s\"}",
            siglaClube1, siglaClube2, gols1, gols2, campeonato
        );
    }

    /**
     * Cria um objeto Partida a partir de uma string JSON simples
     * Nota: Esta é uma implementação simplificada para evitar dependências externas
     */
    public static Partida fromJson(String json) {
        Partida partida = new Partida();
        
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
                        case "sigla_clube1":
                            partida.setSiglaClube1(value);
                            break;
                        case "sigla_clube2":
                            partida.setSiglaClube2(value);
                            break;
                        case "gols1":
                            partida.setGols1(Integer.parseInt(value));
                            break;
                        case "gols2":
                            partida.setGols2(Integer.parseInt(value));
                            break;
                        case "campeonato":
                            partida.setCampeonato(value);
                            break;
                    }
                }
            }
        }
        
        return partida;
    }

    @Override
    public String toString() {
        return String.format("%s %d x %d %s (%s)", 
                           siglaClube1, gols1, gols2, siglaClube2, campeonato);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Partida partida = (Partida) obj;
        return gols1 == partida.gols1 &&
               gols2 == partida.gols2 &&
               siglaClube1.equals(partida.siglaClube1) &&
               siglaClube2.equals(partida.siglaClube2) &&
               campeonato.equals(partida.campeonato);
    }

    @Override
    public int hashCode() {
        int result = siglaClube1.hashCode();
        result = 31 * result + siglaClube2.hashCode();
        result = 31 * result + gols1;
        result = 31 * result + gols2;
        result = 31 * result + campeonato.hashCode();
        return result;
    }
}
