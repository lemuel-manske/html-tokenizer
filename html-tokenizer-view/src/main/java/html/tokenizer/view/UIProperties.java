package html.tokenizer.view;

public class UIProperties {

    public static final String APP_NAME = "HTML Tokenizer";

    // header
    public static final String SELECT_HTML_FILE = "Find HTML";
    public static final String NO_FILE_SELECTED = "No file selected.";
    public static final String RUN_PARSER = "Run";

    // table
    public static final String TAG_COLUMN = "Tag";
    public static final String TAG_OCCURRENCES_COLUMN = "Occurrences";

    // messages
    public static final String UNEXPECTED_CLOSE_TAG = "Unexpected close tag %s when %s was expected.";
    public static final String MISSING_CLOSE_TAG = "Missing close tag for %s.";
    public static final String EMPTY_OR_NO_FILE_SELECTED = "File has no content or no file was selected.";
    public static final String ERROR_READING_FILE = "Error reading file %s.";
    public static final String PARSING_SUCCESS = "File is nicely formatted.";
}
