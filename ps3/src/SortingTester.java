import java.util.Random;

public class SortingTester {
    public static boolean checkSort(ISort sorter, int size) {
        Random random = new Random(size);
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(random.nextInt(), 0);
        }
        long sortCost = sorter.sort(testArray);
        for (int i = 1; i < size; i++) {
            if (testArray[i - 1].compareTo(testArray[i]) == 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isStable(ISort sorter, int size) {
        Random random = new Random(size);
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(random.nextInt(size / 2), -i);
        }
        long sortCost = sorter.sort(testArray);
        for (int i = 1; i < size; i++) {
            if (testArray[i - 1].compareTo(testArray[i]) == 0
                    && testArray[i - 1].getValue() <= testArray[i].getValue()) {
                return false;
            }
        }
        return true;
    }

    public static long sortedArrayCost(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size; i++) {
            testArray[i] = new KeyValuePair(i, 0);
        }
        return sorter.sort(testArray);
    }

    public static long sortedArrayExceptLastCost(ISort sorter, int size) {
        KeyValuePair[] testArray = new KeyValuePair[size];
        for (int i = 0; i < size - 1; i++) {
            testArray[i] = new KeyValuePair(i, 0);
        }
        testArray[size - 1] = new KeyValuePair(-1, 0);
        return sorter.sort(testArray);
    }

    public static void main(String[] args) {
        ISort[] sorters = {new SorterA(), new SorterB(), new SorterC(), new SorterD(), new SorterE(), new SorterF()};
        for (ISort sorter : sorters) {
            System.out.println(isStable(sorter, 100));
            // true false true false false true
            // A, C, F - stable
            // B, D, E - unstable
        }
        for (ISort sorter : sorters) {
            System.out.println(sorter);
            for (int size = 10; size <= 100000; size *= 10) {
                System.out.println(sortedArrayCost(sorter, size));
            }
            // A - 4.6e3, 6.6e4, 9.0e5, 1.2e7, 1.4e8  -   stable,   O(?) sorted - merge
            // B - 3.9e3, 8.2e4, 1.2e6, 1.5e7, 1.9e8  - unstable,   O(?) sorted - quick or evil
            // C - 1.2e3, 7.6e3, 6.7e4, 6.8e5, 6.1e6  -   stable,   O(n) sorted - bubble or insertion
            // D - 4.0e3, 4.1e5, 3.9e7, 3.7e9, 4.1e11 - unstable, O(n^2) sorted - selection
            // E - 1.3e4, 2.1e5, 2.6e6, 3.9e7, 4.0e8  - unstable,   O(?) sorted - quick or evil
            // F - 6.1e2, 3.8e3, 3.3e4, 3.2e5, 3.1e6  -   stable,   O(n) sorted - bubble or insertion
        }
        System.out.println(sorters[2]);
        for (int size = 10; size <= 1000; size *= 10) {
            System.out.println(sortedArrayExceptLastCost(sorters[2], size));
            // C - 6.3e3, 6.2e5, 6.1e7 - O(n^2) sorted except last - bubble
        }
        System.out.println(sorters[5]);
        for (int size = 10; size <= 100000; size *= 10) {
            System.out.println(sortedArrayExceptLastCost(sorters[5], size));
            // F - 9.0e2, 7.0e3, 6.6e4, 6.2e5, 6.8e6 - O(n) sorted except last - insertion
        }
        System.out.println(sorters[1]);
        int size = 1;
        while (checkSort(sorters[1], size)) {
            size++;
        }
        System.out.println(size);
        // B - terminates at around 3200 - evil
        System.out.println(sorters[4]);
        size = 1;
        while (checkSort(sorters[4], size)) {
            size++;
        }
        // E - doesn't terminate - quick
        // final results
        // A - merge
        // B - evil
        // C - bubble
        // D - selection
        // E - quick
        // F - insertion
    }
}
