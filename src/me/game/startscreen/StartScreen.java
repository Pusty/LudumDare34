package me.game.startscreen;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import me.engine.location.Location;
import me.engine.render.PictureLoader;
import me.engine.render.Render2D;
import me.engine.render.SheetLoader;
import me.engine.sound.SoundPlayer;
import me.game.main.StartClass;

public class StartScreen {
	public static String urlSplitter ="/";
	SoundPlayer soundloader=null;
	PictureLoader picloader=null;
	boolean isAlive=true; 
	DisplayMode[] modes;
	public StartScreen(){
		picloader = new PictureLoader();
		soundloader=new SoundPlayer();
	}
	int ticks;
	public void start(){
		initDisplay();
		initGL();
		initTexture();
		soundloader.initSoundPlayer(1f);
		picloader.loadTexture();
		float camdis = -7f;
		screenX = Display.getWidth();
		screenY = Display.getHeight();
		try{

		for (int i = 0; i < modes.length; i++) {
			if(modes[i].getWidth()==800 && modes[i].getHeight()==600){
				screenX = modes[i].getWidth();
				screenY = modes[i].getHeight();
			}
		}
		
		}catch(Exception e){
		}

		while(isAlive){
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();

			GL11.glTranslatef(-0.0f, 0.0f, camdis);
			try{
			render();
			}catch(Exception e){e.printStackTrace();}
			
			Display.update();
			if (Display.isCloseRequested()) {
				isAlive=false;
			}
			try {
				Thread.sleep((int) (50));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		soundloader.removeALData();
		Display.destroy();
		if(startGame==1)
		{this.startGame(false,-1);}
		else if(startGame==2)
		{this.startGame(true,-1);}
		else if(startGame>=3)
		{this.startGame(false,startGame-3);}
		else {
			System.exit(0);
		}
		
	}
	
	int buttonindex=0;
	int side=0;
	boolean mouse=false;
	boolean fullscreen=false;
	boolean vsyncbool=false;
	boolean enterdown=false;
	int soundint=10;
	int screenX=-1;
	int screenY=-1;
	int screenIndex=-1;
	int startGame=-1;
	private void render() throws LWJGLException{
		if(side == 0){
		Render2D.renderText(picloader, "Start",new Location(-3,1.5f),5f/10f);
		Render2D.renderText(picloader, "Level Select",new Location(-3,0f),5f/10f);
		Render2D.renderText(picloader, "Option",new Location(-3,-1.5f),5f/10f);
		}else if(side == 1){
		String res = "R: "+screenX+"/"+screenY;
		Render2D.renderText(picloader, res,new Location(-3,1.5f),5f/res.length());
		String full = "Fullscreen: "+fullscreen;
		Render2D.renderText(picloader, full,new Location(-3,0f),5f/full.length());
		Render2D.renderText(picloader, "Next",new Location(-3,-1.5f),5f/10f);
		}else if(side == 2){
			String vsync = "VSync: "+vsyncbool;
			String sound = "Sound: "+soundint+"/10";
		Render2D.renderText(picloader, vsync,new Location(-3,1.5f),5f/vsync.length());
		Render2D.renderText(picloader, sound,new Location(-3,0f),5f/sound.length());
		Render2D.renderText(picloader, "Next",new Location(-3,-1.5f),5f/10f);
		}else if(side >= 3){
		Render2D.renderText(picloader, "Level "+(0+2*(side-3)),new Location(-3,1.5f),5f/10f);
		Render2D.renderText(picloader, "Level "+(1+2*(side-3)),new Location(-3,0f),5f/10f);
		Render2D.renderText(picloader, "Next",new Location(-3,-1.5f),5f/10f);
		}else if(side == -1){
			String f1 = "Play Game";
			String f2 = "New Game";
		Render2D.renderText(picloader, f1,new Location(-3,1.5f),5f/10f);
		Render2D.renderText(picloader, f2,new Location(-3,0f),5f/10f);
		Render2D.renderText(picloader, "Back",new Location(-3,-1.5f),5f/10f);
		}
		if(ticks>0){ticks--;return;}
		
		if(Keyboard.isKeyDown(28)){
			isAlive=false;startGame=1;return;
		}
		
		if(Mouse.isButtonDown(0) && !mouse){
			mouse=true;
			int buttonindex = -1;
			float value = Mouse.getY()/(float)Display.getHeight() * 4f;
			if(value >= 0f && value < 1f)return;			
			if(value >= 1f && value < 2f)buttonindex=2;
			if(value >= 2f && value < 3f)buttonindex=1;
			if(value >= 3f && value < 4f)buttonindex=0;
			buttonindex=buttonindex+3*side;
			ticks=5;
			
				 if(buttonindex == 0){side=-1;}
			else if(buttonindex == 1){side=3;}
			else if(buttonindex == 2){side=1;}
			
			else if(buttonindex == 3){
				screenIndex++;
				if(modes.length<=screenIndex)screenIndex=0;
				screenX=modes[screenIndex].getWidth();
				screenY=modes[screenIndex].getHeight();
			}
			else if(buttonindex == 4){fullscreen = !fullscreen;}
			else if(buttonindex == 5){side=2;}
			
			else if(buttonindex == 6){vsyncbool=!vsyncbool;}
			else if(buttonindex == 7){soundint++;if(soundint>10)soundint=0;}
			else if(buttonindex == 8){side=0;}
			else if(buttonindex >= 9){
				int cur = buttonindex-3*side;
				if(cur==2){
					if(side+1>7)
						side=0;
					else
						side=side+1;
				}else {
					isAlive=false;
					startGame=3+cur+2*(side-3);
				}
			}
			
			else if(buttonindex == -1){side=0;}
			else if(buttonindex == -2){isAlive=false;startGame=2;}
			else if(buttonindex == -3){isAlive=false;startGame=1;}
			
		}else if(!Mouse.isButtonDown(0) && mouse){
			mouse=false;
		}
		
	}
	
	private void initTexture(){
		try{

		String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
				"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
				"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
				"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
				"-", "/", " ", "_" };
		SheetLoader sheetloader = new SheetLoader(System.getProperty("user.dir")
				+ StartScreen.urlSplitter+"img"+StartScreen.urlSplitter+"letters.png", 8, 8, 16, 16);
		for (int a = 0; a < letter.length; a++) {
			picloader.ImportFromSheet("char_" + letter[a],
					sheetloader, a % 8, a / 8);

		}
		}catch(Exception e){e.printStackTrace();}
		}

	private void initDisplay() {

		DisplayMode chosenMode = null;

		try {
		modes =	Display.getAvailableDisplayModes();
			for (int i = 0; i < modes.length; i++) {
				if(modes[i].getWidth()==320 && modes[i].getHeight()==200){
					chosenMode = modes[i];
				}
			}
			if(chosenMode==null)
				for (int i = 0; i < modes.length; i++) {
					if(modes[i].getWidth()==640 && modes[i].getHeight()==480){
						chosenMode = modes[i];
					}
				}
			if(chosenMode==null)
				chosenMode = Display.getDesktopDisplayMode();
		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to determine display modes.");
			System.exit(0);
		}

		if (chosenMode == null) {
			Sys.alert("Error", "Unable to find appropriate display mode.");
			System.exit(0);
		}


		try {
			Display.setDisplayMode(chosenMode);
//			 Display.setVSyncEnabled(true);
			Display.setFullscreen(false); // FULLSCREEn
			Display.setTitle("A Fox's Garden");
			Display.setIcon(Render2D.getIcons(System.getProperty("user.dir")
					+StartScreen.urlSplitter+"img"+StartScreen.urlSplitter+"icon32.png"));

			Display.create();

		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}

	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f, Display.getWidth()
				/ Display.getHeight(), 0.1f, 100.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClearDepth(1.0f);
		
	    GL11.glClearStencil(0);
	    GL11.glDisable(GL11.GL_DITHER);

	   
	    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
	    
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		return;
	}
	
	public void startGame(boolean start,int level){
		StartClass gameclass = new StartClass();
		if(level!=-1){
			gameclass.setLevel(level);
			gameclass.getSavedData().loaded=true;
			gameclass.getSavedData().putData("level", level);
			gameclass.getSavedData().saveToFile("player.txt");
		}
		gameclass.setWidth(this.screenX);
		gameclass.setHeight(this.screenY);
		gameclass.setFullscreen(this.fullscreen);
		gameclass.setVSync(this.vsyncbool);
		gameclass.setSoundPower(this.soundint*0.1f);
		gameclass.setLoadGame(start);
		gameclass.startInit();
	}

	public static void main(String[] str){
		StartScreen startscreen = new StartScreen();
		startscreen.start();
	}
}
