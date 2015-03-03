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

    private boolean highRes = false;

    private double xOffset, yOffset, zoom;
    private int iteration, zoomStep;
    private boolean textureBool = false;

    private int vao, vbo, ibo;

    private float[] vertices;
    private int[] indices;



    private Shader lowResShader;
    private Shader highResShader;

    public Fractal()
    {
        createShaders();
        createMesh();
        initMesh();
    }

    public void input() {
        if (Input.getKey(Input.KEY_A))
            xOffset -= XMOVE / zoom;
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
            if (zoomStep > 0 && zoom > 200) {
                zoomStep--;
                zoom -= 3 * (zoomStep * zoom) / ZOOMDIV;
            }
            else
                zoom = 200;
        }

        if (Input.getKey(Input.KEY_I))
            iteration++;
        if (Input.getKey(Input.KEY_O))
            if(iteration > 3)
                iteration--;
    }

    public void update()
    {
        updateUniforms();

        if(highRes)
            highResShader.bind();
        else
            lowResShader.bind();
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
        highResShader.setUniformd("xOffset", xOffset);
        highResShader.setUniformd("yOffset", yOffset);
        highResShader.setUniformd("zoom", zoom);
        highResShader.setUniformi("iteration", iteration);
        highResShader.setUniformi("textureBool", textureBool ? 1 : 0);

        lowResShader.setUniformf("xOffset", (float) xOffset);
        lowResShader.setUniformf("yOffset", (float) yOffset);
        lowResShader.setUniformf("zoom", (float) zoom);
        lowResShader.setUniformi("iteration", iteration);
        lowResShader.setUniformi("textureBool", textureBool ? 1 : 0);
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
                1f, 1f, 0.0f};

        indices = new int[]{
                0, 1, 2,
                2, 3, 0};
    }

    private void createShaders()
    {
        xOffset = 0.0f;
        yOffset = 0.0f;
        zoom = 200.0f;
        iteration = 255;
        zoomStep = 1;

        new Texture("pal1");

        lowResShader = new Shader("lowResFractal");
        lowResShader.setUniformf("xOffset", (float) xOffset);
        lowResShader.setUniformf("yOffset", (float) yOffset);
        lowResShader.setUniformf("zoom", (float) zoom);
        lowResShader.setUniformi("iteration", iteration);
        lowResShader.setUniformi("textureBool", 0);
        lowResShader.setUniformi("height", GLWindow.getWidth());
        lowResShader.setUniformi("width", GLWindow.getHeight());

        highResShader = new Shader("highResFractal");
        highResShader.setUniformd("xOffset", xOffset);
        highResShader.setUniformd("yOffset", yOffset);
        highResShader.setUniformd("zoom", zoom);
        highResShader.setUniformi("iteration", iteration);
        highResShader.setUniformi("textureBool", 0);
        highResShader.setUniformi("height", GLWindow.getWidth());
        highResShader.setUniformi("width", GLWindow.getHeight());
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

    public boolean getHighRes()
    {
        return highRes;
    }
    public void setHighRes(boolean highRes)
    {
        this.highRes = highRes;
    }

    public boolean getTextureBool()
    {
        return textureBool;
    }
    public void setTextureBool(boolean textureBool)
    {
        this.textureBool = textureBool;
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