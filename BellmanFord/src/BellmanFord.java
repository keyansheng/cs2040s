import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class BellmanFord {
    // DO NOT MODIFY THE TWO STATIC VARIABLES BELOW
    public static int INF = 20000000;
    public static int NEGINF = -20000000;

    // TODO: add additional attributes and/or variables needed here, if any
    private ArrayList<ArrayList<IntPair>> adjList;
    private int[] dists;

    public BellmanFord(ArrayList<ArrayList<IntPair>> adjList) {
        // TODO: initialize your attributes here, if any
        this.adjList = adjList;
        dists = new int[adjList.size()];
    }

    // TODO: add additional methods here, if any
    private int addWeight(int v, int weight) {
        return dists[v] == INF || dists[v] == NEGINF ? dists[v] : dists[v] + weight;
    }

    private void relaxAll() {
        relaxAll(false);
    }

    private void relaxAll(boolean negWeightCycle) {
        for (int i = 0; i < dists.length; i++) {
            for (int u = 0; u < dists.length; u++) {
                ArrayList<IntPair> edges = adjList.get(u);
                for (IntPair edge : edges) {
                    int v = edge.first;
                    int weight = edge.second;
                    int newDist = addWeight(u, weight);
                    if (dists[v] > newDist) {
                        dists[v] = negWeightCycle ? NEGINF : newDist;
                    }
                }
            }
        }
    }

    public void computeShortestPaths(int source) {
        Arrays.fill(dists, INF);
        dists[source] = 0;
        relaxAll();
        relaxAll(true);
    }

    public int getDistance(int node) { 
        // TODO: implement your getDistance operation here
        return dists[node];
    }

    public static void main(String[] args) {
        ArrayList<ArrayList<IntPair>> adjList =
                new ArrayList<>(
                        Stream.of(
                                        List.of(new IntPair(3, 2)),
                                        List.of(new IntPair(2, -2)),
                                        List.of(new IntPair(1, 1)),
                                        List.<IntPair>of(),
                                        List.<IntPair>of())
                                .map(list -> new ArrayList<>(list))
                                .toList());

        BellmanFord bf = new BellmanFord(adjList);
        bf.computeShortestPaths(0);

        System.out.println(adjList);
        System.out.println(Arrays.toString(bf.dists));
    }
}
