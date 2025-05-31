package Utils;

import java.io.*;
import java.util.ArrayList;

public class Desempacotamento {

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

	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> lerArrayDeBytes(byte[] dadosBytes) {
		ArrayList<T> lista = new ArrayList<>();

		// Se os dados de entrada forem nulos ou vazios, retorna uma lista vazia para segurança
		if (dadosBytes == null || dadosBytes.length == 0) {
			return lista;
		}

		try (ByteArrayInputStream byteStream = new ByteArrayInputStream(dadosBytes);
				 ObjectInputStream objInput = new ObjectInputStream(byteStream)) {

			lista = (ArrayList<T>) objInput.readObject();

		} catch (IOException erro1) {
			System.err.printf("Erro de I/O ao desempacotar bytes: %s%n", erro1.getMessage());
			erro1.printStackTrace();
		} catch (ClassNotFoundException erro2) {
			System.err.printf("Erro: Classe não encontrada durante desempacotamento: %s%n", erro2.getMessage());
			erro2.printStackTrace();
		}

		return lista;
	}
}