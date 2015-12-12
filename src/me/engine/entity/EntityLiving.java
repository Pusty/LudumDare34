package me.engine.entity;


import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;

import java.util.HashMap;
import java.util.Random;

import org.lwjgl.opengl.GL11;


import me.engine.location.Location;
import me.engine.location.Velocity;
import me.engine.skill.Skill;
import me.engine.entity.Entity;
import me.engine.main.MainClass;

public class EntityLiving extends Entity {

	Location p1;
	boolean hasseenplayer;
	public boolean seesplayer;
	protected int emoticon;
	Location loc2;
	Velocity velocity;
	int coints;
	int health;
	boolean onGround=false;
	boolean onWall=false;
	MainClass main;
	Skill[] skills;
	public EntityLiving(MainClass m,float x, float y, float sx, float sy) {
		super(x, y, sx, sy);
		main=m;
		hasseenplayer = false;
		seesplayer = false;
		p1 = new Location(0, 0);
		loc2 = new Location(0, 0);
		emoticon = 0;
		velocity = new Velocity(0, 0);
		coints = 0;
		health = getStartHealth();
		skills=new Skill[2];
	}
	
	public boolean  onWall(){return onWall;}
	public boolean onGround(){return onGround;}
	public void setWall(boolean b){onWall=b;}
	public void setGround(boolean b){onGround=b;}
	public void setSkill(int id,Skill s){skills[id]=s;}
	public Skill getSkill(int id){return skills[id];}
	
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int h) {
		health = h;
	}

	public boolean isDead() {	
		return health < 1;
	}

	public int getStartHealth() {
		return 10;
	}

	public int getCoints() {
		return coints;
	}

	public void setCoints(int c) {
		coints = c;
	}


	public Velocity getV() {
		return velocity;
	}

	public Location getLocation2() {
		return loc2;
	}

	public int getEmoticon() {
		return emoticon;
	}

	public void setEmoticon(int e) {
		emoticon = e;
	}


	protected float seedistance = 10f;

	public Location point1() {
		return p1;
	}
	int seetick=0;

	public boolean hasSeenPlayer() {
		return hasseenplayer;
	}


	public void move(MainClass mainclass) {
	}

	public Velocity getMoveDirection(){
		return new Velocity(0,0);
	}
	
	protected boolean side=false;
	public boolean moving=false;
	public float getSpeed(){return 0.025f;}

	
	
	public void setSightVelocity(Velocity v){
		velocity=v;
	}

	public boolean getSide() {
		return side;
	}

	public void setSide(boolean b) {
		side = b;
	}
	
	   int allreadystepped;
	   int goalID=-1;
	   protected int lastID=-1;
	 
	   
	   int itemID = 0;
	   public int getItemID(){return itemID;}
	   public void setItemID(int i){itemID=i;}
	

	   Location nextlocation=null;

	   Location nextloc;
	   Random random = new Random();
		int times = 0;

		public Location getNextLocation() {
			if(nextlocation==null)this.recalcNextLocation(true);
			return nextlocation;
		}

		public void render(MainClass m){
			GL11.glPushMatrix();
			GL11.glTranslatef(location.x+0.5f, location.z+0.5f, 0);
			if(!side)
				GL11.glRotatef(180, 0, 1f, 0f);
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

	public String getRenderID(){
		if(getAnimation() != null)
			return getAnimation().getCur();
		return "null";
	}
	
	HashMap<Integer,Integer> status = new HashMap<Integer,Integer>();

	public void addStatus(String effect,int time,boolean stack) {
		try{
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
	int currently=0;
		if(status.containsKey(effectindex)){
			currently=status.get(effectindex);
		}
		if(stack)
		status.put(effectindex, currently+time);
		else if(!stack && currently == 0)
			status.put(effectindex, currently+time);
		}catch(Exception e){
			System.out.println("Effect does not exist: "+effect);
		}
	}
	
	public void removeEffect(String effect) {
		try{
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
		if(status.containsKey(effectindex)){
			status.remove(effectindex);
		}
		}catch(Exception e){
			System.out.println("Effect does not exist: "+effect);
		}
	}
	
	public int hasEffect(String effect){
		try{
			if(effect == null)return -1;
		int effectindex=Status.valueOf(Status.class, effect).ordinal();
		int currently=0;
		if(status.containsKey(effectindex)){
		currently=status.get(effectindex);
		}else return -1;
			return currently;
		}catch(Exception e){
			return -1;
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public void tickStatus(){
		try{
		HashMap<Integer,Integer> cloneMap = (HashMap<Integer, Integer>) status.clone();
		Object[] array = cloneMap.keySet().toArray().clone();
		if(array.length>0)
	for(int i=0;i<array.length;i++){
			if(!status.containsKey(array[i]))continue;
			int value = status.get(array[i])-1;
			int effect = (Integer) array[i];
			status.put(effect,value);
			if(value<0)status.remove(effect);
		}
		}catch(Exception e){}
	}
	//if i is positive then b == overheal if i is negative then b == canKill
	public int damage(int i,boolean b) {
		int start = getHealth();
		if(getHealth()<=0)return 0;
		if(i>0 && this.hasEffect("DMG")>=0)return start;
		if(i>0 && b){
			if(getHealth()-i>0)setHealth(this.getHealth()-i);
			else setHealth(0);
		}else if(i>0 && !b){
			if(getHealth()-i>0)setHealth(this.getHealth()-i);
			else setHealth(1);
		}else if(i<0 && b)setHealth(this.getHealth()-i);
		else if(i<0 && !b){
			if(getHealth()-i>this.getStartHealth())setHealth(getStartHealth());
			else setHealth(getHealth()-i);
		}
		if(start>getHealth())this.addStatus("DMG",Status.DAMAGE_COOLDOWN,false);
		
		return getHealth();
	}


	public void jump(MainClass m){
		 if(onGround())
				addStatus("JUMP",100,false);
			 else if(onWall() &&  getWallJumps() < maxWallJumps() && hasEffect("JUMP") < 0){
					if(!getSide()){
				addStatus("WALLJUMP_R",100,false);
				 addWallJump();
					}else if(getSide()){
				addStatus("WALLJUMP_L",100,false);
				addWallJump();
					}
			 }
	}
	
	public boolean left=false;
	public boolean right=false;
	int walljumpcount=0;
	public int getWallJumps(){return walljumpcount;}
	public void addWallJump(){walljumpcount=walljumpcount+1;}
	public int maxWallJumps(){
		return 0;
	}
	public boolean left(MainClass m) {
		left=true;
		return true;
	}
	
	public boolean right(MainClass m) {
		right=true;
		return true;
	}
	

	int intick=0;
	public void tick(MainClass m){
	//TICK Entity SKILLS AND SO ON	
	if(getHealth()>0){
		if(hasEffect("SKILL1")>=9999){
			this.removeEffect("SKILL1");
			Skill skill = this.getSkill(0);
			skill.startSkill(this, m,"SKILL1");
			this.addStatus("SKILL1", skill.getCooldown(), false);
		}else if(hasEffect("SKILL1")>0)
		{
			Skill skill = this.getSkill(0);
			skill.tick(this,m);
		}else if(hasEffect("SKILL1")==0){
			Skill skill = this.getSkill(0);
			skill.end(this,m);
		}
		
		if(hasEffect("ATTACK")>9000 && this.hasEffect("COOLDOWN")  < 20){
			this.addStatus("COOLDOWN", 130, true);
			this.removeEffect("ATTACK");
//			this.playAnimation("attack", -1, 120);
		}else if(hasEffect("ATTACK")>9000)
			this.removeEffect("ATTACK");
		
		if(hasEffect("DMG")==Status.DAMAGE_COOLDOWN-1){
//			playAnimation("death",3, Status.DAMAGE_COOLDOWN);
			addStatus("UNMOVE",Status.DAMAGE_COOLDOWN/4,false);
		}
	}
		if(getHealth()==0 && hasEffect("UNMOVE")==-1){
			addStatus("UNMOVE",100,true);
//			playAnimation("death",-1,-1);
			this.setHealth(-1);
		}
		if(getHealth()==-1 && hasEffect("UNMOVE")==-1)
			this.setHealth(-2);
		 if(getHealth()==-2){
			addStatus("UNMOVE",1,true);
//			playAnimation("death",3, 10);
		}
		 
		 
		if(hasEffect("UNMOVE") == -1 && !(onGround() && this.hasEffect("COOLDOWN")>=0)){
			if(right)
				Moveright(m);
			if(left)
				Moveleft(m);
			if((left || right)){
				if(!moving)
					playAnimation("walk",3,-1);
				moving=true;
			}
			else {
				if(moving)
					playAnimation("stand",-1,-1);
				moving=false;
			}
		
		}else {
			if(moving)
				playAnimation("stand",-1,-1);
			moving=false;
		}
		tickStatus();
	}
	public boolean Moveleft(MainClass m) {
		this.getVelocity().add(new Velocity(-1,0).multiplz(getSpeed()));
		side=false;
		return true;
	}
	
	public boolean Moveright(MainClass m) {
		this.getVelocity().add(new Velocity(1,0).multiplz(getSpeed()));
		side=true;
		return true;
	}
	


	int timer=0;
	public void recalcNextLocation(boolean b) {
		times++;
		timer++;
		if(timer<=40 && !b)return;
		timer=0;
		if (nextlocation == null || times > 100){
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();
			times=0;
		}
		if (Location.getDistance(this.getLocation(), nextlocation) < 0.5f) {
			nextlocation = main.getWorld().getPlayer().getLocation()
					.add(new Location(random.nextFloat(), random.nextFloat()))
					.clone();

		}
	}

	
	
}
