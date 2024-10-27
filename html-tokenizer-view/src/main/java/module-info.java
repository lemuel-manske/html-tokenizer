module html.tokenizer.view {
    requires html.tokenizer.parser;

    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens html.tokenizer.view to javafx.fxml;

    exports html.tokenizer.view;
}