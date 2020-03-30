package main;

import game.Piece;

public interface Controller {
    boolean addPiece(Piece newPiece);
    boolean updatePiece(Piece newPiece, Piece oldPiece);
}
