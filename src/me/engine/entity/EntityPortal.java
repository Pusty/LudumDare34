package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;

import me.engine.main.MainClass;


public class EntityPortal extends Entity{
	MainClass main;
	int portal=0;
	boolean from=false;
	boolean visable=true;
	public EntityPortal(MainClass m,float x, float y,boolean f,int pi,boolean vi) {
		super(x, y, 1f,1f);
		main=m;
		from=f;
		portal=pi;
		visable=vi;
	}
	

	public boolean isFrom(){return from;}
	public int getPortal(){return portal;}

	public String getTextureName(int i){
		return null;
	}
	
	
	public void render(MainClass m){
			if(from)return;
		GL11.glPushMatrix();
		GL11.glTranslatef(location.x+0.5f, location.z+0.5f, 0);
		glBindTexture(GL_TEXTURE_2D,
		m.getPictureLoader().getImageAsInteger("tile_57"));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		glEnd(); 
		GL11.glPopMatrix();
	}

}
