import ui.*;

public class Main {
    public static void main(String[] args) {
        Model m = new Model(10);
        SwingView v = new SwingView();
        Controller c = new Controller(v, m);

        c.start();
    }
}
