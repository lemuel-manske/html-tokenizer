module html.tokenizer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens html.tokenizer.view to javafx.fxml;

    exports html.tokenizer.view;
}