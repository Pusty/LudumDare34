package me.engine.entity;


import java.util.Random;

import me.engine.main.MainClass;

public class EntityCrow extends EntityMonster{

	public EntityCrow(MainClass m,boolean side) {
		super(m ,"crow",side);
	}
	
	
	public  String getName(){return "Ghost";}
	
	public String getTextureName(int i){
		return "ghost";
	}
	
	
	
	public float getSpeed(){return 0.025f;}

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
			if(bu.getBuild()!=2)return;
			if(bu.getType()==1){
				if(bu.getKind()-1 < 0)
					bu.setBuild(0);
				else
					bu.setKind(bu.getKind()-1);
				
				mainclass.getWorld().removeEntity(this);
			}
				
		}
	}


}
