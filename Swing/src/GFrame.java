import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Phil on 02.07.14.
 */
public class GFrame extends JFrame implements ActionListener {
    private JPanel drawPanel;
    private JComboBox<Circle> polygonList;
    private ArrayList<Circle> circleList;

    private JSlider sliderHorizontal, sliderVertical;
    private Circle selectedCircle;

    public GFrame(String title)
    {
        initialize(title);

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
        Circle circle = new Circle();
        circleList.add(circle);
        polygonList.addItem(circle);

        final int width = 200;
        final int height = 200;

        JPanel returnPanel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);

                for(Circle circle : circleList)
                {
                    if(circle == selectedCircle)
                    {
                        g2.setColor(Color.RED);
                        g2.drawOval(circle.x, circle.y, circle.radius, circle.radius);
                        g2.setColor(Color.BLACK);
                    }
                    else
                        g2.drawOval(circle.x, circle.y, circle.radius, circle.radius);
                }
                g2.dispose();
            }
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(width, height);
            }
        };
        returnPanel.setSize(width, height);
        returnPanel.setBackground(new Color(200, 200, 200));
        return returnPanel;
    }

    private JPanel generateObjects()
    {
        JPanel objectPanel = new JPanel();

        sliderHorizontal.setName("sliderHorizontal");
        sliderHorizontal.setMinimum(0);
        sliderHorizontal.setMaximum(drawPanel.getWidth());
        sliderHorizontal.addChangeListener(new SliderListener());

        sliderVertical.setName("sliderVertical");
        sliderVertical.setMinimum(0);
        sliderVertical.setMaximum(drawPanel.getHeight());
        sliderVertical.setInverted(true);
        sliderVertical.addChangeListener(new SliderListener());

        JButton addCircle = new JButton("Add Circle");
        addCircle.addActionListener(this);

        objectPanel.add(sliderHorizontal);
        objectPanel.add(sliderVertical);
        objectPanel.add(addCircle);

        objectPanel.add(polygonList);

        objectPanel.setBackground(new Color(200, 200, 250));
        return objectPanel;
    }

    private void initialize(String title)
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle(title);
        setPreferredSize(new Dimension(500, 400));

        polygonList = new JComboBox<Circle>();
        polygonList.addActionListener(this);

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
        else if(e.getSource() instanceof JButton)
        {
            JButton b = (JButton) e.getSource();
            if(b.getActionCommand().equals("Add Circle"))
            {
                Circle circle = new Circle();
                circleList.add(circle);
                polygonList.addItem(circle);
                polygonList.setSelectedItem(circle);
                repaint();
            }
        }
    }

    class Circle
    {
        String name;
        public int x, y, radius;
        public int xOrig, yOrig;
        public Circle(int x, int y, int radius, String name)
        {
            this.x = x;
            this.xOrig = x;
            this.y = y;
            this.yOrig = y;
            this.radius = radius;
            this.name = name;
        }
        public Circle()
        {
            x = 0;
            y = 0;
            xOrig = 0;
            yOrig = 0;
            radius = 50;
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
                selectedCircle.x = selectedCircle.xOrig + source.getValue();
                drawPanel.repaint();
            }
            else if(source.getName().equals("sliderVertical"))
            {
                selectedCircle.y = selectedCircle.yOrig + source.getValue();
                drawPanel.repaint();
            }
		}    
    }
}
