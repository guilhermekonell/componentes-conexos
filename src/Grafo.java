import java.util.ArrayList;
import java.util.List;

/**
 * Guilherme Konell e Anuska Kepler Rehn
 */
public class Grafo {

    private List<Character> vertices;

    public Grafo() {
        this.vertices = new ArrayList<>();
    }

    public List<Character> getVertices() {
        return vertices;
    }

    public void setVertices(List<Character> vertices) {
        this.vertices = vertices;
    }

    public boolean containsVertice(Character vertice) {
        return this.vertices.stream().anyMatch(v -> v.equals(vertice));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Character vertice : vertices) {
            stringBuilder.append(vertice).append(",");
        }

        return stringBuilder.toString();
    }
}
