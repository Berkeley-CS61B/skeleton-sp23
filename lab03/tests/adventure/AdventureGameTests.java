package adventure;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import helpers.CaptureSystemOutput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import static com.google.common.truth.Truth.assertWithMessage;

@CaptureSystemOutput
public class AdventureGameTests {
    static final String PREFIX_PATH = "solution/tests/data/";
    static final String BEE_PATH = "BeeCountingStage";
    static final String SPECIES_PATH = "SpeciesListStage";
    static final String PALINDROME_PATH = "PalindromeStage";
    static final String MACHINE_PATH = "MachineStage";
    static final String GAME_PATH = "game";

    static final Map<String, Class<?>> NAME_TO_STAGE = Map.of(
            BEE_PATH, BeeCountingStage.class,
            SPECIES_PATH, SpeciesListStage.class,
            PALINDROME_PATH, PalindromeStage.class,
            MACHINE_PATH, MachineStage.class,
            GAME_PATH, AdventureGame.class
    );

    private AdventureGame getGame(String stagePath) {
        In in = new In(new File(PREFIX_PATH + stagePath + "/" + "input.txt"));
        StdRandom.setSeed(1337);
        AdventureStage stage;
        try {
            stage = (AdventureStage) NAME_TO_STAGE.get(stagePath)
                    .getConstructor(In.class).newInstance(in);
        } catch (InvocationTargetException |
                 InstantiationException |
                 IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return new AdventureGame(in, stage);
    }

    private void compareOutputToExpected(String stagePath, CaptureSystemOutput.OutputCapture capture) {
        String expected = new In(new File(PREFIX_PATH + stagePath + "/" + "answers.txt")).readAll();
        String cleanedExpected = expected.replace("\r\n", "\n").strip();
        String cleanedCapture = capture.toString().replace("\r\n", "\n").strip();

        assertWithMessage("Game outputs for " + NAME_TO_STAGE.get(stagePath).getSimpleName() + " did not match")
                .that(cleanedCapture).isEqualTo(cleanedExpected);
    }

    @DisplayName("Individual stage tests")
    @ParameterizedTest(name = "{0}")
    @ValueSource(strings = {BEE_PATH, SPECIES_PATH, PALINDROME_PATH, MACHINE_PATH})
    public void testStage(String stagePath, CaptureSystemOutput.OutputCapture capture) {
        getGame(stagePath).handleStage();
        compareOutputToExpected(stagePath, capture);
    }

    @DisplayName("Integration test with incorrect inputs")
    @Test
    public void testGame(CaptureSystemOutput.OutputCapture capture) {
        In in = new In(new File(PREFIX_PATH + GAME_PATH + "/" + "input.txt"));
        StdRandom.setSeed(1337);
        AdventureGame game = new AdventureGame(in);
        game.play();
        compareOutputToExpected(GAME_PATH, capture);
    }

}
