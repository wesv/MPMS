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
        field = new Field(m.getSize(), 0.05);
    }

    public void start() {
        view.init(model.getSize(), model.getSize());
    }

    public void flip(Location pos) {
        try {
            field.flip(pos);
        } catch (GameEndException e) {
            view.endGame(GameEndReasons.HIT_MINE);
            field.flipAllMines();
        }

        update();
    }

    public void flag(Location position) {
        field.flag(position);

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
                Tile t = field.getTileAt(new Location(x, y));

                if(t.hasBeenAccessed()) {
                    if (t instanceof MineTile) {
                        if (((MineTile) t).wasNormallyFlipped())
                            model.setStatus(x, y, Model.CLICKED_MINE);
                        else if (t.isFlagged())
                            model.setStatus(x, y, Model.FLAGGED_MINE);
                        else
                            model.setStatus(x, y, Model.MINE);
                    }
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
