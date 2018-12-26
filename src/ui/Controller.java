package ui;

import ai.AI;
import logic.*;
import ui.Model.Status;

public class Controller {

    public enum GameEndReasons {
        HIT_MINE, WIN, RESTART
    }

    private Model model;
    private View view;
    private Field field;
    private AI dumbDumb;

    private double _density;

    public Controller(int size, double mineDensity) {
        this._density = mineDensity;
        field = new Field(size, mineDensity);
        model = new Model(size, field.numMines());

        dumbDumb = new AI(this);
    }

    public void start() {
        view.init(model.getSize(), model.getSize());
        view.updateTiles(model);
    }

    public void restart() {
        int size = model.getSize();
        field = new Field(size, _density);
        model = new Model(size, field.numMines());
        view.updateTiles(model);
    }

    /**
     * Launch a new game with a different size and density. The new controller will be automatically set itself to the view
     * @param size The size of hte grid. The grid is a square with a length of size
     * @param mineDensity A percentage for how many mines there should be in the grid
     * @param newView The view to use a new controller on.
     */
    public static void launchNewGame(int size, double mineDensity, View newView) {
        Controller newController = new Controller(size, mineDensity);
        newController.setView(newView);
        newView.setController(newController);
        new Thread(newController::start).start();
    }

    /**
     * Flips the mine on the grid at position <code>pos</code>. Calls the flip method in the <code>Field</code> class. Then, if a GameEndException is thrown
     * @param pos
     * @see Field
     */
    void flip(Location pos) {

        if(!field.flip(pos))
            update();
        else {
            field.flipAllMines();
            update();
            view.endGame(GameEndReasons.HIT_MINE);
        }
    }

    public void flag(Location position) {
        field.flag(position);

        update();
    }

    private void update() {
        updateModel();
        view.updateTiles(model);

        dumbDumb.executeMove();
        if(field.checkIfWon()) {
            view.endGame(GameEndReasons.WIN);
        }
    }

    private void updateModel() {
        for(int x = 0; x < model.getSize(); x++) {
            for(int y = 0; y < model.getSize(); y++) {
                Tile t = field.getTileAt(new Location(x, y));

                if(t.hasBeenFlipped()) {
                    if (t instanceof MineTile) {
                        if (((MineTile) t).wasNormallyFlipped())
                            model.setStatus(x, y, Status.CLICKED_MINE);
                        else if (t.isFlagged())
                            model.setStatus(x, y, Status.FLAGGED_MINE);
                        else
                            model.setStatus(x, y, Status.MINE);
                    }
                    else if (t instanceof NumberTile) {
                        model.setStatus(x, y, Status.valueOf(((NumberTile) t).nearbyMines()));
                    }
                }
                else if(t.isFlagged())
                    model.setStatus(x, y, Status.FLAGGED);
                else
                    model.setStatus(x, y, Status.UNFLIPPED);

            }
        }
    }

    public Model getModel() {
        return model;
    }

    public void setView(View v) {
        view = v;
    }
}
