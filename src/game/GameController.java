package game;

import com.google.gson.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Controller;
import graphic.GraphicController;

import java.io.*;
import java.util.*;

public class GameController implements Controller {
    private Board board;
    private Piece activePiece = null;
    private Gson gson = new Gson();
    private GraphicController graphic;
    private final Timeline timeline;

    public GameController() {
        this.board = new Board();
        this.graphic = new GraphicController(this);
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> nextMove()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public Piece getActivePiece() { return this.activePiece; }

    public boolean nextMove() {
        if (activePiece != null && drop())
            return true;
        Piece randomPiece = getRandomPiece();
        clearGrid();
        if (!board.canInsert(randomPiece)) {
            gameOver();
            return false;
        }
        addPiece(randomPiece);
        return true;
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
        if (!board.canInsert(newPiece, oldPiece))
            return false;

        board.removePiece(oldPiece);
        board.addPiece(newPiece);
        graphic.updatePiece(newPiece, oldPiece);
        return true;
    }

    private boolean updatePiece(Piece newPiece) {
        if (!updatePiece(newPiece, activePiece))
            return false;

        activePiece = newPiece;
        return true;
    }

    public boolean drop(int toRow) { return updatePiece(activePiece.move(1, 0, toRow)); }

    public boolean drop() { return drop(Board.ROWS); }

    public boolean shift(int dc) {
        assert Math.abs(dc) == 1;
        return updatePiece(activePiece.move(0, dc));
    }

    public boolean rotate(boolean clockwise) {
        return updatePiece(activePiece.rotate(clockwise));
    }

    boolean clearRow(int r) {
        return updatePiece(activePiece.clearRow(r));
    }

    void clearGrid() {
        for (int i = Board.ROWS - 1; i >= 0; i--) {
            if (!board.fullRow(i))
                continue;
            Piece[] row = board.getRow(i);
            ArrayList<Piece> uniquePieces = new ArrayList<>();
            for (Piece p : row)
                if (!uniquePieces.contains(p))
                    uniquePieces.add(p);
            for (Piece p : uniquePieces) {
                activePiece = p;
                clearRow(i);
                drop(i);
            }
            i++;
        }
        activePiece = null;
    }

    public boolean undo() {
        Piece newPiece = getDefaultPiece(activePiece.getName());
        newPiece.setGraphic(activePiece.getGraphic());
        return updatePiece(newPiece);
    }

    String readFile(String path) {
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

    public Piece getDefaultPiece(String name) {
        String json = readFile("database/" + name + ".json");
        return gson.fromJson(json, Piece.class);
    }

    Piece getRandomPiece() {
        char[] pieces = {'i', 'j', 'l', 'o', 's', 't', 'z'};
        int num = (int) Math.floor(Math.random() * 7) % 7;
        return getDefaultPiece("" + pieces[num]);
    }

    private void gameOver() {
        timeline.stop();
    }

}
