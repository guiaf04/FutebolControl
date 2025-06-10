package modelo;

public class Candidato {
    private int id;
    private String nome;
    private String partido;

    public Candidato(int id, String nome, String partido) {
        this.id = id;
        this.nome = nome;
        this.partido = partido;
    }

    // Getters e Setters
    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getPartido() { return partido; }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Partido: " + partido;
    }
}