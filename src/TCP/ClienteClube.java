package TCP;

import Utils.Empacotamento;
import model.Clube;
import streams.ClubeInputStream;
import streams.ClubeOutputStream;

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

            ClubeInputStream clubeInputStream = new ClubeInputStream(System.in);
            ArrayList<Clube> clubes = clubeInputStream.readSystem();
            byte[] conteudo = Empacotamento.serializarParaBytes(clubes);

            ClubeOutputStream out = new ClubeOutputStream(s.getOutputStream());
            out.enviarDados(conteudo);

            System.out.println("CLIENTE: Aguardando resposta do servidor...");
            ClubeInputStream in = new ClubeInputStream(s.getInputStream());
            ArrayList<Clube> clubesComEstatisticas = in.readTcp();

            System.out.println("CLIENTE: Resposta recebida! Clubes com estatísticas:");
            clubesComEstatisticas.forEach(clube -> {
                System.out.println(" - " + clube.getNome() + " | " + clube.getEstatisticas());
            });

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
