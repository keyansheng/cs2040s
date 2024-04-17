import java.util.HashMap;
import java.util.Hashtable;

public class Solution {
    // TODO: Implement your solution here
    public static int solve(int[] arr) {
        int maxLength = -1;
        int begin = -1;
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                begin = Math.max(begin, map.remove(arr[i]));
            }
            maxLength = Math.max(maxLength, i - begin);
            map.put(arr[i], i);
        }

        return maxLength;
    }
}