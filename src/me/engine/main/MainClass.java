package me.engine.main;

import java.util.Random;

import me.engine.block.BlockHandler;
import me.engine.gui.GuiScreen;
import me.engine.render.PictureLoader;
import me.engine.sound.SoundPlayer;
import me.engine.text.TextPopup;
import me.engine.world.World;

public abstract class MainClass {
	boolean running;
	boolean timerunning;
//	SavedStats savedstats;
	World world;
	 PictureLoader picloader;
	 BlockHandler blockhandler;
	 GameTickHandler gametickhandler;
		private int targetWidth = 800;
		private int targetHeight = 600;	
		float timespeed=1f;
		int debug=0;
		TextPopup[] popuplist;
		public void toggleDebug(){debug=debug+1>2?0:debug+1;}
		public int debug(){return debug;}
	String dialogText=null;
	String dialogFrom=null;
	int dialogCur=0;
	SoundPlayer soundplayer=null;
	SavedData savedData;
	boolean fullscreen;
	boolean vsync;
	float soundpower;
	boolean load;
	GuiScreen gui;
	TextLoader textloader;
	String playerName;
	Random random = new Random();
	long timeTicks=0;
	public MainClass(){
		running=true;
		timerunning=true;
//		savedstats=new SavedStats();
		popuplist = new TextPopup[20];
		picloader = new PictureLoader();
		blockhandler = new BlockHandler();
		timespeed=1f;
		soundplayer=new SoundPlayer();
		savedData=new SavedData();
		gui=null;
		textloader = new TextLoader();
		playerName = "player"+ random.nextInt(1000);
		timeTicks=0;
	}
	public long getTimeTicks() {return timeTicks;}
	public void addTimeTick() {timeTicks=timeTicks+1;}
	public void resetTimeTicks() {timeTicks=0;}
	public void setTimeTick(int t){timeTicks=t;}
	public String getPlayerName(){return playerName;}
	public TextLoader getTextLoader(){return textloader;}
	public GuiScreen getGui(){return gui;}
	public void setGui(GuiScreen g){gui=g;}
	public SavedData getSavedData(){
		return savedData;
	}
	public void setFullscreen(boolean f) {
		fullscreen=f;
	}
	public boolean getFullscreen(){
		return fullscreen;
	}
	public void setVSync(boolean v){
		vsync=v;
	}
	public boolean getVSync(){
		return vsync;
	}
	public void setSoundPower(float p){
		soundpower=p;
	}
	public float getSoundPower(){
		return soundpower;
	}
	public void setLoadGame(boolean b){
		load=b;
	}
	public boolean getLoadGame(){
		return load;
	}

	
	int level=0;
	int score=0;
	public int getLevel() {return level;}
	public void setLevel(int l){level=l;}
	public int getScore() {return score;}
	public void setScore(int s){score=s;}
	public void addScore(int s){score=score+s;}
	
	public void setDialog(String from,String text){
		dialogText=text;
		dialogFrom=from;
		dialogCur=0;
	}
	public String getDialog(){
		return dialogText;
	}
	public String getDialogFrom(){
		return dialogFrom;
	}
	public int getDialogCur(){
		return dialogCur;
	}
	public void addDialogCur(int a){
		dialogCur=dialogCur+a;
	}
	public BlockHandler getBlockHandler(){return blockhandler;}
	public float getTimeSpeed(){return timespeed;}
	public void setTimeSpeed(float f){timespeed=f;}
	
	public int getWidth(){return targetWidth;}
	public int getHeight(){return targetHeight;}
	public void setWidth(int i){
		targetWidth=i;
	}
	public void setHeight(int i){
		targetHeight=i;
	}
	public PictureLoader getPictureLoader(){
		return picloader;
	}
	
	public World getWorld(){
		return world;
	}
	
	public abstract void WorldInit();
	

	
	public GameTickHandler getGameTickHandler(){
		return gametickhandler;
	}
	
	public void setGameTickHandler(GameTickHandler g){
		gametickhandler=g;
	}
	
	public void setWorld(World w){
		world = w;
	}
	public void startInit(){
		preInit();

		SoundInit();
		RenderInit();
		WorldInit();
		Init();
		ControlInit();
		GameTickInit();
		postInit();
	}
	
	public void preInit(){
		
	}
	public void Init(){
	
	}
	public abstract void SoundInit();
	public abstract void RenderInit();
	public abstract void ControlInit();
	public abstract void GameTickInit();
	public void postInit(){
		
	}
	
	public void setRunning(boolean b) {
		timerunning=false;
		running=false;
	}
	
	public SoundPlayer getSoundPlayer(){
		return soundplayer;
	}
	
	public boolean isRunning(){return running;}
	public void setTimeRunning(boolean b){
		timerunning=b;	}
	public boolean isTimeRunning(){return timerunning;}
	public void addTextPopup(TextPopup textPopup) {
		for(int i=0;i<popuplist.length;i++)
			if(popuplist[i]==null){
			popuplist[i] = textPopup;
			break;
			}
	}
	public void setTextPopupArray(TextPopup[] textPopup) {
			popuplist = textPopup;
	}
	public TextPopup[] getTextPopupArray(){return popuplist;}
	public void addLoading(){if(mapLoading>0)mapLoading--;}
	public void setLoaded(){mapLoaded=true;}
	int mapLoading=0;
	boolean mapLoaded=false;
	public void loadMap(){
		mapLoading=100;
		mapLoaded=false;
	}
	public int getMapLoading(){
		return mapLoading;
	}
	public boolean hasMapLoaded() {
		return mapLoaded && mapLoading<=0;
	}
	public String getLoading() {
		return mapLoaded+"|"+mapLoading;
	}


	
}
