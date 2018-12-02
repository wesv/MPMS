package ui;

import exceptions.GameEndException;
import logic.*;

public class Controller {

    public enum GameEndReasons {
        HIT_MINE, WIN
    }

    private Model model;
    private SwingView view;
    private Field field;

    public Controller(SwingView v, Model m) {
        model = m;
        view = v;
        v.setController(this);
        field = new Field(m.getSize(), m.getSize());
    }

    public void start() {
        view.init(model.getSize(), model.getSize());
    }

    public void flip(int row, int col) {
        try {
            field.flip(row, col);
        } catch (GameEndException e) {
            view.endGame(GameEndReasons.HIT_MINE);
            field.flipAllMines();
        }

        update();
    }

    public void flag(int i, int j) {
        field.flag(i, j);

        update();
    }

    private void update() {
        updateModel();
        view.updateTiles(model);

        if(field.checkIfWon()) {
            view.endGame(GameEndReasons.WIN);
        }
    }

    private void updateModel() {
        for(int x = 0; x < model.getSize(); x++) {
            for(int y = 0; y < model.getSize(); y++) {
                Tile t = field.getTileAt(x, y);

                if(t.hasBeenAccessed()) {
                    if (t instanceof MineTile)
                        model.setStatus(x, y, Model.MINE);
                    else if (t instanceof NumberTile)
                        model.setStatus(x, y, ((NumberTile) t).getNumMines());
                }
                else if(t.isFlagged())
                    model.setStatus(x, y, Model.FLAGGED);
                else
                    model.setStatus(x, y, Model.UNFLIPPED);

            }
        }
    }


}
