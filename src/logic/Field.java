package logic;

import exceptions.GameEndException;

import java.util.Arrays;

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

    public boolean isValidLocation(Location loc) {
        return loc.X() >= 0 && loc.X() < this._n
                && loc.Y() >= 0 && loc.Y() < this._n;
    }

    public void flag(int x, int y) {
        flag(Location.create(x, y));
    }

    public void flag(Location loc) {
        if(_board.at(loc).isFlagged())
            _board.at(loc).removeFlag();
        else
            _board.at(loc).setFlag();
    }

    public void flip(Location l) throws GameEndException{

        if(!isValidLocation(l)) return;

        Tile t = _board.at(l);

        if(t.isFlagged() || t.hasBeenAccessed()) return;

        t.flip();

        if(t instanceof BlankTile) {
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

    public void flip(int x, int y) throws GameEndException {
        flip(Location.create(x, y));
    }

    public void flipAllMines() {
        for(int x = 0; x < _n; x++) {
            for(int y = 0; y < _n; y++) {
                if(_board.at(Location.create(x, y)) instanceof MineTile) {
                    try {
                        _board.at(Location.create(x, y)).removeFlag();
                        _board.at(Location.create(x, y)).flip();
                    } catch (GameEndException e) {
                        // Do nothing game is already over.
                    }
                }
            }
        }

    }

    private void initBoard() {
        placeMines();
        placeNumbers();
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


                if(numMines == 0)
                    _board.putAt(new BlankTile(), loc);
                else
                    _board.putAt(new NumberTile(numMines), loc);
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

    public boolean checkIfWon() {
        for(int x = 0; x < _n; x++) {
            for(int y=0; y < _n; y++)
                if(!_board.at(Location.create(x, y)).hasBeenAccessed() && !(_board.at(Location.create(x, y)) instanceof MineTile))
                    return false;
        }
        return true;
    }

    public int size() {
        return _n;
    }

}
