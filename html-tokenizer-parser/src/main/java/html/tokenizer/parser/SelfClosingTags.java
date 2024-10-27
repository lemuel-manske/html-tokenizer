package html.tokenizer.parser;


public class SelfClosingTags {

    private static final String[] EXPECTED_SELF_CLOSING_TAGS = {
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
        "!doctype",
    };

    public static boolean contains(String tag) {
        for (String s : EXPECTED_SELF_CLOSING_TAGS)
            if (s.equals(tag))
                return true;

        return false;
    }

    private SelfClosingTags() {
        // static class
    }
}
