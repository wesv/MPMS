package ui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class View {
    private Controller c;
    private ResourceBundle strings;
    JPanel panel;
    JFrame frame;
    JButton[][] tiles;

    public void init(int size) {
        strings = ResourceBundle.getBundle("ui.strings");

        frame = new JFrame(strings.getString("title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        changeField(size);

        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

    }

    public void changeField(int size) {
        panel = new JPanel(new GridLayout(size, size));
        tiles = new JButton[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tiles[i][j] = new JButton();
            }
        }

        int cellLocation = 0;
        for(JButton[] row: tiles) {
            for(JButton col: row) {
                col.setPreferredSize(new Dimension(50, 50));
                col.setBackground(new Color(127, 127, 127));
                col.addActionListener(e -> {
                    for(int i = 0; i < size; i++) {
                        for(int j = 0; j < size; j++) {
                            if(tiles[i][j] == e.getSource())
                                c.print(i, j);
                        }
                    }
                });
                panel.add(col);
            }
        }
    }

    public void setButtonText(int r, int c, String text) {
        tiles[r][c].setText(text);
    }

    public void setController(Controller controller) {
        c = controller;
    }
}
