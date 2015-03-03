import java.io.File;

/**
 * Created by Phil on 24.08.14.
 */
public class Main {

    public static void main(String[] args)
    {
        String os = System.getProperty("os.name");

        if(os.equals("Mac OS X"))
            System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.1/native/macosx").getAbsolutePath());
        else if(os.startsWith("Windows"))
            System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.1/native/windows").getAbsolutePath());

        boolean glRenderer = true;

        if(glRenderer)
            openGLRenderer.Core.run();
        else
            javaRenderer.Core.run();
    }
}