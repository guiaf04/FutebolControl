package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um campeonato
 */
public class Campeonato {
    private String nome;
    private int ano;
    private List<String> clubes;

    // Construtores
    public Campeonato() {
        this("", 0, new ArrayList<>());
    }

    public Campeonato(String nome, int ano) {
        this(nome, ano, new ArrayList<>());
    }

    public Campeonato(String nome, int ano, List<String> clubes) {
        this.nome = nome;
        this.ano = ano;
        this.clubes = clubes != null ? new ArrayList<>(clubes) : new ArrayList<>();
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public List<String> getClubes() { return new ArrayList<>(clubes); }
    public void setClubes(List<String> clubes) { 
        this.clubes = clubes != null ? new ArrayList<>(clubes) : new ArrayList<>(); 
    }

    // Métodos de gerenciamento de clubes
    public boolean adicionarClube(String siglaClube) {
        if (siglaClube != null && !clubes.contains(siglaClube)) {
            clubes.add(siglaClube);
            return true;
        }
        return false;
    }

    public boolean removerClube(String siglaClube) {
        return clubes.remove(siglaClube);
    }

    public boolean temClube(String siglaClube) {
        return clubes.contains(siglaClube);
    }

    public int getNumeroParticipantes() {
        return clubes.size();
    }

    // Métodos de conversão JSON
    public String toJson() {
        StringBuilder clubesJson = new StringBuilder("[");
        for (int i = 0; i < clubes.size(); i++) {
            clubesJson.append("\"").append(clubes.get(i)).append("\"");
            if (i < clubes.size() - 1) {
                clubesJson.append(",");
            }
        }
        clubesJson.append("]");

        return String.format(
            "{\"nome\":\"%s\", \"ano\":%d, \"clubes\":%s}",
            nome, ano, clubesJson.toString()
        );
    }

    /**
     * Cria um objeto Campeonato a partir de uma string JSON simples
     * Nota: Esta é uma implementação simplificada para evitar dependências externas
     */
    public static Campeonato fromJson(String json) {
        Campeonato campeonato = new Campeonato();
        
        // Parse simples do JSON (sem biblioteca externa)
        json = json.trim();
        if (json.startsWith("{") && json.endsWith("}")) {
            json = json.substring(1, json.length() - 1);
            
            // Extrair nome
            int nomeIdx = json.indexOf("\"nome\":");
            if (nomeIdx != -1) {
                int startQuote = json.indexOf("\"", nomeIdx + 7);
                int endQuote = json.indexOf("\"", startQuote + 1);
                if (startQuote != -1 && endQuote != -1) {
                    campeonato.setNome(json.substring(startQuote + 1, endQuote));
                }
            }

            // Extrair ano
            int anoIdx = json.indexOf("\"ano\":");
            if (anoIdx != -1) {
                int valueStart = anoIdx + 6;
                int valueEnd = json.indexOf(",", valueStart);
                if (valueEnd == -1) valueEnd = json.indexOf("}", valueStart);
                if (valueEnd != -1) {
                    String anoStr = json.substring(valueStart, valueEnd).trim();
                    try {
                        campeonato.setAno(Integer.parseInt(anoStr));
                    } catch (NumberFormatException e) {
                        // Valor padrão
                        campeonato.setAno(0);
                    }
                }
            }

            // Extrair clubes (array)
            int clubesIdx = json.indexOf("\"clubes\":");
            if (clubesIdx != -1) {
                int arrayStart = json.indexOf("[", clubesIdx);
                int arrayEnd = json.indexOf("]", arrayStart);
                if (arrayStart != -1 && arrayEnd != -1) {
                    String clubesArray = json.substring(arrayStart + 1, arrayEnd);
                    if (!clubesArray.trim().isEmpty()) {
                        String[] clubesList = clubesArray.split(",");
                        List<String> clubes = new ArrayList<>();
                        for (String clube : clubesList) {
                            String cleanClube = clube.trim().replaceAll("\"", "");
                            if (!cleanClube.isEmpty()) {
                                clubes.add(cleanClube);
                            }
                        }
                        campeonato.setClubes(clubes);
                    }
                }
            }
        }
        
        return campeonato;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) - %d clubes", nome, ano, getNumeroParticipantes());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Campeonato that = (Campeonato) obj;
        return nome != null ? nome.equals(that.nome) : that.nome == null;
    }

    @Override
    public int hashCode() {
        return nome != null ? nome.hashCode() : 0;
    }
}
