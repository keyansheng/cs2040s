import java.util.HashMap;

public class Solution {
    public static int solve(int[] arr, int target) {
        // TODO: Implement your solution here
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;

        for (int a : arr) {
            int b = target - a;
            int numB = map.getOrDefault(b, 0);
            if (numB == 0) {
                map.put(a, map.getOrDefault(a, 0) + 1);
            } else {
                map.put(b, numB - 1);
                count++;
            }
        }

        return count;
    }
}
