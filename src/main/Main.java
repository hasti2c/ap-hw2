package main;

import javafx.application.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        new MainController(primaryStage);
    }
}
