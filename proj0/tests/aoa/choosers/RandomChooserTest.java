package aoa.choosers;

import aoa.utils.FileUtils;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RandomChooserTest {

    public static final String DICTIONARY_FILE = "data/sorted_scrabble.txt";
    public static final String EXAMPLE_FILE = "data/example.txt";

    @Order(1)
    @DisplayName("RandomChooser has the correct initial pattern")
    @Test
    public void testThatSelectedWordIsInDictionary() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        List<String> words = FileUtils.readWordsOfLength(EXAMPLE_FILE, 4);
        String word = rc.getWord();

        // Tests whether the word is in the list or not and checks for the correct word length.
        assertThat(word).isIn(words);
        assertThat(word.length()).isEqualTo(4);
        String pattern = rc.getPattern();

        //Tests the initial pattern.
        assertThat(pattern).isEqualTo("----");
    }

    @Order(2)
    @DisplayName("RandomChooser selects correct word with seed")
    @Test
    public void testThatCorrectWordIsSelected() {
        StdRandom.setSeed(8041961);
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        List<String> words = FileUtils.readWordsOfLength(EXAMPLE_FILE, 4);
        String word = rc.getWord();
        assertThat(word).isEqualTo("cool");

        StdRandom.setSeed(6141946);
        rc = new RandomChooser(4, EXAMPLE_FILE);
        word = rc.getWord();
        assertThat(word).isEqualTo("good");
    }

    @Order(3)
    @DisplayName("RandomChooser pattern doesn't change with wrong guess")
    @Test
    public void testWrongGuess() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        int i = rc.makeGuess('u');
        String pattern = rc.getPattern();

        // Tests whether there is a word that has letter 'u' in it.
        assertThat(i).isEqualTo(0);
        assertThat(pattern).isEqualTo("----");
    }

    @Order(4)
    @DisplayName("RandomChooser pattern updates correctly with right guesses")
    @Test
    public void testCorrectGuesses() {
        RandomChooser rc = new RandomChooser(4, EXAMPLE_FILE);
        String word = rc.getWord();

        // Make first guess, either first or second letter of word
        int i = StdRandom.uniform(2);
        char firstGuess = word.charAt(i);
        int numRevealed = rc.makeGuess(firstGuess);

        assertThat(numRevealed).isGreaterThan(0);
        String pattern = rc.getPattern();
        assertThat(pattern.charAt(i)).isEqualTo(firstGuess);

        // Make second guess, either third or fourth letter of word
        char secondGuess = word.charAt(i + 2);
        numRevealed = rc.makeGuess(secondGuess);

        assertThat(numRevealed).isGreaterThan(0);
        pattern = rc.getPattern();
        assertThat(pattern.charAt(i + 2)).isEqualTo(secondGuess);
    }

    @Order(5)
    @DisplayName("RandomChooser returns correct number of occurrences of characters")
    @Test
    public void testReturnedOccurrences() {
        RandomChooser rc = new RandomChooser(4, "data/example-ea.txt");

        // Check occurrences and pattern after guessing e
        int occurencesOfE = rc.makeGuess('e');
        String firstPattern = rc.getPattern();
        assertThat(occurencesOfE).isEqualTo(1);
        assertThat(firstPattern).isEqualTo("-e--");

        // Check occurrences and pattern after guessing a
        int occurencesOfA = rc.makeGuess('a');
        String secondPattern = rc.getPattern();
        assertThat(occurencesOfA).isEqualTo(1);
        assertThat(secondPattern).isEqualTo("-ea-");

        // Check occurrences and pattern after guessing o (not in any of the words)
        int occurencesOfO = rc.makeGuess('o');
        String thirdPattern = rc.getPattern();
        assertThat(occurencesOfO).isEqualTo(0);
        assertThat(thirdPattern).isEqualTo("-ea-");
    }

    @Order(6)
    @DisplayName("RandomChooser throws exception for negative word length")
    @Test
    @Timeout(1)
    public void testRCNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> new RandomChooser(-1, DICTIONARY_FILE));
    }

    @Order(7)
    @DisplayName("RandomChooser throws exception for max int word length")
    @Test
    @Timeout(1)
    public void testRCLargeLength() {
        assertThrows(IllegalStateException.class, () -> new RandomChooser(Integer.MAX_VALUE, DICTIONARY_FILE));
    }

    @Order(8)
    @DisplayName("RandomChooser throws exception for non-existent word length")
    @Test
    @Timeout(1)
    public void testRCMedLength() {
        // No words of length 26, but there's longer word(s) ("floccinaucinihilipilification")
        assertThrows(IllegalStateException.class, () -> new RandomChooser(26, DICTIONARY_FILE));
    }
}
