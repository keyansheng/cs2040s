import java.util.Arrays;

class InversionCounter {

    public static long countSwaps(int[] arr) {
        if (arr.length == 1) {
            return 0;
        }
        long swaps = 0;
        int[] one = Arrays.copyOfRange(arr, 0, arr.length / 2);
        int[] two = Arrays.copyOfRange(arr, arr.length / 2, arr.length);
        swaps += countSwaps(one);
        swaps += countSwaps(two);
        for (int i = 0; i < one.length; i++) {
            arr[i] = one[i];
        }
        for (int i = 0; i < two.length; i++) {
            arr[one.length + i] = two[i];
        }
        swaps += mergeAndCount(arr, 0, one.length - 1, one.length, arr.length - 1);
        return swaps;
    }

    /**
     * Given an input array so that arr[left1] to arr[right1] is sorted and arr[left2] to arr[right2] is sorted
     * (also left2 = right1 + 1), merges the two so that arr[left1] to arr[right2] is sorted, and returns the
     * minimum amount of adjacent swaps needed to do so.
     */
    public static long mergeAndCount(int[] arr, int left1, int right1, int left2, int right2) {
        if (arr[right1] <= arr[left2]) {
            return 0;
        }
        long swaps = 0;
        while (left1 <= right1 && left2 <= right2) {
            if (arr[left1] > arr[left2]) {
                int temp = arr[left2];
                for (int i = left2; i > left1; i--) {
                    arr[i] = arr[i - 1];
                    swaps++;
                }
                arr[left1] = temp;
                right1++;
                left2++;
            }
            left1++;
        }
        return swaps;
    }
}
