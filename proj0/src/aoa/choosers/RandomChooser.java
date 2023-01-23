package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import aoa.utils.FileUtils;

public class RandomChooser implements Chooser {
    private final String chosenWord;
    private String pattern;

    public RandomChooser(int wordLength, String dictionaryFile) {
        // TODO: Fill in/change this constructor.
        chosenWord = "";
    }

    @Override
    public int makeGuess(char letter) {
        // TODO: Fill in this method.
        return 0;
    }

    @Override
    public String getPattern() {
        // TODO: Fill in this method.
        return "";
    }

    @Override
    public String getWord() {
        // TODO: Fill in this method.
        return "";
    }
}
