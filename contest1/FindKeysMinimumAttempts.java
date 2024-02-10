public class FindKeysMinimumAttempts implements IFindKeys {
    @Override
    public int[] findKeys(int N, int k, ITreasureExtractor treasureExtractor) {
        // TODO: Problem 1 -- Implement strategy to find correct keys with minimum attempts
        // the asymptotic upper bound is O(k log N)
        // there are k keys and it takes O(log N) attempts to find each key
        int[] bitmap = new int[N];
        for (int i = 0; i < N; i++) {
            bitmap[i] = 1;
        }
        int begin = 0;
        int remainingK = k;
        while (remainingK > 0) {
            int end = N - remainingK;
            while (begin < end) {
                int split;
                if (end - begin > N / k * 2) {
                    split = remainingK + 1;
                } else {
                    split = 2;
                }
                int mid = begin + (end - begin) / split;
                for (int i = begin; i <= mid; i++) {
                    bitmap[i] = 0;
                }
                if (treasureExtractor.tryUnlockChest(bitmap)) {
                    begin = mid + 1;
                } else {
                    end = mid;
                    for (int i = begin; i <= mid; i++) {
                        bitmap[i] = 1;
                    }
                }
            }
            begin++;
            remainingK--;
        }
        for (int i = begin; i < N; i++) {
            bitmap[i] = 0;
        }
        return bitmap;
    }
}
