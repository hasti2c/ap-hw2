package game;

public class Coordinate {
    private int r, c;

    public Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() {
        return r;
    }

    public int getC() {
        return c;
    }

    Coordinate move(int dr, int dc) {
        return new Coordinate(r + dr, c + dc);
    }

    public String toString() {
        return "(" + r + ", " + c + ")";
    }
}
