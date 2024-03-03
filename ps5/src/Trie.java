import java.util.ArrayList;
import java.util.regex.Pattern;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';

    private class TrieNode {
        TrieNode[] children = new TrieNode[62];
        private boolean isEndOfString = false;

        public void insert(String string, int stringIndex) {
            if (stringIndex == string.length()) {
                this.isEndOfString = true;
            } else {
                int child = charToChild(string.charAt(stringIndex));
                if (this.children[child] == null) {
                    this.children[child] = new TrieNode();
                }
                this.children[child].insert(string, stringIndex + 1);
            }
        }

        public boolean contains(String string, int stringIndex) {
            if (stringIndex == string.length()) {
                return this.isEndOfString;
            } else {
                int child = charToChild(string.charAt(stringIndex));
                return this.children[child] != null && this.children[child].contains(string, stringIndex + 1);
            }
        }

        public int prefixSearch(String string, int stringIndex, ArrayList<String> results, int limit) {
            if (limit == 0) {
                return limit;
            } else if (stringIndex >= string.length()) {
                if (this.isEndOfString) {
                    results.add(string);
                    limit--;
                }
                for (int child = 0; child < this.children.length; child++) {
                    if (this.children[child] != null) {
                        limit = this.children[child].prefixSearch(string + childToChar(child), stringIndex + 1, results, limit);
                    }
                }
            } else if (string.charAt(stringIndex) == WILDCARD) {
                for (int child = 0; child < this.children.length; child++) {
                    limit = this.prefixSearch(string.replaceFirst(Pattern.quote(String.valueOf(WILDCARD)), String.valueOf(childToChar(child))), stringIndex, results, limit);
                }
            } else {
                int child = charToChild(string.charAt(stringIndex));
                if (this.children[child] != null) {
                    limit = this.children[child].prefixSearch(string, stringIndex + 1, results, limit);
                }
            }
            return limit;
        }
    }

    private TrieNode root = new TrieNode();

    private static int charToChild(char character) {
        if ('0' <= character && character <= '9') {
            return character - '0';
        } else if ('A' <= character && character <= 'Z') {
            return character - 'A' + 10;
        } else if ('a' <= character && character <= 'z') {
            return character - 'a' + 36;
        } else {
            throw new IllegalArgumentException("Invalid character");
        }
    }

    private static char childToChar(int child) {
        if (0 <= child && child <= 9) {
            return (char) (child + '0');
        } else if (10 <= child && child <= 35) {
            return (char) (child + 'A' - 10);
        } else if (36 <= child && child <= 61) {
            return (char) (child + 'a' - 36);
        } else {
            throw new IllegalArgumentException("Invalid child");
        }
    }

    /**
     * Inserts string s into the Trie.
     *
     * @param s string to insert into the Trie
     */
    void insert(String s) {
        root.insert(s, 0);
    }

    /**
     * Checks whether string s exists inside the Trie or not.
     *
     * @param s string to check for
     * @return whether string s is inside the Trie
     */
    boolean contains(String s) {
        return root.contains(s, 0);
    }

    /**
     * Searches for strings with prefix matching the specified pattern sorted by lexicographical order. This inserts the
     * results into the specified ArrayList. Only returns at most the first limit results.
     *
     * @param s       pattern to match prefixes with
     * @param results array to add the results into
     * @param limit   max number of strings to add into results
     */
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        root.prefixSearch(s, 0, results, limit);
    }


    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }


    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

        String[] result1 = t.prefixSearch("pe", 10);
        String[] result2 = t.prefixSearch("pe.", 10);
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
