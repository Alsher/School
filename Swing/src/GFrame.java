import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Phil on 02.07.14.
 */
public class GFrame extends JFrame implements ActionListener {

    private String title;

    public GFrame(String title)
    {
        this.title = title;
        initialize();

        JPanel container = new JPanel();
        container.add(addButtonPanel());
        container.add(addDrawPanel());

        add(container);

        showGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        System.out.println(e.getActionCommand());
    }

    private JPanel addDrawPanel()
    {

        int xPoly[] = {100, 120, 150};
        int yPoly[] = {150, 120, 130};

        final Polygon polygon = new Polygon(xPoly, yPoly, xPoly.length);

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawPolygon(polygon);
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(200, 200);
            }
        };

        panel.setBackground(new Color(200, 200, 200));

        return panel;
    }

    private JPanel addButtonPanel()
    {
        JPanel panel = new JPanel();

        JButton button1 = new JButton("Button 1");
        JButton button2 = new JButton("Button 2");

        button1.addActionListener(this);
        button2.addActionListener(this);

        panel.add(button1);
        panel.add(button2);

        panel.setBackground(new Color(200, 200, 250));
        return panel;
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
