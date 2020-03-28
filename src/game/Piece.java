package game;

import game.Tile;

import java.util.*;

public class Piece {
    private ArrayList<Tile> tiles = new ArrayList<>();
    Coordinate corner;
    int boxSize;

    Piece(List<Coordinate> coordinates, Coordinate corner, int boxSize) {
        for (Coordinate coordinate : coordinates)
            tiles.add(new Tile(coordinate, this));
        this.corner = corner.clone();
        this.boxSize = boxSize;
    }

    public ArrayList<Tile> getTiles() { return this.tiles; }

    public Piece fall() {
        ArrayList<Coordinate> newPositions = new ArrayList<>();
        for (Tile t : tiles)
            newPositions.add(t.getPosition());
        return new Piece(newPositions, corner, boxSize);
    }
}
