package game;

public class Tile {
    private Coordinate position;
    private Piece piece;

    public Tile(Coordinate position, Piece piece) {
        this.position = position.clone();
        this.piece = piece;
    }

    public Coordinate getPosition() { return position; }

    public Piece getPiece() { return this.piece; }

}
