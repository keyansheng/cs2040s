public class Guessing {

    // Your local variables here
    private int low = 0;
    private int high = 1000;

    /**
     * Implement how your algorithm should make a guess here
     */
    public int guess() {
        return low + (high - low) / 2;
    }

    /**
     * Implement how your algorithm should update its guess here
     */
    public void update(int answer) {
        if (answer == -1) {
            low = this.guess() + 1;
        } else {
            high = this.guess() - 1;
        }
    }
}
