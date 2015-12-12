package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;


import java.util.Random;

import me.engine.entity.EntityLiving;
import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.main.Controls;
import me.engine.main.MainClass;
import me.engine.skill.*;

import org.lwjgl.opengl.GL11;

public class Player extends EntityLiving {

	Location mousevelocity;
	public Player(MainClass m,float x, float y) {
		super(m ,x, y,1f,1f);
//		this.setMainItem(new Item(30));
//		this.setUtilItem(new Item(31));
		
		setSkill(0,new SkillFireball());
	}
	
	public void setSkill(int id,Skill s){
		skills[id]=s;
	}

	
	public  String getName(){return "Player";}
	
	public Location getMouseLocation(){
		return mousevelocity;
	}
	
	public void setMouseLocation(Location v){
		mousevelocity=v;
	}
	@Override
	public Velocity getMoveDirection(){
		if(getMouseLocation()!=null)
		return Location.getNorm(getMouseLocation()).toVelocity();
		return new Velocity(0,0);
	}
	@Override
	public void setHealth(int h) {
		super.setHealth(h);
		main.getSavedData().putData("health", h);
	}

	Random random = new Random();
	public void tick(MainClass m){
		
	//TICK PLAYER SKILLS AND SO ON	
	super.tick(m);
	if(Controls.ispressedDebug){
		damage(1000,true);
		Controls.ispressedDebug=false;
	}
	}


	public String getTextureName(int i){
		return "player";
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

	public String getDialogText(Random random) {
	int index = random.nextInt(3);
	if(index==0)return null;
	if(index==1)return "Maybe some cheese is nearby?";
	if(index==2)return "I am going to collect all the cheese in this world!";
		return null;
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

	public boolean down = false;
	public void down(MainClass mainclass) {
		if(!down){
			mainclass.getWorld().addEntity(new EntitySoul(mainclass,this.getX(),this.getZ()));
			down=true;
		}
	}




	

}


