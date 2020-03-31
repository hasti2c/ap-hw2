package game;

public class Tile {
    private Coordinate position;

    Tile(Coordinate position, Piece piece) {
        this.position = position;
    }

    public Coordinate getPosition() { return position; }
}
