package javaapplication1;

public class CharNode implements Comparable<CharNode> {
    char character; // null character if not leaf
    int frequency;  // sum of both frequency of both children if not leaf
    String code;    // huffman encoding of character
    CharNode left;  // null if leaf
    CharNode right; // null if leaf

    public CharNode(char character, int frequency,  CharNode left, CharNode right) {
        this.character = character;
        this.frequency = frequency;
        this.code = "";
        this.left = left;
        this.right = right;
    }

    public void incrementFrequency() {
        this.frequency++;
    }

    @Override
    public int compareTo(CharNode node) { // the inputs of this function are never both null
        if (this == null || node == null) { // if only one node is null, returns the non-null node as greater
            return this != null ? 1 : -1;
        }

        int frequencyComparision = Integer.compare(this.frequency, node.frequency);
        if (frequencyComparision != 0) {
            return frequencyComparision;
        }
        return Integer.compare(this.character, node.character);
    }

    public String toString() {
        String toReturn = "";
        if (character == '\n') {
            toReturn = "Character: '\\n' Frequency: " + frequency + "\tCode: " + code;
        } else {
            toReturn = "Character: " + character + "\tFrequency: " + frequency + "\tCode: " + code;
        }
        return toReturn;
    }
}
