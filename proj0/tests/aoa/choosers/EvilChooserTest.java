package aoa.choosers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EvilChooserTest {

    public static final String DICTIONARY_FILE = "data/sorted_scrabble.txt";
    public static final String EXAMPLE_FILE = "data/example.txt";

    @Order(1)
    @DisplayName("EvilChooser constructor sets blank pattern")
    @Test
    public void testBasic() {
        EvilChooser ec = new EvilChooser(4, EXAMPLE_FILE);

        //Tests constructor and initial pattern.
        String pattern = ec.getPattern();
        assertThat(pattern).isEqualTo("----");
    }

    @Order(2)
    @DisplayName("EvilChooser executes test case from spec")
    @Test
    public void testSpec() {
        // Tests the case that is detailed in the spec.
        EvilChooser ec = new EvilChooser(4, EXAMPLE_FILE);

        int first = ec.makeGuess('e');
        assertThat(ec.getPattern()).isEqualTo("----");
        assertThat(first).isEqualTo(0);

        int second = ec.makeGuess('o');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(second).isEqualTo(2);

        int third = ec.makeGuess('t');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(third).isEqualTo(0);

        int fourth = ec.makeGuess('d');
        assertThat(ec.getPattern()).isEqualTo("-oo-");
        assertThat(fourth).isEqualTo(0);

        int last = ec.makeGuess('c');
        assertThat(ec.getPattern()).isEqualTo("coo-");
        assertThat(last).isEqualTo(1);
    }

    @Order(3)
    @DisplayName("EvilChooser executes case with larger word length")
    @Test
    public void testBiggerCase() {
        // Tests the case that is detailed in the spec.
        EvilChooser ec = new EvilChooser(6, DICTIONARY_FILE);

        int first = ec.makeGuess('e');
        assertThat(ec.getPattern()).isEqualTo("------");
        assertThat(first).isEqualTo(0);

        int second = ec.makeGuess('o');
        assertThat(ec.getPattern()).isEqualTo("------");
        assertThat(second).isEqualTo(0);

        int third = ec.makeGuess('a');
        assertThat(ec.getPattern()).isEqualTo("------");
        assertThat(third).isEqualTo(0);

        int fourth = ec.makeGuess('i');
        assertThat(ec.getPattern()).isEqualTo("------");
        assertThat(fourth).isEqualTo(0);

        int fifth = ec.makeGuess('u');
        assertThat(ec.getPattern()).isEqualTo("--u---");
        assertThat(fifth).isEqualTo(1);

        int sixth = ec.makeGuess('s');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(sixth).isEqualTo(1);

        int seventh = ec.makeGuess('t');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(seventh).isEqualTo(0);

        int eighth = ec.makeGuess('b');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(eighth).isEqualTo(0);

        int ninth = ec.makeGuess('c');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(ninth).isEqualTo(0);

        int tenth = ec.makeGuess('d');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(tenth).isEqualTo(0);

        int eleventh = ec.makeGuess('f');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(eleventh).isEqualTo(0);

        int twelfth = ec.makeGuess('g');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(twelfth).isEqualTo(0);

        int thirteenth = ec.makeGuess('h');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(thirteenth).isEqualTo(0);

        int fourteenth = ec.makeGuess('j');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(fourteenth).isEqualTo(0);

        int fifteenth = ec.makeGuess('k');
        assertThat(ec.getPattern()).isEqualTo("--u--s");
        assertThat(fifteenth).isEqualTo(0);

        int sixteenth = ec.makeGuess('l');
        assertThat(ec.getPattern()).isEqualTo("-lu--s");
        assertThat(sixteenth).isEqualTo(1);

        int seventeenth = ec.makeGuess('m');
        assertThat(ec.getPattern()).isEqualTo("-lum-s");
        assertThat(seventeenth).isEqualTo(1);

        int eighteenth = ec.makeGuess('p');
        assertThat(ec.getPattern()).isEqualTo("plumps");
        assertThat(eighteenth).isEqualTo(2);
    }

    @Order(4)
    @DisplayName("EvilChooser throws exception for negative word length")
    @Test
    @Timeout(1)
    public void testECNegativeLength() {
        assertThrows(IllegalArgumentException.class, () -> new EvilChooser(-1, DICTIONARY_FILE));
    }

    @Order(5)
    @DisplayName("EvilChooser throws exception for max int word length")
    @Test
    @Timeout(1)
    public void testECLargeLength() {
        assertThrows(IllegalStateException.class, () -> new EvilChooser(Integer.MAX_VALUE, DICTIONARY_FILE));
    }

    @Order(6)
    @DisplayName("EvilChooser throws exception for non-existent word length")
    @Test
    @Timeout(1)
    public void testECMedLength() {
        assertThrows(IllegalStateException.class, () -> new EvilChooser(26, DICTIONARY_FILE));
    }
}
