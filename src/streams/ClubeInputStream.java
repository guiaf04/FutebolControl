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
    private ArrayList<Object> clubes;
    private int numClubes;


    public ClubeInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.clubes = new ArrayList<>();
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    public ArrayList<Object> readSystem() throws IOException {
        Scanner sc = new Scanner(inputStream);

        System.out.println("Informa o número de clubes:");
        numClubes = sc.nextInt();

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

    public ArrayList<Object> readTcp() throws IOException {
        int length = inputStream.read();
        System.out.println("Servidor: Recebendo " + length + " bytes...");

        if (length > 0) {
            // 2. Ler EXATAMENTE essa quantidade de bytes
            byte[] dadosRecebidos = new byte[length];
            inputStream.readNBytes(dadosRecebidos, 0, dadosRecebidos.length);

            // 3. USAR O NOVO MÉTODO para desempacotar os bytes em objetos
            System.out.println("Servidor: Desempacotando os objetos...");
            return Desempacotamento.lerArrayDeBytes(dadosRecebidos);
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        super.close();
    }
}
