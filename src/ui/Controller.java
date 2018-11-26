package ui;

import logic.Field;

public class Controller {
    private Model model;
    private View view;
    private Field field;

    public Controller(View v, Model m) {
        model = m;
        view = v;
        v.setController(this);
        field = new Field(m.getSize(), m.getSize());
    }

    public void start() {
        view.init(model.getSize());
    }

    public void print(int row, int col) {
        System.out.println("Clicked Button At (" + row + ", " + col + ")");
        view.setButtonText(row, col, "M");
    }
}
