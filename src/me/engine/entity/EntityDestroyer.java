package me.engine.entity;


import java.util.Random;

import me.engine.main.MainClass;

public class EntityDestroyer extends EntityMonster{

	public EntityDestroyer(MainClass m,boolean side) {
		super(m ,"destroyer",side);
	}
	
	
	public  String getName(){return "Destroyer";}
	
	public String getTextureName(int i){
		return "destroyer";
	}
	
	
	
	public float getSpeed(){return 0.005f;}

	public void addRender(){
		if(getAnimation().currentanimation != 0 || moving)
			getAnimation().getCurA().addTick();
	}


	public String getDialogText(Random random) {
	return "TUUUT TUUT!";
	}

	@Override
	public void cWE(MainClass mainclass, Entity e) {
		if(e instanceof EntityBuilding){
			EntityBuilding bu = (EntityBuilding)e;
			if(bu.getBuild()==0)return;
			if(bu.getType()==1){
				if(bu.getKind()==3){
					mainclass.getWorld().removeEntity(this);
					bu.setBuild(0);
					bu.setKind(0);
					mainclass.getSoundPlayer().playSound("walldeath", true);
				} else {
					bu.setBuild(0);
					bu.setKind(0);
					mainclass.getSoundPlayer().playSound("walldeath", true);
					}
				
			}else {
				if(bu.getType()==4){
					if(bu.getType()==4 && bu.getKind()==3) {
						mainclass.getWorld().removeEntity(this);
						bu.setBuild(0);
						bu.setKind(0);
						mainclass.getSoundPlayer().playSound("bomb", true);
					}
				} else	{
					bu.setBuild(0);
					bu.setKind(0);
					mainclass.getSoundPlayer().playSound("plantdeath", true);
				}
			
			}
				
		}else if(e instanceof Player){
			Player p = (Player)e;
			int energy = p.getEnergy();
			if(energy>0){
				Random random = new Random();
				float r = 0f;
				for(int i=0;i<energy;i++){
					r= 1f+(random.nextFloat()-0.5f);
					r= random.nextBoolean()?-r:r;
					mainclass.getWorld().addEntity(new EntityEnergy(mainclass,p.getLocation().getX()+r,true));
				}
				p.setEnergy(0);
				mainclass.getSoundPlayer().playSound("coindrop", true);
			}
		}
	}


}
