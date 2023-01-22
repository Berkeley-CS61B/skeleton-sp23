package aoa.choosers;

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
public class EvilChooserIntegrationTest {

    @Order(1)
    @DisplayName("EvilChooser does not use any Map as a field")
    @Test
    public void testNoMapFieldEC() {
        Reflection.assertFieldsEqualTo(EvilChooser.class, Map.class, 0);
    }

    @Order(2)
    @DisplayName("The overall number of fields in EvilChooser is small")
    @Test
    public void testSmallNumberOfFieldsEC() {
        assertWithMessage("EvilChooser has a small number of fields")
                .that(Reflection.getFields(EvilChooser.class).count())
                .isAtMost(4);
    }

    @Order(3)
    @DisplayName("Test EvilChooser Full Game")
    @ParameterizedTest(name = "{0}")
    @FileSource(
            inputs = {
                    "{word length = 3, max wrong guesses = 10, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{word length = 3, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{word length = 5, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{word length = 5, max wrong guesses = 1, guesses = a}",
                    "{word length = 5, max wrong guesses = 10, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{word length = 15, max wrong guesses = 15, guesses = aeioubcdfghjklmnpqrstvwxyz}",
                    "{word length = 20, max wrong guesses = 15, guesses = aeioubcdfghjklmnpqrstvwxyz}",
                    "{word length = 8, max wrong guesses = 14, guesses = aeioubcdfghjklmnpqrstvwxyz}",
                    "{word length = 20, max wrong guesses = 1, guesses = u}",
                    "{word length = 7, max wrong guesses = 26, guesses = abcdefghijklmnopqrstuvwxyz}",
                    "{word length = 7, max wrong guesses = 5, guesses = tusor}",
                    "{word length = 7, max wrong guesses = 8, guesses = tusoraeiz}",
                    "{word length = 7, max wrong guesses = 7, guesses = ziearosut}",
                    "{word length = 4, max wrong guesses = 11, guesses = etaoinshrlud}",
                    "{word length = 12, max wrong guesses = 7, guesses = etaoinshrlud}",
            },
            outputRoot = "tests/data/evilTraces",
            outputFiles = {
                    "trace0-evil.txt",
                    "trace1-evil.txt",
                    "trace2-evil.txt",
                    "trace3-evil.txt",
                    "trace4-evil.txt",
                    "trace5-evil.txt",
                    "trace6-evil.txt",
                    "trace7-evil.txt",
                    "trace8-evil.txt",
                    "trace9-evil.txt",
                    "trace10-evil.txt",
                    "trace11-evil.txt",
                    "trace12-evil.txt",
                    "trace13-evil.txt",
                    "trace14-evil.txt",
            }
    )
    public void testPlayGameWithEvilChooser(Map<String, String> arguments, String expectedOutput, CaptureSystemOutput.OutputCapture capture) throws IOException {
        // Parse arguments
        int length = Integer.parseInt(arguments.get("word length"));
        int wrongAllowed = Integer.parseInt(arguments.get("max wrong guesses"));
        String guesses = arguments.get("guesses");

        // Run the actual game
        runTestGame(EvilChooser.class, length, wrongAllowed, "data/sorted_scrabble.txt", guesses);

        assertWithMessage("Game output was incorrect")
                .that(capture.toString().replace("\r\n", "\n").strip())
                .isEqualTo(expectedOutput.replace("\r\n", "\n").strip());
    }
}
