package streams;

import Utils.Desempacotamento;
import model.*;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClubeInputStream extends InputStream implements Serializable {
    private static final long serialVersionUID = 1L;

    private InputStream inputStream;
    private ArrayList<Clube> clubes;
    private int numClubes;


    public ClubeInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.clubes = new ArrayList<>();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    public ArrayList<Clube> readSystem() throws IOException {
        Scanner sc = new Scanner(inputStream);

        System.out.println("Informa o número de clubes:");
        numClubes = sc.nextInt();
        sc.nextLine(); // Consumir a nova linha após o número

        // Para cada clube
        for (int i = 0; i < numClubes; i++) {
            System.out.println("Informa o nome do clube " + (i + 1) + ":");
            String nome = sc.nextLine();

            System.out.println("Informa a cidade do clube " + (i + 1) + ":");
            String cidade = sc.nextLine();

            Clube clube = new Clube();
            clube.setNome(nome);
            clube.setCidade(cidade);
            clubes.add(clube);
        }

        sc.close();
        return clubes;
    }

    public ArrayList<Clube> readTcp() throws IOException {
        DataInputStream dis = new DataInputStream(this.inputStream);

        int length = dis.readInt();
        System.out.println("Servidor: Recebendo " + length + " bytes...");

        if (length > 0) {
            byte[] dadosRecebidos = new byte[length];
            dis.readFully(dadosRecebidos);

            System.out.println("Servidor: Desempacotando os objetos...");
            return Desempacotamento.lerArrayDeBytes(dadosRecebidos);
        }
        return new ArrayList<>();
    }
        @Override
    public void close() throws IOException {
        inputStream.close();
        super.close();
    }
}
