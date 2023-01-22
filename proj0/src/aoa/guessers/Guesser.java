package aoa.guessers;

import java.util.List;

public interface Guesser {
    public char getGuess(String pattern, List<Character> guesses);
}
