package main;

import game.GameController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameOver {
    MainController mainController;
    GameController gameController;
    VBox vbox;
    Scene scene;

    GameOver(MainController mainController, GameController gameController) {
        this.mainController = mainController;
        this.gameController = gameController;
        configScene();
    }

    Scene getScene() { return scene; }

    private void configBorder() {
        Text score = prettyText("Score: " + gameController.getScore());
        Text clearedLines = prettyText("Cleared Lines: " + gameController.getClearedLines());
        Text time = prettyText("Time: " + gameController.getTime());

        Button newGame = prettyButton("New Game");
        Button highScores = prettyButton("High Scores");
        Button mainMenu = prettyButton("Main Menu");
        newGame.setOnAction(e -> mainController.newGame());
        highScores.setOnAction(e -> mainController.highScores());
        mainMenu.setOnAction(e -> mainController.mainMenu());
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(20);
        hbox.getChildren().addAll(newGame, highScores, mainMenu);

        vbox = new VBox();
        vbox.getChildren().addAll(score, clearedLines, time, hbox);
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void configScene() {
        configBorder();
        scene = new Scene(vbox, 350, 350);
    }
    
    private Text prettyText(String text) {
        Text t = new Text(text);
        return t;
    }
    
    private Button prettyButton(String text) {
        Button b = new Button(text);
        b.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        b.setTextFill(Color.WHITE);
        return b;
    }
}