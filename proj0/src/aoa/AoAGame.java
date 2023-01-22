package aoa;

import aoa.choosers.*;
import aoa.guessers.*;

import java.io.FileNotFoundException;
import java.util.*;

public class AoAGame {
    private static final String DICTIONARY_FILE = "data/example.txt";

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to the Awakening of Azathoth.");
        System.out.println();

        // set basic parameters
        Scanner console = new Scanner(System.in);
        System.out.print("What length word do you want to use? ");
        int wordLength = console.nextInt();
        System.out.print("How many wrong answers allowed? ");
        int maxGuesses = console.nextInt();
        System.out.println();

        // set up the the chooser and start the game
        Chooser chooser = new RandomChooser(wordLength, DICTIONARY_FILE);
        Guesser guesser = new ConsoleGuesser(console);
        //Guesser guesser = new PAGALetterFreqGuesser(DICTIONARY_FILE);
        playGame(chooser, guesser, maxGuesses);
        showResults(chooser);
    }

    private static boolean gameOver(Chooser chooser, int guesses) {
        boolean outOfGuesses = guesses < 1;
        boolean wordIdentified = !chooser.getPattern().contains("-");
        return outOfGuesses || wordIdentified;
    }

    private static boolean validGuess(char c, List<Character> guesses) {
        if (!Character.isLowerCase(c)) {
            return false;
        }
        if (guesses.contains(c)) {
            return false;
        }
        return true;
    }

    private static char getNextValidGuess(Guesser g, String pattern, List<Character> guesses) {
        boolean done = false;
        char c = '?';

        while (!done) {
            c = g.getGuess(pattern, guesses);
            if (!validGuess(c, guesses)) {
                System.out.println("'" + c + "' wasn't a valid guess.");
            } else {
                done = true;
            }
        }
        return c;
    }

    // Plays one game with the user
    public static void playGame(Chooser chooser, Guesser guesser, int maxGuesses) throws FileNotFoundException {
        int guessesRemaining = maxGuesses;
        List<Character> guesses = new ArrayList<>();
        while (!gameOver(chooser, guessesRemaining)) {
            String pattern = chooser.getPattern();
            System.out.println("guesses : " + guessesRemaining);
            System.out.println("guessed : " + guesses);
            System.out.println("current : " + pattern);
            char guess = 'a';
            int count = -1;

            while (count < 0) {
                guess = getNextValidGuess(guesser, pattern, guesses);
                guesses.add(guess);
                count = chooser.makeGuess(guess);
                if (count == 0) {
                    guessesRemaining -= 1;
                }
            }

            if (count == 0) {
                System.out.println("Sorry, there are no " + guess + "'s");
            } else if (count == 1) {
                System.out.println("Yes, there is one " + guess + "");
            } else {
                System.out.println("Yes, there are " + count + " " + guess + "'s");
            }
            System.out.println();
        }
    }

    // reports the results of the game, including showing the answer
    public static void showResults(Chooser chooser) {
        if (!chooser.getPattern().contains("-")) {
            System.out.println("'" + chooser.getWord() + "' was my word! Azathoth resumes its slumber.");
        } else {
            System.out.println("Azathoth awakens. You lose!");
            System.out.println("My word was '" + chooser.getWord() + "'.");
        }
    }

}
