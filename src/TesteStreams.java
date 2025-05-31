import model.Clube;
import model.SerieB;

import java.io.Serializable;

/**
 * Classe para testar a serialização e os streams personalizados
 */
public class TesteStreams implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static void main(String[] args) {
        //try {
//            // Cria alguns clubes para teste
//            Clube[] clubes = criarClubesTeste();
//
//            // Define o número de bytes para cada clube (simplificado para o exemplo)
//            int[] numBytes = {100, 100, 100, 100};
//
//            // Teste com saída padrão (System.out)
//            System.out.println("=== Teste com System.out ===");
//            ClubeOutputStream outStream1 = new ClubeOutputStream(clubes, clubes.length, numBytes, System.out);
//            outStream1.enviarDados();
//
//            // Teste com arquivo
//            System.out.println("\n=== Teste com FileOutputStream ===");
//            FileOutputStream fileOut = new FileOutputStream("clubes.dat");
//            ClubeOutputStream outStream2 = new ClubeOutputStream(clubes, clubes.length, numBytes, fileOut);
//            outStream2.enviarDados();
//            outStream2.close();
//
//            // Teste de leitura do arquivo
//            System.out.println("\n=== Teste com FileInputStream ===");
//            FileInputStream fileIn = new FileInputStream("clubes.dat");
//            ClubeInputStream inStream = new ClubeInputStream(fileIn);
//            Clube[] clubesLidos = inStream.readSystem();
//            inStream.close();
//
//            // Exibe os clubes lidos
//            for (Clube clube : clubesLidos) {
//                System.out.println(clube.exibirInformacoes());
//                System.out.println("----------------------------");
//            }
            
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    
    /**
     * Método auxiliar para criar clubes de teste
     * @return Array de clubes para teste
     */
    private static Clube[] criarClubesTeste() {
        Clube[] clubes = new Clube[4];
        
        // model.Clube genérico
        clubes[0] = new Clube("Flamengo", "Rio de Janeiro", 1895, "Maracanã", 45);
        
        // model.SerieA
        
        // model.SerieB
        String[] promovidos = {"Vitória", "Juventude", "Criciúma", "Atlético-GO"};
        String[] rebaixados = {"Londrina", "Tombense", "Chapecoense", "ABC"};
        SerieB serieB = new SerieB("Campeonato Brasileiro Série B", "Rio de Janeiro", 1971,
                                  "Diversos", 51, 2023, 20, promovidos, rebaixados, 38);
        

        
        return clubes;
    }
}
