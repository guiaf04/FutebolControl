package model;

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
}
