package logic;

/**
 * A subclass of the <code>Tile</code> class where a number denotes the number of nearby <code>MineTile</code>s.
 */
public class NumberTile extends Tile {
    private int _surroundingMines;

    public NumberTile(int numMines) {
        _surroundingMines = numMines;
    }
    public int nearbyMines() {
        return _surroundingMines;
    }

    @Override
    public String toString() {
        if(isFlagged())
            return "" + Tile.flagChar;
        if(hasBeenFlipped())
            return "\u001B[31m" + nearbyMines() + "\u001B[0m";
        return "" + nearbyMines();
    }
}
