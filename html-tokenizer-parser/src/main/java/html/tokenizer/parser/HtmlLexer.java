package html.tokenizer.parser;

import stack.ListStack;
import stack.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes HTML content into {@link HtmlTag} instances.
 *
 * @see HtmlTag
 * @see HtmlParser
 */
public final class HtmlLexer {

    private static final String OPEN_TOKEN = "<(/?)";
    public static final String CLOSE_TOKEN = "/?>";
    public static final String TAG_NAME = "([a-zA-Z][a-zA-Z0-9]*)";
    public static final String REST_OF_TAG_CONTENT = "[^>]*";

    private static final Pattern HTML_TAG = Pattern.compile(OPEN_TOKEN + TAG_NAME + REST_OF_TAG_CONTENT + CLOSE_TOKEN);

    private final Matcher htmlInput;

    public HtmlLexer(final String input) {
        if (input == null)
            throw new NullPointerException("HTML content cannot be null");

        this.htmlInput = HTML_TAG.matcher(input);
    }

    public Stack<HtmlTag> tokenize() {
        Stack<HtmlTag> tags = new ListStack<>();

        while (htmlInput.find()) {
            if (isOpenTag())
                tags.push(HtmlTag.open(tagName()));

            else if (isCloseTag())
                tags.push(HtmlTag.close(tagName()));
        }

        return tags;
    }

    private boolean isCloseTag() {
        return closeTagIndicator().equals("/");
    }

    private boolean isOpenTag() {
        return closeTagIndicator().isEmpty();
    }

    private String closeTagIndicator() {
        return htmlInput.group(1);
    }

    private String tagName() {
        return htmlInput.group(2).toLowerCase();
    }
}
