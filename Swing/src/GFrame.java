import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Philipp Friese
 */
public class GFrame extends JFrame implements ActionListener
{
    private static final int CIRCLE_RADIUS = 50;

    private static final int DRAW_WIDTH = 400;
    private static final int DRAW_HEIGHT = 400;

    private JPanel drawPanel;
    private JComboBox<Circle> circleComboBox;
    private ArrayList<Circle> circleList;

    private JSlider sliderHorizontal, sliderVertical;
    private Circle selectedCircle;

    public GFrame(String title, int width, int height)
    {
        initialize(title, width, height);

        drawPanel = generateDrawPanel();
        JPanel objectPanel = generateObjects();

        JPanel container = new JPanel();
        container.add(objectPanel);
        container.add(drawPanel);

        add(container);

        showGUI();
    }
    
    private JPanel generateDrawPanel()
    {
        circleList = new ArrayList<Circle>();

        JPanel returnPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);

                for(Circle circle : circleList) {
                    if(circle == selectedCircle) {
                        g2.setColor(Color.RED);
                        g.drawOval(circle.x, circle.y, circle.radius, circle.radius);
                        g2.setColor(Color.BLACK);
                    }
                    else
                        g2.drawOval(circle.x, circle.y, circle.radius, circle.radius);
                }
//                g2.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(DRAW_WIDTH, DRAW_HEIGHT);
            }
        };
        returnPanel.setSize(DRAW_WIDTH, DRAW_HEIGHT);
        returnPanel.setBackground(new Color(200, 200, 200));
        return returnPanel;
    }

    private JPanel generateObjects()
    {
        Circle circle = new Circle();
        circleList.add(circle);
        circleComboBox.addItem(circle);
        JPanel objectPanel = new JPanel();

        sliderHorizontal.setName("sliderHorizontal");
        sliderHorizontal.setMinimum(0);
        sliderHorizontal.setMaximum(drawPanel.getWidth() - CIRCLE_RADIUS);
        sliderHorizontal.addChangeListener(new SliderListener());

        sliderVertical.setName("sliderVertical");
        sliderVertical.setMinimum(0);
        sliderVertical.setMaximum(drawPanel.getHeight() - CIRCLE_RADIUS);
        sliderVertical.setInverted(true);
        sliderVertical.addChangeListener(new SliderListener());

        JButton addCircle = new JButton("Add Circle");
        addCircle.addActionListener(this);

        objectPanel.add(sliderHorizontal);
        objectPanel.add(sliderVertical);

//        objectPanel.add(addCircle);
//        objectPanel.add(circleComboBox);

        objectPanel.setBackground(new Color(200, 200, 250));
        return objectPanel;
    }

    private void initialize(String title, int width, int height)
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        setPreferredSize(new Dimension(width, height));

        circleComboBox = new JComboBox<Circle>();
        circleComboBox.addActionListener(this);

        sliderHorizontal = new JSlider(JSlider.HORIZONTAL);
        sliderVertical = new JSlider(JSlider.VERTICAL);
    }

    private void showGUI()
    {
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() instanceof JComboBox) {
            JComboBox cb = (JComboBox) e.getSource();
            selectedCircle = (Circle) cb.getSelectedItem();
            sliderHorizontal.setValue(selectedCircle.x);
            sliderVertical.setValue(selectedCircle.y);
        }
        else if(e.getSource() instanceof JButton) {
            JButton b = (JButton) e.getSource();
            if(b.getActionCommand().equals("Add Circle")) {
                Circle circle = new Circle();
                circleList.add(circle);
                circleComboBox.addItem(circle);
                circleComboBox.setSelectedItem(circle);
                repaint();
            }
        }
    }

    class Circle
    {
        String name;
        int x, y, radius;
        public Circle()
        {
            x = 0;
            y = 0;
            radius = CIRCLE_RADIUS;
            name = "Circle " + (circleList.size() + 1);
        }
        @Override
        public String toString()
        {
            return name;
        }
    }

    class SliderListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			JSlider source = (JSlider)e.getSource();
            if(source.getName().equals("sliderHorizontal"))
            {
                selectedCircle.x = source.getValue();
                drawPanel.repaint();
            }
            else if(source.getName().equals("sliderVertical"))
            {
                selectedCircle.y = source.getValue();
                drawPanel.repaint();
            }
		}    
    }
}