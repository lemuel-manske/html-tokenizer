package html.tokenizer.parser;

import org.junit.jupiter.api.Test;
import stack.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlLexerTest {

    @Test
    void throwNPEForNullHtml() {
        assertThrows(NullPointerException.class,
                () -> parse(null));
    }

    @Test
    void throwNoContentForEmptyHtml() {
        assertThrows(NoContent.class,
                () -> parse("\t\n\f\r\u00A0\s\s\s\s"));
    }

    @Test
    void tokenizeOpenTag() throws MalformedTag {
        Stack<HtmlTag> tags = parse("<html>");

        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    @Test
    void tokenizeClosingTag() throws MalformedTag {
        Stack<HtmlTag> tags = parse("</html>");

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
    }

    @Test
    void tokenizeNotFormattedTag() throws MalformedTag {
        Stack<HtmlTag> tags = parse(
                "<\f\r\t\s\s\n\nhtml\f\r\n\s\s\s>" +
                "<\f\r\t\s/\s\n\nhtml\f\r\n\s\s\s>");

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    @Test
    void tokenizeEmptyTagThrowsMalformedTag() {
        assertThrows(MalformedTag.class,
                () -> parse("<>"));
    }

    @Test
    void tokenizeSelfClosingTags() throws MalformedTag {
        Stack<HtmlTag> tags = parse("<\n\nhr\r\r\t\n\s\s\s\f/>");

        assertEquals(HtmlTag.openTag("hr"), tags.pop());
    }

    @Test
    void tokenizeTagWithAttribute() throws MalformedTag {
        Stack<HtmlTag> tags = parse(
                "<a\s\nhref=\"https://www.google.com\">" +
                "<\s\s\s\n\timg src=\"https://www.google.com/logo.png\"/>" +
                "<div\s\s\sclass=\"container\">");

        assertEquals(HtmlTag.openTag("div"), tags.pop());
        assertEquals(HtmlTag.openTag("img"), tags.pop());
        assertEquals(HtmlTag.openTag("a"), tags.pop());
    }

    @Test
    void tokenizeHtmlStructure() throws MalformedTag {
        Stack<HtmlTag> tags = parse("""
                <html>
                <          body      >
                
                
                
                <
                
                      h1 \f\f\f\t\s\s>
                Hello World!
                </   h1>
                <   p  >
                This is a paragraph.
                <  /    p>
                </body     \n\t>
                </html    >
                """);

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
        assertEquals(HtmlTag.closingTag("body"), tags.pop());
        assertEquals(HtmlTag.openTag("p"), tags.pop());
        assertEquals(HtmlTag.closingTag("h1"), tags.pop());
        assertEquals(HtmlTag.openTag("h1"), tags.pop());
        assertEquals(HtmlTag.closingTag("p"), tags.pop());
        assertEquals(HtmlTag.openTag("body"), tags.pop());
        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    private Stack<HtmlTag> parse(final String input) throws MalformedTag {
        return new HtmlLexer(input).tokenize();
    }
}
