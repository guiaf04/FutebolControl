package Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

	public static <T> void gravarArquivoBinario(ArrayList<T> lista, String nomeArq) throws IOException {
		byte[] dados = serializarParaBytes(lista);

		if (dados.length > 0) {
			Files.write(Paths.get(nomeArq), dados);
		} else {
			throw new IOException("Falha ao serializar a lista, o arquivo não foi gravado.");
		}
	}
}