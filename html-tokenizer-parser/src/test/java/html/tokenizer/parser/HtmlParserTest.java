package html.tokenizer.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlParserTest {

    @Test
    void givenEmptyInputDoNothing() {
        assertDoesNotThrow(() -> parse(""));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "meta",
            "base",
            "br",
            "col",
            "command",
            "embed",
            "hr",
            "img",
            "input",
            "link",
            "param",
            "source",
            "!DOCTYPE",
            "!doctype"
    })
    void givenSelfClosingTagsThenDoNotThrowMissingEndTag(final String selfClosingTag) throws MissingEndTag, UnexpectedEndTag {
        HtmlReport report = parse("<%s>".formatted(selfClosingTag));

        assertEquals(1, report.getTagCount(selfClosingTag));
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
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
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
        assertEquals(1, htmlHtmlReport.getTagCount("meta"));
        assertEquals(1, htmlHtmlReport.getTagCount("!DOCTYPE"));
    }

    private HtmlReport parse(final String html) throws UnexpectedEndTag, MissingEndTag {
        return new HtmlParser(html).parse();
    }
}
