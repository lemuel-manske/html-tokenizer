package html.tokenizer.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlParserTest {

    @Test
    void givenEmptyInputDoNothing() {
        assertDoesNotThrow(() -> parse(""));
    }

    @Test
    void givenEmptyInputThenReportIsEmpty() throws UnexpectedCloseTag, MissingCloseTag {
        HtmlReport htmlReport = parse("");

        assertTrue(htmlReport.isEmpty());
    }

    @Test
    void givenMissingCloseTagsThenThrowMissingCloseTag() {
        String html =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                """;

        assertThrows(MissingCloseTag.class, () -> parse(html));
    }

    @Test
    void givenMissingCloseTagThenInformWhichTagIsMissing() {
        String html =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                </body>
                """;

        MissingCloseTag exception =
                assertThrows(MissingCloseTag.class, () -> parse(html));

        assertEquals(HtmlTag.close("html"), exception.missingTag());
    }

    @Test
    void givenUnexpectedEndCloseThenThrowUnexpectedCloseTag() {
        String html =
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

        assertThrows(UnexpectedCloseTag.class, () -> parse(html));
    }
    
    @Test
    void givenUnexpectedEndCloseThenInformWhichTagWasUnexpected() {
        String html =
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

        UnexpectedCloseTag exception =
                assertThrows(UnexpectedCloseTag.class, () -> parse(html));

        assertEquals(HtmlTag.close("meta"), exception.unexpectedTag());
    }

    @Test
    void givenUnexpectedEndCloseThenInformWhichTagWasExpected() {
        String html =
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

        UnexpectedCloseTag exception =
                assertThrows(UnexpectedCloseTag.class, () -> parse(html));

        assertEquals(HtmlTag.close("html"), exception.expectedTag());
    }



    @Test
    void givenValidHtmlThenGenerateReport() throws UnexpectedCloseTag, MissingCloseTag {
        String html =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                    <p>First</p>
                    <p>Second</p>
                </body>
                </html>
                """;

        HtmlReport htmlHtmlReport = parse(html);

        assertEquals(1, htmlHtmlReport.getTagCount("html"));
        assertEquals(1, htmlHtmlReport.getTagCount("head"));
        assertEquals(1, htmlHtmlReport.getTagCount("title"));
        assertEquals(1, htmlHtmlReport.getTagCount("body"));
        assertEquals(1, htmlHtmlReport.getTagCount("h1"));
        assertEquals(2, htmlHtmlReport.getTagCount("p"));
    }

    private HtmlReport parse(final String html) throws UnexpectedCloseTag, MissingCloseTag {
        return new HtmlParser(html).parse();
    }
}
