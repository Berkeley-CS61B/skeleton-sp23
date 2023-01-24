package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.util.Arrays;
import java.util.Map;

import static com.google.common.truth.Truth.assertWithMessage;

@CaptureSystemOutput
public class AdventureGameTests {
    public static final String PREFIX_PATH = "tests/data/";

    public static final Map<String, Integer> STAGE_TO_LINE_CORRECT = Map.of(
            "BeeCountingStage", 14,
            "SpeciesListStage", 29,
            "PalindromeStage", 37,
            "MachineStage", 57
    );

    public static final Map<String, Integer> STAGE_TO_LINE_INCORRECT = Map.of(
            "BeeCountingStage", 14,
            "SpeciesListStage", 31,
            "PalindromeStage", 40,
            "MachineStage", 60
    );

    @ParameterizedTest
    @DisplayName("Correct inputs")
    @ValueSource(strings = {"BeeCountingStage", "SpeciesListStage", "PalindromeStage", "MachineStage"})
    public void testStageCorrect(String stage, CaptureSystemOutput.OutputCapture capture) {
        runUntilStage(stage, "correctInput.txt", "correctAnswers.txt", capture,
                "Game output for correct inputs on " + stage + " does not match");
    }

    @ParameterizedTest
    @DisplayName("Incorrect inputs")
    @ValueSource(strings = {"BeeCountingStage", "SpeciesListStage", "PalindromeStage", "MachineStage"})
    public void testStageIncorrect(String stage, CaptureSystemOutput.OutputCapture capture) {
        runUntilStage(stage, "incorrectInput.txt", "incorrectAnswers.txt", capture,
                "Game output for incorrect inputs on " + stage + " does not match");
    }


    /** Runs the adventure game until the given stage with the given input and answer files,
     *  and asserts with the given assertion message that they match.*/
    private void runUntilStage(String stage, String inputFile, String answersFile,
                               CaptureSystemOutput.OutputCapture capture,
                               String assertionMessage) {
        try {
            runTestGame(inputFile);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            throw e;
        } catch (Exception ignored) {
        }
        String expected = new In(new File(PREFIX_PATH + answersFile)).readAll();
        String expectedClean = expected.replace("\r\n", "\n").strip();
        String captureClean = capture.toString().replace("\r\n", "\n").strip();
        int stageEndLine = inputFile.equals("correctInput.txt") ?
                STAGE_TO_LINE_CORRECT.get(stage) :
                STAGE_TO_LINE_INCORRECT.get(stage);
        assertWithMessage(assertionMessage)
                .that(sliceTextFileUntil(captureClean, stageEndLine))
                .isEqualTo(sliceTextFileUntil(expectedClean, stageEndLine));
    }

    /** Returns the text file sliced until the given line. */
    private String sliceTextFileUntil(String cleaned, int endLine) {
        return String.join("\n", Arrays.copyOfRange(cleaned.split("\n"), 0, endLine));
    }

    private void runTestGame(String filePath) {
        In in = new In(new File(PREFIX_PATH + filePath));
        StdRandom.setSeed(1337);
        AdventureGame game = new AdventureGame(in);
        game.play();
    }

}
