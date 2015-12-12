package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import org.lwjgl.opengl.GL11;

import me.engine.location.Velocity;
import me.engine.main.MainClass;

import me.engine.skill.Skill;


public class ProjectileFlame extends Projectile{
	public ProjectileFlame(MainClass m,float x, float y,Velocity v,Skill skill) {
		super(m,x,y,v,skill);
	}	
	
	public  String getName(){return "ProjectileFlame";}
	

	public String getTextureName(int i){return null;}
	int lifetime=10;
	public void tick(MainClass m){
		lifetime--;
		if(lifetime<=0)m.getWorld().removeEntity(this);
	}

	
	

	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(location.x+0.5f, location.z+0.5f, 0);
		glBindTexture(GL_TEXTURE_2D,
		m.getPictureLoader().getImageAsInteger("tile_59"));
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
