package html.tokenizer.view;

public class Tag {

    private final String tagName;
    private final Integer occurrences;

    public Tag(final String tagName, final Integer occurrences) {
        this.tagName = tagName;
        this.occurrences = occurrences;
    }

    public String getTagName() {
        return tagName;
    }

    public Integer getOccurrences() {
        return occurrences;
    }
}