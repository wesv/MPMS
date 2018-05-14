package logic;

public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;
    }

    public int getNumMines() {
        return _surroundingMines;
    }
}
