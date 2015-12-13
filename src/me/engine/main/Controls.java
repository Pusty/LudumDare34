package me.engine.main;


import me.engine.entity.Entity;
import me.engine.entity.EntityPortal;
import me.engine.location.Location;
import me.game.main.StartClass;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Controls {
	MainClass mainclass;

	public Controls(MainClass m) {
		mainclass = m;
	}
	public static Location[] entityLocations = new Location[25];
	public void startControls() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					while (mainclass.isRunning() && !Display.isCreated()){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
					}
				Keyboard.create();
				}catch(Exception e){}
				while (mainclass.isRunning()) {
					try {
						controls();
						if (Display.isCloseRequested()) {
							close(mainclass);
//							System.exit(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
						mainclass.setRunning(false);
//						System.exit(0);
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
	}

	float mouseSensitivity = 0.15f;
	boolean ispressed = false;
	boolean ispressedPause = false;
	boolean manuelPause = false;
	boolean ispressedChange = false;
	public static boolean ispressedDebug = false;

	boolean mousePressed=false;
	boolean f5pressed=false;
	boolean escPressed=false;
	boolean e_ispressed=false;
	boolean space_ispressed=false;

	boolean debugPressed = false;

	boolean debug2Pressed = false;
	
	boolean misPressed = false;
	
	public static boolean MOUSE_CONTROL=true;
	
	public void controls() {
		if (true) {

			int mx = Mouse.getX();
			int mz = Mouse.getY();

			int field = Mouse.getY() / (mainclass.getHeight() / (15));

			field = field - 6;
			if (ispressedPause == false && Keyboard.isKeyDown(Keyboard.KEY_P)) {
				ispressedPause = true;
				mainclass.setTimeRunning(manuelPause);
//				if(manuelPause)
			
				
				manuelPause = !manuelPause;
			}

			if (ispressedPause == true && !Keyboard.isKeyDown(Keyboard.KEY_P)) {
				ispressedPause = false;
				mainclass.setTimeRunning(manuelPause);
				if(manuelPause)
					mainclass.getSoundPlayer().unpauseAll();
				else
					mainclass.getSoundPlayer().pauseAll();

				
			}

			float bx = (mx - (mainclass.getWidth() / 2f)) / 29f - 0.5f;
			float bz = (mz - (mainclass.getHeight() / 2f)) / 29f - 0.5f;
	
	

			if(!escPressed && Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) ){
				escPressed=true;
				if(mainclass.getDialog() == null){
				if(mainclass.getGui()==null)StartClass.openInventory(mainclass);
				else StartClass.closeInventory(mainclass);
				}
				
			}else if(escPressed && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){escPressed=false;}
			
			if(Keyboard.isKeyDown(Keyboard.KEY_E) && mainclass.getDialog()==null){
				((StartClass)mainclass).setDialogIL();
			}
			
			
			if(mainclass.isTimeRunning()){
			mainclass.getWorld().getPlayer()
					.setMouseLocation(new Location(bx, bz));

//			float cmx = mainclass.getWorld().getPlayer().getMouseLocation()
//					.getX();
//			float cmz = mainclass.getWorld().getPlayer().getMouseLocation()
//					.getZ();
			}

			if (!Mouse.isButtonDown(0))
				ispressed = false;

			

			
			{
				if(mainclass.getGui() != null)
					mainclass.getGui().tick();				
			}
			
			
			
			if((Mouse.isButtonDown(0) || (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) || (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))) && !mousePressed){
				if(mainclass.getDialog()!=null &&mainclass.getDialogFrom()!=null){
				mainclass.addDialogCur(1);
				}else if(mainclass.getGui() != null){
					mainclass.getGui().click(mainclass, mx, mz);
				}else if(mainclass.getWorld().getPlayer().getHealth() <= 0){
					restart(mainclass);
				}
				mousePressed=true;
			}else if(!((Mouse.isButtonDown(0) || (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) || (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))))){
				mousePressed=false;
			}
			
			


			if (mainclass.isTimeRunning() && mainclass.hasMapLoaded()) {
				/*
				if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					mainclass.getWorld().getPlayer().right(mainclass);
				}else if(!(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)))
					mainclass.getWorld().getPlayer().right=false;			
				
				if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					mainclass.getWorld().getPlayer().left(mainclass);
				}else if(!(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)))
					mainclass.getWorld().getPlayer().left=false;
				*/
				
			/*	if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
					mainclass.getWorld().getPlayer().down(mainclass);
				}else if(!(Keyboard.isKeyDown(Keyboard.KEY_S) && Keyboard.isKeyDown(Keyboard.KEY_DOWN)))
					mainclass.getWorld().getPlayer().down=false;
			*/

				
			if(Controls.MOUSE_CONTROL){
				if (Mouse.isButtonDown(1)) {
					mainclass.getWorld().getPlayer().down(mainclass);
				}else if(!Mouse.isButtonDown(1))
					mainclass.getWorld().getPlayer().down=false;			
				float relativeX = Mouse.getX()/(float)Display.getWidth();
				if(relativeX < 0.45f){
					mainclass.getWorld().getPlayer().left(mainclass);
					mainclass.getWorld().getPlayer().right=false;	
				}else if(relativeX > 0.55f){
					mainclass.getWorld().getPlayer().right(mainclass);
					mainclass.getWorld().getPlayer().left=false;
				}else{
					mainclass.getWorld().getPlayer().right=false;	
					mainclass.getWorld().getPlayer().left=false;
				}
			}else{
				if((Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) || ((Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) && (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)))){
					mainclass.getWorld().getPlayer().down(mainclass);
				}else {
					mainclass.getWorld().getPlayer().down=false;
				if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
					mainclass.getWorld().getPlayer().right(mainclass);
				}else if(!(Keyboard.isKeyDown(Keyboard.KEY_D) && Keyboard.isKeyDown(Keyboard.KEY_RIGHT)))
					mainclass.getWorld().getPlayer().right=false;			
				if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
					mainclass.getWorld().getPlayer().left(mainclass);
				}else if(!(Keyboard.isKeyDown(Keyboard.KEY_A) && Keyboard.isKeyDown(Keyboard.KEY_LEFT)))
					mainclass.getWorld().getPlayer().left=false;
				}
			}
				
			if(Keyboard.isKeyDown(Keyboard.KEY_M) && !misPressed){
				Controls.MOUSE_CONTROL=!Controls.MOUSE_CONTROL;
				misPressed=true;
			}else if(!Keyboard.isKeyDown(Keyboard.KEY_M)){
				misPressed=false;
			}
			
			if((Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) 
					|| (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT) || (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))))
				Controls.MOUSE_CONTROL=false;
			
			if(Mouse.isButtonDown(0) || Mouse.isButtonDown(1))
				Controls.MOUSE_CONTROL=true;	
				
				

			}
		}


	}
	public static int debugAnimation=0;

	public static void close(MainClass m) {
		m.getSavedData().saveToFile("player.txt");
		m.setTimeRunning(true);
		m.setRunning(false);
	}

	public static void restart(MainClass m){
		m.getWorld().getPlayer().setHealth(m.getWorld().getPlayer().getStartHealth());
		m.getSavedData().putData("worldOld",(int)m.getSavedData().getData("world"));
		m.getSavedData().putData("world",(int)m.getSavedData().getData("world"));
		((StartClass) m).load((int)m.getSavedData().getData("world"));
		
		Location to=new Location(0,0);
		for(Entity e:m.getWorld().getEntityArray()){
			if(e==null || !(e instanceof EntityPortal))continue;
			if(((EntityPortal)e).isFrom() && ((EntityPortal)e).getPortal() == (int)m.getSavedData().getData("worldOld"))to = e.getLocation().clone();
		}
		m.getWorld().getPlayer().getLocation().set(to);
	}
	

}
