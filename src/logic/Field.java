package logic;

import exceptions.GameEndException;

public class Field {
    /* The size of the board in nxn */
    private int _n;

    /* The Number of Mines on the board */
    private int _mines;
    private Array2D<Tile> _board;


    public Field(int boardSize, int numMines) {
        _n = boardSize;
        _mines = numMines;
        _board = new Array2D<>(boardSize);
    }

    public boolean canFlip(Location l) {
        return _board.at(l).hasBeenAccessed();
    }

    public void flip(Location l) throws GameEndException{
        Tile t = _board.at(l);

        if(t instanceof Blank) {
            if(!(_board.at(l.up()) instanceof Mine)) {
                this.flip(l.up());
            }
            if(!(_board.at(l.down()) instanceof Mine)) {
                this.flip(l.down());
            }
            if(!(_board.at(l.left()) instanceof Mine)) {
                this.flip(l.left());
            }
            if(!(_board.at(l.right()) instanceof Mine)) {
                this.flip(l.right());
            }
        }

        t.flip();
    }

    public String print() {
        return "";
    }
}
