package main;

import com.sun.javafx.tk.TKScenePaintListener;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

class HighScores {
    private MainController controller;
    private ArrayList<Integer> highScores;
    private VBox list;
    private Scene scene;

    HighScores(MainController controller, ArrayList<Integer> highScores) {
        this.controller = controller;
        this.highScores = highScores;
        configScene();
    }

    Scene getScene() { return scene; }

    private void configList() {
        list = new VBox();
        list.setAlignment(Pos.CENTER);
        list.setSpacing(10);
        list.setBackground(new Background(new BackgroundFill(Color.DARKSEAGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        ArrayList<Text> texts = new ArrayList<>();
        int n = Math.min(10, highScores.size());
        for (int i = 0; i < n; i++)
            texts.add(prettyText(getPlacement(i + 1) + ":    " + highScores.get(i) + "points"));
        list.getChildren().addAll(texts);

        Button mainMenu = prettyButton("Main Menu");
        Button newGame = prettyButton("New Game");
        mainMenu.setOnAction(e -> controller.mainMenu());
        newGame.setOnAction(e -> controller.newGame());

        HBox buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.setMinHeight(50);
        buttons.getChildren().addAll(mainMenu, newGame);
        list.getChildren().add(buttons);
    }

    private void configScene() {
        configList();
        scene = new Scene(list, 300, 400);
    }

    private String getPlacement(int n) {
        String s = ""+n;
        if (10 < (n % 100) && (n % 100) < 20)
            return s + "th";
        switch (n % 10) {
            case 1:
                s += "st";
                break;
            case 2:
                s += "nd";
                break;
            case 3:
                s += "rd";
                break;
            default:
                s += "th";
        }
        return s;
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
