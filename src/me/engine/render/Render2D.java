package me.engine.render;


import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import me.engine.location.Location;
import me.engine.text.TextPopup;
import me.engine.world.Chunk;
import me.engine.entity.Entity;
import me.engine.main.GameTickHandler;
import me.engine.main.MainClass;
import me.game.main.StartClass;




import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

public class Render2D {

	public static int[] chunkList;
	int framerate = 120;
	MainClass mainclass;
	Entity[] entitysbehindplayer;
	public Render2D(MainClass m) {
		mainclass = m;
	}

	public void lookPos(Location loc, boolean t) {
		int m = t ? 1 : -1;
		float ex = 6.5f;
		if(mainclass.getHeight()/(float)mainclass.getWidth() == 3f/4f){
			
		}else{ex=ex+1.5f;}
		if(loc.x < (ex)){
			float nx = loc.x >= ex ? loc.x : ex;
		GL11.glTranslatef(nx * -1 * m, loc.z * -1 * m, 0);			
		}else if(loc.x > mainclass.getWorld().getSizeX()-ex){
			float nx = loc.x <= mainclass.getWorld().getSizeX()-ex ? loc.x : mainclass.getWorld().getSizeX()-ex;
			GL11.glTranslatef(nx * -1 * m, loc.z * -1 * m, 0);			
		}else
		GL11.glTranslatef(loc.getX() * -1 * m, loc.getZ() * -1 * m, 0);
	
	}

	float camdis = -11f;

	float xmove=0;
	
	public static boolean sunMode=true;
	public static boolean sunUp=true;
	public static int sunSpins=-1;
	
	public static int dayStatus(){
		if(sunUp && sunMode)return 1;
		if(sunUp && !sunMode)return -1;
		return 0;
	}
	private static Color fade(Color ac,Color bc, float percent) {
		percent = percent<0?0:percent;
		percent = percent>1?1:percent;
	    int r = Math.round((1-percent) * ac.getRed() + percent * bc.getRed());
	    int g = Math.round((1-percent) * ac.getGreen() + percent * bc.getGreen());
	    int b = Math.round((1-percent) * ac.getBlue() + percent * bc.getBlue());
	    return new Color(r, g, b);
	} 
	
	
	private static void renderSun(MainClass m) {
		float size=2f;
		float time = (float)((m.getTimeTicks()/((StartClass)m).getSunSpeedIL())) + (float)Math.PI; //  Set the speed the sun/moon is traveling
		float distance = 3.5f;
		int spins = (int)(time/(Math.PI*2));
		float degree = (float) ((float)time/(Math.PI*2) - spins);
			if(spins > sunSpins && degree > 0.95){
				sunMode=!sunMode;
				sunSpins=spins;
			}
		sunUp = degree > 0 && degree < 0.5;
		GL11.glPushMatrix();
		Location looking = new Location(0f,0f);
		GL11.glTranslatef(looking.getX() + (float)(distance * Math.cos(time)), looking.getZ() + (float)(distance * Math.sin(time)), 0f);
		String renderID = "sun_"+(sunMode?0:1);
		
		Color sky = new Color( 	238, 232, 170),night = new Color(86, 60, 92);
		if(!sunMode)
			sky = new Color( 201, 192, 187);
		float percent = degree;
		Color interpolate = new Color(255,255,255);
		float multi = 4f;
		if(percent<=0.25 && percent>0)
				interpolate =	fade(night, sky, percent*multi);
		else if(percent<=0.5 && percent>0)
			interpolate =	fade(sky, night, ((percent)-0.25f)*multi);
		else
			interpolate = night;
			
//		GL11.glClearColor((sunMode?night.getRed():sky.getRed())/255f, (sunMode?night.getGreen():sky.getGreen())/255f, (sunMode?night.getBlue():sky.getBlue())/255f, 1);
		GL11.glClearColor(interpolate.getRed()/255f,interpolate.getGreen()/255f,interpolate.getBlue()/255f,1);
		glBindTexture(GL_TEXTURE_2D,m.getPictureLoader().getImageAsInteger(renderID));
		glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(-size/2f,size/2f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(size/2f,size/2f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(size/2f,-size/2f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(-size/2f,-size/2f);	
		GL11.glEnd();
		GL11.glPopMatrix();
	}
	public void render() {
	 mainclass.getSoundPlayer().playSound("bg", false);	
		while (mainclass.isRunning()) {
			if(mainclass.hasMapLoaded()){
			if (!mainclass.isTimeRunning()) {
				try {
					Thread.sleep(1000 / 100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if (chunkList == null) {
				chunkList = new int[mainclass.getWorld().getChunkArray().length];
				for (int i = 0; i < mainclass.getWorld().getChunkArray().length; i++)
					chunkList[i] = -1;
			}

			
//			GL11.glDisable(GL11.GL_DEPTH_TEST);
			
			// START RENDERING
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();

			//SETUP CAM
			GL11.glTranslatef(-0.0f, 0.0f, camdis);
			renderSun(mainclass);
//			GL11.glTranslatef(-0.0f, 0.0f, +25f);

			Location looking = 
					mainclass.getWorld().getPlayer().getLocation().clone();
	
			lookPos(looking, true);
			
			
			//RERENDER
			if (StartClass.rerender == true) {
				mainclass.getPictureLoader().reloadTexture();
				StartClass.rerender = false;
			}
			
			
			
			if (chunkList[0] == -1) {
				int cidx = -1;
				for (Chunk c : mainclass.getWorld().getChunkArray()) {
					cidx++;
					if (c != null && chunkList.length > cidx)
						chunkList[cidx] = createChunkDisplayList(mainclass, c);
				}
				System.out.println("Init Maprender");
			} 
			
			
	
			//Render Map
				for (int i = 0; i < chunkList.length; i++) {
					// DISTANCE IF HERE!!!
					if (mainclass.getWorld().getChunkArray().length <= i)
						continue;
					if (GameTickHandler.inRange(new Location(mainclass.getWorld()
							.getChunkArray()[i].getChunkX(), mainclass
							.getWorld().getChunkArray()[i].getChunkZ()),
							new Location(mainclass.getWorld().getPlayer()
									.getX() / 10, mainclass.getWorld()
									.getPlayer().getZ() / 10),2f)) {
						glCallList(chunkList[i]);
					}

				}
			

				
	
				for(int e=0;e<mainclass.getWorld().getEntityArray().length;e++){
					Entity en = mainclass.getWorld().getEntityArray()[e];
					if(en==null)continue;
					if(en.getZ()+0.1f <= mainclass.getWorld().getPlayer().getZ())continue;
						if(GameTickHandler.inRange(mainclass.getWorld().getPlayer().getLocation(), en.getLocation(), 20))
					en.render(mainclass);
				}
				mainclass.getWorld().getPlayer().render(mainclass,looking);
				for(int e=0;e<mainclass.getWorld().getEntityArray().length;e++){
					Entity en = mainclass.getWorld().getEntityArray()[e];
					if(en==null)continue;
					if(en.getZ()+0.1f > mainclass.getWorld().getPlayer().getZ())continue;
						if(GameTickHandler.inRange(mainclass.getWorld().getPlayer().getLocation(), en.getLocation(), 20))
					en.render(mainclass);
				}
		
		
			
				
			 GL11.glLoadIdentity();
			 GL11.glTranslatef(-0.0f, 0.0f, -camdis);
			 GL11.glTranslatef(-0.0f, 0.0f, -25f + camdis);
			 GL11.glDisable(GL11.GL_LIGHTING);
			 {
				 mainclass.getTextPopupArray()[0] = new TextPopup("SCORE: "+mainclass.getScore(), 1, 3f);
				 if(mainclass.getWorld().getPlayer()!=null)
					 mainclass.getTextPopupArray()[1] = new TextPopup("ENERGY: "+mainclass.getWorld().getPlayer().getEnergy(), 1, 2f);
				 mainclass.getTextPopupArray()[2] = new TextPopup("LEVEL: "+mainclass.getLevel(), 1, 5f);
			 for(TextPopup tp:mainclass.getTextPopupArray()){
			 if(tp==null)continue;
			 tp.render(mainclass);
			 }
			 }
			/* GL11.glPushMatrix();

			 if(mainclass.getFullscreen())
				 GL11.glTranslatef(-4f, 0f, 0f);
			 GL11.glTranslatef(-13.5f, 7.5f, 0f);
			 GL11.glScalef(2f, 2f, 0f);
			 GL11.glTranslatef(0f, -1f, 0f);
			 Inventory.renderByIndex(mainclass,(int)mainclass.getSavedData().getData("skill"));
			 GL11.glTranslatef(0f, 1f, 0f);
			 int health = mainclass.getWorld().getPlayer().getHealth();
			 for(int i=0;i<health;i++){
				 renderImage(mainclass,"item_16");
			GL11.glTranslatef(0.75f, 0f, 0f);
			 }
			GL11.glPopMatrix();*/
			
			if(mainclass.getGui() != null){
				for(int in=0;in<mainclass.getGui().size();in++){
					if(mainclass.getGui().getGuiPart(in)==null)continue;
					mainclass.getGui().getGuiPart(in).render(mainclass);
				}
			}
			
			
			  double curtime = System.currentTimeMillis(); int oneanimationrot
			  = 1000; if(curtime-lastTime >= oneanimationrot)lastTime=curtime;
			  // animation(1,(int)((curtime-lastTime)/oneanimationrot*16),5f);
			  
			  if(mainclass.getDialogFrom()!=null&&mainclass.getDialog()!=null){
			  int length =
			  displayDialog(mainclass.getDialogFrom(),mainclass.getDialog
			  (),mainclass.getDialogCur()); mainclass.setTimeRunning(false);
			  if(mainclass.getDialogCur()>length-1) { mainclass.setDialog(null,
			  null); mainclass.setTimeRunning(true); }
			  
			  
			  
			  }
			
			Display.update();
			Display.sync(60);

	
		}else{
			GL11.glClearColor(0,0,0,1);
			mainclass.addLoading();
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();

			//SETUP CAM
			GL11.glTranslatef(-0.0f, 0.0f, -42);
//			GL11.glTranslatef(-5f, -5f, 0f);
			String text1 = "Did you know";
			String text2 = mainclass.getTextLoader().getText();
			String text3 = ". . .";
			int time = mainclass.getMapLoading();
			int t = time/10;
			for(int i=0;i<t%3;i++)
				text3 = text3+" .";
			GL11.glTranslatef(-text1.length()/2f, 0f, 0f);
			renderString(mainclass,text1);
			GL11.glTranslatef(-text2.length()/2f + text1.length()/2f, -1f, 0f);
			renderString(mainclass,text2);
			GL11.glTranslatef(-text3.length()/2f + text2.length()/2f, -1f, 0f);
			renderString(mainclass,text3);
			
			Display.update();
			Display.sync(60);
		}
		}
		for (int index = 0; index < Render2D.chunkList.length; index++)
			GL11.glDeleteLists(Render2D.chunkList[index], 1);
		mainclass.getSoundPlayer().removeALData();
		Display.destroy();
		System.exit(0);
	}
	

	public static double lastTime = 0;

	private int displayDialog(String from, String text, int cur) {
		{
			String imageName = "tile_36";
			if (from.equalsIgnoreCase("enemy")) {
				imageName = "tile_38";
			}

			GL11.glPushMatrix();
			GL11.glTranslatef(-16f, -10.5f, 0f);
			glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
					.getImageAsInteger("tile_3"));
			glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0, 6.5f);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(32f, 6.5f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(32f, -4f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f, -4f);
			GL11.glEnd();
			GL11.glTranslatef(3, +1f, 0f);
			glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
					.getImageAsInteger(imageName));
			glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0f, 1f);
			GL11.glVertex2f(0, 4);
			GL11.glTexCoord2f(1f, 1f);
			GL11.glVertex2f(4f, 4f);
			GL11.glTexCoord2f(1f, 0f);
			GL11.glVertex2f(4f, 0f);
			GL11.glTexCoord2f(0f, 0f);
			GL11.glVertex2f(0f, 0f);
			GL11.glEnd();

			GL11.glTranslatef(0f, -0.5f, 0f);
			GL11.glScalef(0.5f, 0.5f, 0f);
			renderString(mainclass, from);
			GL11.glScalef(2f, 2f, 0f);
			GL11.glTranslatef(-0f, 0.5f, 0f);

			List<String> textstring = new ArrayList<String>();
			String[] parts = text.split(" ");
			String curtext = "";
			int curindex = 0;
			for (String p : parts) {
				curindex = curindex + p.length();
				if (curindex < 12) {
					curtext = curtext + p + " ";
				} else {
					curindex = 0;
					textstring.add(curtext);
					curtext = p + " ";
				}
			}
			textstring.add(curtext);

			GL11.glTranslatef(5f, 3.5f, 0f);
			int tind = cur;
			if (textstring.size() >= tind + 1)
				renderString(mainclass, textstring.get(tind + 0));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 2)
				renderString(mainclass, textstring.get(tind + 1));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 3)
				renderString(mainclass, textstring.get(tind + 2));
			GL11.glTranslatef(0f, -1.5f, 0f);
			if (textstring.size() >= tind + 4)
				renderString(mainclass, "/ / / / / / / / / / /");
			GL11.glPopMatrix();
			return textstring.size();
		}
	}

	public void animation(int id, int ind, float m) {
		String name = "anim_" + id + "_" + ind;
		int picx = mainclass.getPictureLoader()
				.getImage("anim_" + id + "_" + 0).getWidth(null);
		int picy = mainclass.getPictureLoader()
				.getImage("anim_" + id + "_" + 0).getHeight(null);
		float xratio = (float) picx / (float) picy;
		float yratio = (float) picy / (float) picx;
		xratio = xratio * m;
		yratio = yratio * m;
		if (xratio > yratio)
			yratio = m;
		else
			xratio = m;
		glBindTexture(GL_TEXTURE_2D, mainclass.getPictureLoader()
				.getImageAsInteger(name));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(0f, yratio);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(xratio, yratio);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(xratio, 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		GL11.glEnd();
	}

	public static int createChunkDisplayList(MainClass m, Chunk c) {
		int displayList = glGenLists(1);
		return updateChunkDisplayList(m, c, displayList);
	}

	public static int updateChunkDisplayList(MainClass m, Chunk c, int list) {
		glNewList(list, GL_COMPILE);
		{
			int blockID = 0;
			Location blockLocation;
			for (int bx = 0; bx < c.getSizeX(); bx++)
				for (int bz = 0; bz < c.getSizeZ(); bz++) {
					blockID = c.getBlockID(bx, bz);
					blockLocation = new Location(c.getChunkX() * c.getSizeX()
							+ bx, c.getChunkZ() * c.getSizeZ() + bz);
					m.getBlockHandler()
							.getById(blockID)
							.render(m,
									(int) blockLocation.getX(),
									(int) blockLocation.getZ(),
									blockID,0);
				}
		}
		glEndList();
		return list;
	}


	public void renderChunk(MainClass m, Chunk c, int chunkX, int chunkZ, int i) {

	}

	public static void renderImage(MainClass m,String img) {
		glBindTexture(GL_TEXTURE_2D, m.getPictureLoader()
				.getImageAsInteger(img));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(0f, 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(1f, 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(1f, 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(0f, 0f);
		GL11.glEnd();
	}
	
	public static void renderText(PictureLoader p, String s, Location loc,float xsize) {
		GL11.glTranslatef(loc.x, loc.z, 0f);
		GL11.glScalef(xsize, 1f, 1f);
		for (int xo = 0; xo < s.toUpperCase().length(); xo++) {
			GL11.glTranslatef(xo, 0, 0);
			renderChar(p, 0, 0, s.toUpperCase().toCharArray()[xo]);
			GL11.glTranslatef(-xo, 0, 0);
		}
		GL11.glScalef(1f/xsize, 1f, 1f);
		GL11.glTranslatef(-loc.x, -loc.z, 0f);
	}

	public static void renderString(MainClass m, String s) {
		for (int xo = 0; xo < s.toUpperCase().length(); xo++) {
			GL11.glTranslatef(xo, 0, 0);
			renderChar(m.getPictureLoader(), 0, 0, s.toUpperCase().toCharArray()[xo]);
			GL11.glTranslatef(-xo, 0, 0);
		}
	}

	public static void renderChar(PictureLoader loader, float x, float y, char c) {
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D,
				loader.getImageAsInteger("char_" + c));
		glBegin(GL_QUADS);
		GL11.glTexCoord2f(0f, 1f);
		GL11.glVertex2f(x + 0f, y + 1f);
		GL11.glTexCoord2f(1f, 1f);
		GL11.glVertex2f(x + 1f, y + 1f);
		GL11.glTexCoord2f(1f, 0f);
		GL11.glVertex2f(x + 1f, y + 0f);
		GL11.glTexCoord2f(0f, 0f);
		GL11.glVertex2f(x + 0f, y + 0f);
		GL11.glEnd();

	}
	
	
	
	public float measureString(String str)
	{
		return str.length() * 1.f;
	}

	public static ByteBuffer[] getIcons(String path) {
		try {
			Image image = ImageIO.read(new File(path));
			ByteBuffer[] buffer = new ByteBuffer[3];
			buffer[0] = forSizeBuffer(image, 16);
			buffer[1] = forSizeBuffer(image, 32);
			buffer[2] = forSizeBuffer(image, 128);
			return buffer;
		} catch (Exception e) {
			return null;
		}
	}

	public static ByteBuffer forSizeBuffer(Image img, int size) {
		BufferedImage bimg = PictureLoader.resizeIcon(img, size, size);
		byte[] bytebuffer = new byte[bimg.getWidth() * bimg.getHeight() * 4];
		int c = 0;
		for (int y = 0; y < size; y++)
			for (int x = 0; x < size; x++) {
				int rgb = bimg.getRGB(x, y);
				bytebuffer[c + 0] = (byte) (((rgb << 8) >> 24) & 0xFF);
				bytebuffer[c + 1] = (byte) (((rgb << 16) >> 24) & 0xFF);
				bytebuffer[c + 2] = (byte) (((rgb << 24) >> 24) & 0xFF);
				bytebuffer[c + 3] = (byte) ((rgb >> 24) & 0xFF);
				c = c + 4;
			}
		return ByteBuffer.wrap(bytebuffer);
	}

	public void startRender() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				initDisplay(false);
				initGL();
				mainclass.getSoundPlayer().initSoundPlayer(mainclass.getSoundPower());
				mainclass.getPictureLoader().loadTexture();
				render();
			}
		}).start();
	}

	private void initDisplay(boolean fullscreen) {

		DisplayMode chosenMode = null;

		try {
			DisplayMode[] modes = Display.getAvailableDisplayModes();
			DisplayMode m = Display.getDisplayMode();
			
			if(mainclass.getFullscreen()){
				mainclass.setWidth(m.getWidth());
				mainclass.setHeight(m.getHeight());
			}
			for (int i = 0; i < modes.length; i++) {
				if ((modes[i].getWidth() == mainclass.getWidth())
						&& (modes[i].getHeight() == mainclass.getHeight())) {
					chosenMode = modes[i];
					break;
				}
			}

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
			 Display.setVSyncEnabled(mainclass.getVSync());
			Display.setFullscreen(mainclass.getFullscreen()); // FULLSCREEn
			Display.setTitle("A Fox's Garden");

			Display.setIcon(getIcons(System.getProperty("user.dir")
					+ "\\img\\icon32.png"));

			Display.create();

		} catch (LWJGLException e) {
			Sys.alert("Error", "Unable to create display.");
			System.exit(0);
		}

	}

	private void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();

		GLU.gluPerspective(45.0f, ((float) mainclass.getWidth())
				/ ((float) mainclass.getHeight()), 0.1f, 100.0f);

		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();

		GL11.glEnable(GL11.GL_TEXTURE_2D);

	    GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
	    GL11.glEnableClientState(GL11.GL_COLOR_ARRAY);
		GL11.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		GL11.glClearDepth(1.0f);
		
//		GL11.glDisable(GL11.GL_DEPTH_TEST); //DEPTH TEST
		
	    GL11.glClearStencil(0);
	    GL11.glDisable(GL11.GL_DITHER);

	   
	    GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
	    
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_BLEND);

		return;
	}

	public static void setLight(int light, Location lightpos, float h,
			float angle) {
		if (!GL11.glIsEnabled(light))
			GL11.glEnable(light);
		GL11.glLight(
				light,
				GL11.GL_POSITION,
				BufferTools.asFlippedFloatBuffer(new float[] { lightpos.x,
						lightpos.z, h, angle }));
	}

	public static void day() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 25, 0);
	}

	public static void mid() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 25, 0);
	}

	public static void night() {
		setLight(GL11.GL_LIGHT0, new Location(0.5f, 0.5f), 5, 1f);
	}

	
	static IntBuffer viewport = BufferUtils.createIntBuffer(16);
	   static FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
	   static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	   static FloatBuffer winZ = BufferUtils.createFloatBuffer(20);
	   static FloatBuffer position = BufferUtils.createFloatBuffer(3);
	   
	 /*  static public Location getMousePosToCoords(int mx,int my){
		   Location v=null;
		   x=p+ru
		   x=p+uv+tw
		   
		   return v;
	   }*/
	   static public Location getMousePositionIn3dCoords(int mouseX, int mouseY)
	   {

	      viewport.clear();
	      modelview.clear();
	      projection.clear();
	      winZ.clear();;
	      position.clear();
	      float winX, winY;


	      GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
	      GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
	      GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );

	      winX = (float)mouseX;
	      winY = (float)mouseY;

	      GL11.glReadPixels(mouseX, (int)winY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

	      float zz = winZ.get();

	      GLU.gluUnProject(winX, winY, zz, modelview, projection, viewport, position);



	      Location v = new Location (position.get(0),position.get(1));


	      return v ;
	   }
	

}
