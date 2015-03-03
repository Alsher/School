package openGLRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Core {

    private static Fractal fractal;
    private static JFrame window;

    private static JLabel frameLabel, iterationLabel, xOffset, yOffset, zoom, highRes;
    private static JPanel container;
    private static JButton toggleHR, toggleTexture;

    public static void run()
    {
        boolean isRunning = true;
        GLWindow.createDisplay(1000, 1000, "Fractal");

        fractal = new Fractal();
        window = new JFrame();

        initializeConsole();

        double frameTime = 1.0 / 240.0;

        double lastTime = (double)System.nanoTime()/(double)1000000000L;
        double unprocessedTime = 0;
        double frameCounter = 0;
        int frames = 0;

        while(isRunning) {

            boolean render = false;

            double startTime = (double) System.nanoTime() / (double) 1000000000L;
            double passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime;
            frameCounter += passedTime;

            if (frameCounter >= 1.0)
            {
                updateConsole(frames);
                frames = 0;
                frameCounter = 0;
            }

            while(unprocessedTime > frameTime)
            {
                render = true;

                if(GLWindow.isCloseRequested())
                    isRunning = false;

                fractal.input();
                Input.update();
                fractal.update();

                unprocessedTime -= frameTime;
            }

            if(render)
            {
                fractal.render();
                GLWindow.render();
                frames++;
                updateConsole(0);
            }
            else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        cleanUp();
    }

    private static void updateConsole(int frames)
    {
        if(frames != 0)
            frameLabel.setText("FPS: " + String.valueOf(frames));
        iterationLabel.setText("Iteration: " + String.valueOf(fractal.getIteration()));
        xOffset.setText("x Offset: " + String.valueOf(fractal.getxOffset()));
        yOffset.setText("y Offset: " + String.valueOf(fractal.getyOffset()));
        zoom.setText("Zoom: " + String.valueOf(fractal.getZoom()));
        highRes.setText("HighRes: " + fractal.getHighRes());
        window.pack();
        window.setVisible(true);
    }

    private static void initializeConsole()
    {
        int width = 450;
        int height = 200;
        window.setResizable(false);
        window.setPreferredSize(new Dimension(width, height));
        window.setSize(width, height);
        window.setLocation(
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()  / 6),
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2)
        );
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLayout(new FlowLayout());
        window.setFocusable(false);
        window.setFocusableWindowState(false);

        container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        frameLabel = new JLabel("Iteration: 0");
        iterationLabel = new JLabel("Frames: 0");
        xOffset = new JLabel("x Offset: 0");
        yOffset = new JLabel("y Offset: 0");
        zoom = new JLabel("Zoom: 0");
        highRes = new JLabel("HighRes: false");

        toggleHR = new JButton("Toggle HR");
        toggleHR.setName("highRes");
        toggleHR.addActionListener(new ButtonListener());

        toggleTexture = new JButton("Toggle Texture");
        toggleTexture.setName("texture");
        toggleTexture.addActionListener(new ButtonListener());

        container.add(frameLabel);
        container.add(iterationLabel);
        container.add(xOffset);
        container.add(yOffset);
        container.add(zoom);
        container.add(highRes);
        container.add(toggleHR);
        container.add(toggleTexture);

        window.add(container);
        window.pack();
    }

    private static void cleanUp()
    {
        GLWindow.dispose();
        window.dispose();
    }

    static class ButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton)e.getSource();
            String name = button.getName();
            if(name.equals("highRes"))
                fractal.setHighRes(!fractal.getHighRes());
            else if(name.equals("texture"))
                fractal.setTextureBool(!fractal.getTextureBool());
        }
    }
}

