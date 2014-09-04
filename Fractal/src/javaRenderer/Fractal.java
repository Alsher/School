package javaRenderer;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Phil on 16.08.14.
 */
public class Fractal{

    private int width, height;
    private int iterationValue;

    private double zoomFactor;
    private int zoomStep;

    private boolean fastMode;
    private int lastMax;

    private double moveX, moveY;

    private BufferedImage fractalImage;

    public enum MoveDirection
    {
        UP, DOWN, LEFT, RIGHT
    }

    public Fractal(int width, int height)
    {
        this.width = width;
        this.height = height;

        iterationValue = 255;
        lastMax = iterationValue;
        zoomFactor = 200;
        moveX = 0;
        moveY = 0;
        zoomStep = 1;
        fastMode = false;
    }

    /** Calculations **/

    private void createImage()
    {
        fractalImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        int c = new Color(0,0,0).getRGB();

        double start = System.nanoTime();

        //iterate through whole screen
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (isInSet( //check if data is in Mandelbrot-Set
                        ((x - width / 2) / zoomFactor) + moveX,   //x is modified to allow interaction, width / 2 for centering
                        ((y - height / 2) / zoomFactor) + moveY)) //y is modified to allow interaction, height / 2 for centering
                    fractalImage.setRGB(x, y, c);
            }
        }

        System.out.println("Calculation took " + ((System.nanoTime() - start) / 1000000000.0) + "ms");
    }

    private boolean isInSet(double a, double b)
    {
        /* Mandelbrot is calculated by the following algorithm (has to be used on every pixel)
         * complex z = 0;
         * while(abs(z) <= 2.0)
         *  z = z * z + c;
         */

        double cx = 0, cy = 0;
        double cxsqr, cysqr;


//          "old code"
//        for(int i = 0; i < iterationValue; i++)
//        {
//            double temp = cx * cx - cy * cy + a;
//            cy = 2 * cx * cy + b;
//            cx = temp;
//
//
//            if(cx * cx + cy * cy > 4)
//                return false;
//        }
        for(int i = 0; i < iterationValue; i++)
        {
            cxsqr = cx * cx;
            cysqr = cy * cy;

            cy = cx * cy;
            cy += cy; //multiply by two
            cy += b; //add pixel y
            cx = cxsqr - cysqr + a; //subtract squares and add pixel x;

            if(cx * cx + cy * cy > 4.0)
                return false;
        }

        return true;
    }

    /** Interaction **/

    public void move(MoveDirection moveDirection, double amount)
    {
        switch(moveDirection)
        {
            case UP:
                setMoveY(moveY - amount);
                break;

            case DOWN:
                setMoveY(moveY + amount);
                break;

            case LEFT:
                setMoveX(moveX - amount);
                break;

            case RIGHT:
                setMoveX(moveX + amount);
                break;
            default:
                break;
        }
    }

    public void zoom(double amount)
    {
        if(amount > 0)
            zoomStep++;
        else
            zoomStep--;
        setZoomFactor(zoomFactor + ((amount * zoomStep) / 5));
    }

    public void toggleFast()
    {
        if(!fastMode) {
            fastMode = true;
            lastMax = getIteration();
            setIterationValue(5);
        }
        else {
            fastMode = false;
            setIterationValue(lastMax);
        }
    }

    /** Getters ans Setters **/

    public BufferedImage getImage()
    {
        createImage();
        return fractalImage;
    }

    public void changeIteration(int amount)
    {
        iterationValue += amount;
    }

    public double getZoomFactor() {
        return zoomFactor;
    }
    public void setZoomFactor(double amount) {
        this.zoomFactor = amount;
    }

    public Integer getIteration()
    {
        return iterationValue;
    }
    public void setIterationValue(int iterationValue)
    {
        this.iterationValue = iterationValue;
    }

    public int getZoomStep()
    {
        return zoomStep;
    }

    public double getMoveX() {
        return moveX;
    }
    private void setMoveX(double moveX) {
        this.moveX = moveX;
    }

    public double getMoveY() {
        return moveY;
    }
    private void setMoveY(double moveY) {
        this.moveY = moveY;
    }
}