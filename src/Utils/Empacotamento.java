package Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Empacotamento {

	public static <T> byte[] serializarParaBytes(ArrayList<T> lista) {
		// Usa um ByteArrayOutputStream para escrever os dados do objeto em memória
		try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				 ObjectOutputStream objOutput = new ObjectOutputStream(byteStream)) {

			objOutput.writeObject(lista);

			return byteStream.toByteArray();

		} catch (IOException e) {
			System.err.println("Erro ao serializar objeto para bytes: " + e.getMessage());
			e.printStackTrace();
			// Retorna um array vazio em caso de erro para evitar NullPointerException
			return new byte[0];
		}
	}

	public static void gravarArquivoBinario(ArrayList<Object> lista, String nomeArq) {
		// Este método apenas grava em disco, não retorna os bytes.
		try (FileOutputStream arq = new FileOutputStream(nomeArq);
				 ObjectOutputStream objOutput = new ObjectOutputStream(arq)) {

			objOutput.writeObject(lista);
		} catch (IOException e) {
			System.out.println("Erro ao gravar arquivo binário: " + e.getMessage());
		}
	}
}