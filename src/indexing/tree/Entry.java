package indexing.tree;

class Entry implements Comparable {
    private String name;
    private long time;

    Entry(String name, long time) {
        this.name = name;
        this.time = time;
        System.out.println("Entry " + name + " " + time + " created.");
    }

    String getName() {
        return name;
    }

    private long getTime() {
        return time;
    }

    @Override
    public int compareTo(Object o) {
        long x = this.time;
        long y = ((Entry) o).getTime();
        // comparison took from Long class
        return (x < y) ? 1 : ((x == y) ? this.name.compareTo(((Entry) o).getName()) : -1);
    }
}
