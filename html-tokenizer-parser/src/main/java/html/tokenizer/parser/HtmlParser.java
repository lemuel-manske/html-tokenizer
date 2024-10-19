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
 * <p>It returns a {@link Report} with all the tags that were correctly closed.
 *
 * @see HtmlLexer
 * @see MissingCloseTag
 * @see UnexpectedCloseTag
 * @see Report
 */
public final class HtmlParser {

    private final HtmlLexer lexer;

    public HtmlParser(final String input) {
        this.lexer = new HtmlLexer(input);
    }

    public Report parse() {
        Report report = new Report();

        Stack<HtmlTag> closingTags = new ListStack<>();

        Stack<HtmlTag> allTags = lexer.tokenize();

        while (!allTags.isEmpty()) {
            HtmlTag currTag = allTags.pop();

            if (currTag.isClosingTag()) {
                closingTags.push(currTag);
            } else {
                HtmlTag expectedTag = HtmlTag.close(currTag.tagName());

                if (closingTags.isEmpty())
                    throw new MissingCloseTag(expectedTag);

                HtmlTag closingTag = closingTags.pop();

                if (!currTag.tagName().equals(closingTag.tagName()))
                    throw new UnexpectedCloseTag(closingTag, expectedTag);

                report.add(currTag.tagName());
            }
        }

        return report;
    }
}
