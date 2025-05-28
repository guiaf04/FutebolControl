import model.Clube;
import model.SerieA;
import model.SerieB;
import model.SocioTorcedor;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

/**
 * Classe ClubeInputStream - Subclasse de InputStream para ler dados de clubes
 * Implementa a funcionalidade de ler dados de um conjunto de objetos model.Clube
 */
public class ClubeInputStream extends InputStream implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private InputStream inputStream;
    private Clube[] clubes;
    private int numClubes;
    
    /**
     * Construtor da classe ClubeInputStream
     * @param inputStream InputStream de origem
     */
    public ClubeInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    /**
     * Método para ler um byte do stream
     * @return O byte lido, ou -1 se o fim do stream foi alcançado
     * @throws IOException Se ocorrer um erro de I/O
     */
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
    
    /**
     * Método para ler todos os dados dos clubes do stream de origem
     * @return Array de objetos model.Clube lidos
     * @throws IOException Se ocorrer um erro de I/O
     */
    public Clube[] lerDados() throws IOException {
        // Lê o número de clubes
        numClubes = inputStream.read();
        clubes = new Clube[numClubes];
        
        // Para cada clube
        for (int i = 0; i < numClubes; i++) {
            // Lê o nome do clube
            int nomeLength = inputStream.read();
            byte[] nomeBytes = new byte[nomeLength];
            inputStream.read(nomeBytes);
            String nome = new String(nomeBytes);
            
            // Lê a cidade do clube
            int cidadeLength = inputStream.read();
            byte[] cidadeBytes = new byte[cidadeLength];
            inputStream.read(cidadeBytes);
            String cidade = new String(cidadeBytes);
            
            // Lê o ano de fundação
            int anoFundacao = inputStream.read();
            
            // Lê o tipo de clube
            int tipo = inputStream.read();
            
            // Cria o objeto apropriado com base no tipo
            switch (tipo) {
                case 1: // model.SerieA
                    // Lê o campeão
                    int campeaoLength = inputStream.read();
                    byte[] campeaoBytes = new byte[campeaoLength];
                    inputStream.read(campeaoBytes);
                    String campeao = new String(campeaoBytes);
                    
                    SerieA serieA = new SerieA();
                    serieA.setNome(nome);
                    serieA.setCidade(cidade);
                    serieA.setAnoFundacao(anoFundacao);
                    serieA.setCampeao(campeao);
                    clubes[i] = serieA;
                    break;
                    
                case 2: // model.SerieB
                    // Lê o número de equipes
                    int numeroEquipes = inputStream.read();
                    
                    SerieB serieB = new SerieB();
                    serieB.setNome(nome);
                    serieB.setCidade(cidade);
                    serieB.setAnoFundacao(anoFundacao);
                    serieB.setNumeroEquipes(numeroEquipes);
                    clubes[i] = serieB;
                    break;
                    
                case 3: // model.SerieA.Libertadores
                    // Lê a fase atual
                    int faseLength = inputStream.read();
                    byte[] faseBytes = new byte[faseLength];
                    inputStream.read(faseBytes);
                    String fase = new String(faseBytes);
                    
                    SerieA.Libertadores libertadores = new SerieA.Libertadores();
                    libertadores.setNome(nome);
                    libertadores.setCidade(cidade);
                    libertadores.setAnoFundacao(anoFundacao);
                    libertadores.setFaseAtual(fase);
                    clubes[i] = libertadores;
                    break;
                    
                case 4: // model.SocioTorcedor
                    // Lê o nome do sócio
                    int nomeSocioLength = inputStream.read();
                    byte[] nomeSocioBytes = new byte[nomeSocioLength];
                    inputStream.read(nomeSocioBytes);
                    String nomeSocio = new String(nomeSocioBytes);
                    
                    SocioTorcedor socio = new SocioTorcedor();
                    socio.setNome(nomeSocio);
                    socio.setCidade(cidade);
                    socio.setAnoFundacao(anoFundacao);
                    clubes[i] = socio;
                    break;
                    
                default: // model.Clube genérico
                    Clube clube = new Clube();
                    clube.setNome(nome);
                    clube.setCidade(cidade);
                    clube.setAnoFundacao(anoFundacao);
                    clubes[i] = clube;
                    break;
            }
        }
        
        return clubes;
    }
    
    /**
     * Método para fechar o stream
     * @throws IOException Se ocorrer um erro de I/O
     */
    @Override
    public void close() throws IOException {
        inputStream.close();
        super.close();
    }
}
