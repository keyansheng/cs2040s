///////////////////////////////////
// This is the main shift register class.
// Notice that it implements the ILFShiftRegister interface.
// You will need to fill in the functionality.
///////////////////////////////////

/**
 * class ShiftRegister
 * @author
 * Description: implements the ILFShiftRegister interface.
 */
public class ShiftRegister implements ILFShiftRegister {
    ///////////////////////////////////
    // Create your class variables here
    ///////////////////////////////////
    // TODO:
    private int[] register;
    private int size;
    private int tap;

    ///////////////////////////////////
    // Create your constructor here:
    ///////////////////////////////////
    ShiftRegister(int size, int tap) {
        // TODO:
        this.size = size;
        this.tap = tap;
    }

    ///////////////////////////////////
    // Create your class methods here:
    ///////////////////////////////////
    /**
     * setSeed
     * @param seed
     * Description:
     */
    @Override
    public void setSeed(int[] seed) {
        // TODO:
        this.register = seed;
    }

    /**
     * shift
     * @return
     * Description:
     */
    @Override
    public int shift() {
        // TODO:
        int feedbackBit = this.register[size - 1] ^ this.register[tap];
        for (int i = size - 1; i > 0; i--) {
            this.register[i] = this.register[i - 1];
        }
        this.register[0] = feedbackBit;
        return feedbackBit;
    }

    /**
     * generate
     * @param k
     * @return
     * Description:
     */
    @Override
    public int generate(int k) {
        // TODO:
        int output = 0;
        while (k > 0) {
            output <<= 1;
            output |= shift();
            k--;
        }
        return output;
    }

    /**
     * Returns the integer representation for a binary int array.
     * @param array
     * @return
     */
    private int toDecimal(int[] array) {
        // TODO:
        return 0;
    }
}
