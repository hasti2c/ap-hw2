package game;

public class Coordinate implements Cloneable {
    private int r, c;

    public Coordinate(int r, int c) {
        this.r = r;
        this.c = c;
    }

    public int getR() { return r; }
    public int getC() { return c; }

    public Coordinate clone() {
        try {
            return (Coordinate) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    public Coordinate drop() {
        return new Coordinate(r + 1, c);
    }
}
