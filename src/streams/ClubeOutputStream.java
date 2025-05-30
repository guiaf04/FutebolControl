package streams;

import model.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ClubeOutputStream extends OutputStream implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Object> clubes;
    private int numClubes;
    private int[] numBytes;
    private OutputStream outputStream;
    private int currentClube;
    private int currentByte;

    public ClubeOutputStream(ArrayList<Object> clubes, int numClubes, OutputStream outputStream) {
        this.clubes = clubes;
        this.numClubes = numClubes;
        this.numBytes = new int[100];
        this.outputStream = outputStream;
        this.currentClube = 0;
        this.currentByte = 0;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    public void enviarDados() throws IOException {
        // Escreve o número de clubes
        outputStream.write(numClubes);
        
        // Para cada clube
        for (int i = 0; i < numClubes; i++) {
            Clube clube = (Clube) clubes.get(i);
            
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
        }
        
        // Flush para garantir que todos os dados foram enviados
        outputStream.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
        super.close();
    }
}
