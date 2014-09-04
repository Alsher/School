import javax.swing.*;
import java.awt.*;

public class SwingTest
{
    public static void main(String[] args)
    {
        Grafiken(false);
    }

    private static void Grafiken(boolean Elemente)
    {
        JFrame frame = new JFrame("title");

        if(Elemente) {
            JPanel container = new JPanel();
            JButton button1  = new JButton("JButton 1");
            JButton button2  = new JButton("JButton 2");

            container.add(button1);
            container.add(button2);
        }

        JPanel graphicPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                int xPos = 10;
                int yPos = 10;
                int width = 20;
                int height = 20;

                g.drawOval(xPos, yPos, width, height);
            }
        };

        frame.add(graphicPanel);

        frame.setPreferredSize(new Dimension(400, 200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
    }
}
