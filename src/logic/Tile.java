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
     * @return Whether this object has been accessed or not.
     * @see Tile#flip()
     */
    public boolean hasBeenAccessed() {
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
     *  Flips a tile over. More specifically, this method sets <code>hasBeenAccessed</code> to true.
     * @return If the tile clicked on should end the game. This method will always return false and subclasses should overwrite this method to set it to true.
     */
    public boolean flip() {
        _accessed = true;
        return false;
    }

    void toggleFlag() {
        _flag = !_flag;
    }

    public boolean isFlagged() {
        return _flag;
    }
}
