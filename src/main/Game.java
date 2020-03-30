package main;

import game.*;

import java.io.IOException;

public class Game {
    static Board mainBoard = new Board();
    static GameController mainController;

    public static void main(String[] args) {
        mainController = new GameController();
    }
}
