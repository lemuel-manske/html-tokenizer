package html.tokenizer.parser;

import stack.ListStack;
import stack.Stack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlLexer {

    public static final String NULLISH_CHARACTERS = "[\s\f\n\r\t\u00A0]";
    private static final Pattern TAG_PATTERN = Pattern.compile("<(/?)\\s*([a-zA-Z][a-zA-Z0-9]*)([^<>])*(\\s*/)?\\s*>");

    private final Matcher htmlInput;

    public HtmlLexer(String input) {
        if (input == null)
            throw new NullPointerException("HTML content cannot be null");

        String trimmedInput = input.trim();

        if (trimmedInput.isEmpty() || trimmedInput.matches(NULLISH_CHARACTERS))
            throw new NoContent();

        this.htmlInput = TAG_PATTERN.matcher(input);
    }

    public Stack<HtmlTag> tokenize() throws MalformedTag {
        Stack<HtmlTag> tags = new ListStack<>();

        while (htmlInput.find()) {
            if (getTagName() == null || getTagName().isEmpty())
                throw new MalformedTag();

            if (isOpenTag() || isSelfClosingTag())
                tags.push(HtmlTag.openTag(getTagName()));

            else if (isCloseTag())
                tags.push(HtmlTag.closingTag(getTagName()));
        }

        if (tags.isEmpty())
            throw new MalformedTag();

        return tags;
    }

    private boolean isCloseTag() {
        return getClosingIndicator().equals("/");
    }

    private boolean isOpenTag() {
        return getClosingIndicator().isEmpty();
    }

    private boolean isSelfClosingTag() {
        return getSelfClosingIndicator() != null && !getSelfClosingIndicator().isEmpty();
    }

    private String getClosingIndicator() {
        return htmlInput.group(1);
    }

    private String getTagName() {
        return htmlInput.group(2);
    }

    private String getSelfClosingIndicator() {
        return htmlInput.group(4);
    }
}
