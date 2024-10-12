package html.tokenizer.view;

import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AppController implements Initializable {

    @FXML private Button openFileChooser;
    @FXML private TextField chosenFile;

    @FXML private Button runParser;

    @FXML private TextArea parsingOutput;

    @FXML private TableView<Tag> tags;
    @FXML private TableColumn<Tag, String> tagId;
    @FXML private TableColumn<Tag, Integer> tagOccurrences;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        makeColumnsHalfSizeOfTable();

        setTextForComponentsFromBundle(resourceBundle);
    }

    private void makeColumnsHalfSizeOfTable() {
        DoubleBinding size = tags.widthProperty().divide(2);

        tagId.prefWidthProperty().bind(size);
        tagOccurrences.prefWidthProperty().bind(size);
    }

    private void setTextForComponentsFromBundle(ResourceBundle resourceBundle) {
        openFileChooser.setText(resourceBundle.getString("--find-file"));
        chosenFile.setText(resourceBundle.getString("--chosen-file"));
        runParser.setText(resourceBundle.getString("--run-parser"));
        parsingOutput.setText(resourceBundle.getString("--output-message-initial-content"));
        tagId.setText(resourceBundle.getString("--tag-col"));
        tagOccurrences.setText(resourceBundle.getString("--tag-occurrences-col"));
    }

}