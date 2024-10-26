module html.tokenizer.view {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires html.tokenizer.parser;

    requires sort;

    opens html.tokenizer.view to javafx.fxml;

    exports html.tokenizer.view;
}