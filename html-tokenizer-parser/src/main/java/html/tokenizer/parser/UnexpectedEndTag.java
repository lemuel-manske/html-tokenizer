package html.tokenizer.parser;

/**
 * Indicates that an end tag was found, but it was not expected.
 *
 * <p>For example, if the HTML content is:
 *
 * <pre>
 * &lt;div&gt;
 * &lt;p&gt;Hello&lt;/div&gt;
 * &lt;/p&gt;
 * </pre>
 *
 * The unexpected end tag would be the <code>&lt;/div&gt;</code> tag when expecting the <code>&lt;/p&gt;</code> tag.
 *
 * @see HtmlLexer
 */
public final class UnexpectedEndTag extends Exception {

    private static final String MESSAGE = "Unexpected end tag %s when expecting %s";

    private final transient HtmlTag unexpectedTag;
    private final transient HtmlTag expectedTag;

    public UnexpectedEndTag(final HtmlTag unexpectedTag, final HtmlTag expectedTag) {
        super(MESSAGE.formatted(unexpectedTag.getTagName(), expectedTag.getTagName()));

        this.unexpectedTag = unexpectedTag;
        this.expectedTag = expectedTag;
    }

    public HtmlTag getUnexpectedTag() {
        return unexpectedTag;
    }

    public HtmlTag getExpectedTag() {
        return expectedTag;
    }
}
