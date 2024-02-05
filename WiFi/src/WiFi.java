import java.util.Arrays;
import java.util.Map;

class WiFi {

    /**
     * Implement your solution here
     */
    public static double computeDistance(int[] houses, int numOfAccessPoints) {
        int firstHouse = Integer.MAX_VALUE;
        int lastHouse = Integer.MIN_VALUE;
        for (int house : houses) {
            firstHouse = Integer.min(firstHouse, house);
            lastHouse = Integer.max(lastHouse, house);
        }
        double low = 0.0;
        double high = (lastHouse - firstHouse) / 2;
        while (high - low > 0.5) {
            double mid = low + (high - low) / 2;
            if (WiFi.coverable(houses, numOfAccessPoints, mid)) {
                high = mid;
            } else {
                low = mid;
            }
        }
        return high;
    }

    /**
     * Implement your solution here
     */
    public static boolean coverable(int[] houses, int numOfAccessPoints, double distance) {
        Arrays.sort(houses);
        double covered = Double.MIN_VALUE;
        for (int house : houses) {
            if (house > covered) {
                numOfAccessPoints--;
                if (numOfAccessPoints < 0) {
                    return false;
                }
                covered = house + distance * 2;
            }
        }
        return true;
    }
}
