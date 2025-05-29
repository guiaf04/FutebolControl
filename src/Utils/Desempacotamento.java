package Utils;

import java.io.*;
import java.util.ArrayList;

public class Desempacotamento {

	/**
	 * Método original: Desserializa objetos gravados em um ARQUIVO binário.
	 * @param nomeArq O caminho do arquivo a ser lido.
	 * @return A lista de objetos lida do arquivo.
	 */
	public static ArrayList<Object> lerArquivoBinario(String nomeArq) {
		ArrayList<Object> lista = new ArrayList<>();
		try {
			File arq = new File(nomeArq);
			if (arq.exists()) {
				ObjectInputStream objInput = new ObjectInputStream(new FileInputStream(arq));
				lista = (ArrayList<Object>) objInput.readObject();
				objInput.close();
			}
		} catch (IOException erro1) {
			System.out.printf("Erro ao ler arquivo: %s", erro1.getMessage());
		} catch (ClassNotFoundException erro2) {
			System.out.printf("Erro de classe não encontrada: %s", erro2.getMessage());
		}
		return lista;
	}

	/**
	 * NOVO MÉTODO: Desserializa objetos a partir de um ARRAY DE BYTES.
	 * Ideal para desempacotar dados recebidos pela rede.
	 * * @param dadosBytes O array de bytes que contém os objetos serializados.
	 * @return A lista de objetos desempacotada dos bytes.
	 */
	public static ArrayList<Object> lerArrayDeBytes(byte[] dadosBytes) {
		ArrayList<Object> lista = new ArrayList<>();

		// O try-with-resources garante que os streams serão fechados automaticamente.
		try (ByteArrayInputStream byteStream = new ByteArrayInputStream(dadosBytes);
				 ObjectInputStream objInput = new ObjectInputStream(byteStream)) {

			// Lê o objeto diretamente do stream de bytes em memória
			lista = (ArrayList<Object>) objInput.readObject();

		} catch (IOException erro1) {
			System.out.printf("Erro de I/O ao desempacotar bytes: %s", erro1.getMessage());
			erro1.printStackTrace();
		} catch (ClassNotFoundException erro2) {
			System.out.printf("Erro: Classe não encontrada durante desempacotamento: %s", erro2.getMessage());
			erro2.printStackTrace();
		}

		return lista;
	}
}