package ui;

import exceptions.GameEndException;
import logic.*;

public class Controller {

    public enum GameEndReasons {
        HIT_MINE, WIN
    }

    private Model model;
    private View view;
    private Field field;

    private Controller(View v, Model m, double mineDensity) {
        model = m;
        view = v;
        v.setController(this);
        field = new Field(m.getSize(), mineDensity);
    }

    private void start() {
        view.init(model.getSize(), model.getSize());
    }

    public static void launchNewGame(int size, double mineDensity, View newView) {
        Model newModel = new Model(size);
        Controller newController = new Controller(newView, newModel, mineDensity);

        new Thread(newController::start).start();
    }

    public void flip(Location pos) {
        try {
            field.flip(pos);
        } catch (GameEndException e) {
            field.flipAllMines();
            update();
            view.endGame(GameEndReasons.HIT_MINE);
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
