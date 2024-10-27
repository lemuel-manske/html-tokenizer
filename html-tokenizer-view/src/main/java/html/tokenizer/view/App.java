package html.tokenizer.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static final int VIEW_WIDTH = 640;
    private static final int VIEW_HEIGHT = 540;

    @Override
    public void start(final Stage stage) throws IOException {
        Parent app = FXMLLoader.load(getClass().getResource("app-view.fxml"));

        Scene scene = new Scene(app, VIEW_WIDTH, VIEW_HEIGHT);

        stage.setTitle(View.APP_NAME);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}