package html.tokenizer.parser;

/**
 * Indicates that is missing a closing tag for a given open tag in the HTML document.
 *
 * <p>For example, if the HTML content is:
 *
 * <pre>
 * &lt;html&gt;
 *    &lt;head&gt;
 *    &lt;title&gt;Hello, World!&lt;/title&gt;
 *    &lt;/head&gt;
 * </pre>
 *
 * The missing end tag would be for the <code>html</code> tag.
 *
 * @see HtmlLexer
 */
public final class MissingEndTag extends Exception {

    private static final String MESSAGE = "Missing end tag for open tag %s";

    private final transient HtmlTag missingTag;

    public MissingEndTag(final HtmlTag missingTag) {
        super(MESSAGE.formatted(missingTag.getTagName()));

        this.missingTag = missingTag;
    }

    public HtmlTag getMissingTag() {
        return missingTag;
    }
}
