package indexing.tree;

class Entry implements Comparable<Entry> {
    private String name;
    private long time;

    Entry(String name, long time) {
        this.name = name;
        this.time = time;
    }

    String getName() {
        return name;
    }

    private long getTime() {
        return time;
    }

    @Override
    public int compareTo(Entry o) {
        long x = this.time;
        long y = o.getTime();
        // comparison took from Long class
        return (x < y) ? 1 : ((x == y) ? this.name.compareTo(o.getName()) : -1);
    }
}
