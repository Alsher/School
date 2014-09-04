package openGLRenderer;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

/**
 * Created by Phil on 27.08.14.
 */
public class Texture
{
    org.newdawn.slick.opengl.Texture texture;
    public Texture(String fileName)
    {
        try {
            texture = TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream("./res/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getTextureID()
    {
        return texture.getTextureID();
    }
}
