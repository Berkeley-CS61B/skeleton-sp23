package ngordnet.main;

import ngordnet.browser.NgordnetServer;

public class Main {
    public static void main(String[] args) {
        NgordnetServer hns = new NgordnetServer();

        /* The following code might be useful to you.

        String wordFile = "./data/ngrams/top_14377_words.csv";
        String countFile = "./data/ngrams/total_counts.csv";
        NGramMap ngm = new NGramMap(wordFile, countFile);

        */

        hns.startUp();
        hns.register("history", new DummyHistoryHandler());
        hns.register("historytext", new DummyHistoryTextHandler());
    }
}
