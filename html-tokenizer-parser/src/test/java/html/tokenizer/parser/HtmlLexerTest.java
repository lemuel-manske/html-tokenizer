package html.tokenizer.parser;

import org.junit.jupiter.api.Test;
import stack.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void shouldNotRecognizeTagsStartingWithNumber() {
        Stack<HtmlTag> tags = parse("<1><1html><1     >");

        assertTrue(tags.isEmpty());
    }

    @Test
    void shouldNotRecognizeEmptyTagName() {
        Stack<HtmlTag> tags = parse("<>");

        assertTrue(tags.isEmpty());
    }

    @Test
    void shouldNotRecognizeTagsWithSpaceBetweenOpenAndCloseTokens() {
        Stack<HtmlTag> tags = parse(
                """
                <\s\s\t\r\f
                html>
                
                <\s\s\f\r
                /\s\s\f\r
                html>
                """);

        assertTrue(tags.isEmpty());
    }

    @Test
    void shouldRecognizeOpenTag() {
        Stack<HtmlTag> tags = parse(
                """
                <html\s\s\s\f\r
                >
                <html>
                """);

        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    @Test
    void tokenizeClosingTag() {
        Stack<HtmlTag> tags = parse("</html>");

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
    }

    @Test
    void tokenizeTagWithSpaces() {
        Stack<HtmlTag> tags = parse(
                """
                <html\f\r
                \s\s\s>
                </html\f\r
                \s\s\s>
                """);

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    @Test
    void tokenizeSelfClosingTags() {
        Stack<HtmlTag> tags = parse(
                """
                <hr\r\r\t
                \s\s\s\f/>
                <img/>
                """);

        assertEquals(HtmlTag.openTag("hr"), tags.pop());
    }

    @Test
    void tokenizeTagWithAttribute() {
        Stack<HtmlTag> tags = parse(
                "<a   \nhref=\"https://www.google.com\">" +
                "<img        src=\"https://www.google.com/logo.png\"/>" +
                "<div   class=\"container\">");

        assertEquals(HtmlTag.openTag("div"), tags.pop());
        assertEquals(HtmlTag.openTag("img"), tags.pop());
        assertEquals(HtmlTag.openTag("a"), tags.pop());
    }

    @Test
    void tokenizeHtmlStructure() {
        Stack<HtmlTag> tags = parse("""
                <html>
                <body>
                <h1>
                Hello World!
                </h1>
                <p>
                This is a paragraph.
                </p>
                </body>
                </html>
                """);

        assertEquals(HtmlTag.closingTag("html"), tags.pop());
        assertEquals(HtmlTag.closingTag("body"), tags.pop());
        assertEquals(HtmlTag.closingTag("p"), tags.pop());
        assertEquals(HtmlTag.openTag("p"), tags.pop());
        assertEquals(HtmlTag.closingTag("h1"), tags.pop());
        assertEquals(HtmlTag.openTag("h1"), tags.pop());
        assertEquals(HtmlTag.openTag("body"), tags.pop());
        assertEquals(HtmlTag.openTag("html"), tags.pop());
    }

    private Stack<HtmlTag> parse(final String input) {
        return new HtmlLexer(input).tokenize();
    }
}
