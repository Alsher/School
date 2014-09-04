/**
 * Created by Phil on 24.08.14.
 */
public class Main {

    public static void main(String[] args)
    {
//        System.setProperty("org.lwjgl.librarypath", new File("natives").getAbsolutePath());
        boolean glRenderer = true;

        if(glRenderer)
            openGLRenderer.Core.run();
        else
            javaRenderer.Core.run();
    }
}