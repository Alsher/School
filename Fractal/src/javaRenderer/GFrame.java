package javaRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Phil on 16.08.14.
 */
public class GFrame extends JFrame implements KeyListener {

    private int width, height;

    private Fractal fractal;

    public GFrame(String title, int width, int height){
        this.width = width;
        this.height = height;
        fractal = new Fractal(width, height);

        setTitle(title);
        setPreferredSize(new Dimension(width, height));
        setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel fractalPanel = generateFractalPanel();

        add(fractalPanel);

        pack();
        setVisible(true);

        Scanner scanner = new Scanner(System.in);
        String in = scanner.next();

        while(!in.equals("quit"))
            switch(in.toCharArray()[0])
            {
                case 'p':
                    printImage(2000, 2000);
                    in = scanner.next();
                    break;
                case 'z':
                    fractal.setZoomFactor(Double.parseDouble(in.substring(1)));
                    repaint();
                    in = scanner.next();
                    break;
                case 'i':
                    fractal.setIterationValue(Integer.parseInt(in.substring(1)));
                    repaint();
                    in = scanner.next();
                    break;
            }
    }

    private JPanel generateFractalPanel()
    {
        JPanel returnPanel = new JPanel()
        {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2 = (Graphics2D) g;
                BufferedImage image = fractal.getImage();
                g2.drawImage(image, 0, 0, this);

                g2.setColor(Color.RED);
                g2.drawString("Current Iteration: " + fractal.getIteration(), 20, 20);
                g2.drawString("Current Zoom Factor: " + fractal.getZoomFactor(), 20, 40);
                g2.drawString("Current Zoom Step: " + fractal.getZoomStep(), 20, 60);
                g2.drawString("Current Position: " + fractal.getMoveX() + " | " + fractal.getMoveY(), 20, 80);
                g2.setColor(Color.BLACK);
            }

            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(getWidth(), getHeight());
            }
        };
        returnPanel.setFocusable(true);
        returnPanel.addKeyListener(this);

        return returnPanel;
    }

    private void printImage(int width, int height) {
        try {
            ImageIO.write(fractal.getImage(), "jpg", new File("/Users/Phil/Desktop/image.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                fractal.move(Fractal.MoveDirection.LEFT, 50 / fractal.getZoomFactor());
                break;
            case KeyEvent.VK_RIGHT:
                fractal.move(Fractal.MoveDirection.RIGHT, 50 / fractal.getZoomFactor());
                break;
            case KeyEvent.VK_UP:
                fractal.move(Fractal.MoveDirection.UP, 50 / fractal.getZoomFactor());
                break;
            case KeyEvent.VK_DOWN:
                fractal.move(Fractal.MoveDirection.DOWN, 50 / fractal.getZoomFactor());
                break;

            case KeyEvent.VK_ADD:
                fractal.zoom(50);
                break;
            case KeyEvent.VK_SUBTRACT:
                fractal.zoom(-50);
                break;

            case KeyEvent.VK_I:
                fractal.changeIteration(25);
                break;
            case KeyEvent.VK_O:
                fractal.changeIteration(-25);
                break;

            case KeyEvent.VK_F:
                fractal.toggleFast();
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
