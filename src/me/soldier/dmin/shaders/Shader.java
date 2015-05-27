package me.soldier.dmin.shaders;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.*;

import me.soldier.dmin.math.*;

/**
 * @author Osoldier
 * @Project OSEngine
 * @since 7 oct. 2014
 */
public abstract class Shader {


	private int programID, vertexShaderID, fragmentShaderID;
	
	public Shader(String vertexFile, String fragmentFile) {
		vertexShaderID = loadShader(vertexFile, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFile, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();
		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);
		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
	}
	
	protected abstract void bindAttributes();
	public abstract void loadUniforms();
	
	protected void bindAttribute(int attr, String varName) {
		glBindAttribLocation(programID, attr, varName);
	}
	
	public void start() {
		glUseProgram(programID);
	}
	
	public void stop() {
		glUseProgram(0);
	}
	
	public int getAttributeLocation(String pName) {
		start();
		return glGetAttribLocation(programID, pName);
	}

	public void setUniform(String pName, float value) {
		start();
		glUniform1f(glGetUniformLocation(programID, pName), value);
	}

	public void setUniform(String pName, Vector3f value) {
		start();
		glUniform3f(glGetUniformLocation(programID, pName), value.x, value.y, value.z);
	}

	public void setUniform(String pName, int value) {
		start();
		glUniform1i(glGetUniformLocation(programID, pName), value);
	}

	public void setUniformMat4f(String pName, Matrix4f matrix) {
		start();
		glUniformMatrix4(glGetUniformLocation(programID, pName), false, matrix.toFloatBuffer());
	}
	
	public void cleanUp() {
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}
	
	private static int loadShader(String file, int type) {
		StringBuilder shaderSrc = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null) {
				shaderSrc.append(line).append("\n");
			}
			reader.close();
		} catch(IOException e) {
			System.err.println("Couldn't read file!");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSrc);
		glCompileShader(shaderID);
		if(glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shaderID));
			System.err.println("Couldn't compile shader");
			System.exit(-1);
		}
		return shaderID;
	}

	
}
