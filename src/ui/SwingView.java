package ui;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
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
        field.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(35, 35, 35, 35), //TODO set top to 0
                new CompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 2, new Color(113, 113, 113)),
                        BorderFactory.createMatteBorder(2, 2, 0, 0, Color.WHITE)
                )

        ));
        field.setBackground(new Color(192, 192, 192));

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
                outdentTile(col);
                col.setPreferredSize(new Dimension(500 / size, 500 / size));
                col.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        //TODO add reference in order to remove event later
                        super.mouseClicked(e);

                        if(_tilesCanBeClicked) {

                            boolean toFlag = false;
                            if (e.getButton() > 1) {
                                toFlag = true;
                            }

                            //TODO create custom button to remove this loop
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

    private void outdentTile(JButton but) {
        but.setBorder(new CompoundBorder(
                BorderFactory.createMatteBorder(1, 1, 0, 0, Color.WHITE),
                BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(113, 113, 113))
        ));
        but.setBackground(new Color(192, 192, 192));
    }

    private void indentTile(JButton but) {
        but.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(123, 123, 123)));
        but.setBackground(new Color(189, 189, 189));
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
                        indentTile(tiles[x][y]);
                        break;
                    case Model.UNFLIPPED:
                        tiles[x][y].setText("");
                        break;
                    default:
                        tiles[x][y].setText(""+ status);
                        indentTile(tiles[x][y]);

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
