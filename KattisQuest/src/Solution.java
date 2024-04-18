import java.util.Map;
import java.util.TreeMap;

public class Solution {
    // TODO: Include your data structures here
    private final TreeMap<Quest, Integer> map = new TreeMap<>();

    public Solution() {
        // TODO: Construct/Initialise your data structures here
    }

    void add(long energy, long value) {
        // TODO: Implement your insertion operation here
        Quest quest = new Quest(energy, value);
        map.put(quest, map.getOrDefault(quest, 0) + 1);
    }

    long query(long remainingEnergy) {
        // TODO: Implement your query operation here
        long totalGold = 0;

        for (Map.Entry<Quest, Integer> entry =
                        map.floorEntry(new Quest(remainingEnergy, Long.MAX_VALUE));
                entry != null;
                entry = map.floorEntry(new Quest(remainingEnergy, Long.MAX_VALUE))) {
            Quest quest = entry.getKey();
            int count = entry.getValue();
            remainingEnergy -= quest.energy;
            totalGold += quest.gold;
            if (count == 1) {
                map.remove(quest);
            } else {
                map.put(quest, count - 1);
            }
        }

        return totalGold;
    }

    private static class Quest implements Comparable<Quest> {
        private final long energy;
        private final long gold;

        private Quest(long energy, long gold) {
            this.energy = energy;
            this.gold = gold;
        }

        @Override
        public int compareTo(Quest quest) {
            int compare = Long.compare(energy, quest.energy);
            return compare == 0 ? Long.compare(gold, quest.gold) : compare;
        }
    }
}
