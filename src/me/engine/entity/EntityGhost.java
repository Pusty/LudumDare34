package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;


import java.util.Random;

import me.engine.entity.EntityLiving;
import me.engine.location.Location;
import me.engine.main.MainClass;
import me.game.main.StartClass;

import org.lwjgl.opengl.GL11;

public class EntityGhost extends EntityLiving {

	public EntityGhost(MainClass m,float x, float y) {
		super(m ,x, y,1f,1f);
	}
	
	
	public  String getName(){return "Ghost";}
	
	public String getTextureName(int i){
		return "shoe";
	}
	
	
	
	public float getSpeed(){return 0.025f;}

	public void addRender(){
		if(getAnimation().currentanimation != 0 || moving)
			getAnimation().getCurA().addTick();
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

	public String getDialogText(Random random) {
	return "HUIII";
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



	int nextLocationEntity=-1;
	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		

		if (nextlocation == null || times > 100){
			Location location=new Location(main.getWorld().getPlayer().getX(),StartClass.HEIGHT);
			float dis=Float.MAX_VALUE;
			for(int i=0;i<main.getWorld().getEntityArray().length;i++){
				Entity e = main.getWorld().getEntityArray()[i];
				if(e==null)continue;
				if(!(e instanceof EntityBuilding))continue;
				EntityBuilding bu = (EntityBuilding)e;
				if(bu.getBuild()==1 &&  Location.getDistance(new Location(getLocation().x,StartClass.HEIGHT), e.getLocation()) < dis){nextLocationEntity=i;location=e.getLocation();dis=(float) Location.getDistance(new Location(getLocation().x,StartClass.HEIGHT), e.getLocation());}
			}
			nextlocation = location.clone();
			times=0;
		}
		if (Location.getDistance(new Location(getLocation().x,StartClass.HEIGHT), nextlocation) < 1f) {
			if(nextLocationEntity>=0) {
//				main.getWorld().removeEntity(this);
				if(main.getWorld().getEntityArray()[nextLocationEntity] instanceof EntityBuilding){
					if(((EntityBuilding) main.getWorld().getEntityArray()[nextLocationEntity]).getBuild()==1)
					((EntityBuilding) main.getWorld().getEntityArray()[nextLocationEntity]).setBuild(2);
				}
			}
			nextlocation = location.clone();

		}
	}

	
	

}


