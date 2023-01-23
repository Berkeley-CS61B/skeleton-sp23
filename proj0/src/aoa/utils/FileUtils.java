package aoa.utils;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<String> readWords(String filename) {
        List<String> returnList = new ArrayList<>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            returnList.add(in.readString());
        }
        return returnList;
    }

    public static List<String> readWordsOfLength(String filename, int length) {
        List<String> returnList = new ArrayList<>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            String word = in.readString();
            if (word.length() == length) {
                returnList.add(word);
            }
        }
        return returnList;
    }

}
