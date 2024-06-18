package javaapplication1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HuffmanEncoder {
    HuffmanTree tree;               // huffman tree of text in given file
    String fileAddress;             // address of the file to decode
    String fileContents;            // contents of the file to decode
    char pseudoEOF;                 // decoding the file is stopped when we encounter this character
    int[] newLines;                 // to deal with characters with values 10, 13, 133
    int currIndexForNewLines = 0;   // current index for newLines array

    public HuffmanEncoder(String address) {
        fileAddress = address;
        fileContents = getFileContents(fileAddress);
        tree = new HuffmanTree(fileContents);
        pseudoEOF = tree.getPseudoEOF();
        newLines = new int[fileContents.length()];
    }

    /**
     * Purpose: collects the contents of a text file as a string
     * Returns: String - the contents of the file
     */
    private String getFileContents(String fileAddress) {
        String fileContents = "";
        try {
            File file = new File(fileAddress);
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                fileContents += data + '\n'; //manually adding new line since reader doesn't read it
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while getting file contents.");
            e.printStackTrace();
        }
        return fileContents.substring(0, fileContents.length()-1); // to remove extra newline added at file end
    }

    /**
     * Purpose: converts the given string into its huffman code using huffman tree
     * Returns: String - the encoded string
     */
    public String encode(String input) {
        String output = "";
        for (int i = 0; i < input.length(); i++) {
            int character = input.charAt(i);
            output += tree.hashTable.search(character).code;
        }

        while (output.length() % 8 != 0) {
            output += "0";
        }
        return output;
    }

    /**
     * Purpose: converts the given huffman encoded string to its original form
     * Returns: String - the decoded string
     */
    public String decode(String input) {
        String output = "";
        int i = 0;
        while (i < input.length()) {
            CharNode curr = tree.root;
            while (curr != null) {
                char character = input.charAt(i);
                curr = character == '0' ? curr.left : curr.right;
                i++;
                if (curr.character == pseudoEOF) {
                    return output;
                }
                if (tree.isLeaf(curr)) {
                    output += curr.character;
                    break;
                }
            }
        }
        return output;
    }

    /**
     * Purpose: writes a compressed file using the huffman code of the original file
     * Returns: void
     */
    public void makeCompressedFile() {
        try {
            File file = new File(fileAddress.substring(0, fileAddress.length()-4) + "_compressed" + ".txt"); // .txt
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
            FileWriter myWriter = new FileWriter(file);

            String encodedStr = encode(fileContents+pseudoEOF);
            for (int i = 0; i < encodedStr.length(); i += 8) {
                int charAsInt = Integer.parseInt(encodedStr.substring(i, i+8), 2);
                if (charAsInt == 10 || charAsInt == 13 || charAsInt == 133) {
                    newLines[currIndexForNewLines++] = charAsInt;
                }
                char b = (char) charAsInt;
                myWriter.write(b);
            }
            myWriter.close();

            System.out.println("Successfully wrote to the compressed file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing compressed file.");
            e.printStackTrace();
        }
    }

    /**
     * Purpose: converts a string to its binary form
     * Returns: String - representing the binary form
     */
    public String convertStringToBinary(String input) {
        String output = "";
        currIndexForNewLines = 0;
        for (int i = 0; i < input.length(); i++) {
            char chr = input.charAt(i);
            String tempString = "";
            if (chr == 10) {
                tempString = Integer.toBinaryString(newLines[currIndexForNewLines++]);
                tempString = String.format("%8s", tempString).replaceAll(" ", "0");
            } else {
                tempString = String.format("%8s", Integer.toBinaryString(chr)).replaceAll(" ", "0");
            }
            output += tempString;
        }
        return output;
    }

    /**
     * Purpose: writes a decompressed file using the contents of the compressed file
     * Returns: void
     */
    public void makeDecompressedFile() {
        try {
            File file = new File(fileAddress.substring(0, fileAddress.length()-4) + "_decompressed.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            }
            FileWriter myWriter = new FileWriter(file);

            String compressedFileContents = getFileContents(
                    fileAddress.substring(0, fileAddress.length()-4) + "_compressed.txt");

            String binaryString = convertStringToBinary(compressedFileContents);
            String decodedString = decode(binaryString);

            myWriter.write(decodedString);
            myWriter.close();
            System.out.println("Successfully wrote to the decompressed file.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing compressed file.");
            e.printStackTrace();
        }
    }
}
