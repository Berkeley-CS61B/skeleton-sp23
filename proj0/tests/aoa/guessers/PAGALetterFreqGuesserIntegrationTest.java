package aoa.guessers;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PAGALetterFreqGuesserIntegrationTest {

    @Order(1)
    @DisplayName("PAGALetterFreqGuesser has no fields")
    @Test
    public void testNoFields() {
        assertWithMessage("PAGALetterFreqGuesser has 1 instance field")
                .that(Reflection.getFields(PAGALetterFreqGuesser.class).count())
                .isAtMost(1);
    }

    static Stream<Arguments> getGuessProvider() {
        return Stream.of(
                arguments("most occurrences", "---", List.of('a'), 'e'),
                arguments("most occurrences", "---", List.of('a', 'e', 'o'), 'i'),
                arguments("most occurrences", "sc--nc-", List.of('s', 'n', 'c'), 'e'),
                arguments("alphabetical order", "-ee", List.of('e'), 'b'),
                arguments("alphabetical order", "-ppl-", List.of('p', 'l'), 'a'),
                arguments("correct letter", "en-ineerin-", List.of('e', 'n', 'i', 'r'), 'g'),
                arguments("matching pattern", "---s", List.of('a', 'e', 'i', 'o', 's'), 'u')
        );
    }

    @Order(2)
    @DisplayName("Test getGuess Method in PAGALetterFreqGuesser")
    @ParameterizedTest(name = "PAGALetterFreqGuesser: pattern = {1}; guesses = {2}; expected = {3}")
    @MethodSource("getGuessProvider")
    public void testGetGuess(String msg, String pattern, List<Character> guesses, char expected) throws FileNotFoundException {
        PAGALetterFreqGuesser guesser = new PAGALetterFreqGuesser("data/sorted_scrabble.txt");

        assertWithMessage("Not " + msg)
                .that(guesser.getGuess(pattern, guesses))
                .isEqualTo(expected);
    }
}
