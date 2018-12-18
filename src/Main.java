import ui.*;

public class Main {
    public static void main(String[] args) {

        Controller newController = new Controller(10, 0.15, new SwingView());

        new Thread(newController::start).start();
}
}
