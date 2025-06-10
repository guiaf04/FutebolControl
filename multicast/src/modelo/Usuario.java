package modelo;

public class Usuario {
    public enum TipoUsuario { ELEITOR, ADMIN }

    private String login;
    private String senha;
    private TipoUsuario tipo;

    public Usuario(String login, String senha, TipoUsuario tipo) {
        this.login = login;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters
    public String getLogin() { return login; }
    public String getSenha() { return senha; }
    public TipoUsuario getTipo() { return tipo; }
}