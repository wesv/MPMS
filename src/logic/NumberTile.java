package logic;

public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;

        this.flippedChar = (char) ('0' + numMines);
    }

    public int getNumMines() {
        return _surroundingMines;
    }
}
