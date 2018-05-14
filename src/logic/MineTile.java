package logic;

import exceptions.GameEndException;

public class MineTile extends Tile {

    private boolean normalFlip = false;

    @Override
    public void flip() throws GameEndException {
        super.flip();
        normalFlip = true;
        throw new GameEndException("You have hit a mine.");
    }

    public void endOfGameFlip() {
        _flag = false;
        _accessed = true;
    }

    public boolean wasNormallyFlipped()
    {
        return normalFlip;
    }
}
