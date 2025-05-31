package streams;

import model.*;
import service.ClubeService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClubeOutputStream extends OutputStream implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Clube> clubes;
    private int numClubes;
    private int[] numBytes;
    private OutputStream outputStream;
    private int currentClube;
    private int currentByte;

    public ClubeOutputStream(ArrayList<Clube> clubes, int numClubes, OutputStream outputStream) {
        this.clubes = clubes;
        this.numClubes = numClubes;
        this.numBytes = new int[100];
        this.outputStream = outputStream;
        this.currentClube = 0;
        this.currentByte = 0;
    }

    public ClubeOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setClubes(ArrayList<Clube> clubes) {
        this.clubes = clubes;
    }

    @Override
    public void write(int b) throws IOException {
        outputStream.write(b);
    }

    public void enviarDados(byte[] conteudo) throws IOException {
        DataOutputStream dos = new DataOutputStream(this.outputStream);

        System.out.println("Enviando " + conteudo.length + " bytes de dados.");

        dos.writeInt(conteudo.length);

        dos.write(conteudo);

        dos.flush();
    }

    @Override
    public void close() throws IOException {
        outputStream.close();
        super.close();
    }
}
