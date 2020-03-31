package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import game.GameController;
import graphic.GraphicController;
import javafx.scene.*;
import javafx.stage.*;

import java.io.*;

public class MainController {
    private int rows = 20, columns = 10;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Stage stage;

    MainController(Stage stage) {
        this.stage = stage;
        mainMenu();
    }

    private void configStage(Scene scene) {
        stage.setScene(scene);
        stage.show();
    }

    public void mainMenu() {
        MainMenu menu = new MainMenu(this);
        configStage(menu.getScene());
    }

    private GameController getNewGame() {
        String json = readFile("database/scores.json");
        GameController newGame = gson.fromJson(json, GameController.class);
        newGame.config(this);
        GraphicController graphic = newGame.getGraphic();
        graphic.setMainController(this);
        configStage(graphic.getScene());
        return newGame;
    }

    void newGame() {
        GameController game = getNewGame();
        game.newGame();
    }

    void highScores() {
        HighScores highScores = new HighScores(this, getNewGame().getHighScores());
        configStage(highScores.getScene());
    }

    public void gameOver(GameController game) {
        GameOver gameOver = new GameOver(this, game);
        configStage(gameOver.getScene());
    }

    public void settings() {
        Settings settings = new Settings(this);
        configStage(settings.getScene());
    }

    public Gson getGson() { return gson; }

    public int getRows() { return rows; }

    public int getColumns() { return columns; }

    void setRows(int rows) { this.rows = rows; }

    void setColumns(int columns) { this.columns = columns; }

    public String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        try {
            Reader reader = new FileReader(path);
            int data = reader.read();
            while (data != -1) {
                sb.append((char) data);
                data = reader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public void writeFile(String s, String path) {
        try {
            Writer writer = new FileWriter(path);
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
