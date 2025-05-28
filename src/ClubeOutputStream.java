import model.Clube;
import model.SerieA;
import model.SerieB;
import model.SocioTorcedor;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Classe ClubeOutputStream - Subclasse de OutputStream para enviar dados de clubes
 * Implementa a funcionalidade de enviar dados de um conjunto de objetos model.Clube
 */
public class ClubeOutputStream extends OutputStream implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Clube[] clubes;
    private int numClubes;
    private int[] numBytes;
    private OutputStream outputStream;
    private int currentClube;
    private int currentByte;
    
    /**
     * Construtor da classe ClubeOutputStream
     * @param clubes Array de objetos model.Clube a serem transmitidos
     * @param numClubes Número de objetos que terão dados enviados
     * @param numBytes Array com o número de bytes para cada objeto
     * @param outputStream OutputStream de destino
     */
    public ClubeOutputStream(Clube[] clubes, int numClubes, int[] numBytes, OutputStream outputStream) {
        this.clubes = clubes;
        this.numClubes = numClubes;
        this.numBytes = numBytes;
        this.outputStream = outputStream;
        this.currentClube = 0;
        this.currentByte = 0;
    }
    
    /**
     * Método para escrever um byte no stream
     * @param b Byte a ser escrito
     * @throws IOException Se ocorrer um erro de I/O
     */
    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }
    
    /**
     * Método para enviar todos os dados dos clubes para o stream de destino
     * @throws IOException Se ocorrer um erro de I/O
     */
    public void enviarDados() throws IOException {
        // Escreve o número de clubes
        outputStream.write(numClubes);
        
        // Para cada clube
        for (int i = 0; i < numClubes; i++) {
            Clube clube = clubes[i];
            
            // Escreve o nome do clube
            byte[] nomeBytes = clube.getNome().getBytes();
            outputStream.write(nomeBytes.length);
            outputStream.write(nomeBytes);
            
            // Escreve a cidade do clube
            byte[] cidadeBytes = clube.getCidade().getBytes();
            outputStream.write(cidadeBytes.length);
            outputStream.write(cidadeBytes);
            
            // Escreve o ano de fundação
            outputStream.write(clube.getAnoFundacao());
            
            // Se for um campeonato (model.SerieA, model.SerieB ou model.SerieA.Libertadores), escreve informações adicionais
            if (clube instanceof SerieA) {
                SerieA serieA = (SerieA) clube;
                outputStream.write(1); // Tipo 1 = model.SerieA
                
                // Escreve o campeão
                byte[] campeaoBytes = serieA.getCampeao().getBytes();
                outputStream.write(campeaoBytes.length);
                outputStream.write(campeaoBytes);
            } else if (clube instanceof SerieB) {
                SerieB serieB = (SerieB) clube;
                outputStream.write(2); // Tipo 2 = model.SerieB
                
                // Escreve o número de equipes
                outputStream.write(serieB.getNumeroEquipes());
            } else if (clube instanceof SerieA.Libertadores) {
                SerieA.Libertadores libertadores = (SerieA.Libertadores) clube;
                outputStream.write(3); // Tipo 3 = model.SerieA.Libertadores
                
                // Escreve a fase atual
                byte[] faseBytes = libertadores.getFaseAtual().getBytes();
                outputStream.write(faseBytes.length);
                outputStream.write(faseBytes);
            } else if (clube instanceof SocioTorcedor) {
                SocioTorcedor socio = (SocioTorcedor) clube;
                outputStream.write(4); // Tipo 4 = model.SocioTorcedor
                
                // Escreve o nome do sócio
                byte[] nomeBytes2 = socio.getNome().getBytes();
                outputStream.write(nomeBytes2.length);
                outputStream.write(nomeBytes2);
            } else {
                outputStream.write(0); // Tipo 0 = model.Clube genérico
            }
        }
        
        // Flush para garantir que todos os dados foram enviados
        outputStream.flush();
    }
    
    /**
     * Método para fechar o stream
     * @throws IOException Se ocorrer um erro de I/O
     */
    @Override
    public void close() throws IOException {
        outputStream.close();
        super.close();
    }
}
