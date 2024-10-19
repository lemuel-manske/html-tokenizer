package html.tokenizer.parser;

import list.LinkedList;

/**
 * Represents a report of the whole analysis.
 *
 * <p>Has a list of all tags found in the HTML, with their names and count.
 */
public final class Report {

    private final LinkedList<ReportEntry> entries;

    public Report() {
        this.entries = new LinkedList<>();
    }

    public void add(final String tagName) {
        ReportEntry entry = get(tagName);

        if (entry == null)
            entries.add(new ReportEntry(tagName));
        else
            entry.incrementCount();
    }

    public ReportEntry get(final String tagToFind) {
        for (int i = 0; i < entries.size(); i++) {
            ReportEntry row = entries.getByIndex(i).value();

            if (row.tagName().equals(tagToFind)) {
                return row;
            }
        }

        return null;
    }
}
