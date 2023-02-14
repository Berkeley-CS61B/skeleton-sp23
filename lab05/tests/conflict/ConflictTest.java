package conflict;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ConflictTest {

    static final File HELLO_FILE = new File("src/conflict/hello.txt");

    /**
     * Test that hello.txt has a conflict, or that the conflict has been resolved appropriately.
     */
    @Test
    public void testConflictExists() {
        List<String> fileContents = readAllLines(HELLO_FILE);
        // regex pattern match the desired merge conflict
        String pattern = "<<<<<<< HEADHello, I am a 61B student!=======Hello, I am NOT a 61A student!>>>>>>>.*";
        // test conflict resolved first, then if that fails, run this test
        try {
            testConflictResolved();
        } catch (AssertionError e) {
            assertThat(String.join("", fileContents)).matches(pattern);
        }
    }


    /**
     * Test that the conflict in hello.txt has been resolved
     * according to the Lab 05 Gradescope assignment instructions.
     */
    @Test
    public void testConflictResolved() {
        List<String> fileContents = readAllLines(HELLO_FILE);
        // regex pattern match a merge conflict
        String pattern = "<<<<<<< HEAD.*=======.*>>>>>>>.*";
        assertThat(String.join("", fileContents)).doesNotMatch(pattern);
        assertWithMessage("Did you follow the Gradescope instructions exactly for how to resolve the merge conflict?")
                .that(fileContents.get(0).hashCode()).isEqualTo(-1880044555);
        assertWithMessage("Did you follow the Gradescope instructions exactly for how to resolve the merge conflict?")
                .that(fileContents.get(fileContents.size() - 1).hashCode()).isEqualTo(-2057734860);
    }

    /** Read all lines of a file into a string list, trimming whitespace. */
    private List<String> readAllLines(File f) {
        Scanner s;
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<String> lst = new ArrayList<>();
        while (s.hasNextLine()) {
            String clean = s.nextLine().trim();
            if (!clean.isEmpty()) {
                lst.add(clean);
            }
        }
        return lst;
    }

}
