package ui;

public interface View {
    void init(int width, int height);
    void updateTiles(Model model);
    void setController(Controller controller);
    void endGame(Controller.GameEndReasons reason);
    void clickTile(logic.Location at);

}
