package html.tokenizer.parser;

import java.util.Objects;

/**
 * Represents an HTML tag.
 *
 * <p>It can be an open tag, a self-closing tag or a closing tag.
 *
 * @see HtmlLexer
 * @see HtmlParser
 */
public final class HtmlTag {

    public static final String PRINT_FORMAT = "[ isClosingTag=%b , tagName=%s ]";

    private final String tagName;
    private final boolean isClosingTag;

    public static HtmlTag open(final String tagName) {
        return new HtmlTag(tagName, false);
    }

    public static HtmlTag close(final String tagName) {
        return new HtmlTag(tagName, true);
    }

    private HtmlTag(final String tagName, final boolean isClosingTag) {
        this.tagName = tagName;
        this.isClosingTag = isClosingTag;
    }

    public String tagName() {
        return tagName;
    }

    public boolean isClosingTag() {
        return isClosingTag;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        HtmlTag htmlTag = (HtmlTag) obj;
        return isClosingTag == htmlTag.isClosingTag && tagName.equals(htmlTag.tagName);
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(isClosingTag, tagName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName, isClosingTag);
    }
}
