package ui;

import exceptions.GameEndException;
import logic.Field;

public class Console implements UserInterface {
    @Override
    public void run() throws GameEndException {
        Field f = new Field(3, 2);
        f.flipAllMinea();


        System.out.println(f.print());
    }
}
