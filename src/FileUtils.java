import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {

    private static String ARQUIVO_ENTRADA = "C:\\temp\\entrada.in";
    private static int QUANTIDADE_VERTICE = 0;
    private static int QUANTIDADE_ARESTA = 1;
    private static List<Character> ALFABETO = Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z');

    public static File getFile() {
        //System.out.println("Lendo o arquivo de entrada no diretório " + ARQUIVO_ENTRADA);
        return new File(ARQUIVO_ENTRADA);
    }

    public static List<List<Grafo>> getGrafosOfFile(File entrada) {
        List<List<Grafo>> grafos = new ArrayList<>();

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
                    Character v1 = vertices[0].charAt(0);
                    Character v2 = vertices[1].charAt(0);

                    boolean achouGrafo = false;
                    for (Grafo grafo : grafosTest) {
                        // se tem um dos vertice no grafo, e não tem o outro, adiciona o vertice no grafo
                        if (!grafo.containsVertice(v1) && grafo.containsVertice(v2)) {
                            grafo.getVertices().add(v1);
                            verticesDisponiveis.remove(v1);
                            achouGrafo = true;
                            break;
                        } else if (grafo.containsVertice(v1) && !grafo.containsVertice(v2)) {
                            grafo.getVertices().add(v2);
                            verticesDisponiveis.remove(v2);
                            achouGrafo = true;
                            break;
                        } else if (grafo.containsVertice(v1) && grafo.containsVertice(v2)) {
                            achouGrafo = true;
                            break;
                        }
                    }

                    // se não achou grafo, é um novo grafo
                    if (!achouGrafo) {
                        Grafo novoGrafo = new Grafo();
                        novoGrafo.getVertices().addAll(Arrays.asList(v1, v2));
                        grafosTest.add(novoGrafo);
                        verticesDisponiveis.removeAll(novoGrafo.getVertices());
                    }
                }

                // se ainda tiver aresta disponível, cria um grafo pra cada um (pois são grafos )
                verticesDisponiveis.forEach(ver -> {
                    Grafo novoGrafo = new Grafo();
                    novoGrafo.getVertices().add(ver);
                    grafosTest.add(novoGrafo);
                });

                grafos.add(agrupaArestasComVerticesIguais(grafosTest));
            }


        } catch (FileNotFoundException e) {
            System.out.println("Arquivo de entrada não encontrado!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return grafos;
    }

    private static List<Grafo> agrupaArestasComVerticesIguais(List<Grafo> grafosTest) {
        List<Grafo> toReturn = new ArrayList<>();

        grafosTest.forEach(grafo -> {
            boolean achouGrafo = false;
            for (Grafo grafoToReturn : toReturn) {
                for (Character vertice : grafoToReturn.getVertices()) {
                    if (grafo.containsVertice(vertice)) {
                        grafoToReturn.getVertices().addAll(grafo.getVertices());
                        achouGrafo = true;
                        break;
                    }
                }
            }

            if (!achouGrafo) {
                toReturn.add(grafo);
            }
        });

        // remove vertices repetidos e ordena
        toReturn.forEach(g -> {
            g.setVertices(g.getVertices().stream().distinct().collect(Collectors.toList()));
            g.getVertices().sort(Character::compareTo);
        });

        return toReturn;
    }

}
