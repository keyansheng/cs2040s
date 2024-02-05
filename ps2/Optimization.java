/**
 * The Optimization class contains a static routine to find the maximum in an array that changes direction at most once.
 */
public class Optimization {

    /**
     * A set of test cases.
     */
    static int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 10, 8, 6, 4},
            {67, 65, 43, 42, 23, 17, 9, 100},
            {4, -100, -80, 15, 20, 25, 30},
            {2, 3, 4, 5, 6, 7, 8, 100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83}
    };

    /**
     * Returns the maximum item in the specified array of integers which changes direction at most once.
     *
     * @param dataArray an array of integers which changes direction at most once.
     * @return the maximum item in data Array
     */
    public static int searchMax(int[] dataArray) {
        // TODO: Implement this
        // case 1: length < 2
        if (dataArray.length == 0) {
            return 0;
        } else if (dataArray.length == 1) {
            return dataArray[0];
        }
        // case 2: v-shaped i.e. largest integer is at the start or end
        if (dataArray[0] > dataArray[1]) {
            return Math.max(dataArray[0], dataArray[dataArray.length - 1]);
        }
        // case 3: ^-shaped i.e. largest integer is somewhere in the middle
        // check if each subarray of length 2 is increasing or decreasing
        // search range: 0..length-2 (lower index of each subarray)
        int low = 0;
        int high = dataArray.length - 2;
        while (low < high) {
            int mid = low + (high - low) / 2;
            // since each element is unique, each comparison is either < or >, not =
            if (dataArray[mid] < dataArray[mid + 1]) {
                // increasing, largest integer is in right half
                low = mid + 1;
            } else {
                // decreasing, largest integer is in left half
                high = mid;
            }
        }
        // since the set of integers is a total order, any non-empty finite set of integers must have a largest integer
        // precondition/invariant: low <= indexOfLargest <= high
        // postcondition: (low <= indexOfLargest <= high) and not (low < high) implies low == indexOfLargest == high
        return Math.max(dataArray[low], dataArray[low + 1]);
    }

    /**
     * A routine to test the searchMax routine.
     */
    public static void main(String[] args) {
        for (int[] testCase : testCases) {
            System.out.println(searchMax(testCase));
        }
    }
}
