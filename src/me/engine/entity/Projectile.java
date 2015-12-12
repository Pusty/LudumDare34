package me.engine.entity;


import me.engine.location.TempVelocity;
import me.engine.location.Velocity;
import me.engine.main.MainClass;
import me.engine.render.Animation;
import me.engine.skill.Skill;


public class Projectile extends Entity{
	MainClass main;
	Velocity vel;
	Skill skill;
	public Projectile(MainClass m,float x, float y,Velocity v,Skill skill) {
		super(x, y, 1f,1f);
		main=m;
		vel=v;
		this.setTempVelocity(new TempVelocity(v.x,v.z,1000));
		this.skill=skill;
	}	
	public Skill getSkill(){return skill;}
	public void tick(MainClass m){}
	public void hit(MainClass m){}
	
	
	public  String getName(){return "Projectile";}
	
	public void addRender(){
		Animation a = getAnimation().getCurA();
		a.addTick();
		if (a.played && a.ticks == 0)a.ticks =  a.speed*2/4;
	}


}
