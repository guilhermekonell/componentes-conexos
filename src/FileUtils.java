import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Guilherme Konell e Anuska Kepler Rehn
 */
public class FileUtils {

    private static String ARQUIVO_ENTRADA = "C:\\temp\\entrada.in";
    private static int QUANTIDADE_VERTICE = 0;
    private static int QUANTIDADE_ARESTA = 1;
    private static List<Character> ALFABETO = Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');

    public static File getFile() {
        return new File(ARQUIVO_ENTRADA);
    }

    public static List<List<Grafo>> getGrafosOfFile(File entrada) {
        List<List<Grafo>> testes = new ArrayList<>();

        try {
            BufferedReader arquivo = new BufferedReader(new FileReader(entrada));
            arquivo.readLine(); // quantidade de casos de teste

            while (true) {
                String lineQtdVerticesArestas = arquivo.readLine();
                // se for null, é porque chegou ao fim do arquivo
                if (lineQtdVerticesArestas == null) {
                    break;
                }

                String[] verticesArestas = lineQtdVerticesArestas.split(" ");
                List<Character> verticesDisponiveis = new ArrayList<>(ALFABETO.subList(0, Integer.parseInt(verticesArestas[QUANTIDADE_VERTICE])));

                List<Grafo> grafosTest = new ArrayList<>();
                // percorre as arestas e vai criando os grafos
                for (int i = 0; i < Integer.parseInt(verticesArestas[QUANTIDADE_ARESTA]); i++) {
                    String aresta = arquivo.readLine();
                    String[] vertices = aresta.split(" ");

                    incluiOuCriaGrafo(verticesDisponiveis, grafosTest, vertices);
                }

                // se ainda tiver aresta disponível, cria um grafo pra cada um (pois são grafos de 1 só vertice)
                verticesDisponiveis.forEach(ver -> {
                    Grafo novoGrafo = new Grafo();
                    novoGrafo.getVertices().add(ver);
                    grafosTest.add(novoGrafo);
                });

                testes.add(grafosTest);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de entrada não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return testes;
    }

    // verifica se algum dos vertices já estão em algum grafo e inclui, se não, cria um novo grafo
    private static void incluiOuCriaGrafo(List<Character> verticesDisponiveis, List<Grafo> grafosTest, String[] vertices) {
        Character vertice1 = vertices[0].charAt(0);
        Character vertice2 = vertices[1].charAt(0);

        boolean achouGrafo = false;
        for (Grafo grafo : grafosTest) {
            // se tem um dos vertice no grafo, e não tem o outro, adiciona o vertice no grafo
            if (!grafo.containsVertice(vertice1) && grafo.containsVertice(vertice2)) {
                grafo.getVertices().add(vertice1);
                verticesDisponiveis.remove(vertice1);
                achouGrafo = true;
                break;
            } else if (grafo.containsVertice(vertice1) && !grafo.containsVertice(vertice2)) {
                grafo.getVertices().add(vertice2);
                verticesDisponiveis.remove(vertice2);
                achouGrafo = true;
                break;
            } else if (grafo.containsVertice(vertice1) && grafo.containsVertice(vertice2)) {
                achouGrafo = true;
                break;
            }
        }

        // se não achou grafo que já tenha algum vertice igual, é um novo grafo
        if (!achouGrafo) {
            Grafo novoGrafo = new Grafo();
            novoGrafo.getVertices().addAll(Arrays.asList(vertice1, vertice2));
            grafosTest.add(novoGrafo);
            verticesDisponiveis.removeAll(novoGrafo.getVertices());
        }
    }


}
