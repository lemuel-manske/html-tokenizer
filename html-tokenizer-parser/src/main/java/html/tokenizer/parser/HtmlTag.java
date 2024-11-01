package html.tokenizer.parser;

/**
 * Represents an HTML tag.
 *
 * <p>It can be an open tag or a closing tag (self-closing tags are considered open tags).
 *
 * @see HtmlLexer
 * @see HtmlParser
 */
public final class HtmlTag implements Comparable<HtmlTag> {

    private static final String START_TAG = "<%s>";
    private static final String END_TAG = "</%s>";

    private final String tagName;
    private final boolean endTag;

    public static HtmlTag startTag(final String tagName) {
        return new HtmlTag(tagName, false);
    }

    public static HtmlTag endTag(final String tagName) {
        return new HtmlTag(tagName, true);
    }

    private HtmlTag(final String tagName, final boolean endTag) {
        this.tagName = tagName;
        this.endTag = endTag;
    }

    public String getTagName() {
        return tagName;
    }

    public boolean isEndTag() {
        return endTag;
    }

    @Override
    public String toString() {
        return endTag ? END_TAG.formatted(tagName) : START_TAG.formatted(tagName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HtmlTag htmlTag = (HtmlTag) obj;
        return endTag == htmlTag.endTag && tagName.equals(htmlTag.tagName);
    }

    @Override
    public int compareTo(HtmlTag o) {
        if (o == null) return 1;
        return tagName.compareTo(o.tagName);
    }
}
