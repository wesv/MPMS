package logic;

import exceptions.GameEndException;

public class Mine extends Tile {
    public Mine(Location l) {
        super.setLocation(l);
    }

    @Override
    public void flip() throws GameEndException {
        super.flip();
        throw new GameEndException("You have hit a mine.");
    }
}
