import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Phil on 02.07.14.
 */
public class GFrame extends JFrame implements ActionListener {

    private String title;

    private JPanel drawPanel;
    private JPanel buttonPanel;

    private JSlider slideSize;

    private Polygon polygon;
    private int[] xPolyArray;
    private int[] yPolyArray;

    public GFrame(String title)
    {
        this.title = title;
        initialize();

        drawPanel = generateDrawPanel();
        buttonPanel = generateButtons();

        JPanel container = new JPanel();
        container.add(buttonPanel);
        container.add(drawPanel);

        add(container);

        showGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("Add"))
        {
            for(int i = 0; i < xPolyArray.length; i++) {
                xPolyArray[i] += slideSize.getValue();
                yPolyArray[i] += slideSize.getValue();
            }
            drawPanel.repaint();
        }
        else if(e.getActionCommand().equals("Subtract"))
        {
            for(int i = 0; i < xPolyArray.length; i++) {
                xPolyArray[i] -= slideSize.getValue();
                yPolyArray[i] -= slideSize.getValue();
            }
            drawPanel.repaint();
        }
    }

    private JPanel generateDrawPanel()
    {
        xPolyArray = new int[]{100, 120, 150};
        yPolyArray = new int[]{150, 120, 130};

        JPanel returnPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);

                //TODO: improve new Polygon()
                polygon = new Polygon(xPolyArray, yPolyArray, xPolyArray.length);

                g.drawPolygon(polygon);
                g.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };

        returnPanel.setBackground(new Color(200, 200, 200));
        return returnPanel;
    }

    private JPanel generateButtons()
    {
        JPanel objectPanel = new JPanel();

        JButton button1 = new JButton("Add");
        JButton button2 = new JButton("Subtract");
        slideSize = new JSlider();

        button1.addActionListener(this);
        button2.addActionListener(this);

        objectPanel.add(button1);
        objectPanel.add(button2);
        objectPanel.add(slideSize);

        objectPanel.setBackground(new Color(200, 200, 250));
        return objectPanel;
    }

    private void initialize()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        setPreferredSize(new Dimension(500, 400));
    }

    private void showGUI()
    {
        pack();
        setVisible(true);
    }
}
