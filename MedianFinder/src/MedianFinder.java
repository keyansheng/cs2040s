import java.util.TreeMap;
import java.util.TreeSet;

public class MedianFinder {

    // TODO: Include your data structures here
    private final TreeSet<Int> lessThanMedian = new TreeSet<>();
    private final TreeSet<Int> atLeastMedian = new TreeSet<>();

    public MedianFinder() {
        // TODO: Construct/Initialise your data structures here
    }

    public void insert(int x) {
        // TODO: Implement your insertion operation here
        Int X = new Int(x);

        if (atLeastMedian.isEmpty()) {
            atLeastMedian.add(X);
            return;
        }

        if (lessThanMedian.size() < atLeastMedian.size()) {
            if (X.compareTo(atLeastMedian.first()) <= 0) {
                lessThanMedian.add(X);
            } else {
                lessThanMedian.add(atLeastMedian.pollFirst());
                atLeastMedian.add(X);
            }
        } else {
            if (X.compareTo(lessThanMedian.last()) < 0) {
                atLeastMedian.add(lessThanMedian.pollLast());
                lessThanMedian.add(X);
            } else {
                atLeastMedian.add(X);
            }
        }
    }

    public int getMedian() {
        // TODO: Implement your getMedian operation here
        Int median = atLeastMedian.pollFirst();
        if (lessThanMedian.size() > atLeastMedian.size()) {
            atLeastMedian.add(lessThanMedian.pollLast());
        }
        return median.value;
    }

    private static class Int implements Comparable<Int> {
        private static int count = 0;
        private int id;
        private int value;

        private Int(int value) {
            id = count;
            count++;
            this.value = value;
        }

        @Override
        public int compareTo(Int integer) {
            int compare = Integer.compare(value, integer.value);
            return compare == 0 ? Integer.compare(id, integer.id) : compare;
        }
    }
}
