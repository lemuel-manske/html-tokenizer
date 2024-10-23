package html.tokenizer.parser;

/**
 * Indicates that is missing a closing tag for a given tag in the HTML document.
 */
public final class MissingCloseTag extends Exception {

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
