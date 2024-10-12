package html.tokenizer.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class App extends Application {

    public static final int VIEW_WIDTH = 640;
    public static final int VIEW_HEIGHT = 540;

    public static final URL VIEW_URL = App.class.getResource("app-view.fxml");
    public static final ResourceBundle VIEW_CONTENT_PROPS = ResourceBundle.getBundle("messages");

    @Override
    public void start(final Stage stage) throws IOException {
        FXMLLoader app = new FXMLLoader(VIEW_URL, VIEW_CONTENT_PROPS);

        Scene scene = new Scene(app.load(), VIEW_WIDTH, VIEW_HEIGHT);

        stage.setTitle(VIEW_CONTENT_PROPS.getString("--app-window-name"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}