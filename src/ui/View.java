package ui;

public interface View {
    /**
     * Initialize the User Interface with a field size of width x height. At the end of init, the user interface must be shown
     * to the user before this method call is finished
     * @param width The number of tiles wide the grid should be
     * @param height The number of tiles tall the grid should be
     */
    void init(int width, int height);

    /**
     * Update the mine field with the <code>model</code> given. The <code>model</code> must be the same size as the field, otherwise behavior is undefined.
     * This method uses <code>Model.Status</code> to determine what to set the tiles to.
     * @param model the model to update with
     * @see Model.Status
     */
    void updateTiles(Model model);

    /**
     * Sets the <code>Controller</code> object used for controlling actions on the interface.
     * @param controller the new controller to set to
     */
    void setController(Controller controller);
    void endGame(Controller.GameEndReasons reason);

    /**
     * Preforms a click action on the tile at location <code>at</code>. This method is used as fluff for the View when another source clicks a tile
     * @param at
     */
    void clickTile(logic.Location at);

}
