import indexing.ISearcher;
import indexing.ISearcherImplTree;

public class Main {
    public static void main(String[] args) {
        String[] names = {"aaa", "aaa", "aab", "aab", "aac", "abc", "acb", "aaa", "aaa"};
        long[] dates = {1, 3, 2, 1, 3, 2, 1, 3, 2};
        String guess = "aa";

        ISearcher searcher = new ISearcherImplTree();
        searcher.refresh(names, dates);

        for (String s : searcher.guess(guess)) {
            System.out.println(s);
        }
    }
}
