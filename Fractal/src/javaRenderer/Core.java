package javaRenderer;

import java.io.IOException;

/**
 * Created by Phil on 16.08.14.
 */
public class Core
{
    public static void run()
    {
        int mb = 1024*1024;
        Runtime runtime = Runtime.getRuntime();
        System.out.println("Max Memory:" + runtime.maxMemory() / mb);

        double start = System.nanoTime();
        new javaRenderer.GFrame("Frame", 1000, 1000);
        System.out.println((System.nanoTime() - start) / 1000000000.0);
    }
}
