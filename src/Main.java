import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        File arquivoEntrada = FileUtils.getFile();
        List<List<Grafo>> grafosEncontrados = FileUtils.getGrafosOfFile(arquivoEntrada);

        for (int test = 0; test < grafosEncontrados.size(); test++) {
            System.out.println("Case #" + (test + 1) + ":");
            for (Grafo grafo : grafosEncontrados.get(test)) {
                System.out.println(grafo.toString());
            }
            System.out.println();
        }
    }

}
