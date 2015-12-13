package me.engine.entity;

import java.util.Random;

import me.engine.entity.EntityLiving;
import me.engine.location.Location;
import me.engine.main.MainClass;
import me.engine.render.Render2D;
import me.game.main.StartClass;

import org.lwjgl.opengl.GL11;

public class EntityText extends EntityLiving {

	String text;
	public EntityText(MainClass m,float x, String t) {
		super(m, x, StartClass.HEIGHT - 2.5f, 1f, 1f);
		text=t;
	}
	public String getText(){
		return text;
	}

	public String getName() {
		return "Text";
	}

	public String getTextureName(int i) {
		return null;
	}

	public void playAnimation(int ind, int f, int s) {
	}

	public void playAnimation(String name, int f, int s) {
	}

	public float getSpeed() {
		return 0.015f;
	}

	public void addRender() {
	}

	public void render(MainClass m) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f + this.getX(), 0.5f + this.getZ(), 0f);
		Render2D.renderString(m, getText());
		GL11.glPopMatrix();
	}

	public String getDialogText(Random random) {
		return "I'm a Text";
	}

	public void render(MainClass m, Location looking) {
		GL11.glPushMatrix();
		GL11.glTranslatef(0.5f + looking.getX(), 0.5f + looking.getZ(), 0f);
		Render2D.renderString(m, getText());
		GL11.glPopMatrix();
	}



	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;

		if (nextlocation == null || times > 100){
			nextlocation = new Location(0f-getText().length(),StartClass.HEIGHT);
			times=0;
		}
		if (Location.getDistance(new Location(getLocation().x,StartClass.HEIGHT), nextlocation) < 1f) {
				main.getWorld().removeEntity(this);
				nextlocation = location.clone();
		}
	}

}
