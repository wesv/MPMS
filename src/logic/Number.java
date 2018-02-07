package logic;

public class Number extends Tile {
    private int _surroundingMines;

    public Number(int numMines) {
        _surroundingMines = numMines;
    }

    public int getNumMines() {
        return _surroundingMines;
    }
}
