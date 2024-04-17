import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TSPGraph implements IApproximateTSP {

    @Override
    public void MST(TSPMap map) {
        // TODO: implement this method
        int count = map.getCount();
        int start = 0;

        TreeMapPriorityQueue<Double, Integer> pq = new TreeMapPriorityQueue<>();
        for (int v = 0; v < count; v++) {
            pq.add(v, Double.POSITIVE_INFINITY);
        }
        pq.decreasePriority(start, 0d);

        boolean[] S = new boolean[count];

        while (!pq.isEmpty()) {
            int v = pq.extractMin();
            S[v] = true;
            for (int w = 0; w < count; w++) {
                if (!S[w]) {
                    double oldWeight = pq.lookup(w);
                    double newWeight = map.pointDistance(v, w);
                    if (newWeight < oldWeight) {
                        pq.decreasePriority(w, newWeight);
                        // decreasePriority checks if newWeight < oldWeight but setLink does not
                        map.setLink(w, v, false);
                    }
                }
            }
        }
    }

    @Override
    public void TSP(TSPMap map) {
        MST(map);
        // TODO: implement the rest of this method.
        int count = map.getCount();
        int start = 0;

        List<List<Integer>> adjacencyList = new ArrayList<>(count);
        for (int v = 0; v < count; v++) {
            adjacencyList.add(new ArrayList<>());
        }
        for (int v = 0; v < count; v++) {
            int w = map.getLink(v);
            if (w != -1) {
                adjacencyList.get(v).add(w);
                adjacencyList.get(w).add(v);
            }
        }

        Deque<Integer> stack = new ArrayDeque<>();
        stack.addFirst(start);

        boolean[] S = new boolean[count];

        // placeholder since we do not know the parent of start yet
        int u = start;
        while (!stack.isEmpty()) {
            int v = stack.removeFirst();
            S[v] = true;
            map.setLink(v, u, false);
            u = v;
            for (int w : adjacencyList.get(v)) {
                if (!S[w]) {
                    stack.addFirst(w);
                }
            }
        }
        map.setLink(start, u, false);
    }

    @Override
    public boolean isValidTour(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        int count = map.getCount();
        int start = 0;

        int visited = 0;

        boolean[] S = new boolean[count];

        int v = map.getLink(start);
        while (!S[v]) {
            if (v == -1) {
                return false;
            }
            S[v] = true;
            visited++;
            v = map.getLink(v);
        }
        return visited == count;
    }

    @Override
    public double tourDistance(TSPMap map) {
        // Note: this function should with with *any* map, and not just results from TSP().
        // TODO: implement this method
        if (!isValidTour(map)) {
            return -1;
        }

        int count = map.getCount();
        int start = 0;

        double distance = 0;

        int v = start;
        for (int i = 0; i < count; i++) {
            int w = map.getLink(v);
            distance += map.pointDistance(v, w);
            v = w;
        }
        return distance;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "../hundredpoints.txt");
        TSPGraph graph = new TSPGraph();

        graph.MST(map);
        // graph.TSP(map);
        // System.out.println(graph.isValidTour(map));
        // System.out.println(graph.tourDistance(map));
    }
}
