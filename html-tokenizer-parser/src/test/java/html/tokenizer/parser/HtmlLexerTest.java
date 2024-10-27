package html.tokenizer.parser;

import list.StaticList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HtmlLexerTest {

    @Test
    void shouldThrowForNullHtmlContent() {
        assertThrows(NullPointerException.class, () -> parse(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<!-- regular comment -->",
            "<!-- comment with white spaces --         >",
            "<!-- first --     -- second --             >",
    })
    void shouldIgnoreComments(final String comment) {
        assertNoTagsWereTokenized(comment);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "\n",
            "\t",
            "\r",
            "\f",
            "\s\s\s",
    })
    void shouldNotRecognizeEmptyInput(final String emptyInput) {
        assertNoTagsWereTokenized(emptyInput);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<1>",
            "<.>",
            "<->",

            "<1html>",
            "<1     >",
            "<1     html>",
            "<   1   >",
            "<   1   html>",
            "<   1   html     >",

            "<1html/>",
            "<1html     />",
            "<   1html     />",
            "<   1html/>",
            "<   1   html/>",
            "<   1   html     />",

            "< . >",
            "< .html>",
            "< .      />",
            "< .     html>",
            "< .      html/>",

            "<   -   >",
            "<   -   html>",
            "<   -html     >",
            "<   -   html/>",
            "<   -   html     />",

            "<>",
            "< >",
            "<\n>",
            "<\t>",
            "<\r>",
            "<\f>",
    })
    void shouldNotRecognizeTagsStartingWithDigitsHyphensPeriodsOrJustEmpty(final String tagStartingWithNumber) {
        assertNoTagsWereTokenized(tagStartingWithNumber);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "< html>",
            "<  html  >",
    })
    void shouldNotRecognizeStartTagsWithSpaceBetweenStartOrEndTokens(final String invalidTag) {
        assertNoTagsWereTokenized(invalidTag);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "< /html>",
            "<  /  html>",
            "< /  html  >",
    })
    void shouldNotRecognizeEndTagsWithSpaceBetweenStartOrEndTokens(final String invalidTag) {
        assertNoTagsWereTokenized(invalidTag);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<!doctype html>",
            "<!DOCTYPE html>",
            "<!doctype html   >",
            "<!DOCTYPE html   >",
            "<!DocTypE html>",
    })
    void shouldRecognizeDoctype(final String docType) {
        HtmlTag expectedTag = HtmlTag.startTag("!doctype");

        assertEquals(expectedTag, parse(docType).get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "html",
            "html   ",
            "html1",
            "custom-tag",
            "custom.dot.tag",
            "custom-tag-1",
            "custom-tag-1.2",
            "custom-tag-1.2.3",
    })
    void shouldRecognizeStartTag(final String openTagName) {
        StaticList<HtmlTag> tags = parse("<%s>".formatted(openTagName));

        HtmlTag expectedTrimmedTag = HtmlTag.startTag(openTagName.trim());

        assertEquals(expectedTrimmedTag, tags.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "html",
            "html   ",
            "html1",
            "custom-tag",
            "custom.dot.tag",
            "custom-tag-1",
            "custom-tag-1.2",
            "custom-tag-1.2.3",
    })
    void shouldRecognizeEndTag(final String endTagName) {
        StaticList<HtmlTag> tags = parse("</%s>".formatted(endTagName));

        HtmlTag expectedTrimmedTag = HtmlTag.endTag(endTagName.trim());

        assertEquals(expectedTrimmedTag, tags.get(0));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "<div href=\"https://www.google.com\">",
            "<div  src=\"https://www.google.com/logo.png\"   >",
            "<div  id=\"first\"  class=\"container\"   >",
            "<div disabled>",
            "<div hidden>",
    })
    void shouldRecognizeJustTagNameWhenTagHasAttribute(final String tagWithAttribute) {
        assertTags(tagWithAttribute, HtmlTag.startTag("div"));
    }

    @Test
    void shouldTokenizeHtmlStructure() {
        String fullHtmlStructure = """
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
                """;

        assertTags(fullHtmlStructure,

                HtmlTag.startTag("html"),
                HtmlTag.startTag("body"),
                HtmlTag.startTag("h1"),
                HtmlTag.endTag("h1"),
                HtmlTag.startTag("p"),
                HtmlTag.endTag("p"),
                HtmlTag.endTag("body"),
                HtmlTag.endTag("html"));
    }

    @Test
    void shouldTokenizeUpperCaseTagsToLowerCase() {
        String upperCaseHtmlStructure = """
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
                """;

        assertTags(upperCaseHtmlStructure,

                HtmlTag.startTag("html"),
                HtmlTag.startTag("body"),
                HtmlTag.startTag("h1"),
                HtmlTag.endTag("h1"),
                HtmlTag.startTag("p"),
                HtmlTag.endTag("p"),
                HtmlTag.endTag("body"),
                HtmlTag.endTag("html"));
    }

    private StaticList<HtmlTag> parse(final String input) {
        return new HtmlLexer(input).tokenize();
    }

    private void assertTags(String htmlContent, HtmlTag... expectedTags) {
        StaticList<HtmlTag> tags = parse(htmlContent);

        for (int i = 0; i < expectedTags.length; i++)
            assertEquals(expectedTags[i], tags.get(i));
    }

    private void assertNoTagsWereTokenized(final String content) {
        assertTrue(parse(content).isEmpty());
    }
}
