package aoa.choosers;

import aoa.AoAGame;
import aoa.guessers.ConsoleGuesser;
import jh61b.utils.Reflection;

import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.Scanner;

public class ChooserHelpers {

    public static void runTestGame(
            Class<? extends Chooser> clazz,
            int wordLength,
            int maxWrongGuesses,
            String dictionaryFile,
            String guesses
    ) throws FileNotFoundException {
        @SuppressWarnings("unchecked") Constructor<? extends Chooser> constructor =
                Reflection.getConstructor(clazz, int.class, String.class);
        AoAGame.playGame(
                Reflection.newInstance(constructor, wordLength, dictionaryFile),
                new ConsoleGuesser(new Scanner(String.join("\n", guesses.split("")))),
                maxWrongGuesses
        );
    }

}
