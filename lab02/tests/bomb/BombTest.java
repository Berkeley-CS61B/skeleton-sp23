package bomb;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.google.common.truth.Truth.assertWithMessage;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BombTest {
    // You won't be able to find any passwords here, sorry!

    public static String[] lines;

    /** Runs up to the given phase in BombMain and modifies the lines variable to have its output.*/
    public static void getBombMainOutputUntil(int phase) {
        PrintStream systemErr = System.err;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setErr(new PrintStream(outputStream));
        BombMain.main(new String[]{"" + phase});
        System.setErr(systemErr);

        String output = outputStream.toString();
        lines = output.split("\r?\n");
    }

    @Test
    @Tag("phase0")
    @DisplayName("Bomb Phase 0")
    public void testBombPhase0() {
        getBombMainOutputUntil(0);
        assertWithMessage("Phase 0 incorrect").that(lines[0].split("\"")[1].hashCode()).isEqualTo(-777276206);
    }

    @Test
    @Tag("phase1")
    @DisplayName("Bomb Phase 1")
    public void testBombPhase1() {
        getBombMainOutputUntil(1);
        assertWithMessage("Phase 1 incorrect").that(lines[1].split("\"")[1].hashCode()).isEqualTo(1729584786);
    }

    @Test
    @Tag("phase2")
    @DisplayName("Bomb Phase 2")
    public void testBombPhase2() {
        getBombMainOutputUntil(2);
        assertWithMessage("Phase 2 incorrect")
                .that(lines[2].split("\"")[1].split(" ")[1337].hashCode())
                .isEqualTo(1097364068);
    }

}
