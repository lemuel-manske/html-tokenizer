package html.tokenizer.parser;

import list.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Represents a report of {@link HtmlParser parser} execution.
 *
 * <p>Has a list of all tags found in the HTML, with their names and count.
 */
public final class HtmlReport implements Iterable<HtmlReport.Entry> {

    private final LinkedList<Entry> entries;

    public HtmlReport() {
        this.entries = new LinkedList<>();
    }

    public void incrementTagCount(final String tagName) {
        Optional.ofNullable(get(tagName))
                .ifPresentOrElse(Entry::increment, () -> entries.add(Entry.forTag(tagName)));
    }

    public int getTagCount(final String tagToFind) {
        Entry entry = get(tagToFind);

        return entry == null
                ? 0
                : entry.count();
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public Iterator<HtmlReport.Entry> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < entries.size();
            }

            @Override
            public Entry next() {
                if (!hasNext()) throw new NoSuchElementException();
                return entries.getByIndex(index++).value();
            }
        };
    }

    private Entry get(final String tagToFind) {
        for (Entry entry : this)
            if (entry.tagName().equals(tagToFind))
                return entry;

        return null;
    }

    /**
     * Represents a tag entry in the report.
     *
     * <p>Contains the tag name and its count.
     */
    public static final class Entry {

        private static final String PRINT_FORMAT = "[ tagName=%s , count=%d ]";

        private final String tagName;
        private int count;

        private Entry(final String tagName) {
            this(tagName, 1);
        }

        private Entry(final String tagName, final int count) {
            this.tagName = tagName;
            this.count = count;
        }

        public static Entry forTag(final String tagName) {
            return new Entry(tagName);
        }

        public String tagName() {
            return tagName;
        }

        public int count() {
            return count;
        }

        public void increment() {
            count++;
        }

        @Override
        public String toString() {
            return PRINT_FORMAT.formatted(tagName, count);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Entry that = (Entry) obj;

            return tagName.equals(that.tagName) && count == that.count;
        }
    }
}
