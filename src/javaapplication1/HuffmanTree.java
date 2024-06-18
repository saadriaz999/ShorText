package javaapplication1;


public class HuffmanTree {
    CharNode root;
    private char pseudoEOF = '\0';  //dummy pseudoEndOfFile -> to signal the end of file
    HashTable hashTable = new HashTable();   //use this hash table to make heap and then huffman tree

    public HuffmanTree (String input) {  //input is the String from a file
        buildHashTable(input);
        pseudoEOF = addPseudoEOF();
        root = buildHuffmanTree();
        addEncoding(root, "");
    }

    public char getPseudoEOF() {
        return pseudoEOF;
    }

    public boolean isLeaf(CharNode node) {
        return node.left == null && node.right == null;
    }

    public void buildHashTable(String input) {
        for (int i = 0; i < input.length(); i++) {
            hashTable.insert(input.charAt(i));
        }
    }

    public char addPseudoEOF() {
        while (true) {   //get a random number within 65536 of hashtable where the cell is null (unused)
            int randInt = (int) Math.random()*(hashTable.size() - 500 + 1) + 500;
            if (hashTable.isNull(randInt)) {
                pseudoEOF = (char) randInt;
                hashTable.insert(pseudoEOF);
                break;
            }
        }
        return pseudoEOF;
    }

    public BinaryHeap buildHeap() {
        BinaryHeap heap = new BinaryHeap();
        for (int i = 0; i < hashTable.size(); i++) {
            if (!hashTable.isNull(i)) {
                heap.insert(hashTable.search(i));
            }
        }
        return heap;
    }

    public CharNode buildHuffmanTree() {
        BinaryHeap heap = buildHeap();
        CharNode min1 = null;
        CharNode min2 = null;
        while (!heap.isEmpty()) {
            min1 = heap.delete();
            if (heap.isEmpty()) {
                break;
            }
            else {
                min2 = heap.delete();
            }

            CharNode newNode = new CharNode('\0', min1.frequency+min2.frequency, min1, min2);
            heap.insert(newNode);
        }
        return min1;
    }

    public void addEncoding(CharNode root, String code) {
        if (root == null) {
            return;
        }
        addEncoding(root.left, code + "0");
        if (isLeaf(root)) {
            root.code = code;
        }
        addEncoding(root.right, code + "1");
    }

    public void printCharFrequencies(CharNode root) {
        if (root == null) {
            return;
        }
        printCharFrequencies(root.left);
        if (root.left == null && root.right == null) {
            System.out.println(root);
        }
        printCharFrequencies(root.right);
    }
}
