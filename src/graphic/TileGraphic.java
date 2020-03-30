package graphic;

import game.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TileGraphic {
    private Tile tile;
    private Rectangle square;
    private Coordinate position;
    private Color color;

    TileGraphic(Tile tile, Color color) {
        this.tile = tile;
        this.position = tile.getPosition();
        this.color = color;
        tileConfig();
    }

    Rectangle getSquare() { return this.square; }

    Coordinate getPosition() { return this.position; }

    void setPosition(Coordinate position) { this.position = position; }

    private void tileConfig() {
        this.square = new Rectangle(30, 30, color);
    }

}
