package logic;

/**
 * The outline for any Tile. The board is filled with tiles.
 */
public abstract class Tile {

    public static char flagChar = '⚑';

    /* Whether this tile has been selected or not */
    protected boolean _accessed;

    /* Whether this tile is flagged or not */
    protected boolean _flag;

    /**
     * This method returns <code>true</code> if the flip method for this class has
     * been called, <code>false</code> otherwise.
     *
     * @return Whether this object has been flipped or not.
     * @see Tile#flip()
     */
    public boolean hasBeenFlipped() {
        return _accessed;
    }

    /**
     * If this object has been flipped, return thisChar which should
     * be overwritten in any class that extends this one. Otherwise,
     * it returns an underscore.
     *
     * @return thisChar if accessed, an underscore otherwise.
     */
    public String toString() {
        if(this.isFlagged())
            return "" + flagChar;
        return "_";
    }

    /**
     *  Flips a tile over. Then a boolean is returned denoting whether this tile should end the game or not.
     *  This method will always return false and subclasses should overwrite this method to set it to true.
     * @return true if the tile clicked on should end the game, false otherwise.
     */
    public boolean flip() {
        _accessed = true;
        return false;
    }

    /**
     * Toggles whether this tile is flagged or not.
     */
    void toggleFlag() {
        _flag = !_flag;
    }

    /**
     * Returns if the tile is flagged.
     * @return true if flagged, false otherwise
     */
    public boolean isFlagged() {
        return _flag;
    }
}
