package modelo;

import java.util.Map;

public class Mensagem {
    private String tipo; // Ex: "LOGIN", "VOTAR", "LISTA_CANDIDATOS"
    private Map<String, Object> payload;

    public Mensagem(String tipo, Map<String, Object> payload) {
        this.tipo = tipo;
        this.payload = payload;
    }

    // Getters e Setters
    public String getTipo() { return tipo; }
    public Map<String, Object> getPayload() { return payload; }
}