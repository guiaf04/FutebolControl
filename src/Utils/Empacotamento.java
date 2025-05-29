package Utils;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Empacotamento {

	/**
	 * NOVO MÉTODO (RECOMENDADO): Serializa uma lista de objetos diretamente para um array de bytes em memória.
	 * Perfeito para enviar dados pela rede.
	 * @param lista A lista de objetos a ser serializada.
	 * @return Um array de bytes contendo os dados do objeto serializado.
	 */
	public static byte[] serializarParaBytes(ArrayList<Object> lista) {
		// Usa um ByteArrayOutputStream para escrever os dados do objeto em memória
		try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
				 ObjectOutputStream objOutput = new ObjectOutputStream(byteStream)) {

			// Escreve o objeto no stream de bytes
			objOutput.writeObject(lista);

			// Retorna o conteúdo do stream como um array de bytes pronto para ser enviado
			return byteStream.toByteArray();

		} catch (IOException e) {
			System.err.println("Erro ao serializar objeto para bytes: " + e.getMessage());
			e.printStackTrace();
			// Retorna um array vazio em caso de erro para evitar NullPointerException
			return new byte[0];
		}
	}

	/**
	 * MÉTODO ANTIGO (NÃO RECOMENDADO PARA REDE): Serializa e grava os objetos em um arquivo binário.
	 * @param lista Lista de objetos a serem gravados.
	 * @param nomeArq Nome do arquivo a ser criado no disco.
	 */
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