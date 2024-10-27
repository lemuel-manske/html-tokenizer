package html.tokenizer.parser;

import list.StaticList;
import stack.ListStack;
import stack.Stack;

/**
 * Parses an HTML input and checks if all tags are correctly closed.
 *
 * <p>Uses a {@link HtmlLexer} to tokenize the input.
 *
 * <p>Throws a {@link MissingEndTag} if a tag is missing a closing tag, and
 * a {@link UnexpectedEndTag} if a closing tag is unexpected.
 *
 * <p>Returns a {@link HtmlReport} with all the tags that were correctly closed.
 *
 * @see HtmlLexer
 * @see MissingEndTag
 * @see UnexpectedEndTag
 * @see HtmlReport
 */
public final class HtmlParser {

    private final HtmlLexer lexer;

    public HtmlParser(final String input) {
        this.lexer = new HtmlLexer(input);

    }

    public HtmlReport parse() throws MissingEndTag, UnexpectedEndTag {
        StaticList<HtmlTag> allTags = lexer.tokenize();

        HtmlReport htmlReport = new HtmlReport();

        Stack<HtmlTag> closingTags = new ListStack<>();

        for (int i = allTags.size() - 1; i >= 0; i--) {
            HtmlTag currTag = allTags.get(i);

            if (currTag.isEndTag()) {
                closingTags.push(currTag); continue;
            }

            if (isSelfClosingTag(currTag)) {
                htmlReport.put(currTag.tagName()); continue;
            }

            HtmlTag expectedTag = HtmlTag.endTag(currTag.tagName());

            if (closingTags.isEmpty())
                throw new MissingEndTag(expectedTag);

            HtmlTag closingTag = closingTags.pop();

            if (!currTag.tagName().equals(closingTag.tagName()))
                throw new UnexpectedEndTag(closingTag, expectedTag);

            htmlReport.put(currTag.tagName());
        }

        return htmlReport;
    }

    private boolean isSelfClosingTag(HtmlTag tag) {
        return SelfClosingTags.contains(tag.tagName());
    }
}
