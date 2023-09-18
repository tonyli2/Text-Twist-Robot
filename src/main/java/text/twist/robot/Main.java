package text.twist.robot;
import robot.trainer.TextReader;

import java.awt.*;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, InterruptedException, AWTException {

        //Train robot by using a training text file (Change to your own custom file)
        String trainingText = "bigText.txt";
        String dictionaryPath = "dictionary.txt";
        TextReader.trainRobot(trainingText, dictionaryPath);

        TextTwistRobot.startRobot(dictionaryPath);
    }
}
