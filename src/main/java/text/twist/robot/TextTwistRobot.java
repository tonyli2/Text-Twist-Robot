package text.twist.robot;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Scanner;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextTwistRobot {

    /**
     * Robot takes in the given letters from Text-Twist and determines all the known
     * words that it can make with Moby Dick as its archive.
     * @param word a singular word parsed from Moby Dick
     * @param letters the set of letters given in each round of Text-Twist
     * @return true: the known word can be created from letters | false: the known
     * word cannot be constructed from letters
     */
    public static boolean canMake(String word, String letters){

        char[] lettersArray = letters.toCharArray();
        char[] wordArray = word.toCharArray();
        int counter = 0;

        //WordArray is the "word" within the dictionary
        //It is something that the robot knows
        //Whereas letterArray is the scrambled letters that the program gives us
        for(int i = 0; i<wordArray.length;i++) {
            for(int k = 0; k<lettersArray.length; k++) {
                if(wordArray[i]==lettersArray[k]) {
                    counter++;
                    lettersArray[k] = '\0';
                    break;
                }
                else if(wordArray[i] != lettersArray[k]) {
                    if(k == lettersArray.length-1) {
                        return false;
                    }
                }
            }
        }

        if(counter == wordArray.length) {
            return true;
        }
        return false;
    }

    /**
     * Starts the robot, and allows it to begin reading off the dictionary and
     * inputting known words into the keyboard
     * @param dictionaryPath filepath to dictionary that robot reads from
     * @throws InterruptedException thrown if a thread is interrupted
     * @throws FileNotFoundException thrown if Scanner cannot find dictionary
     * @throws AWTException thrown from Robot class
     */
    public static void startRobot(String dictionaryPath) throws InterruptedException, FileNotFoundException,
            AWTException {

        File dictionary = new File(dictionaryPath);
        Scanner sc = new Scanner(dictionary);
        Scanner input = new Scanner(System.in);
        String uInput = "";
        String word = "";
        Robot key = new Robot();
        System.out.println("What are the 6 letters in order?");
        uInput = input.next();

        // Gives us enough time to get to web page
        Thread.sleep(5000);
        while(sc.hasNext()) {
            word = sc.next();
            if(TextTwistRobot.canMake(word,uInput)) {

                for(int i = 0; i<word.length();i++) {
                    char letter = word.charAt(i);
                    if(letter == 'a') key.keyPress(KeyEvent.VK_A);
                    if(letter == 'b') key.keyPress(KeyEvent.VK_B);
                    if(letter == 'c') key.keyPress(KeyEvent.VK_C);
                    if(letter == 'd') key.keyPress(KeyEvent.VK_D);
                    if(letter == 'e') key.keyPress(KeyEvent.VK_E);
                    if(letter == 'f') key.keyPress(KeyEvent.VK_F);
                    if(letter == 'g') key.keyPress(KeyEvent.VK_G);
                    if(letter == 'h') key.keyPress(KeyEvent.VK_H);
                    if(letter == 'i') key.keyPress(KeyEvent.VK_I);
                    if(letter == 'j') key.keyPress(KeyEvent.VK_J);
                    if(letter == 'k') key.keyPress(KeyEvent.VK_K);
                    if(letter == 'l') key.keyPress(KeyEvent.VK_L);
                    if(letter == 'm') key.keyPress(KeyEvent.VK_M);
                    if(letter == 'n') key.keyPress(KeyEvent.VK_N);
                    if(letter == 'o') key.keyPress(KeyEvent.VK_O);
                    if(letter == 'p') key.keyPress(KeyEvent.VK_P);
                    if(letter == 'q') key.keyPress(KeyEvent.VK_Q);
                    if(letter == 'r') key.keyPress(KeyEvent.VK_R);
                    if(letter == 's') key.keyPress(KeyEvent.VK_S);
                    if(letter == 't') key.keyPress(KeyEvent.VK_T);
                    if(letter == 'u') key.keyPress(KeyEvent.VK_U);
                    if(letter == 'v') key.keyPress(KeyEvent.VK_V);
                    if(letter == 'w') key.keyPress(KeyEvent.VK_W);
                    if(letter == 'x') key.keyPress(KeyEvent.VK_X);
                    if(letter == 'y') key.keyPress(KeyEvent.VK_Y);
                    if(letter == 'z') key.keyPress(KeyEvent.VK_Z);
                    Thread.sleep(15);
                }
                key.keyPress(KeyEvent.VK_ENTER);
            }
        }

    }
}
