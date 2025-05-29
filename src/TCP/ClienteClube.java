package TCP;

import Utils.Empacotamento;
import model.Clube;
import model.Partidas;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe TCP.ClienteClube - Implementa um cliente para o sistema de clubes
 * Conecta-se ao servidor e envia requisições
 */
public class ClienteClube {
    public static void main(String args[]) {
        // arguments supply message and hostname
        Socket s = null;
        try {
            int serverPort = 7896;
            s = new Socket("172.25.210.29", serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            String envio = "sistemas_distribuidos";
            System.out.println("Sent: "+envio);

            ArrayList<Object> clubes = new ArrayList<>();

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
