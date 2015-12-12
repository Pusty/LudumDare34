package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.Random;

import me.engine.main.MainClass;
import me.engine.render.Render2D;

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
		return "stone";
	}
	Random random = new Random();
	private float pmR(){
		return random.nextFloat()-0.5f;
	}
	private int canDrop=0;
	private boolean canUpgrade=true;
	public void buildingTick(MainClass m){
		if(this.getBuild()==0)return;
		if(getType()==1)return;
		if(getType()==0){
			if(getBuild()==1 && Render2D.dayStatus()==-1 && canUpgrade==true){
				setBuild(2);
				m.addScore(25);			
				canUpgrade=false;
			}else if(Render2D.dayStatus()==-1)
				canDrop=this.getKind()+1;
			if(Render2D.dayStatus()==1 && canDrop>0) {
				m.getWorld().addEntity(new EntitySoul(m, this.getX()+pmR(),true));
				m.addScore(10);
				canDrop--;
			}else if(Render2D.dayStatus()==1)
				canUpgrade=true;
		}
	}
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f+this.getX(), 0.5f+this.getZ(), 0f);
		String name="";
		if(type==0)
			name = "plant_";
		else if(type==1)
		name = "wall_";
		if(build==0)
			name = "stone";
		
		
		if(build!=0)
		name = name + kind;
		
		//DEBUG
		
		
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
		
		GL11.glPopMatrix();
	}

	public int getKind() {
		return kind;
	}
	public void setKind(int in) {
		kind = in;
	}
}
