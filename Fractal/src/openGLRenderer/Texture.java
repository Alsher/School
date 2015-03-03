package openGLRenderer;

import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

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

    public void bind()
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, getTextureID());
    }

    public int getTextureID()
    {
        return texture.getTextureID();
    }
}
