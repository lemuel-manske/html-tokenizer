package html.tokenizer.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlParserTest {

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
    void givenValidHtmlThenGenerateReport() {
        String html =
                """
                <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Test</h1>
                </body>
                </html>
                """;

        Report report = parse(html);

        assertEquals(1, report.get("html").count());
        assertEquals(1, report.get("head").count());
        assertEquals(1, report.get("title").count());
        assertEquals(1, report.get("body").count());
        assertEquals(1, report.get("h1").count());
    }

    private Report parse(final String html) {
        return new HtmlParser(html).parse();
    }
}
