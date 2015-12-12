package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import me.engine.main.MainClass;

import org.lwjgl.opengl.GL11;

public class EntityBuilding extends Entity{
	MainClass main;
	float size=4f;
	int type=0;
	int build=0;
	int kind=0;
	public EntityBuilding(MainClass m,float x, float y,int type,int build) {
		super(x, y, 1f,1f);
		main=m;
		this.type=type;
		this.build=build;
	}	

	public int getType(){return type;}
	public int getBuild(){return build;}
	public void setBuild(int b){build=b;}
	public void setType(int t){type=t;}
	
	public void addRender(){
	}
	
	public void playAnimation(int ind,int f,int s){
	}
	public void playAnimation(String name,int f,int s){
	}
	public String getRenderID(){
		return "stone_0";
	}
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f+this.getX(), 0.5f+this.getZ(), 0f);
		String name="";
		if(type==0)
			name = "fox_statue_";
		else if(type==1)
		name = "wall_";
		else
			name = "lamp";
		if(build==0)
			name = "stone_";

		name = name + kind;
		
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger(name));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,1.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,1.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		GL11.glEnd();
		
		
		if(this.getBuild()==1){
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger("build"));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,1.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,1.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		GL11.glEnd();
		}
		GL11.glPopMatrix();
	}

	public int getKind() {
		return kind;
	}
	public void setKind(int in) {
		kind = in;
	}
}
