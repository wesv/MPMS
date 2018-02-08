package logic;

public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;

        this.thisChar = "" +numMines;
    }

    public int getNumMines() {
        return _surroundingMines;
    }
}
