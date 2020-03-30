package graphic;

import game.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.*;
import main.Controller;


public class GraphicController implements Controller {
    private GridPane gridPane;
    private BorderPane borderPane;
    private Scene scene;
    private Stage stage = new Stage();
    private GameController game;
    private Image textures;

    public GraphicController(GameController game) {
        this.game = game;
        configGrid();
        configScene();
    }

    private void configGrid() {
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(4));
        gridPane.setVgap(0.5);
        gridPane.setHgap(0.5);
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 20; j++) {
                Rectangle r = new Rectangle (30, 30);
                r.setFill(Color.BLACK);
                gridPane.add(r, i, j);
            }
    }

    private void configBorder() {
        borderPane = new BorderPane();
    }

    private void configScene() {
        scene = new Scene(gridPane, Color.DARKGRAY);
        scene.setOnKeyPressed(this::onKeyPressed);
        stage.setScene(scene);
        stage.show();
    }

    public void hidePiece(PieceGraphic pg) {
        for (TileGraphic tg : pg.getTiles())
            gridPane.getChildren().remove(tg.getSquare());
    }

    public void showTile(TileGraphic tg) {
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
        if (oldPiece.getGraphic() == null)
            System.out.println("WTF");
        PieceGraphic pg = oldPiece.getGraphic();
        hidePiece(pg);
        pg.pieceUpdate(newPiece);
        for (TileGraphic tg : pg.getTiles())
            showTile(tg);
        return true;
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
        }
    }
}
