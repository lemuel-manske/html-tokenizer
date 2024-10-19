package html.tokenizer.parser;

import stack.ListStack;
import stack.Stack;

/**
 * Parses an HTML input and checks if all tags are correctly closed.
 *
 * <p>It uses a {@link HtmlLexer} to tokenize the input.
 *
 * <p>It throws a {@link MissingCloseTag} if a tag is missing a closing tag, and
 * a {@link UnexpectedCloseTag} if a closing tag is unexpected.
 *
 * <p>It returns a {@link HtmlReport} with all the tags that were correctly closed.
 *
 * @see HtmlLexer
 * @see MissingCloseTag
 * @see UnexpectedCloseTag
 * @see HtmlReport
 */
public final class HtmlParser {

    private final HtmlLexer lexer;

    public HtmlParser(final String input) {
        this.lexer = new HtmlLexer(input);
    }

    public HtmlReport parse() throws MissingCloseTag, UnexpectedCloseTag {
        HtmlReport htmlHtmlReport = new HtmlReport();

        Stack<HtmlTag> allTags = lexer.tokenize();

        Stack<HtmlTag> closingTags = new ListStack<>();

        while (!allTags.isEmpty()) {
            HtmlTag currTag = allTags.pop();

            if (currTag.isClosingTag()) {
                closingTags.push(currTag); continue;
            }

            HtmlTag expectedTag = HtmlTag.close(currTag.tagName());

            if (closingTags.isEmpty())
                throw new MissingCloseTag(expectedTag);

            HtmlTag closingTag = closingTags.pop();

            if (!currTag.tagName().equals(closingTag.tagName()))
                throw new UnexpectedCloseTag(closingTag, expectedTag);

            htmlHtmlReport.incrementTagCount(currTag.tagName());
        }

        return htmlHtmlReport;
    }
}
