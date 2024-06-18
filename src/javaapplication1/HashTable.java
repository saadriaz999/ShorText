package javaapplication1;

public class HashTable {
    public static int noOfUnicodeChars = 65536;
    CharNode[] table = new CharNode[noOfUnicodeChars];

    public int size() {
        return noOfUnicodeChars;
    }

    public int hash(char key) {
        return (int) key;
    }

    public void insert(char key) {
        int hashedKey = hash(key);
        if (table[hashedKey] == null) {
            CharNode newNode = new CharNode(key, 1, null, null);
            table[hashedKey] = newNode;
        } else {
            table[hashedKey].incrementFrequency();
        }
    }

    public CharNode search(int index) {
        return table[index];
    }

    public boolean isNull(int index) {
        return table[index] == null;
    }

    public String toString() {
        String output = "";
        for (int i = 0; i < table.length; i++) {
            if (!isNull(i)) {
                output += table[i] + " ";
            }
        }
        return output;
    }
}
