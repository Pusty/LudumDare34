package me.engine.entity;

import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.engine.entity.EntityLiving;
import me.engine.location.Location;
import me.engine.main.MainClass;
import me.game.main.StartClass;

import org.lwjgl.opengl.GL11;

public class EntitySoul extends EntityLiving {

	int type = 0;
	boolean fromPlant=false;
	public EntitySoul(MainClass m, float x,boolean fp) {
		super(m, x, StartClass.HEIGHT, 1f, 1f);
		Random r = new Random();
		type = r.nextInt(8);
		fromPlant=fp;
	}
	public boolean isPlant(){
		return fromPlant;
	}

	public String getName() {
		return "Soul";
	}

	public String getTextureName(int i) {
		return null;
	}

	public void playAnimation(int ind, int f, int s) {
	}

	public void playAnimation(String name, int f, int s) {
	}

	public float getSpeed() {
		return 0.025f;
	}

	public void addRender() {
	}

	public void render(MainClass m) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f + this.getX(), 0.5f + this.getZ(), 0f);
		if (side)
			GL11.glRotatef(180, 0, 1f, 0f);
		glBindTexture(GL_TEXTURE_2D,
				m.getPictureLoader().getImageAsInteger("energy"));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f, 0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	public String getDialogText(Random random) {
		return "HUIII";
	}

	public void render(MainClass m, Location looking) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f + looking.getX(), 0.5f + looking.getZ(), 0f);
		if (side)
			GL11.glRotatef(180, 0, 1f, 0f);
		glBindTexture(GL_TEXTURE_2D,
				m.getPictureLoader().getImageAsInteger("soul_" + type));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-0.5f, 0.5f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(0.5f, 0.5f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(0.5f, -0.5f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-0.5f, -0.5f);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	int nextLocationEntity = -1;
	boolean destroy=false;
	boolean through=false;

	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		
		
		if(!destroy && through){
			if(Location.getDistance(new Location(getLocation().x,	StartClass.HEIGHT), main.getWorld().getPlayer().getLocation()) < 0.25f){
				main.getWorld().removeEntity(this);main.getWorld().getPlayer().setEnergy(main.getWorld().getPlayer().getEnergy()+1);times=0;destroy=true;return;
			}
		}
		if (timer <= 40 && !b)
			return;
		
		timer = 0;
		nextlocation = this.getLocation();
		

	
		if(destroy)return;

		if (times > 100) {
			times=0;
			through=true;
			if(isPlant())return;

			float dis = Float.MAX_VALUE;
			List<Integer> souls=new ArrayList<Integer>();
			for (int i = 0; i < main.getWorld().getEntityArray().length; i++) {
				Entity e = main.getWorld().getEntityArray()[i];
				if (e == null)
					continue;
				if(e instanceof EntitySoul && Location.getDistance(new Location(getLocation().x, StartClass.HEIGHT), e.getLocation()) < 1f)
					souls.add(i);
				if (!(e instanceof EntityBuilding))
					continue;
				float ndis = (float) Location.getDistance(new Location(getLocation().x,	StartClass.HEIGHT), e.getLocation());
				if (ndis < dis) {
					nextLocationEntity = i;
					dis = ndis;
				}
			}
			if(dis < 1.0f) {
				if(main.getWorld().getEntityArray()[nextLocationEntity] instanceof EntityBuilding){
					EntityBuilding bu = (EntityBuilding)main.getWorld().getEntityArray()[nextLocationEntity];
					if(bu.getBuild()==0){
						main.getWorld().removeEntity(this);times=0;destroy=true;
							if(bu.getType()!=1)	
								bu.setBuild(1);
							else if(bu.getType()==1)
								bu.setBuild(2);
					}else {
						if(bu.getBuild()==2 || bu.getType() == 1){
							if(bu.getType()==0 && bu.getKind()+1 < 4 && souls.size()>=4){
								int cur=0;
								for(int i:souls){
									if(cur>=4)break;
									main.getWorld().getEntityArray()[i]=null;
									cur++;
								}
								bu.setKind(bu.getKind()+1);
								bu.setBuild(1);
							}else if(bu.getType()==1 && bu.getKind()+1 < 4 && souls.size()>=(bu.getKind()+1)*(bu.getKind()+1)){
								int cur=0;
								for(int i:souls){
									if(cur>=(bu.getKind()+1)*(bu.getKind()+1))break;
									main.getWorld().getEntityArray()[i]=null;
									cur++;
								}
								bu.setKind(bu.getKind()+1);
								bu.setBuild(1);
							}
						}
					}
				
				

					
				}
			}
			times = 0;
		}

	}

}
