import javax.swing.*;
import java.awt.*;

/**
 * Created by Phil on 03.03.15.
 */
public class Renderer extends JFrame
{
    private static final int MUL = 80;
    private static final int COLUMNSIZE = 11;

    private static final int WIDTH = MUL * COLUMNSIZE, HEIGHT = MUL * COLUMNSIZE; //to make sure things are dividable by 11

    private JPanel drawPanel;

    private final Player p1, p2, p3, p4; //don't wish to delete players

    public Renderer(Player p1, Player p2, Player p3, Player p4)
    {
        setTitle("Game");
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

        generateDrawPanel();
        add(drawPanel);

        pack();
        setVisible(true);
    }

    private void generateDrawPanel()
    {
        drawPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                g.setColor(Color.BLACK);
                int xC = -5;
                int yC = 5;
                for(int i = 1; i <= COLUMNSIZE; ++i)
                {
                    final int widthSpacer = getWidth() / COLUMNSIZE;
                    final int heightSpacer = getHeight() / COLUMNSIZE;
                    //horizontal axis labeling
                    g.drawString((xC < -4 || xC > 4) ? "" : String.valueOf(xC), i * widthSpacer - (widthSpacer / 2) , heightSpacer / 2);
                    g.drawString((xC < -4 || xC > 4) ? "" : String.valueOf(xC), i * widthSpacer - (widthSpacer / 2) , getHeight() - heightSpacer / 2);

                    //vertical axis labeling
                    g.drawString((yC < -4 || yC > 4) ? "" : String.valueOf(yC), widthSpacer / 2 , i * heightSpacer - (getHeight() / COLUMNSIZE / 2));
                    g.drawString((yC < -4 || yC > 4) ? "" : String.valueOf(yC), getWidth() - (widthSpacer / 2), i * heightSpacer - (heightSpacer/ 2));

                    ++xC;
                    --yC;

                    //grid
                    g.drawLine(i * widthSpacer, 0, i * widthSpacer, getHeight());
                    g.drawLine(0, i * heightSpacer, getWidth(), i * heightSpacer);

                    //"home"
                    g.setColor(Color.BLUE);
                    g.fillRect(5 * widthSpacer, 5 * heightSpacer, widthSpacer, heightSpacer);
                    g.setColor(Color.WHITE);
                    g.drawString("Home", 6 * widthSpacer - (widthSpacer / 2) - "Home".length() * 4, 6 * heightSpacer - (heightSpacer / 2));
                    g.setColor(Color.BLACK);

                    //player 1
                    g.setColor(p1.isSelected() ? Color.GREEN : Color.BLUE);
                    g.fillOval((p1.getX() + 5) * widthSpacer, (COLUMNSIZE - (p1.getY() + 6)) * heightSpacer, widthSpacer, heightSpacer);
                    g.setColor(Color.WHITE);
                    g.drawString("1", (p1.getX() + 6) * widthSpacer - (widthSpacer / 2) - "1".length() * 4, (COLUMNSIZE - (p1.getY() + 5)) * heightSpacer - (heightSpacer / 2) + "1".length() * 4);

                    //player 2
                    g.setColor(p2.isSelected() ? Color.GREEN : Color.BLUE);
                    g.fillOval((p2.getX() + 5) * widthSpacer, (COLUMNSIZE - (p2.getY() + 6)) * heightSpacer, widthSpacer, heightSpacer);
                    g.setColor(Color.WHITE);
                    g.drawString("2", (p2.getX() + 6) * widthSpacer - (widthSpacer / 2) - "2".length() * 4, (COLUMNSIZE - (p2.getY() + 5)) * heightSpacer - (heightSpacer / 2) + "2".length() * 4);

                    //player 3
                    g.setColor(p3.isSelected() ? Color.GREEN : Color.RED);
                    g.fillOval((p3.getX() + 5) * widthSpacer, (COLUMNSIZE - (p3.getY() + 6)) * heightSpacer, widthSpacer, heightSpacer);
                    g.setColor(Color.WHITE);
                    g.drawString("3", (p3.getX() + 6) * widthSpacer - (widthSpacer / 2) - "3".length() * 4, (COLUMNSIZE - (p3.getY() + 5)) * heightSpacer - (heightSpacer / 2) + "3".length() * 4);

                    //player 4
                    g.setColor(p4.isSelected() ? Color.GREEN : Color.RED);
                    g.fillOval((p4.getX() + 5) * widthSpacer, (COLUMNSIZE - (p4.getY() + 6)) * heightSpacer, widthSpacer, heightSpacer);
                    g.setColor(Color.WHITE);
                    g.drawString("4", (p4.getX() + 6) * widthSpacer - (widthSpacer / 2) - "4".length() * 4, (COLUMNSIZE - (p4.getY() + 5)) * heightSpacer - (heightSpacer / 2) + "4".length() * 4);

                    g.setColor(Color.BLACK);
                }
            }

            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(getWidth(), getHeight());
            }
        };
    }

    public void update()
    {
        repaint();
    }
}