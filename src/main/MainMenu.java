package main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

class MainMenu {
    private MainController controller;
    private Scene scene;
    private VBox vbox;

    MainMenu(MainController controller) {
        this.controller = controller;
        configScene();
    }

    Scene getScene() { return this.scene; }

    private void configVbox() {
        vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        Button newGame = prettyButton("New Game");
        Button highScores = prettyButton("High Scores");
        Button settings = prettyButton("Settings");
        newGame.setOnAction(e -> controller.newGame());
        highScores.setOnAction(e -> controller.highScores());
        settings.setOnAction(e -> controller.settings());

        vbox.getChildren().addAll(newGame, highScores, settings);
        vbox.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void configScene() {
        configVbox();
        scene = new Scene(vbox, 300, 300);
    }

    private Button prettyButton(String text) {
        Button b = new Button(text);
        b.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setTextFill(Color.WHITE);
        return b;
    }
}
