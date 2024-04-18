public class Test {
    public static void main(String[] args) {
        MedianFinder mf = new MedianFinder();
        for (int x = 4; x > 0; x--) {
            mf.insert(x);
        }
        for (int i = 0; i < 4; i++) {
            System.out.println(mf.getMedian());
        }
    }
}
