package logic;

import exceptions.GameEndException;

public class MineTile extends Tile {
    private static boolean firstMineHit = false;

    public MineTile() {
        this.flippedChar = 'M';
    }

    @Override
    public void flip() throws GameEndException {
        super.flip();
        if(!firstMineHit)
            this.flippedChar = 'm';
        firstMineHit = true;
        throw new GameEndException("You have hit a mine.");
    }
}
