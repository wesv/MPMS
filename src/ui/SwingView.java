package ui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;

public class SwingView implements View{
    private ImageIcon _minePic = new ImageIcon("resources/mine.png");
    private ImageIcon _flagPic = new ImageIcon("resources/flag.png");

    private final Color _indentedColor = new Color(189, 189, 189);
    private final Color _unindentedColor = new Color(192, 192, 192);
    private final Color _borderColor = new Color(113, 113, 113);

    private Controller _controller;
    private boolean _tilesCanBeClicked;

    private JFrame _frame;
    private JPanel _field;
    private TileButton[][] _tiles;
    private JLabel mineCountLabel;

    private final int boardWidth = 500;


    public void init(int width, int height) {
        init((width + height) / 2);
    }

    private void init(int size) {
        ResourceBundle strings = ResourceBundle.getBundle("ui.strings");
        _tilesCanBeClicked = true;

        /* constants used for the UI */
        final int borderWidth = 35;

        _frame = new JFrame(strings.getString("title"));
        _frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel(new BorderLayout());
        JPanel topRow = new JPanel(new GridBagLayout());

        Color backgroundColor = new Color(192, 192, 192);

        initField(size);
        _field.setBorder(new CompoundBorder(
                BorderFactory.createEmptyBorder(0, borderWidth, borderWidth, borderWidth),
                new CompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 2, 2, _borderColor),
                        BorderFactory.createMatteBorder(2, 2, 0, 0, Color.WHITE)
                )

        ));
        _field.setBackground(backgroundColor);


        final int rowHeight = 60;

    /* Create the Label Stating the number of mines left to find */
        mineCountLabel = new JLabel("0");
        mineCountLabel.setFont(new Font(mineCountLabel.getFont().getName(), Font.PLAIN, 30));
        mineCountLabel.setPreferredSize(new Dimension(50, rowHeight));
        mineCountLabel.setBackground(Color.WHITE);
        GridBagConstraints labelConstraint = new GridBagConstraints();
        labelConstraint.gridx = 0;
        labelConstraint.gridy = 0;
        labelConstraint.gridwidth = 1;
        labelConstraint.gridheight = 1;
        labelConstraint.insets = new Insets(0, borderWidth, 0, 0);
        labelConstraint.anchor = GridBagConstraints.LINE_START;
        labelConstraint.fill = GridBagConstraints.HORIZONTAL;
        topRow.add(mineCountLabel, labelConstraint);


        JLabel timerLabel = new JLabel("0");
        timerLabel.setFont(new Font(mineCountLabel.getFont().getName(), Font.PLAIN, 30));
        timerLabel.setPreferredSize(new Dimension(50, rowHeight));
        timerLabel.setHorizontalAlignment(JLabel.RIGHT);
        GridBagConstraints timerConstraint = new GridBagConstraints();
        timerConstraint.gridx = 2;
        timerConstraint.gridy = 0;
        timerConstraint.gridwidth = 1;
        timerConstraint.gridheight = 1;
        timerConstraint.insets = new Insets(0, 0, 0, borderWidth);
        timerConstraint.anchor = GridBagConstraints.CENTER;
        topRow.add(timerLabel, timerConstraint);

        TileButton restartButton = new TileButton(new logic.Location(-1, -1));
        restartButton.setPreferredSize(new Dimension(40, 40));
        restartButton.unindent();
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(new Rectangle(e.getComponent().getLocationOnScreen(), e.getComponent().getSize()).contains(e.getLocationOnScreen())) {
                    _controller.restart();
                    _tilesCanBeClicked = true;
                }
            }
        });
        GridBagConstraints buttonConstraint = new GridBagConstraints();
        buttonConstraint.gridx = 1;
        buttonConstraint.gridy = 0;
        buttonConstraint.gridwidth = 1;
        buttonConstraint.gridheight = 1;
        buttonConstraint.insets = new Insets(
                (rowHeight - restartButton.getPreferredSize().height) / 2,
                boardWidth / 2 - borderWidth * 2 ,
                (rowHeight - restartButton.getPreferredSize().height) / 2,
                boardWidth / 2 - borderWidth * 2
        );
        buttonConstraint.anchor = GridBagConstraints.CENTER;
        buttonConstraint.weightx = 1.0;
        topRow.add(restartButton, buttonConstraint);

        topRow.setBackground(backgroundColor);

        panel.add(_field, BorderLayout.CENTER);
        panel.add(topRow, BorderLayout.NORTH);


        _frame.add(panel);

        //_frame.setResizable(false);
        _frame.pack();
        _frame.setMinimumSize(new Dimension(_frame.getWidth(), _frame.getHeight()));
        _frame.setVisible(true);

    }

    private void initField(int size) {
        _field = new JPanel(new GridLayout(size, size));
        _tiles = new TileButton[size][size];
        Dimension tileSize = new Dimension(boardWidth / size, boardWidth / size);

        //Rescale Pictures to match the size of the tiles
        Image newMineImg = _minePic.getImage().getScaledInstance(
                (int)tileSize.getWidth(),
                (int)tileSize.getHeight(),
                Image.SCALE_SMOOTH);
        _minePic = new ImageIcon(newMineImg);

        Image newFlagImg = _flagPic.getImage().getScaledInstance(
                (int)tileSize.getWidth(),
                (int)tileSize.getHeight(),
                Image.SCALE_SMOOTH);

        _flagPic = new ImageIcon(newFlagImg);

        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                _tiles[i][j] = new TileButton(new logic.Location(i, j));

                _tiles[i][j].unindent();
                _tiles[i][j].setPreferredSize(tileSize);
                _tiles[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        super.mouseClicked(e);

                        if(new Rectangle(e.getComponent().getLocationOnScreen(),
                                         e.getComponent().getSize())
                                .contains(e.getLocationOnScreen()))
                        {

                            if (_tilesCanBeClicked) {
                                if (e.getButton() > 1) // Right  Click
                                    _controller.flag(((TileButton) e.getSource()).getGridLocation());
                                else
                                    _controller.flip(((TileButton) e.getSource()).getGridLocation());
                            }
                        }
                    }
                });

                _field.add(_tiles[i][j]);
            }
        }

    }

    /**
     * Mix the images used for the mine and the flag to show which mines you flagged correctly.
     * The flag icon will be on top of the mine icon.
     *
     * @return an ImageIcon mixing the 2 images together.
     */
    private ImageIcon mixFlagAndMineImages() {
        // These 2 images have the same dimensions as defined in the initField method
        Image mine = _minePic.getImage();
        Image flag = _flagPic.getImage();

        int width = mine.getWidth(null);
        int height= mine.getHeight(null);

        BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        Graphics g = combined.getGraphics();

        g.drawImage(mine, 0, 0, null);
        g.drawImage(flag, 0, 0, null);

        return new ImageIcon(combined);
    }

    public void updateTiles(Model m) {
        int flags = 0;

        for(int x = 0; x < _tiles.length; x++) {
            for (int y = 0; y < _tiles[x].length; y++) {
                int status = m.getStatus(x, y);
                switch(status) {
                    case Model.FLAGGED:
                        _tiles[x][y].setIcon(_flagPic);
                        flags++;
                        break;
                    case Model.MINE:
                        _tiles[x][y].setIcon(_minePic);
                        _tiles[x][y].indent();
                        break;
                    case Model.CLICKED_MINE:
                        _tiles[x][y].setIcon(_minePic);
                        _tiles[x][y].indent();
                        _tiles[x][y].setBackground(Color.RED);
                        break;
                    case Model.FLAGGED_MINE:
                        _tiles[x][y].setIcon(mixFlagAndMineImages());
                        _tiles[x][y].indent();
                        flags++;
                        break;
                    case Model.UNFLIPPED:
                        _tiles[x][y].setText("");
                        _tiles[x][y].setIcon(null);
                        _tiles[x][y].unindent();
                        break;
                    case 0:
                        _tiles[x][y].setText("");
                        _tiles[x][y].indent();
                        break;
                    default:
                        _tiles[x][y].setText(""+ status);
                        _tiles[x][y].indent();
                }
            }
        }

        mineCountLabel.setText("" + (m.numMines() - flags));
    }

    public void setController(Controller controller) {
        _controller = controller;
    }

    public void endGame(Controller.GameEndReasons reasons) {
        _tilesCanBeClicked = false;
        if(reasons == Controller.GameEndReasons.WIN) {
            JOptionPane.showMessageDialog(null, "You did it!! \n You got a score of TODO", "Congrats", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void clickTile(logic.Location at) {

    }

    /**
     * Custom button to be the tiles on the Minesweeper Grid.
     */
    private class TileButton extends JButton {
        /* The location of the button in the grid */
        private logic.Location loc;

        /* The borders used for when its clicked/not clicked */
        private Border unindentedBorder;
        private Border indentedBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(123, 123, 123));

        TileButton(logic.Location l) {
            this.loc = l;

            this.setFocusPainted(false);

            //Set Area to False so we can draw our own Fill
            super.setContentAreaFilled(false);

            unindentedBorder = new CompoundBorder(
                    BorderFactory.createMatteBorder(1, 1, 0, 0, Color.WHITE),
                    BorderFactory.createMatteBorder(0, 0, 1, 1, _borderColor)
            );

        }

        logic.Location getGridLocation() {
            return loc;
        }

        void unindent() {
            this.setBorder(unindentedBorder);
            this.setBackground(_unindentedColor);
        }

        void indent() {
            this.setBorder(indentedBorder);
            this.setBackground(_indentedColor);
        }

        @Override
        protected void paintComponent(Graphics g) {

            if(getModel().isPressed()) {
                g.setColor(_indentedColor);
            } else {
                g.setColor(getBackground());
            }
            
            g.fillRect(0, 0, getWidth(), getHeight());
            super.paintComponent(g);

        }

        @Override
        protected void paintBorder(Graphics g) {
            super.paintBorder(g);
            
            if(getModel().isPressed()) {
                indentedBorder.paintBorder(this, g, 0, 0, getWidth(), getHeight());
            }
        }

        @Override
        public void setContentAreaFilled(boolean b) {
        }
    }
}
