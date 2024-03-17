import java.util.HashMap;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class MarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final char NOCHARACTER = (char) 0;

	private HashMap<String, HashMap<Character, Integer>> charMap = new HashMap<>();

	private int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public MarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(String text) {
		// Build the Markov model here
		// Excluding the last kgram
		int numOfKgrams = text.length() - order;
		for (int i = 0; i < numOfKgrams; i++) {
			HashMap<Character, Integer> freqMap =
					charMap.computeIfAbsent(text.substring(i, i + order), k -> new HashMap<>());
			freqMap.merge(text.charAt(i + order), 1, Integer::sum);
			// Store the total freq under the NOCHARACTER key
			freqMap.merge(NOCHARACTER, 1, Integer::sum);
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		return getFrequency(kgram, NOCHARACTER);
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, char c) {
		return charMap.containsKey(kgram) ? charMap.get(kgram).getOrDefault(c, 0) : 0;
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public char nextCharacter(String kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (!charMap.containsKey(kgram)) {
			return NOCHARACTER;
		}
		HashMap<Character, Integer> freqMap = charMap.get(kgram);
		int index = generator.nextInt(freqMap.get(NOCHARACTER));
		for (char c = 1; c < 256; c++) {
			if (freqMap.containsKey(c)) {
				index -= freqMap.get(c);
				if (index < 0) {
					return c;
				}
			}
		}
		// Should not reach here
		return NOCHARACTER;
	}
}
