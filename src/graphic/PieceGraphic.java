package graphic;

import game.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class PieceGraphic {
    private Piece piece;
    private ArrayList<TileGraphic> tiles = new ArrayList<>();
    private Color color;
    private Image blockImg;
    private GraphicController controller;

    PieceGraphic(Piece piece, GraphicController controller) {
        this.piece = piece;
        this.color = Color.valueOf(piece.getColor());
        this.controller = controller;
        //blockImg = controller.getTextures().get;
        pieceConfig();
    }

    ArrayList<TileGraphic> getTiles() { return this.tiles; }

    private void pieceConfig() {
        for (Tile t : piece.getTiles())
            tiles.add(new TileGraphic(t, color, controller));
    }

    void pieceUpdate(Piece newPiece) {
        ArrayList<Tile> newTiles = newPiece.getTiles();
        while(tiles.size() > newTiles.size()) {
            controller.removeTile(tiles.get(tiles.size() - 1));
            tiles.remove(tiles.size() - 1);
        }
        for (int i = 0; i < tiles.size(); i++)
            tiles.get(i).setPosition(newTiles.get(i).getPosition());
    }
}
