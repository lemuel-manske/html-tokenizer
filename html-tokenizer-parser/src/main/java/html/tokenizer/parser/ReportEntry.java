package html.tokenizer.parser;

public final class ReportEntry {

    private static final String PRINT_FORMAT = "[ tagName=%s , count=%d ]";

    private final String tagName;
    private int count;

    public ReportEntry(String tagName) {
        this.tagName = tagName;
        this.count = 1;
    }

    public String tagName() { return tagName; }

    public int count() { return count; }

    public void incrementCount() { count++; }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(tagName, count);
    }
}
