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
        initBoard();
    }

    public boolean canFlip(Location l) {
        return _board.at(l).hasBeenAccessed();
    }

    public void flip(Location l) throws GameEndException{
        Tile t = _board.at(l);

        /*if(t instanceof BlankTile) {
            if(!(_board.at(l.up()) instanceof MineTile)) {
                this.flip(l.up());
            }
            if(!(_board.at(l.down()) instanceof MineTile)) {
                this.flip(l.down());
            }
            if(!(_board.at(l.left()) instanceof MineTile)) {
                this.flip(l.left());
            }
            if(!(_board.at(l.right()) instanceof MineTile)) {
                this.flip(l.right());
            }
        }*/

        t.flip();
    }

    public void flipAllMinea() {
        for(int x = 0; x < _n; x++) {
            for(int y = 0; y < _n; y++) {
                if(_board.at(Location.create(x, y)) instanceof MineTile) {
                    try {
                        _board.at(Location.create(x, y)).flip();
                    } catch (GameEndException e) {
                        // Do nothing.
                    }
                }
            }
        }

    }

    private void initBoard() {
        for (int x = 0; x < _mines; x++) {
            Location randLocation = Location.create((int) Math.floor(Math.random() * _n),
                    (int) Math.floor(Math.random() * _n));

            while (_board.at(randLocation) != null) {
                randLocation = Location.create((int) Math.floor(Math.random() * _n),
                        (int) Math.floor(Math.random() * _n));
            }

            _board.putAt(new MineTile(randLocation), randLocation);
        }

        for (int x = 0; x < _n; x++) {
            for (int y = 0; y < _n; y++) {

                if(_board.at(Location.create(x, y)) == null) {
                    _board.putAt(new BlankTile(), Location.create(x, y));
                }
            }
        }
    }

    public String print() {
        StringBuilder str = new StringBuilder();

        for(int x = 0; x < _n; x++) {
            str.append("|");
            for(int y = 0; y < _n; y++) {
                str.append(_board.at(Location.create(x, y)).getChar());
                if(y != _n)
                    str.append("|");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public int size() {
        return _n;
    }
}
