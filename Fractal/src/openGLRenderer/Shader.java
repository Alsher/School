package openGLRenderer;

import org.lwjgl.opengl.ARBGpuShaderFp64;
import org.lwjgl.util.glu.GLU;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.lwjgl.opengl.GL11.GL_NO_ERROR;
import static org.lwjgl.opengl.GL11.glGetError;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Phil on 24.08.14.
 */
public class Shader {

    private int program;

    public Shader(String fName)
    {
        String fragmentShader = readFile(fName + ".fs");
        String vertexShader = readFile(fName + ".vs");


        addFragmentShader(fragmentShader);
        addVertexShader(vertexShader);

        compileShader();
        bind();
    }

    public void setUniformi(String name, int value)
    {
        int location = glGetUniformLocation(program, name);
        if(location == 0xFFFFFFFF)
            System.err.println("Error: Uniform invalid: " + name);
        glUniform1i(location, value);
    }
    public void setUniformf(String name, float value)
    {
        int location = glGetUniformLocation(program, name);
        if(location == 0xFFFFFFFF)
            System.err.println("Error: Uniform invalid: " + name);
        glUniform1f(location, value);
    }

    public void setUniformd(String name, double value)
    {
        int location = glGetUniformLocation(program, name);
        if(location == 0xFFFFFFFF)
            System.err.println("Error: Uniform invalid: " + name);
        ARBGpuShaderFp64.glUniform1d(location, value);
    }

    public void setSampler2D(String samplerName, int texture)
    {
        int location = glGetUniformLocation(program, samplerName);
        if(location == 0xFFFFFFFF)
            System.err.println("Error: Uniform invalid: " + samplerName);
        glUniform1i(location, texture);
    }

    public void bind()
    {
        glUseProgram(program);
    }

    private void compileShader()
    {
        glLinkProgram(program);
        if(glGetProgrami(program, GL_LINK_STATUS) == 0)
            System.err.println(glGetProgramInfoLog(program, 1024));

        glValidateProgram(program);
        if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
            System.err.println(glGetProgramInfoLog(program, 1024));
    }

    private void addFragmentShader(String shader)
    {
        addProgram(shader, GL_FRAGMENT_SHADER);
    }

    private void addVertexShader(String shader)
    {
        addProgram(shader, GL_VERTEX_SHADER);
    }

    private void addProgram(String text, int type)
    {
        int shader = glCreateShader(type);

        if(shader == 0)
            System.err.println("Error while creating shader");

        if(program == 0)
            program = glCreateProgram();

        glShaderSource(shader, text);
        glCompileShader(shader);

        if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
        {
            System.err.println("Error: " + glGetShaderInfoLog(shader, 1024));
            System.exit(1);
        }

        glAttachShader(program, shader);
    }

    private String readFile(String fileName)
    {
        StringBuilder shaderSource = new StringBuilder();
        BufferedReader shaderReader;
        try
        {
            shaderReader = new BufferedReader(new FileReader("./res/" + fileName));
            String line;

            while((line = shaderReader.readLine()) != null)
                shaderSource.append(line).append("\n");

            shaderReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit(1);
        }
        return shaderSource.toString();
    }

    private void getGLError(String errorMessage)
    {
        int errorValue = glGetError();
        if(errorValue != GL_NO_ERROR)
        {
            String errorString = GLU.gluErrorString(errorValue);
            System.err.println(errorMessage + ": " + errorString);
        }
    }
}