package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Controller;
import graphic.GraphicController;
import main.MainController;

import java.util.*;

public class GameController implements Controller {
    private ArrayList<Integer> highScores = new ArrayList<>();
    private transient int score = 0, clearedLines = 0, time = 0;
    private transient Board board;
    private transient Piece activePiece = null;
    private transient MainController mainController;
    private transient GraphicController graphic;
    private transient Timeline timeline;

    public void config(MainController mainController) {
        this.mainController = mainController;
        board = new Board(mainController.getRows(), mainController.getColumns());
        graphic = new GraphicController(mainController, this);
    }

    public void newGame() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> nextMove()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public MainController getMainController() { return mainController; }

    public void setMainController(MainController mainController) { this.mainController = mainController; }

    public GraphicController getGraphic() { return graphic; }

    public Piece getActivePiece() { return this.activePiece; }

    public ArrayList<Integer> getHighScores() { return highScores; }

    public int getScore() { return score; }

    public int getClearedLines() { return clearedLines; }

    public int getTime() { return time; }

    private void nextMove() {
        time++;
        if (activePiece != null)
            if (drop())
                return;
            else
                score++;
        Piece randomPiece = getRandomPiece();
        clearGrid();
        if (!board.canInsert(randomPiece)) {
            gameOver();
            return;
        }
        addPiece(randomPiece);
    }

    public boolean addPiece(Piece newPiece) {
        clearGrid();
        if (!board.canInsert(newPiece))
            return false;
        activePiece = newPiece;
        board.addPiece(newPiece);
        graphic.addPiece(newPiece);
        return true;
    }

    public boolean updatePiece(Piece newPiece, Piece oldPiece) {
        graphic.updateTopBar(score, clearedLines, time);
        if (!board.canInsert(newPiece, oldPiece))
            return false;

        board.removePiece(oldPiece);
        board.addPiece(newPiece);
        graphic.updatePiece(newPiece, oldPiece);

        if(oldPiece == activePiece)
            activePiece = newPiece;
        return true;
    }

    private boolean updatePiece(Piece newPiece) {
        if (!updatePiece(newPiece, activePiece))
            return false;

        activePiece = newPiece;
        return true;
    }

    private boolean drop(int toRow, Piece p) { return updatePiece(p.move(1, 0, toRow), p); }

    public boolean drop() { return drop(mainController.getRows(), activePiece); }

    public boolean shift(int dc) {
        assert Math.abs(dc) == 1;
        return updatePiece(activePiece.move(0, dc));
    }

    public boolean rotate(boolean clockwise) {
        return updatePiece(activePiece.rotate(clockwise));
    }

    boolean clearRow(int r, Piece piece) {
        return updatePiece(piece.clearRow(r), piece);
    }

    void clearGrid() {
        for (int i = mainController.getRows() - 1; i >= 0; i--) {
            if (!board.fullRow(i))
                continue;

            clearedLines++;
            score += 10;
            Piece[] row = board.getRow(i);

            for (Piece p : row)
                if (p != null)
                    clearRow(i, p);
            ArrayList<Piece> uniquePieces = new ArrayList<>();
            for (int j = i - 1; j >= 0; j--)
                for (Piece p : board.getRow(j))
                    if (!uniquePieces.contains(p) && p != null)
                        uniquePieces.add(p);
            for (Piece p : uniquePieces)
                drop(i, p);

            i++;
        }
        activePiece = null;
    }

    public boolean undo() {
        Piece newPiece = getDefaultPiece(activePiece.getName());
        newPiece.setGraphic(activePiece.getGraphic());
        return updatePiece(newPiece);
    }

    private Piece getDefaultPiece(String name) {
        String json = mainController.readFile("database/pieces/" + name + ".json");
        Piece newPiece = mainController.getGson().fromJson(json, Piece.class);
        return newPiece.move(0, mainController.getColumns()/2);
    }

    Piece getRandomPiece() {
        char[] pieces = {'i', 'j', 'l', 'o', 's', 't', 'z'};
        int num = (int) Math.floor(Math.random() * 7) % 7;
        return getDefaultPiece("" + pieces[num]);
    }

    public void stop() { timeline.stop(); }

    private void gameOver() {
        timeline.stop();
        resetScore();
        mainController.gameOver(this);
    }

    private void resetScore() {
        highScores.add(score);
        Collections.sort(highScores);
        Collections.reverse(highScores);
        score = 0;
        mainController.writeFile(mainController.getGson().toJson(this), "database/scores.json");
    }

}
