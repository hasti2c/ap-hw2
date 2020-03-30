package main;

import com.google.gson.Gson;
import game.GameController;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.*;

import java.io.IOException;

public class Main extends Application {
    static GameController controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        controller = new GameController();
    }
}
