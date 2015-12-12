package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.location.TempVelocity;
import me.engine.location.Velocity;
import me.engine.main.MainClass;
import me.engine.render.AnimationHandler;

public class Entity{
	Location location;
	float sizex;
	float sizez;
	Velocity velocity;
	TempVelocity tempvelocity;
	int index;
	AnimationHandler privathandler;
	public Entity(float x,float z,float sx,float sz){
		index=(new Random()).nextInt(1000000);
		location=new Location(x,z);
		this.sizex=sx;
		this.sizez=sz;
		this.velocity=new Velocity(0,0);
		tempvelocity=null;
		if(getTextureName(-1)!=null)
		privathandler=AnimationHandler.getHandler(getTextureName(-1)).copy();
//		System.out.println(privathandler);
	}
	public AnimationHandler getAnimation(){
		return privathandler;
	}
	
	public String getName(){return this.getClass().getSimpleName();}

	
	public int getIndex(){return index;}
	public Velocity getVelocity(){
		return velocity;
	}
	public TempVelocity getTempVelocity(){
		return tempvelocity;
	}
	public void setTempVelocity(TempVelocity tv){
		tempvelocity=tv;
	}

	
	public float getSizeX(){
		return sizex;
	}
	public float getSizeZ(){
		return sizez;
	}
	public Location getLocation(){
		return location;
	}
	public float getX(){
		return location.getX();
	}
	public float getZ(){
		return location.getZ();
	}

	
	public String getTextureName(int i){
		return "player";
	}
	


	
	public void addRender(){
		getAnimation().getCurA().addTick();
	}
	
	public void playAnimation(int ind,int f,int s){
		getAnimation().playAnimation(ind,f,s);
	}
	public void playAnimation(String name,int f,int s){
		getAnimation().playAnimation(name,f,s);
	}
	public String getRenderID(){
		if(getAnimation() != null)
			return getAnimation().getCur();
		return "null";
	}
	
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(location.x+0.5f, location.z+0.5f, 0);
		glBindTexture(GL_TEXTURE_2D,
		m.getPictureLoader().getImageAsInteger(getRenderID()));
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

	
	public Entity copy(){
		Entity e = new Entity(this.getLocation().getX(),this.getLocation().getZ(),this.getSizeX(),this.getSizeZ());
		return e;
	}


}
