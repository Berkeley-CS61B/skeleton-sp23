package aoa.guessers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import java.util.List;
import static com.google.common.truth.Truth.assertThat;

public class PAGALetterFreqGuesserTest {

    @Order(1)
    @DisplayName("PAGALetterFreqGuesser correctly eliminates letters")
    @Test
    public void testGetGuessBlankThen_o__Pattern() {
        PAGALetterFreqGuesser nlfg = new PAGALetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = nlfg.getGuess("----", List.of());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is o if someone has already guessed e.
        guess = nlfg.getGuess("----", List.of('e'));
        assertThat(guess).isEqualTo('o');

        // check that the next guess is c if someone has already guessed e and o.
        guess = nlfg.getGuess("-oo-", List.of('e', 'o'));
        assertThat(guess).isEqualTo('c');
    }

    @Order(2)
    @DisplayName("PAGALetterFreqGuesser correctly guesses -o-- pattern")
    @Test
    public void testGetGuess_o__Pattern() {
        PAGALetterFreqGuesser palfg = new PAGALetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary if the 2nd letter is o.
        char guess = palfg.getGuess("-o--", List.of('o'));
        assertThat(guess).isEqualTo('e');
    }

    @Order(3)
    @DisplayName("PAGALetterFreqGuesser eliminates letters for -e-- and ---l patterns")
    @Test
    public void testGetGuess_e__PatternUntilCool() {
        PAGALetterFreqGuesser palfg = new PAGALetterFreqGuesser("data/example.txt");

        // check that the next guess is a if the pattern is currently -e-- and only e has been guessed.
        // this is because there are 2 as and 2 os, but a comes earlier in the alphabet
        char guess = palfg.getGuess("-e--", List.of('e'));
        assertThat(guess).isEqualTo('a');

        // check that the next guess is o if a is not found (since the word must be cool)
        guess = palfg.getGuess("---l", List.of('l', 'a'));
        assertThat(guess).isEqualTo('o');

        // check that the next guess is c since two os were found (since the word must be cool)
        guess = palfg.getGuess("-ool", List.of('l', 'a', 'o'));
        assertThat(guess).isEqualTo('c');
    }

    @Order(4)
    @DisplayName("PAGALetterFreqGuesser handles -o--a- pattern on large file")
    @Test
    public void testGetGuess_o__a_PatternLargeFile() {
        PAGALetterFreqGuesser palfg = new PAGALetterFreqGuesser("data/sorted_scrabble.txt");

        // check that the next guess is s, if the previous guesses were o and a
        char guess = palfg.getGuess("-o--a-", List.of('o', 'a'));
        assertThat(guess).isEqualTo('s');

        // check that the next guess is s, if the previous guesses were o, a, and s
        guess = palfg.getGuess("-o--a-", List.of('o', 'a', 's'));
        assertThat(guess).isEqualTo('l');

        // check that the next guess is n, if the previous guesses were o, a, l, and s
        guess = palfg.getGuess("-o--a-", List.of('o', 'a', 's', 'l'));
        assertThat(guess).isEqualTo('n');
    }
}
