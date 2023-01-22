package aoa.choosers;

import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import helpers.FileSource;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;

import java.io.IOException;
import java.util.Map;

import static com.google.common.truth.Truth.assertWithMessage;
import static aoa.choosers.ChooserHelpers.runTestGame;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@CaptureSystemOutput
public class RandomChooserIntegrationTest {

    @Order(1)
    @DisplayName("RandomChooser does not use any Map as a field")
    @Test
    public void testNoMapFieldRC() {
        Reflection.assertFieldsEqualTo(RandomChooser.class, Map.class, 0);
    }

    @Order(2)
    @DisplayName("The overall number of fields in RandomChooser is small")
    @Test
    public void testSmallNumberOfFieldsRC() {
        assertWithMessage("RandomChooser has a small number of fields")
                .that(Reflection.getFields(RandomChooser.class).count())
                .isAtMost(4);
    }

    @Order(3)
    @DisplayName("Test RandomChooser Full Game")
    @ParameterizedTest(name = "{0}")
    @FileSource(
            inputs = {
                    "{seed = 1337, word length = 3, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{seed = 1337, word length = 3, max wrong guesses = 10, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{seed = 2, word length = 3, max wrong guesses = 10, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{seed = 44, word length = 7, max wrong guesses = 6, guesses = debatsacfghijklmnopqruvwxyz}",
                    "{seed = 6, word length = 20, max wrong guesses = 16, guesses = aeioubcdfghjklmnpqrstvwxyz}",
                    "{seed = 19, word length = 14, max wrong guesses = 1, guesses = aeiou}",
                    "{seed = 19, word length = 14, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{seed = 239, word length = 9, max wrong guesses = 7, guesses = gsnreou}",
                    "{seed = 77, word length = 21, max wrong guesses = 4, guesses = iqzyx}",
                    "{seed = 1288, word length = 7, max wrong guesses = 1, guesses = negator}",
                    "{seed = 1972, word length = 5, max wrong guesses = 8, guesses = computer}",
                    "{seed = 1972, word length = 19, max wrong guesses = 3, guesses = xvcounterz}",
                    "{seed = 2019, word length = 8, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{seed = 2019, word length = 8, max wrong guesses = 7, guesses = redfqin}",
                    "{seed = 2019, word length = 8, max wrong guesses = 2, guesses = redfqin}",
            },
            outputRoot = "tests/data/randomTraces",
            outputFiles = {
                    "trace0.txt",
                    "trace1.txt",
                    "trace2.txt",
                    "trace3.txt",
                    "trace4.txt",
                    "trace5.txt",
                    "trace6.txt",
                    "trace7.txt",
                    "trace8.txt",
                    "trace9.txt",
                    "trace10.txt",
                    "trace11.txt",
                    "trace12.txt",
                    "trace13.txt",
                    "trace14.txt",
            }
    )
    public void testPlayGameWithRandomChooser(Map<String, String> arguments, String expectedOutput, CaptureSystemOutput.OutputCapture capture) throws IOException {
        // Parse arguments
        int seed = Integer.parseInt(arguments.get("seed"));
        int length = Integer.parseInt(arguments.get("word length"));
        int wrongAllowed = Integer.parseInt(arguments.get("max wrong guesses"));
        String guesses = arguments.get("guesses");

        // Set Random field to correct seed
        StdRandom.setSeed(seed);

        // Run the actual game
        runTestGame(RandomChooser.class, length, wrongAllowed, "data/sorted_scrabble.txt", guesses);

        assertWithMessage("Game output was incorrect")
                .that(capture.toString().replace("\r\n", "\n").strip())
                .isEqualTo(expectedOutput.replace("\r\n", "\n").strip());
    }
}
