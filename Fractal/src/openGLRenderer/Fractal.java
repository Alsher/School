package openGLRenderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by Phil on 23.08.14.
 */
public class Fractal {

    private static final double XMOVE = 1.5f;
    private static final double YMOVE = 1.5f;

    private static final int ZOOMDIV = 50000;

    private double xOffset, yOffset, zoom;
    private int iteration, zoomStep;

    private int vao, vbo, ibo;

    private float[] vertices;
    private int[] indices;

    private Shader shader;
    private Texture texture;

    public Fractal()
    {
        xOffset = 0.0f;
        yOffset = 0.0f;
        zoom = 200.0f;
        iteration = 255;
        zoomStep = 1;

        texture = new Texture("pal");

        shader = new Shader("fractal");
        shader.setUniformd("xOffset", xOffset);
        shader.setUniformd("yOffset", yOffset);
        shader.setUniformd("zoom", zoom);
        shader.setUniformi("iteration", iteration);

//        shader.setSampler2D("tex", texture.getTextureID());

        shader.setUniformi("height", Window.getWidth());
        shader.setUniformi("width", Window.getHeight());

        createMesh();
        initMesh();
    }

    public void input() {
        if (Input.getKey(Input.KEY_A)) {
            xOffset -= XMOVE / zoom;

            System.out.println(xOffset);
        }
            if (Input.getKey(Input.KEY_D))
            xOffset += XMOVE / zoom;

        if (Input.getKey(Input.KEY_W))
            yOffset += YMOVE / zoom;
        if (Input.getKey(Input.KEY_S))
            yOffset -= YMOVE / zoom;

        if (Input.getKey(Input.KEY_ADD)) {
            zoomStep++;
            zoom += 3 * (zoomStep * zoom) / ZOOMDIV;
        }
        if (Input.getKey(Input.KEY_SUBTRACT)) {
            if (zoomStep > 0) {
                zoomStep--;
                zoom -= 3 * (zoomStep * zoom) / ZOOMDIV;
            }
            else
                zoom = 200;
        }

        if (Input.getKey(Input.KEY_I))
            iteration += 1;
        if (Input.getKey(Input.KEY_O))
            if(iteration > 3)
                iteration -= 1;

        updateUniforms();
    }

    public void render()
    {
        glBindVertexArray(vao);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    private void updateUniforms()
    {
        shader.setUniformd("xOffset", xOffset);
        shader.setUniformd("yOffset", yOffset);
        shader.setUniformd("zoom", zoom);
        shader.setUniformi("iteration", iteration);
    }

    private void initMesh()
    {
        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        ibo = glGenBuffers();

        IntBuffer indicesBuffer = BufferUtils.createIntBuffer(indices.length);
        indicesBuffer.put(indices);
        indicesBuffer.flip();

        FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertices.length);
        verticesBuffer.put(vertices);
        verticesBuffer.flip();

        glBindVertexArray(vao);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * 4, 0);
        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        getGLError("initMesh");
    }

    private void createMesh()
    {
        vertices = new float[]{
                -1f, 1f, 0.0f,
                -1f, -1f, 0.0f,
                1f, -1f, 0.0f,
                1f, 1f, 0.0f    };

        indices = new int[]{
                0, 1, 2,
                2, 3, 0};
    }

    private void getGLError(String errorMessage)
    {
        int errorValue = glGetError();
        if (errorValue != GL_NO_ERROR)
        {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println(errorMessage + ": " + errorString);
        }
    }

    public double getZoom() {
        return zoom;
    }

    public int getIteration() {
        return iteration;
    }
    public void setIteration(int iteration)
    {
        this.iteration = iteration;
    }

    public double getxOffset() {
        return xOffset;
    }
    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public double getyOffset() {
        return yOffset;
    }
    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}