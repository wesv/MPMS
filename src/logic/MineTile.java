package logic;

/**
 * A subclass of the <code>Tile</code> class that denotes if a mine is at the specified Tile.
 */
public class MineTile extends Tile {

    private boolean normalFlip = false;

    @Override
    public boolean flip() {
        super.flip();
        normalFlip = true;
        return true;
    }

    /**
     * Flips the tile without ending the game.
     * A flip that happens at the end of the game should show the location of each mine without being known as the mine hit by the user.
     * A mine hit normally would use the <code>Tile.flip()</code> method, and this method should only be used to flip mines after a game ends.
     * @see Tile#flip()
     */
    public void endOfGameFlip() {
        _accessed = true;
    }

    /**
     * Returns whether this Tile was flipped using the <code>flip()</code> method
     * @return true if this Tile was flipped using <code>MineTile.flip()</code> method, false otherwise.
     * @see MineTile#endOfGameFlip()
     * @see Tile#flip()
     */
    public boolean wasNormallyFlipped()
    {
        return normalFlip;
    }

    @Override
    public String toString() {
        if(isFlagged())
            return Tile.flagChar + "";
        return "M";
    }
}
