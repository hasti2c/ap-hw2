package game;

import graphic.PieceGraphic;

import java.util.*;

public class Piece {
    private String name;
    private ArrayList<Tile> tiles = new ArrayList<>();
    private Coordinate corner;
    private int boxSize, orientation = 0;
    private Coordinate[][][] wallJumps;
    private String color;
    private int[] subImg = new int[4];
    private transient PieceGraphic graphic;

    private Piece(String name, List<Coordinate> coordinates, Coordinate corner, int boxSize, PieceGraphic graphic, String color) {
        this.name = name;
        for (Coordinate coordinate : coordinates)
            tiles.add(new Tile(coordinate, this));
        this.corner = corner;
        this.boxSize = boxSize;
        this.color = color;
        this.graphic = graphic;
    }

    String getName() { return name; }

    public ArrayList<Tile> getTiles() { return tiles; }

    public PieceGraphic getGraphic() { return this.graphic; }

    public String getColor() { return color; }

    public void setGraphic(PieceGraphic graphic) { this.graphic = graphic; }

    Piece move(int dr, int dc, int toRow) {
        ArrayList<Coordinate> newPositions = new ArrayList<>();
        for (Tile t : tiles)
            if (t.getPosition().getR() < toRow)
                newPositions.add(t.getPosition().move(dr, dc));
            else
                newPositions.add(t.getPosition());
        Coordinate newCorner = corner;
        if (newCorner.getR() < toRow)
            newCorner = newCorner.move(dr, dc);
        return movedClone(newPositions, newCorner);
    }

    Piece move(int dr, int dc) {
        int maxRow = 0;
        for (Tile t : tiles)
            maxRow = Math.max(maxRow, t.getPosition().getR());
        return move(dr, dc, maxRow + 1);
    }

    Piece rotate(boolean clockwise) {
        ArrayList<Coordinate> newPositions = new ArrayList<>();
        for (Tile t : tiles) {
            Coordinate pos = t.getPosition();
            int dr = pos.getR() - corner.getR(), dc = pos.getC() - corner.getC();
            int newdr = clockwise ? dc : boxSize - 1 - dc;
            int newdc = clockwise ? boxSize - 1 - dr : dr;
            newPositions.add(new Coordinate(corner.getR() + newdr, corner.getC() + newdc));
        }
        return movedClone(newPositions, corner);
    }

    Piece clearRow(int row) {
        ArrayList<Coordinate> newPositions = new ArrayList<>();
        for (Tile t : tiles) {
            Coordinate pos = t.getPosition();
            if (pos.getR() != row)
                newPositions.add(pos);
        }
        return movedClone(newPositions, corner);
    }

    private Piece movedClone(ArrayList<Coordinate> newPositions, Coordinate newCorner) {
        return new Piece(name, newPositions, newCorner, boxSize, graphic, color);
    }
}
