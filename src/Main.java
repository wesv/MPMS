import ui.*;

public class Main {
    public static void main(String[] args) {
        Model m = new Model(4);
        View v = new View();
        Controller c = new Controller(v, m);

        c.start();
    }
}
