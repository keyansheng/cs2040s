import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * This is the main class for your Markov Model.
 *
 * Assume that the text will contain ASCII characters in the range [1,255].
 * ASCII character 0 (the NULL character) will be treated as a non-character.
 *
 * Any such NULL characters in the original text should be ignored.
 */
public class WordMarkovModel {

	// Use this to generate random numbers as needed
	private Random generator = new Random();

	// This is a special symbol to indicate no character
	public static final String NOWORD = "";

	private HashMap<List<String>, HashMap<String, Integer>> wordMap = new HashMap<>();

	private int order;

	/**
	 * Constructor for MarkovModel class.
	 *
	 * @param order the number of characters to identify for the Markov Model sequence
	 * @param seed the seed used by the random number generator
	 */
	public WordMarkovModel(int order, long seed) {
		// Initialize your class here
		this.order = order;

		// Initialize the random number generator
		generator.setSeed(seed);
	}

	/**
	 * Builds the Markov Model based on the specified text string.
	 */
	public void initializeText(List<String> text) {
		// Build the Markov model here
		// Excluding the last kgram
		int numOfKgrams = text.size() - order;
		for (int i = 0; i < numOfKgrams; i++) {
			HashMap<String, Integer> freqMap =
					wordMap.computeIfAbsent(text.subList(i, i + order), k -> new HashMap<>());
			freqMap.merge(text.get(i + order), 1, Integer::sum);
			// Store the total freq under the NOCHARACTER key
			freqMap.merge(NOWORD, 1, Integer::sum);
		}
	}

	/**
	 * Returns the number of times the specified kgram appeared in the text.
	 */
	public int getFrequency(String kgram) {
		return getFrequency(kgram, NOWORD);
	}

	/**
	 * Returns the number of times the character c appears immediately after the specified kgram.
	 */
	public int getFrequency(String kgram, String word) {
		return wordMap.containsKey(kgram) ? wordMap.get(kgram).getOrDefault(word, 0) : 0;
	}

	/**
	 * Generates the next character from the Markov Model.
	 * Return NOCHARACTER if the kgram is not in the table, or if there is no
	 * valid character following the kgram.
	 */
	public String nextWord(List<String> kgram) {
		// See the problem set description for details
		// on how to make the random selection.
		if (!wordMap.containsKey(kgram)) {
			return NOWORD;
		}
		HashMap<String, Integer> freqMap = wordMap.get(kgram);
		int index = generator.nextInt(freqMap.get(NOWORD));
		for (HashMap.Entry<String, Integer> entry : freqMap.entrySet()) {
			String word = entry.getKey();
			if (!NOWORD.equals(word)) {
				index -= entry.getValue();
				if (index < 0) {
					return word;
				}
			}
		}
		// Should not reach here
		return NOWORD;
	}
}
