package ui;

import exceptions.GameEndException;
import logic.Field;
import logic.MineTile;
import logic.NumberTile;
import logic.Tile;

import java.util.Scanner;

public class Console implements Runnable {
    @Override
    public void run() {
        Field f = new Field(4, 2);
        Scanner in = new Scanner(System.in);

        loop:
        while(true) {
            System.out.println(fieldToString(f));
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
                        System.out.println(fieldToString(f));
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
                System.out.println(f);
                break;
            }
        }
    }

    public String fieldToString(Field f) {
        StringBuilder str = new StringBuilder();


        for(int x = 0; x < f.size(); x++) {
            str.append("|");
            for(int y = 0; y < f.size(); y++) {
                str.append(convertMineToString(f.tileAt(x, y)));
                if(y != f.size())
                    str.append("|");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private String convertMineToString(Tile mine) {
        if(mine.isFlagged())
            return Tile.flagChar + "";

        if(mine.hasBeenAccessed()) {
            if (mine instanceof MineTile) {
                if(((MineTile)mine).wasNormallyFlipped())
                    return "\u001B[31mM\u001B[0m"; //Red Text Formatting in ANSI
                return "M";
            }
            if (mine instanceof NumberTile)
                return ((NumberTile) mine).getNumMines() + "";
        }
        return "_";
    }
}
