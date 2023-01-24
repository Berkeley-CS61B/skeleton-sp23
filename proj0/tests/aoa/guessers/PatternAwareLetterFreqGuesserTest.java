package aoa.guessers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class PatternAwareLetterFreqGuesserTest {

    @Order(1)
    @DisplayName("PatternAwareLetterFreqGuesser returns correct guesses for blank pattern")
    @Test
    public void testGetGuessBlankPattern() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the first guess is e, the most common letter in the dictionary.
        char guess = palfg.getGuess("----", List.of());
        assertThat(guess).isEqualTo('e');

        // check that the next guess is l if someone has already guessed e.
        guess = palfg.getGuess("----", List.of('e'));
        assertThat(guess).isEqualTo('l');

        // check that the next guess is b if someone has already guessed l, o, x, a, e (in that order).
        guess = palfg.getGuess("----", List.of('l', 'o', 'x', 'a', 'e'));
        assertThat(guess).isEqualTo('b');
    }

    @Order(2)
    @DisplayName("PatternAwareLetterFreqGuesser returns correct guesses for -o-- pattern")
    @Test
    public void testGetGuess_o__Pattern() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the first guess is c, the most common letter in the dictionary if the 2nd letter is o.
        char guess = palfg.getGuess("-o--", List.of('o'));
        assertThat(guess).isEqualTo('c');

        // check that the next guess is d.
        guess = palfg.getGuess("-o--", List.of('o', 'c'));
        assertThat(guess).isEqualTo('d');

        // check that the next guess is e, if the previous guessers were d, c, x, o.
        guess = palfg.getGuess("-o--", List.of('d', 'c', 'x', 'o'));
        assertThat(guess).isEqualTo('e');
    }

    @Order(3)
    @DisplayName("PatternAwareLetterFreqGuesser handles -e-- and ---l patterns")
    @Test
    public void testGetGuess_e__PatternUntilCool() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

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

        // check that the next guess is e, if the previous guess were l, a, c, d and the pattern is ---l
        guess = palfg.getGuess("---l", List.of('l', 'a', 'c', 'd'));
        assertThat(guess).isEqualTo('o');
    }

    @Order(4)
    @DisplayName("PatternAwareLetterFreqGuesser handles -e-- pattern after multiple guesses")
    @Test
    public void testGetGuess_e__PatternIfLACDGuessed() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/example.txt");

        // check that the next guess is o, if the previous guess were l, a, c, d and the pattern is ---l
        char guess = palfg.getGuess("---l", List.of('l', 'a', 'c', 'd'));
        assertThat(guess).isEqualTo('o');
    }

    @Order(5)
    @DisplayName("PatternAwareLetterFreqGuesser handles -o--a- pattern on large file")
    @Test
    public void testGetGuess_o__a_PatternLargeFile() {
        PatternAwareLetterFreqGuesser palfg = new PatternAwareLetterFreqGuesser("data/sorted_scrabble.txt");

        // check that the next guess is s, if the previous guesses were o and a
        char guess = palfg.getGuess("-o--a-", List.of('o', 'a'));
        assertThat(guess).isEqualTo('s');

        // check that the next guess is s, if the previous guesses were o, a, and s
        guess = palfg.getGuess("-o--a-", List.of('o', 'a', 's'));
        assertThat(guess).isEqualTo('l');

        // check that the next guess is c, if the previous guesses were o, a, l, and s
        guess = palfg.getGuess("-o--a-", List.of('o', 'a', 's', 'l'));
        assertThat(guess).isEqualTo('c');
    }
}


