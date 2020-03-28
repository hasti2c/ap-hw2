package game;

import java.util.ArrayList;

public class Board {
    private final int ROWS = 50, COLUMNS = 50;
    private Piece[][] grid = new Piece[ROWS][COLUMNS];
    private ArrayList<Piece> pieces = new ArrayList<>();

    private Piece getPiece(Coordinate pos) { return grid[pos.getR()][pos.getC()]; }

    private void setPiece(Coordinate pos, Piece p) { grid[pos.getR()][pos.getC()] = p; }

    boolean canInsert(Piece piece) { return canInsert(piece, null); }

    boolean canInsert(Piece piece, Piece ignored) {
        for (Tile t : piece.getTiles()) {
            Coordinate pos = t.getPosition();
            Piece current = getPiece(t.getPosition());
            if (getPiece(pos) != null && getPiece(pos) != ignored)
                return false;
        }
        return true;
    }

    boolean addPiece(Piece p) {
        if (!canInsert(p))
            return false;

        pieces.add(p);
        for (Tile t : p.getTiles()) {
            Coordinate pos = t.getPosition();
            setPiece(pos, p);
        }
        return true;
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
