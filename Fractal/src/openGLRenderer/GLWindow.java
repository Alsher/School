package openGLRenderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;

/**
 * Created by Phil on 23.08.14.
 */
public class GLWindow {

    public static void createDisplay(int width, int height, String title)
    {
        Display.setTitle(title);
        try {
            PixelFormat pixelFormat = new PixelFormat();
            ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
                    .withForwardCompatible(true)
                    .withProfileCore(true);

            Display.setDisplayMode(new DisplayMode(width, height));
            Display.create(pixelFormat, contextAtrributes);
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0f);
        GL11.glViewport(0, 0, width, height);
    }

    public static void dispose()
    {
        Display.destroy();
        Keyboard.destroy();
    }

    public static void render()
    {
        Display.update();
    }

    public static boolean isCloseRequested()
    {
        return Display.isCloseRequested();
    }

    public static int getWidth()
    {
        return Display.getWidth();
    }

    public static int getHeight()
    {
        return Display.getHeight();
    }
}