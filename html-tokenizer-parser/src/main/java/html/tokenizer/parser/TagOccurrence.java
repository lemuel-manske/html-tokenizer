package html.tokenizer.parser;

public final class TagOccurrence implements Comparable<TagOccurrence> {

    private static final String PRINT_FORMAT = "[ %s: %d ]";

    private Integer occurrences = 1;
    private final String tag;

    TagOccurrence(final String tag) { this.tag = tag; }

    void incrementTagOccurrence() { occurrences++; }

    public String getTag() { return tag; }
    public Integer getOccurrences() { return occurrences; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TagOccurrence that = (TagOccurrence) obj;
        return tag.equals(that.tag);
    }

    @Override
    public int compareTo(TagOccurrence o) {
        if (o == null) return 1;
        return tag.compareTo(o.tag);
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(tag, occurrences);
    }
}
