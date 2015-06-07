package me.soldier.dmin.components;

import me.soldier.dmin.math.*;
import me.soldier.dmin.models.*;
import static org.lwjgl.opengl.GL11.*;

public class GLButton extends GLComponent {

	private Texture tex;
	
	public GLButton(Vector2f position, Vector2f size, String imgPath) {
		this.position = position;
		this.size = size;
		this.tex = new Texture(imgPath);
	}
	
	@Override
	public void Render() {
		tex.bind();
		glBegin(GL_QUADS);
		glVertex2f(this.position.x, this.position.y);
		glVertex2f(this.position.x+this.size.x, this.position.y);
		glVertex2f(this.position.x+this.size.x, this.position.y+this.size.y);
		glVertex2f(this.position.x, this.position.y+this.size.y);
		glEnd();
	}

}
