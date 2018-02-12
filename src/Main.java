import ui.Console;
import exceptions.GameEndException;

public class Main {
    public static void main(String[] args) {
        try {
            new Console().run();
        } catch (GameEndException e) {
            e.printStackTrace();
        }
    }
}
