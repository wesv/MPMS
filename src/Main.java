import logic.*;
import exceptions.GameEndException;

public class Main {
    public static void main(String[] args) {
        Field f = new Field(3, 2);
        f.flipAllMinea();


        System.out.println(f.print());
    }
}
