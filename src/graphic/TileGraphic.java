package graphic;

import game.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class TileGraphic {
    private Rectangle square;
    private Coordinate position;
    private Color color;
    private GraphicController controller;

    TileGraphic(Tile tile, Color color, GraphicController controller) {
        this.position = tile.getPosition();
        this.color = color;
        this.controller = controller;
        tileConfig();
    }

    Rectangle getSquare() { return this.square; }

    Coordinate getPosition() { return this.position; }

    void setPosition(Coordinate position) { this.position = position; }

    private void tileConfig() {
        this.square = new Rectangle(controller.getBlockSize(), controller.getBlockSize(), color);
    }

}
