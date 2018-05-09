package ui;

import exceptions.GameEndException;
import logic.Field;

import java.util.Scanner;

public class Console implements UserInterface {
    @Override
    public void run() throws GameEndException {
        Field f = new Field(10, 10);
        Scanner in = new Scanner(System.in);

        loop:
        while(true) {
            System.out.println(f.print());
            System.out.print(">> ");
            String[] input = in.nextLine().split(" ");

            if(input.length < 3) {
                System.err.println("Invalid input");
                continue;
            }

            int x = Integer.valueOf(input[1]);
            int y = Integer.valueOf(input[2]);

            switch(input[0].toLowerCase()) {
                case "flip":
                    try {
                        f.flip(x, y);
                    } catch (GameEndException e) {
                        f.flipAllMines();
                        System.out.println(f.print());
                        System.out.println("you suck lol");
                        break loop;
                    }
                    break;
                case "flag":
                    f.flag(x, y);
                    break;
                default:
                    System.out.println("Command Not Recognized");
                    break;
            }

            if(f.checkIfWon())
            {
                System.out.println("YOU WIN!!");
                break;
            }
        }
    }
}
