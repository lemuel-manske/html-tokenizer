package html.tokenizer.parser;

import list.StaticList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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
    void doNothingForEmptyInput() {
        assertDoesNotThrow(() -> parse("\t\n\f\r\u00A0\s\s\s\s"));
    }
    
    @Test
    void shouldNotRecognizeTagsStartingWithNumber() {
        StaticList<HtmlTag> tags = parse("<1><1html><1     >");

        assertTrue(tags.isEmpty());
    }

    @Test
    void shouldNotRecognizeEmptyTagName() {
        StaticList<HtmlTag> tags = parse("<>");

        assertTrue(tags.isEmpty());
    }

    @Test
    void shouldNotRecognizeTagsWithSpaceBetweenOpenAndCloseTokens() {
        StaticList<HtmlTag> tags = parse(
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
        StaticList<HtmlTag> tags = parse(
                """
                <html\s\s\s\f\r
                >
                <html>
                """);

        assertEquals(HtmlTag.open("html"), tags.get(0));
    }

    @Test
    void tokenizeClosingTag() {
        StaticList<HtmlTag> tags = parse("</html>");

        assertEquals(HtmlTag.close("html"), tags.get(0));
    }

    @Test
    void tokenizeTagWithSpaces() {
        StaticList<HtmlTag> tags = parse(
                """
                <html\f\r
                \s\s\s>
                </html\f\r
                \s\s\s>
                """);

        assertTags(tags, HtmlTag.open("html"), HtmlTag.close("html"));
    }

    private void assertTags(StaticList<HtmlTag> tags, HtmlTag... expectedTags) {
        for (int i = 0; i < expectedTags.length; i++)
            assertEquals(expectedTags[i], tags.get(i));
    }

    @Test
    void tokenizeSelfClosingTags() {
        StaticList<HtmlTag> tags = parse(
                """
                <hr\r\r\t
                \s\s\s\f/>
                <img/>
                """);

        assertTags(tags, HtmlTag.open("hr"), HtmlTag.open("img"));
    }

    @Test
    void tokenizeTagWithAttribute() {
        StaticList<HtmlTag> tags = parse(
                "<a   \nhref=\"https://www.google.com\">" +
                "<img        src=\"https://www.google.com/logo.png\"/>" +
                "<div   class=\"container\">");

        assertTags(tags, HtmlTag.open("a"), HtmlTag.open("img"), HtmlTag.open("div"));
    }

    @Test
    void tokenizeHtmlStructure() {
        StaticList<HtmlTag> tags = parse("""
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

        assertTags(tags,
                HtmlTag.open("html"),
                HtmlTag.open("body"),
                HtmlTag.open("h1"),
                HtmlTag.close("h1"),
                HtmlTag.open("p"),
                HtmlTag.close("p"),
                HtmlTag.close("body"),
                HtmlTag.close("html"));
    }

    @Test
    void tokenizeUpperCaseTagsToLowerCaseOnes() {
        StaticList<HtmlTag> tags = parse(
                """
                <HTML>
                <BODY>
                <H1>
                Hello World!
                </H1>
                <P>
                This is a paragraph.
                </P>
                </BODY>
                </HTML>
                """);

        assertTags(tags,
                HtmlTag.open("html"),
                HtmlTag.open("body"),
                HtmlTag.open("h1"),
                HtmlTag.close("h1"),
                HtmlTag.open("p"),
                HtmlTag.close("p"),
                HtmlTag.close("body"),
                HtmlTag.close("html"));
    }

    private StaticList<HtmlTag> parse(final String input) {
        return new HtmlLexer(input).tokenize();
    }
}
