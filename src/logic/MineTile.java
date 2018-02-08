package logic;

import exceptions.GameEndException;

public class MineTile extends Tile {
    public MineTile(Location l) {
        super.setLocation(l);

        this.thisChar = "M";
    }

    @Override
    public void flip() throws GameEndException {
        super.flip();
        throw new GameEndException("You have hit a mine.");
    }
}
