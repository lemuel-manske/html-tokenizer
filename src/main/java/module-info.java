module html.tokenizer {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;

    opens html.tokenizer.view to javafx.fxml;

    exports html.tokenizer.view;
}