package html.tokenizer.parser;

import list.StaticList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes the HTML content into {@link HtmlTag} instances, which can be start or end tags (self-closing tags are
 * considered start tags).
 *
 * <p>For this implementation, the <a href="https://www.ietf.org/rfc/rfc1866.txt">Hypertext Markup Language - 2.0 RFC</a> was used as a reference
 * for tokenizing the HTML content into tags. There might be edge cases that are not covered by this implementation
 * as a full HTML parser is not the goal of this project.</p>
 *
 * @see HtmlTag
 * @see HtmlParser
 */
public final class HtmlLexer {

    private static final String START_TOKEN = "<(/?)";
    public static final String END_TOKEN = ">";
    public static final String NAME = "([a-zA-Z][a-zA-Z0-9-.]*)";
    public static final String REST_OF_TAG_CONTENT = "[^>]*";

    private static final Pattern HTML_TAG = Pattern.compile(START_TOKEN + NAME + REST_OF_TAG_CONTENT + END_TOKEN);

    private final Matcher htmlInput;

    public HtmlLexer(final String input) {
        if (input == null)
            throw new NullPointerException("HTML content cannot be null");

        this.htmlInput = HTML_TAG.matcher(input);
    }

    public StaticList<HtmlTag> tokenize() {
        final StaticList<HtmlTag> tags = new StaticList<>(htmlInput.groupCount());

        while (htmlInput.find()) {
            if (isStartTag())
                tags.add(HtmlTag.startTag(tagName()));

            else if (isEndTag())
                tags.add(HtmlTag.endTag(tagName()));
        }

        return tags;
    }

    private boolean isEndTag() {
        return endTagIndicator().equals("/");
    }

    private boolean isStartTag() {
        return endTagIndicator().isEmpty();
    }

    private String endTagIndicator() {
        return htmlInput.group(1);
    }

    private String tagName() {
        return htmlInput.group(2).toLowerCase();
    }
}
