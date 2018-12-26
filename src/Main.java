import ui.*;

public class Main {
    public static void main(String[] args) {

        Controller newController = new Controller(10, 0.15);
        View ui = new SwingView();
        newController.setView(ui);
        ui.setController(newController);
        new Thread(newController::start).start();
}
}
