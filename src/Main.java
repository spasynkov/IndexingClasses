import indexing.ISearcherImpl;

public class Main {
    public static void main(String[] args) {
        String[] names = {"aaa", "aaa", "aab", "aab", "aac", "abc", "acb", "aaa", "aaa"};
        long[] dates = {1, 3, 2, 1, 3, 2, 1, 3, 2};

        ISearcherImpl searcher = new ISearcherImpl();
        searcher.refresh(names, dates);
        searcher.print();
    }
}
