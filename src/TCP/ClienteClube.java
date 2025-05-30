package TCP;

import Utils.Empacotamento;
import streams.ClubeInputStream;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class ClienteClube {
    public static void main(String args[]) {
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("localhost", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            ArrayList<Object> clubes = new ArrayList<>();

            ClubeInputStream clubeInputStream = new ClubeInputStream(System.in);

            // Serializa os objetos e grava no arquivo binário
            byte[] conteudo = Empacotamento.serializarParaBytes(clubes);

            System.out.println("Enviando " + conteudo.length + " bytes de dados.");
            out.writeInt(conteudo.length);

            // 2. Enviar o ARRAY de bytes em si.
            out.write(conteudo);
            out.flush(); // Garante que todos os dados foram enviados da buffer

            String data = in.readUTF(); // read a line of data from the stream
            System.out.println("Received: " + data);
        } catch (UnknownHostException e) {
            System.out.println("Socket:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("readline:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {
                    System.out.println("close:" + e.getMessage());
                }
        }
    }
}
