package html.tokenizer.parser;

/**
 * Indicates that a close tag was found when it was not expected.
 */
public final class UnexpectedCloseTag extends Exception {

    public static final String UNEXPECTED_CLOSE_TAG = "Unexpected close tag %s";

    private final transient HtmlTag unexpectedTag;
    private final transient HtmlTag expectedTag;

    public UnexpectedCloseTag(final HtmlTag unexpectedTag, final HtmlTag expectedTag) {
        super(UNEXPECTED_CLOSE_TAG.formatted(unexpectedTag.tagName()));

        this.unexpectedTag = unexpectedTag;
        this.expectedTag = expectedTag;
    }

    public HtmlTag unexpectedTag() {
        return unexpectedTag;
    }

    public HtmlTag expectedTag() {
        return expectedTag;
    }
}
