import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        int sum = 0;
        if (L.size() == 0) {
	        return sum;
        }
	    for (int num : L) {
	        sum += num;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        List<Integer> res = new ArrayList<>();
        if (L.size() == 0) {
            return res;
        }
        for (int num : L) {
            if ((num & 1) == 0) {
                res.add(num);
            }
        }
        return res;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        List<Integer> res = new ArrayList<>();
        if (L1.size() < L2.size()) {
            for (int num: L1) {
                if (L2.contains(num)) {
                    res.add(num);
                }
            }
        } else {
            for (int num: L2) {
                if (L1.contains(num)) {
                    res.add(num);
                }
            }
        }
        return res;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        int res = 0;
        for (String word : words) {
            for (char ch : word.toCharArray()) {
                if (ch == c) {
                    res++;
                }
            }
        }
        return res;
    }
}
