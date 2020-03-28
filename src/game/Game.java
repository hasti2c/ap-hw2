package game;

public class Game {
    static Board mainBoard = new Board();

    public static void main(String[] args) {

        // System.out.println(mainBoard);

        Coordinate c1 = new Coordinate(3, 5);
        Coordinate c2 = c1.clone();

    }
}
