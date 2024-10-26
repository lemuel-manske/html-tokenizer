package html.tokenizer.view;

import html.tokenizer.parser.HtmlParser;
import html.tokenizer.parser.HtmlReport;
import html.tokenizer.parser.MissingCloseTag;
import html.tokenizer.parser.UnexpectedCloseTag;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import sort.QuickSort;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class AppController {

    @FXML private Button openFileChooser;
    @FXML private TextField chosenFile;
    @FXML private Button runParser;
    @FXML private TextArea parsingOutput;
    @FXML private TableView<HtmlReport.TagOccurrence> tags;
    @FXML private TableColumn<HtmlReport.TagOccurrence, String> tagId;
    @FXML private TableColumn<HtmlReport.TagOccurrence, Integer> tagOccurrences;

    private final ObservableList<HtmlReport.TagOccurrence> tagsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        openFileChooser.setText(Messages.SELECT_HTML_FILE);
        chosenFile.setText(Messages.NO_FILE_SELECTED);
        runParser.setText(Messages.RUN_PARSER);
        tagId.setText(Messages.TAG_COLUMN);
        tagOccurrences.setText(Messages.TAG_OCCURRENCES_COLUMN);

        DoubleBinding size = tags.widthProperty().divide(2);
        tagId.prefWidthProperty().bind(size);
        tagOccurrences.prefWidthProperty().bind(size);

        tagId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTag()));
        tagOccurrences.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getOccurrences()).asObject());
    }

    @FXML
    public void selectHtmlFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(Messages.SELECT_HTML_FILE);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HTML Files", "*.html"));

        File file = fileChooser.showOpenDialog(openFileChooser.getScene().getWindow());

        if (file == null) {
            chosenFile.setText(Messages.NO_FILE_SELECTED);
            return;
        }

        chosenFile.setText(file.getAbsolutePath());
    }

    @FXML
    public void parseHtmlContent() throws IOException {
        File htmlFile = new File(chosenFile.getText());

        if (!htmlFile.exists()) {
            parsingOutput.setText(Messages.NO_FILE_SELECTED);
            return;
        }

        String fileContent = Files.readString(htmlFile.toPath(), StandardCharsets.UTF_8);

        if (fileContent.isEmpty()) {
            parsingOutput.setText(Messages.FILE_HAS_NO_CONTENT);
            return;
        }

        try {
            HtmlReport htmlReport = new HtmlParser(fileContent).parse();

            tagsList.clear();

            tagsList.addAll(htmlReport.getTags(new QuickSort<>()));

            tags.setItems(tagsList);

            parsingOutput.setText(Messages.PARSING_SUCCESS);
        }

        catch (UnexpectedCloseTag e) {
            parsingOutput.setText(Messages.UNEXPECTED_CLOSE_TAG.formatted(e.expectedTag(), e.unexpectedTag()));
            tagsList.clear();
        }

        catch (MissingCloseTag e) {
            parsingOutput.setText(Messages.MISSING_CLOSE_TAG.formatted(e.missingTag()));
            tagsList.clear();
        }
    }
}
