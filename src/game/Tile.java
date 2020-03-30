package game;

public class Tile {
    private Coordinate position;
    transient private Piece piece;

    Tile(Coordinate position, Piece piece) {
        this.position = position;
        this.piece = piece;
    }

    public Coordinate getPosition() { return position; }

    Piece getPiece() { return this.piece; }


}
