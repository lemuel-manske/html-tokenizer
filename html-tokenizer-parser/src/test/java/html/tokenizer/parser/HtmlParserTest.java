package html.tokenizer.parser;

import org.junit.jupiter.api.Test;
import sort.QuickSort;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HtmlParserTest {

    @Test
    void givenEmptyInputDoNothing() {
        assertDoesNotThrow(() -> parse(""));
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

    @Test
    void givenValidHtmlThenGenerateReportWithAllTags() throws UnexpectedCloseTag, MissingCloseTag {
        String html =
                """
                <html>
                <head>
                </head>
                </html>
                """;

        HtmlReport htmlHtmlReport = parse(html);

        HtmlReport.TagOccurrence[] tags = htmlHtmlReport.getTags(new QuickSort<>());

        assertEquals(2, tags.length);

        assertEquals("head", tags[0].getTag());
        assertEquals(1, tags[0].getOccurrences());

        assertEquals("html", tags[1].getTag());
        assertEquals(1, tags[1].getOccurrences());
    }

    private HtmlReport parse(final String html) throws UnexpectedCloseTag, MissingCloseTag {
        return new HtmlParser(html).parse();
    }
}
