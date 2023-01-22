package aoa.choosers;

import java.util.List;

public interface Chooser {
    public int makeGuess(char letter);

    public String getPattern();

    public String getWord();
}
