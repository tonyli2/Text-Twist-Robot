package robot.trainer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class TextReader {

    /**
     * Determines if the given sequence of chars is considered a word
     * @param word the sequence of chars to be evaluated
     * @return true: if the sequence of chars is indeed a word | false: otherwise
     */
    public static boolean isValid(String word) {

        if(word.length() > 6 || word.length() < 3)
            return false;

        for(int i = 0; i<word.length(); i++) {
            if(word.charAt(i) < 97 || word.charAt(i) > 122)
                return false;
        }
        return true;
    }

    /**
     * Takes in a .txt file and scrapes it to form a dictionary of words which the robot will use
     * to complete a round of Text-Twist 2
     * @param trainingTextPath file path of training text
     * @param dictionaryPath file path to dictionary
     * @throws FileNotFoundException Exception thrown if the path to the training text file does not exist
     */
    public static void trainRobot(String trainingTextPath, String dictionaryPath) throws FileNotFoundException {

        File text = new File(trainingTextPath);
        File dictionary = new File(dictionaryPath);
        PrintWriter output = new PrintWriter(dictionary);
        CustomHTable<String, String> hashTable = new CustomHTable<>();

        Scanner sc = new Scanner(text);
        String word = "";
        int counter = 0;

        // Put all valid words into the hash table and into dictionary
        while(sc.hasNext()) {
            word = sc.next();

            // If the word is valid, and it does not exist in the hashTable, add it to table and dictionary
            if(isValid(word) && hashTable.get(word) == null) {
                hashTable.add(word, word);
                output.println(hashTable.get(word));
            }
        }
        output.close();
        sc.close();
    }
}
