package logic;

import exceptions.GameEndException;

public class Field {
    /* The size of the board in nxn */
    private int _n;

    /* The Number of Mines on the board */
    private int _mines;
    private Array2D<Tile> _board;


    public Field(int boardSize, double mineDensity) {
        _n = boardSize;
        if(mineDensity >= 1 || mineDensity <= 0) throw new RuntimeException("Mine Density is an invalid value ( "+ mineDensity +")");
        _mines = (int)Math.floor(boardSize * boardSize * mineDensity) + 1;
        _board = new Array2D<>(boardSize);
        placeMines();
        placeNumbers();
    }

    private boolean isValidLocation(Location loc) {
        return loc.X() >= 0 && loc.X() < this._n
                && loc.Y() >= 0 && loc.Y() < this._n;
    }

    /*public void flag(int x, int y) {
        flag(Location.create(x, y));
    }*/

    public void flag(Location loc) {
        _board.at(loc).toggleFlag();
    }

    public void flip(Location l) throws GameEndException {

        if(!isValidLocation(l)) return;

        Tile t = _board.at(l);

        if(t.isFlagged() || t.hasBeenAccessed()) return;

        t.flip();

        //Flip all surrounding tiles if no surrounding mines
        if(t instanceof NumberTile && ((NumberTile)t).getNumMines() == 0) {
            flip(l.up());
            flip(l.down());
            flip(l.left());
            flip(l.right());
            flip(l.diagonalUpLeft());
            flip(l.diagonalUpRight());
            flip(l.diagonalDownLeft());
            flip(l.diagonalDownRight());
        }
    }

    /*public void flip(int x, int y) throws GameEndException {
        flip(Location.create(x, y));
    }*/

    public void flipAllMines() {
        for(int x = 0; x < _n; x++) {
            for(int y = 0; y < _n; y++) {
                if(_board.at(x, y) instanceof MineTile) {
                    ((MineTile)_board.at(x,y)).endOfGameFlip();
                }
            }
        }

    }

    public Tile getTileAt(Location l) {
        return _board.at(l);
    }

    private void placeMines() {
        for (int x = 0; x < _mines; x++) {
            Location randLocation = Location.create((int) Math.floor(Math.random() * _n),
                    (int) Math.floor(Math.random() * _n));

            while (_board.at(randLocation) != null) {
                randLocation = Location.create((int) Math.floor(Math.random() * _n),
                        (int) Math.floor(Math.random() * _n));
            }

            _board.putAt(new MineTile(), randLocation);
        }
    }

    private void placeNumbers() {
        for(int x = 0; x < _n; x++) {
            for(int y = 0; y < _n; y++) {
                int numMines = 0;

                Location loc = Location.create(x, y);

                if(_board.at(loc) instanceof MineTile) continue;

                if(isValidLocation(loc.up())) {
                    if(_board.at(loc.up()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.down())) {
                    if(_board.at(loc.down()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.left())) {
                    if(_board.at(loc.left()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.right())) {
                    if(_board.at(loc.right()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.diagonalUpLeft())) {
                    if(_board.at(loc.diagonalUpLeft()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.diagonalUpRight())) {
                    if(_board.at(loc.diagonalUpRight()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.diagonalDownLeft())) {
                    if(_board.at(loc.diagonalDownLeft()) instanceof MineTile)
                        numMines++;
                }
                if(isValidLocation(loc.diagonalDownRight())) {
                    if(_board.at(loc.diagonalDownRight()) instanceof MineTile)
                        numMines++;
                }


                _board.putAt(new NumberTile(numMines), loc);
            }
        }
    }

    public String toString() {
        StringBuilder str = new StringBuilder();


        for(int x = 0; x < _n; x++) {
            str.append("|");
            for(int y = 0; y < _n; y++) {
                str.append(_board.at(x, y).toString());
                if(y != _n)
                    str.append("|");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public boolean checkIfWon() {
        for(int x = 0; x < _n; x++) {
            for(int y=0; y < _n; y++) {
                Tile tile = _board.at(x, y);

                if(tile instanceof MineTile && !tile.isFlagged())
                    return false;
                if(tile instanceof NumberTile && !tile.hasBeenAccessed())
                    return false;
            }
        }
        return true;
    }

    public int size() {
        return _n;
    }

    public Tile tileAt(int x, int y) {
        return _board.at(x, y);
    }
}
