package game;

import java.util.ArrayList;

public class Board {
    final static int ROWS = 20, COLUMNS = 10;
    private Piece[][] grid = new Piece[ROWS][COLUMNS];
    private ArrayList<Piece> pieces = new ArrayList<>();

    private Piece getPiece(Coordinate pos) { return grid[pos.getR()][pos.getC()]; }

    private void setPiece(Coordinate pos, Piece p) { grid[pos.getR()][pos.getC()] = p; }

    private boolean validPosition(Coordinate pos) { return pos.getR() >= 0 && pos.getR() < ROWS && pos.getC() >= 0 && pos.getC() < COLUMNS; }

    Piece[] getRow(int r) { return grid[r]; }

    boolean canInsert(Piece piece) { return canInsert(piece, null); }

    boolean canInsert(Piece piece, Piece ignored) {
        for (Tile t : piece.getTiles()) {
            Coordinate pos = t.getPosition();
            if (!validPosition(pos))
                return false;
            Piece current = getPiece(pos);
            if (current != null && current != ignored)
                return false;
        }
        return true;
    }

    void addPiece(Piece p) {
        assert canInsert(p);
        pieces.add(p);
        for (Tile t : p.getTiles()) {
            Coordinate pos = t.getPosition();
            setPiece(pos, p);
        }
    }

    boolean removePiece(Piece p) {
        pieces.remove(p);
        for (Tile t : p.getTiles()) {
            Coordinate pos = t.getPosition();
            assert getPiece(pos) == p;
            setPiece(pos,null);
        }
        return true;
    }

    boolean fullRow(int r) {
        for (Piece p : grid[r])
            if (p == null)
                return false;
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0 ; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++)
                if (grid[i][j] == null)
                    sb.append(". ");
                else
                    sb.append("x ");
            sb.append("\n");
        }
        return sb.toString();
    }
}
