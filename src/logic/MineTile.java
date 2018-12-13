package logic;

public class MineTile extends Tile {

    private boolean normalFlip = false;

    @Override
    public boolean flip() {
        super.flip();
        normalFlip = true;
        return true;
    }

    public void endOfGameFlip() {
        _accessed = true;
    }

    public boolean wasNormallyFlipped()
    {
        return normalFlip;
    }

    public String toString() {
        if(isFlagged())
            return Tile.flagChar + "";
        return "M";
    }
}
