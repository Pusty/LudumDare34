package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import me.engine.location.Location;
import me.engine.main.MainClass;
import me.game.main.StartClass;


public class EntityMonster extends EntityLiving {
	protected String playerName;
	boolean playerSide;
	public EntityMonster(MainClass m, String name,boolean pside) {
		super(m ,pside?1:m.getWorld().getSizeX()-1f, StartClass.HEIGHT,1f,1f);
		playerName = name;
		playerSide=pside;
		side=pside;
	}
	

	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		

		if (nextlocation == null || times > 100){
			nextlocation = new Location(!getPlayerSide()?1f:main.getWorld().getSizeX()-1f,StartClass.HEIGHT);
			times=0;
		}
		if (Location.getDistance(new Location(getLocation().x,StartClass.HEIGHT), nextlocation) < 1f) {
				main.getWorld().removeEntity(this);
				nextlocation = location.clone();
		}
	}
	public boolean getPlayerSide() {
		return playerSide;
	}

	public String getDialogText(Random r){
		return "DIALOG HERE";
	}
	
	public void render(MainClass m){
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f+this.getX(), 0.5f+this.getZ(), 0f);
		if(!side)
			GL11.glRotatef(180, 0, 1f, 0f);
		String renderID = getRenderID();
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger(renderID));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	

	public void render(MainClass m, Location looking) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f+looking.getX(), 0.5f+looking.getZ(), 0f);
		if(!side)
			GL11.glRotatef(180, 0, 1f, 0f);
		String renderID = getRenderID();
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger(renderID));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f,0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f,0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f,-0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f,-0.5f);	
		GL11.glEnd();
		GL11.glPopMatrix();
	}


	
	public float getSpeed(){return 0.01f;}

	public  String getName(){return "EntityMonster";}

	//Collide With Entity
	public void cWE(MainClass mainclass, Entity e) {
		
	}



//	public void render(MainClass m){
//		super.render(m);
//}
}
