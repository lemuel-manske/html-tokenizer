package html.tokenizer.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a report of {@link HtmlParser parser} execution.
 *
 * <p>Has a list of all tags found in the HTML, with their names and count.
 */
public final class HtmlReport {

    private final Map<String, Integer> entries; // TODO: use implementation from `data-structures`

    public HtmlReport() {
        this.entries = new HashMap<>();
    }

    public void incrementTagCount(final String tagName) {
        entries.merge(tagName, 1, Integer::sum);
    }

    public int getTagCount(final String tagToFind) {
        return Optional.ofNullable(entries.get(tagToFind)).orElse(0);
    }

    public boolean isEmpty() {
        return entries.isEmpty();
    }

    public Map<String, Integer> entries() {
        return entries;
    }
}
