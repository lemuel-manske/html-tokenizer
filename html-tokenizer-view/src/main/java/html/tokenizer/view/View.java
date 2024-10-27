package html.tokenizer.view;

public class View {

    public static final String APP_NAME = "HTML Tokenizer";

    // header messages
    public static final String SELECT_HTML_FILE = "Carregar arquivo";
    public static final String RUN_PARSER = "Executar";

    // table headers
    public static final String TAG_COLUMN = "Tag";
    public static final String TAG_OCCURRENCES_COLUMN = "Ocorrências";

    // output messages
    public static final String UNEXPECTED_END_TAG_WHEN_ANOTHER_WAS_EXPECTED = "Tag final %s inesperada quando %s era esperada.";
    public static final String MISSING_END_TAG = "Tag final faltando %s.";
    public static final String FILE_HAS_NO_CONTENT = "Arquivo não tem conteúdo.";
    public static final String NO_FILE_SELECTED = "Nenhum arquivo selecionado.";
    public static final String PARSING_SUCCESS = "Arquivo está bem formatado.";
}
