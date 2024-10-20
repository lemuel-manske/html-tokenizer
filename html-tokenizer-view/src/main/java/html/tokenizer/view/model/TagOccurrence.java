package html.tokenizer.view.model;

import java.util.Map;

public class TagOccurrence {

    private final String tag;
    private final Integer occurrences;

    public static TagOccurrence fromMapEntry(Map.Entry<String, Integer> tag) {
        return new TagOccurrence(tag.getKey(), tag.getValue());
    }

    private TagOccurrence(final String tag, final Integer occurrences) {
        this.tag = tag;
        this.occurrences = occurrences;
    }

    public String getTag() {
        return tag;
    }

    public Integer getOccurrences() {
        return occurrences;
    }
}
