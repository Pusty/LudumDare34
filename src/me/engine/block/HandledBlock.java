package me.engine.block;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;


import me.engine.main.MainClass;

public class HandledBlock {
	int id;
	boolean event;
	boolean collisiontick;
	String multiblockname;

	public HandledBlock(int id) {
		this.id = id;
		event = false;
		collisiontick = false;
		multiblockname = null;
	}
	int renderID = -1;


	public void render(MainClass mainclass, int x,int z,int id,int meta) {
		if (id == 0)
			return;
		if (renderID == -1) {
				renderID = mainclass.getPictureLoader().getImageAsInteger(
						"tile_" + this.id);
		}
		glBindTexture(GL_TEXTURE_2D, renderID);
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x + 0f, z + 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + 1f, z + 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + 1f, z + 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x + 0f, z + 0f);
		glEnd();

	}



}
