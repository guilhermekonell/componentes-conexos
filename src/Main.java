import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        File arquivoEntrada = FileUtils.getFile();
        List<List<Grafo>> grafosEncontrados = FileUtils.getGrafosOfFile(arquivoEntrada);

        for (int test = 0; test < grafosEncontrados.size(); test++) {
            System.out.println("Case #" + (test + 1) + ":");
            // necessário agrupar alguns grafos que tem vertices iguais, mas que ficaram em grafos diferentes
            List<Grafo> grafosAgrupados = agrupaArestasComVerticesIguais(grafosEncontrados.get(test));
            for (Grafo grafo : grafosAgrupados) {
                System.out.println(grafo.toString());
            }
            System.out.println(grafosAgrupados.size() + " connected components\n");
        }
    }

    private static List<Grafo> agrupaArestasComVerticesIguais(List<Grafo> grafosTest) {
        List<Grafo> toReturn = new ArrayList<>();

        // percorre cada grafo do test
        grafosTest.forEach(grafoAtual -> {
            boolean achouGrafo = false;
            // verifica se tem algum grafo que contém algum vertice igual dos vertices grafo atual
            for (Grafo grafoToReturn : toReturn) {
                for (Character vertice : grafoToReturn.getVertices()) {
                    // se encontou vertice igual, adiciona os vertices do grafo atual
                    // no grafo com vertice igual encontrado
                    if (grafoAtual.containsVertice(vertice)) {
                        grafoToReturn.getVertices().addAll(grafoAtual.getVertices());
                        achouGrafo = true;
                        break;
                    }
                }
            }

            // se não achou grafo com vertice igual, adiciona como um novo grafo
            if (!achouGrafo) {
                toReturn.add(grafoAtual);
            }
        });

        // remove vertices repetidos e ordena em ordem alfabetica
        toReturn.forEach(g -> {
            g.setVertices(g.getVertices().stream().distinct().collect(Collectors.toList()));
            g.getVertices().sort(Character::compareTo);
        });

        return toReturn;
    }

}
