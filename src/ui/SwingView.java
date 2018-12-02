package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

public class SwingView implements View{
    private Controller _c;
    private boolean _tilesCanBeClicked;

    private JPanel field;
    private JButton[][] tiles;


    public void init(int width, int height) {
        init((width + height) / 2);
    }

    private void init(int size) {
        ResourceBundle strings = ResourceBundle.getBundle("ui.strings");
        _tilesCanBeClicked = true;

        JFrame frame = new JFrame(strings.getString("title"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());



        changeField(size);

        panel.add(field, BorderLayout.CENTER);


        frame.add(panel);

        frame.pack();
        frame.setVisible(true);

    }

    private void changeField(int size) {
        field = new JPanel(new GridLayout(size, size));
        tiles = new JButton[size][size];

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                tiles[i][j] = new JButton();
            }
        }


        for(JButton[] row: tiles) {
            for(JButton col: row) {
                col.setPreferredSize(new Dimension(500 / size, 500 / size));
                col.setBackground(new Color(127, 127, 127));
                col.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);

                        System.out.println(_tilesCanBeClicked);
                        if(_tilesCanBeClicked) {

                            boolean toFlag = false;
                            System.out.println(e.getButton());
                            if (e.getButton() > 1) {
                                toFlag = true;
                            }

                            for (int i = 0; i < size; i++) {
                                for (int j = 0; j < size; j++) {
                                    if (tiles[i][j] == e.getSource())
                                        if (toFlag)
                                            _c.flag(i, j);
                                        else
                                            _c.flip(i, j);
                                }
                            }
                        }
                    }
                });

                field.add(col);
            }
        }
    }

    public void updateTiles(Model m) {
        for(int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles.length; y++) {
                int status = m.getStatus(x, y);
                switch(status) {
                    case Model.FLAGGED:
                        tiles[x][y].setText("F");
                        break;
                    case Model.MINE:
                        tiles[x][y].setText("M");
                        break;
                    case Model.UNFLIPPED:
                        tiles[x][y].setText("");
                        break;
                    default:
                        tiles[x][y].setText(""+ status);

                }
            }
        }
    }

    public void setController(Controller controller) {
        _c = controller;
    }

    public void endGame(Controller.GameEndReasons reasons) {
        StringBuilder endOfGameText = new StringBuilder();

        switch(reasons) {
            case WIN:
                endOfGameText.append("You win! Congrats!");
                break;
            case HIT_MINE:
                endOfGameText.append("Oh no! You ran into a mine I see.");
                break;
        }
        endOfGameText.append("\n\n Would you like to play once more?");
        int response = JOptionPane.showConfirmDialog(null, endOfGameText, "Game Over", JOptionPane.YES_NO_OPTION);
        _tilesCanBeClicked = false;
    }
}
