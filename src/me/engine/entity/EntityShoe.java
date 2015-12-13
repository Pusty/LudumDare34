package me.engine.entity;


import java.util.Random;

import me.engine.main.MainClass;

public class EntityShoe extends EntityMonster{

	public EntityShoe(MainClass m,boolean side) {
		super(m ,"shoe",side);
	}
	
	
	public  String getName(){return "Shoe";}
	
	public String getTextureName(int i){
		return "shoe";
	}
	
	
	
	public float getSpeed(){return 0.015f;}

	public void addRender(){
		if(getAnimation().currentanimation != 0 || moving)
			getAnimation().getCurA().addTick();
	}


	public String getDialogText(Random random) {
	return "HUIII";
	}

	@Override
	public void cWE(MainClass mainclass, Entity e) {
		if(e instanceof EntityBuilding){
			EntityBuilding bu = (EntityBuilding)e;
			if(bu.getBuild()==0)return;
			if(bu.getType()==1){
				if(bu.getKind()<3){
					if(bu.getKind()-1 < 0){
						bu.setBuild(0);
						mainclass.getSoundPlayer().playSound("walldeath", true);
					}else {
						bu.setKind(bu.getKind()-1);
					mainclass.getSoundPlayer().playSound("wallattack", true);
					}
				}else
					mainclass.getSoundPlayer().playSound("enemydeath", true);
				
				mainclass.getWorld().removeEntity(this);
			}else {
				if(bu.getType()!=4){
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
