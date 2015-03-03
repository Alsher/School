import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created by Phil on 03.03.15.
 */
public class GUI extends JFrame implements KeyListener
{
    private static final int WIDTH = 300, HEIGHT = 500;

    private Game parentGame;

    private JPanel container;
    private JLabel currentPlayer;
    private JLabel dice1, dice2;

    private int curDice;
    private int d1, d2;

    public GUI(Game parentGame)
    {
        this.parentGame = parentGame;

        setTitle("GUI");
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        currentPlayer = new JLabel("Current Player: null");
        dice1 = new JLabel("<html><b>Dice 1: null</b>");
        dice2 = new JLabel("Dice 2: null");

        container.add(currentPlayer);
        container.add(dice1);
        container.add(dice2);

        add(container);

        addKeyListener(this);

        pack();
        setVisible(true);
    }

    public void update(int curPlayer, int d1, int d2)
    {
        this.d1 = d1;
        this.d2 = d2;
        curDice = 1;
        parentGame.getPlayer(d1).select();

        currentPlayer.setText("<html><b>Current Player :" + String.valueOf(curPlayer) + "</b></html>");
        dice1.setText("<html><b>Dice 1: " + String.valueOf(d1) + "</b></html>");
        dice2.setText("Dice 2: " + String.valueOf(d2));
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int selDice = (curDice == 1 ? d1 : d2);
        boolean successful = false;
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_UP:
                successful = parentGame.updatePlayerPosition(selDice, Game.UP);
                break;
            case KeyEvent.VK_DOWN:
                successful = parentGame.updatePlayerPosition(selDice, Game.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                successful = parentGame.updatePlayerPosition(selDice, Game.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                successful = parentGame.updatePlayerPosition(selDice, Game.RIGHT);
                break;
        }
        if(successful) {
            repaint();
            if (curDice == 1) {
                parentGame.getPlayer(d1).deselect();
                parentGame.getPlayer(d2).select();
                dice1.setText("Dice 1: " + String.valueOf(d1));
                dice2.setText("<html><b>Dice 2: " + String.valueOf(d2) + "</b></html>");
                curDice = 2;
            } else {
                parentGame.getPlayer(d2).deselect();
                parentGame.newRound();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
