package html.tokenizer.view;

public class View {

    public static final String APP_NAME = "HTML Tokenizer";

    // header messages
    public static final String SELECT_HTML_FILE = "Find file";
    public static final String RUN_PARSER = "Run";

    // table headers
    public static final String TAG_COLUMN = "Tag";
    public static final String TAG_OCCURRENCES_COLUMN = "Occurrences";

    // output messages
    public static final String UNEXPECTED_CLOSE_TAG_WHEN_ANOTHER_WAS_EXPECTED = "Unexpected close tag %s when %s was expected.";
    public static final String MISSING_CLOSE_TAG = "Missing close tag for %s.";
    public static final String FILE_HAS_NO_CONTENT = "File has no content.";
    public static final String NO_FILE_SELECTED = "No file selected.";
    public static final String PARSING_SUCCESS = "File is nicely formatted.";
}
