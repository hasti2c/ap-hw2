package main;

import game.Board;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

class Settings {
    private MainController controller;
    private Scene scene;
    private BorderPane borderPane;
    private HBox dimensions, buttons;
    private GridPane controls;

    Settings(MainController controller) {
        this.controller = controller;
        configScene();
    }

    Scene getScene() { return scene; }

    private void configDimensions() {
        Text title = prettyText("Grid Dimensions");
        Text limits = prettyText("(from 5 to 50)");
        VBox vbox = new VBox(title, limits);
        vbox.setAlignment(Pos.CENTER);

        Text rows = prettyText(controller.getRows() + " rows");
        Text x = prettyText("x");
        Text columns = prettyText(controller.getColumns() + " columns");
        Button edit = prettyButton("Edit");

        TextField editRows = new TextField();
        TextField editColumns = new TextField();
        editRows.setMaxWidth(90);
        editColumns.setMaxWidth(90);
        Button done = prettyButton("done");

        edit.setOnAction( e -> {
           dimensions.getChildren().set(1, editRows);
           dimensions.getChildren().set(3, editColumns);
           dimensions.getChildren().set(4, done);
           dimensions.setSpacing(10);
        });

        done.setOnAction( e -> {
            String r = editRows.getText(), c = editColumns.getText();
            controller.setRows(validNumber(editRows) ? Integer.parseInt(r) : 20);
            controller.setColumns(validNumber(editColumns) ? Integer.parseInt(c) : 10);
            rows.setText(controller.getRows() + " rows");
            columns.setText(controller.getColumns() + " columns");
            dimensions.getChildren().set(1, rows);
            dimensions.getChildren().set(3, columns);
            dimensions.getChildren().set(4, edit);
            dimensions.setSpacing(30);
        });

        dimensions = new HBox();
        dimensions.setAlignment(Pos.CENTER);
        dimensions.setSpacing(30);
        dimensions.setMinHeight(100);
        dimensions.getChildren().addAll(vbox, rows, x, columns, edit);
    }

    private void configControls() {
        controls = new GridPane();
        controls.setAlignment(Pos.CENTER);
        controls.setHgap(30);
        controls.setVgap(20);

        Text[][] texts = new Text[][]{
            {prettyText("Right"), prettyText("RIGHT ARROW")},
            {prettyText("Left"), prettyText("LEFT ARROW")},
            {prettyText("Down"), prettyText("DOWN ARROW")},
            {prettyText("Rotate Clockwise"), prettyText("UP ARROW or X")},
            {prettyText("Rotate Anticlockwise"), prettyText("CONTROL or Z")},
            {prettyText("Undo"), prettyText("R")},
            {prettyText("Main Menu"), prettyText("ESCAPE or M")},
            {prettyText("Settings"), prettyText("S")},
        };

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 2; j++)
                controls.add(texts[i][j], j, i);
        }
    }

    private void configButtons() {
        Button mainMenu = prettyButton("Main Menu");
        Button newGame = prettyButton("New Game");
        mainMenu.setOnAction(e -> controller.mainMenu());
        newGame.setOnAction(e -> controller.newGame());

        buttons = new HBox();
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);
        buttons.setMinHeight(100);
        buttons.getChildren().addAll(mainMenu, newGame);
    }

    private void configBorder() {
        configDimensions();
        configControls();
        configButtons();
        borderPane = new BorderPane(controls);
        borderPane.setTop(dimensions);
        borderPane.setBottom(buttons);
    }

    private void configScene() {
        configBorder();
        scene = new Scene(borderPane, 400, 500);
    }

    private boolean validNumber(TextField input) {
        try {
            int a = Integer.parseInt(input.getText());
            return a >= 5 && a <= 50;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    
    private Text prettyText(String text) {
        Text t = new Text(text);
        return t;
    }
    
    private Button prettyButton(String text) {
        Button b = new Button(text);
        return b;
    }
}
