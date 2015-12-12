package me.engine.main;



import me.engine.entity.Entity;
import me.engine.entity.EntityBuilding;
import me.engine.entity.EntityGhost;
import me.engine.entity.EntityLiving;
import me.engine.entity.EntityMonster;
import me.engine.entity.EntityPortal;
import me.engine.entity.EntitySoul;
import me.engine.entity.Projectile;

import me.engine.location.Location;
import me.game.main.StartClass;

public class GameTickHandler {
	MainClass mainclass;

	public GameTickHandler(MainClass m) {
		mainclass = m;
	}

	public void startGameTick() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mainclass.isRunning()) {
					if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {
						gameTick();
						mainclass.addTimeTick();
					}
					try {
						Thread.sleep((int) (1000 / 200 / mainclass
								.getTimeSpeed()));

					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mainclass.isRunning()) {
					if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {
						gameTickEntity();
					}
					try {
						// Thread.sleep((int)(1000/200/mainclass.getTimeSpeed()));
						Thread.sleep((int) (1000 / 200 / mainclass
								.getTimeSpeed()));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
	}
int ticks=0;
int tickupdate=40;
	public void gameTickEntity() {
		ticks++;
		if(ticks>tickupdate)ticks=0;
		for (int i = 0; i < mainclass.getWorld().getEntityArray().length; i++) {
			if (mainclass.getWorld().getEntityArray()[i] == null)
				continue;
			if (mainclass.getWorld().getEntityArray()[i] instanceof EntityLiving) {
				EntityLiving el = (EntityLiving) mainclass.getWorld()
						.getEntityArray()[i];
				if (el == null)
					continue;
				
				
				el.tick(mainclass);
				movingTickEntity(mainclass, el);
				
				if (el instanceof EntityMonster) {
						((EntityMonster) el).addRender();
				}
				
				el.recalcNextLocation(false);
				if(ticks==tickupdate){
					Location next = el.getNextLocation();
					Location mainl = el.getLocation();
					if (next.x > mainl.x && Math.abs(next.x - mainl.x) > 0.1f)
						el.right(mainclass);
					else
						el.right=false;
					if (next.x <= mainl.x && Math.abs(next.x - mainl.x) > 0.1f)
						el.left(mainclass);
					else
						el.left=false;

				}
			}  else if (mainclass.getWorld().getEntityArray()[i] instanceof EntityBuilding) {
				((EntityBuilding)mainclass.getWorld().getEntityArray()[i]).buildingTick(mainclass);
			} else if (mainclass.getWorld().getEntityArray()[i] instanceof EntityPortal) {
				continue;
				/*
				EntityPortal ep = (EntityPortal) mainclass.getWorld()
						.getEntityArray()[i];
				ep.addRender();

				if (ep == null || ep.isFrom())
					continue;
				if (Location.getDistance(mainclass.getWorld().getPlayer()
						.getLocation(), ep.getLocation()) < 0.5f) {
					// LOADING NEW MAP
					mainclass.getSavedData().putData("worldOld",(int)mainclass.getSavedData().getData("world"));
					mainclass.getSavedData().putData("world",ep.getPortal());
					((StartClass) mainclass).load((int)mainclass.getSavedData().getData("world"));
					System.out.println("Load new map");
				}
				*/
			} else if (mainclass.getWorld().getEntityArray()[i] instanceof Projectile) {
				Projectile pf = (Projectile) mainclass.getWorld()
						.getEntityArray()[i];
				movingTickProjectile(mainclass, pf);
				if (pf.getSkill() != null) {
					if(ticks==40)
					pf.tick(mainclass);
					
				if(!pf.getSkill().projectileHit(mainclass,pf, mainclass.getWorld().getPlayer()))
					for (int a = 0; a < mainclass.getWorld().getEntityArray().length; a++) {
						if (mainclass.getWorld().getEntityArray()[a] == null)
							continue;
						if (mainclass.getWorld().getEntityArray()[a] instanceof EntityLiving) {
							EntityLiving el = (EntityLiving) mainclass
									.getWorld().getEntityArray()[a];
							if (pf.getSkill().projectileHit(mainclass,pf, el))
								break;
						}
					}
				}

			}
		}

	}
	long lastSynced;
	public void gameTick() {
		mainclass.getWorld().getPlayer().tick(mainclass);
		movingTickEntity(mainclass, mainclass.getWorld().getPlayer());
		mainclass.getWorld().getPlayer().addRender();
		if(ticks == tickupdate-1){
			mainclass.getSavedData().putData("level",mainclass.getLevel());
			if(((StartClass)mainclass).isGoalIL()){
				mainclass.setLevel(mainclass.getLevel()+1);
				((StartClass)mainclass).load(mainclass.getLevel()+1);
			}
		}
//		if (mainclass.getWorld().getPlayer().getHealth() < 1)
//			mainclass.setTimeRunning(false);
		if(System.currentTimeMillis() - lastSynced > 1000f/30f){
//			syncForMultiplayer(mainclass);
			lastSynced = System.currentTimeMillis();
		}
		
		
		
	}

	public static boolean collision(Location b, float x, float z) {
		return inRange(new Location(x,z),b,1f);
	}

	public static void movingTickEntity(MainClass mainclass, EntityLiving living) {
		boolean done = true;
		boolean done2 = true;
		if (true) {
			{
				float x = living.getLocation().getX()
						+ living.getVelocity().getX();
				float z = living.getLocation().getZ();
				if(living instanceof EntityGhost)
					z = StartClass.HEIGHT + (float)Math.sin(x)/3;
				if(living instanceof EntitySoul)
					z = StartClass.HEIGHT + (float)Math.sin(System.currentTimeMillis()/100d*mainclass.getTimeSpeed() + living.getIndex())/3;
				if (living.getTempVelocity() != null)
					x = x + living.getTempVelocity().getX();
				float x2 = living.getLocation().getX();
				
				if(living instanceof EntityMonster){
					for(int i=0;i<mainclass.getWorld().getEntityArray().length;i++){
						Entity e = mainclass.getWorld().getEntityArray()[i];
						if(e == null)continue;
						if(GameTickHandler.inRange(e.getLocation(), new Location(x,z), 0.5f))	{
							((EntityMonster)living).cWE(mainclass,e);
							if(!(e instanceof EntityBuilding))continue;
							EntityBuilding bu = (EntityBuilding)e;
								if(bu.getBuild()!=2 || bu.getType()!=1)continue;
								done=false;break;
						}
					}
				}

			
				if (x < 0 || x > mainclass.getWorld().getSizeX() - 1)
					done = false;
				if (x2 < 0 || x2 > mainclass.getWorld().getSizeX() - 1)
					done2 = false;

				
				if (done) {
					living.getLocation().setX(x);
					living.getLocation().setZ(z);
				} else if (done2) {
					living.getLocation().setX(x2);
					living.getLocation().setZ(z);
				}
				
				 if(!done){
					living.setWall(true);
				}
				
				living.getVelocity().reset();
				if (living.getTempVelocity() != null)
					living.setTempVelocity(living.getTempVelocity().sub());

			}
		}
	}

	public static void movingTickProjectile(MainClass mainclass, Projectile proj) {

		boolean done3 = true;
		if (!proj.getVelocity().isnull() || proj.getTempVelocity() != null) {
			{
				float x = proj.getLocation().getX() + proj.getVelocity().getX();
				if (proj.getTempVelocity() != null)
					x = x + proj.getTempVelocity().getX();
				
				
				
				for (int index = 0; index < mainclass.getWorld()
						.getEntityArray().length; index++) {
					Entity e = mainclass.getWorld().getEntityArray()[index];
					if (e == null)
						continue;
					if (e instanceof Projectile)
						continue;
				}
				
				if (x < 0 || x > mainclass.getWorld().getSizeX() - 1)
					done3 = false;

				if (done3) {
					proj.getLocation().setX(x);
				} else {
					mainclass.getWorld().removeEntity(proj);
					proj.hit(mainclass);
				}

				proj.getVelocity().reset();
				if (proj.getTempVelocity() != null)
					proj.setTempVelocity(proj.getTempVelocity().sub());

			}
		}
	}

	public static boolean inRange(Location block, Location loc, float range) {
		return loc.x + range > block.x && loc.z + range > block.z
				&& loc.x - range < block.x && loc.z - range < block.z;
	}
}
