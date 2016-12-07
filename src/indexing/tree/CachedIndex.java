package indexing.tree;

class CachedIndex {
    private int value;

    private static int maxLength;

    private CachedIndex(int value) {
        this.value = value;
    }

    int getValue() {
        return value;
    }

    static CachedIndex getIndexObject(int index) {
        return IntsCache.VALUES[index];
    }

    static void generateIndexes(int maxLength) {
        CachedIndex.maxLength = maxLength;
    }

    private static class IntsCache {
        private static final CachedIndex[] VALUES = new CachedIndex[maxLength];

        static {
            System.out.println("Creating cached indexes...");
            for (int i = 0; i < VALUES.length; i++) {
                VALUES[i] = new CachedIndex(i);
            }
            System.out.println("" + VALUES.length + " indexes created.");
        }
    }
}