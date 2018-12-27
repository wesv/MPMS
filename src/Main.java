import ui.*;

public class Main {
    public static void main(String[] args) {

        Controller newController = new Controller(15, 0.18);
        View ui = new SwingView();
        newController.setView(ui);
        ui.setController(newController);
        new Thread(newController::start).start();
}
}
