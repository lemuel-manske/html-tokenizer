package html.tokenizer.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlParserTest {

    @Test
    void givenEmptyInputDoNothing() {
        assertDoesNotThrow(() -> parse(""));
    }

    @Test
    void givenMissingEndTagsThenThrowMissingEndTag() {
        String contentMissingHtmlEndTag =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                </body>
                """;

        MissingEndTag exception =
                assertThrows(MissingEndTag.class, () -> parse(contentMissingHtmlEndTag));

        assertEquals(HtmlTag.endTag("html"), exception.missingTag());
    }
    
    @Test
    void givenUnexpectedEndTagThenThrowUnexpectedEndTag() {
        String contentWithUnexpectedMetaEndTagForHtmlStartTag =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                </body>
                </meta>
                """;

        UnexpectedEndTag exception =
                assertThrows(UnexpectedEndTag.class, () -> parse(contentWithUnexpectedMetaEndTagForHtmlStartTag));

        assertEquals(HtmlTag.endTag("meta"), exception.unexpectedTag());
        assertEquals(HtmlTag.endTag("html"), exception.expectedTag());
    }

    @Test
    void givenValidHtmlThenGenerateReport() throws UnexpectedEndTag, MissingEndTag {
        String html =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body class="main">
                    <h1>Test</h1>
                    <a href="https://www.example.com">Example</a>
                    <p>First</p>
                    <p id="second">Second</p>
                    <a href="https://www.google.com">Google</a>
                </body>
                </html>
                """;

        HtmlReport htmlHtmlReport = parse(html);

        assertEquals(1, htmlHtmlReport.getTagCount("html"));
        assertEquals(1, htmlHtmlReport.getTagCount("head"));
        assertEquals(1, htmlHtmlReport.getTagCount("title"));
        assertEquals(1, htmlHtmlReport.getTagCount("body"));
        assertEquals(1, htmlHtmlReport.getTagCount("h1"));
        assertEquals(2, htmlHtmlReport.getTagCount("a"));
        assertEquals(2, htmlHtmlReport.getTagCount("p"));
    }

    private HtmlReport parse(final String html) throws UnexpectedEndTag, MissingEndTag {
        return new HtmlParser(html).parse();
    }
}
