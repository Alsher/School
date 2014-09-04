package openGLRenderer;

import javax.swing.*;
import java.awt.*;

public class Core {

    private static Fractal fractal;
    private static JFrame window;

    private static JLabel frameLabel, iterationLabel, xOffset, yOffset;

    public static void run()
    {
        boolean isRunning = true;
        Window.createDisplay(1000, 1000, "Fractal");

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

                if(Window.isCloseRequested())
                    isRunning = false;

                fractal.input();
                Input.update();

                unprocessedTime -= frameTime;
            }

            if(render)
            {
                fractal.render();
                Window.render();
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
        window.pack();
        window.setVisible(true);
    }

    private static void initializeConsole()
    {
        int width = 160;
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

        frameLabel = new JLabel("Iteration: 0");
        iterationLabel = new JLabel("Frames: 0");
        xOffset = new JLabel("x Offset: 0");
        yOffset = new JLabel("y Offset: 0");

        window.add(frameLabel);
        window.add(iterationLabel);
        window.add(xOffset);
        window.add(yOffset);
        window.pack();
    }

    private static void cleanUp()
    {
        Window.dispose();
        window.dispose();
    }
}