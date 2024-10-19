package html.tokenizer.parser;

/**
 * Indicates that html content is missing a closing tag.
 */
public final class MissingCloseTag extends RuntimeException {

    public static final String MISSING_CLOSE_TAG = "Missing close tag %s";

    private final transient HtmlTag missingTag;

    public MissingCloseTag(final HtmlTag missingTag) {
        super(MISSING_CLOSE_TAG.formatted(missingTag.tagName()));
        this.missingTag = missingTag;
    }

    public HtmlTag missingTag() {
        return missingTag;
    }
}
