package graphic;

import game.*;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import main.Controller;
import main.MainController;

import java.util.ArrayList;


public class GraphicController implements Controller {
    private GridPane gridPane;
    private BorderPane borderPane;
    private HBox topBar;
    private Scene scene;
    private MainController mainController;
    private GameController game;
    private Image textures;
    private final int BLOCKSIZE, HEIGHT, WIDTH;

    public GraphicController(MainController mainController, GameController game) {
        this.mainController = mainController;
        this.game = game;
        BLOCKSIZE = Math.min(30, Math.min(500/mainController.getRows(), 1250/mainController.getColumns()));
        HEIGHT = BLOCKSIZE * mainController.getRows();
        WIDTH = BLOCKSIZE * mainController.getColumns();
        configScene();
    }

    public void setMainController(MainController mainController) { this.mainController = mainController; }

    private void configGrid() {
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(0.5);
        gridPane.setHgap(0.5);
        for (int i = 0; i < mainController.getColumns(); i++)
            for (int j = 0; j < mainController.getRows(); j++) {
                Rectangle r = new Rectangle (BLOCKSIZE, BLOCKSIZE);
                r.setFill(Color.BLACK);
                gridPane.add(r, i, j);
            }
        gridPane.setAlignment(Pos.CENTER);
    }

    private void configTopBar() {
        topBar = new HBox();
        topBar.setAlignment(Pos.BOTTOM_CENTER);
        topBar.setSpacing(Math.max(WIDTH - 200, 50));
        topBar.setMinHeight(75);

        VBox gameData = new VBox();
        gameData.setAlignment(Pos.CENTER);
        Text score = prettyText("Score: 0");
        Text clearedLines = prettyText("ClearedLines: 0");
        Text time = prettyText("Time: 0s");
        gameData.getChildren().addAll(score, clearedLines, time);

        VBox controls = new VBox();
        controls.setAlignment(Pos.CENTER);
        Text esc = prettyText("ESC/M: Main Menu");
        Text s = prettyText("S: Settings");
        controls.getChildren().addAll(esc, s);

        topBar.getChildren().addAll(gameData, controls);
    }

    private void configBorder() {
        configGrid();
        configTopBar();
        borderPane = new BorderPane(gridPane);
        borderPane.setTop(topBar);
        borderPane.setBackground(new Background(new BackgroundFill(Color.MIDNIGHTBLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private void configScene() {
        configBorder();
        scene = new Scene(borderPane,Math.max(WIDTH + 100, 300), Math.max(300, HEIGHT + 125), Color.DARKGRAY);
        scene.setOnKeyPressed(this::onKeyPressed);
    }

    public Scene getScene() { return scene; }

    int getBlockSize() { return BLOCKSIZE; }

    private void removePiece(PieceGraphic pg) {
        for (TileGraphic tg : pg.getTiles())
            gridPane.getChildren().remove(tg.getSquare());
    }

    void removeTile(TileGraphic tg) { gridPane.getChildren().remove(tg.getSquare()); }

    private void showTile(TileGraphic tg) {
        Coordinate pos = tg.getPosition();
        gridPane.add(tg.getSquare(), pos.getC(), pos.getR());
    }

    public boolean addPiece(Piece newPiece) {
        PieceGraphic pg = new PieceGraphic(newPiece, this);
        newPiece.setGraphic(pg);
        for (TileGraphic tg : pg.getTiles())
            showTile(tg);
        return true;
    }

    public boolean updatePiece(Piece newPiece, Piece oldPiece) {
        if (oldPiece == null)
            return addPiece(newPiece);
        PieceGraphic pg = oldPiece.getGraphic();
        removePiece(pg);
        pg.pieceUpdate(newPiece);
        for (TileGraphic tg : pg.getTiles())
            showTile(tg);
        return true;
    }

    public void updateTopBar(int score, int clearedLines, int time) {
        ObservableList<Node> texts = ((VBox) topBar.getChildren().get(0)).getChildren();
        ((Text) texts.get(0)).setText("Score: " + score);
        ((Text) texts.get(1)).setText("Cleared Lines: " + clearedLines);
        ((Text) texts.get(2)).setText("Time: " + time + "s");
    }

    private void onKeyPressed(KeyEvent event) {
        if (game.getActivePiece() == null)
            return;
        switch (event.getCode()) {
            case RIGHT:
                game.shift(1);
                return;
            case LEFT:
                game.shift(-1);
                return;
            case UP:
            case X:
                game.rotate(true);
                return;
            case CONTROL:
            case Z:
                game.rotate(false);
                return;
            case DOWN:
                game.drop();
                return;
            case R:
                game.undo();
                return;
            case ESCAPE:
            case M:
                game.stop();
                mainController.mainMenu();
                return;
            case S:
                game.stop();
                mainController.settings();
        }
    }

    private Text prettyText(String text) {
        Text t = new Text(text);
        t.setFill(Color.WHITE);
        return t;
    }
}
