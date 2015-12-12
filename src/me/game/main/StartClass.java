package me.game.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import me.engine.location.Location;
import me.engine.main.Controls;
import me.engine.main.GameTickHandler;
import me.engine.main.Inventory;
import me.engine.main.MainClass;
import me.engine.block.HandledBlock;
import me.engine.entity.EntityBuilding;
import me.engine.entity.EntityCrow;
import me.engine.entity.Player;
import me.engine.gui.Button;
import me.engine.gui.GuiScreen;
import me.engine.gui.SkillInventory;
import me.engine.world.LevelScript;
import me.engine.world.World;
import me.engine.render.AnimationHandler;
import me.engine.render.Render2D;
import me.engine.render.SheetLoader;
import me.game.startscreen.StartScreen;

public class StartClass extends MainClass {
	public static final float HEIGHT = 7.5f;
	public static String mainFolder = System.getProperty("user.dir") + StartScreen.urlSplitter+"img"+StartScreen.urlSplitter ;

	public void preInit() {
		try {
			SheetLoader sheetloader;
			sheetloader = new SheetLoader(mainFolder+"tilemap.png", 8, 8, 32, 32);
			for (int a = 0; a < 8 * 8; a++) {
				getPictureLoader().ImportFromSheet("tile_" + String.valueOf(a),
						sheetloader, a % 8, a / 8);
			}

			String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
					"J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
					"V", "W", "X", "Y", "Z", ":", "!", "?", ".", "[", "]", "0",
					"1", "2", "3", "4", "5", "6", "7", "8", "9", "(", ")", "+",
					"-", "/", " ", "_" };
			sheetloader = new SheetLoader(mainFolder+"letters.png", 8, 8, 16, 16);
			for (int a = 0; a < letter.length; a++) {
				getPictureLoader().ImportFromSheet("char_" + letter[a],
						sheetloader, a % 8, a / 8);

			}
			
			sheetloader = new SheetLoader(mainFolder+"char.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("player_walk_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			sheetloader = new SheetLoader(mainFolder+"char_stand.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("player_stand_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			
			sheetloader = new SheetLoader(mainFolder+"shoe.png", 1, 4, 32, 32);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("shoe_walk_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			
			sheetloader = new SheetLoader(mainFolder+"wall.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("wall_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			sheetloader = new SheetLoader(mainFolder+"plant.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("plant_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			
			
			sheetloader = new SheetLoader(mainFolder+"planets.png", 1, 2, 32, 32);
			for (int a = 0; a < 2; a++) {
				getPictureLoader().ImportFromSheet("sun_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			getPictureLoader().addImage("energy", mainFolder+"sunenergy.png");
			getPictureLoader().addImage("stone", mainFolder+"stone.png");
			
			
			
			
			//Init animations
//			AnimationHandler.addHandler("player", 1);
//			AnimationHandler.getHandler("player").addAnimation(36, "walk", 100, 1, true);
			
//			AnimationHandler.addHandler("enemy", 1);
//			AnimationHandler.getHandler("enemy").addAnimation(38, "walk", 100, 1, true);
			AnimationHandler.addHandler("shoe", 1);
			AnimationHandler.getHandler("shoe").addAnimation("shoe", "walk", 100, 1, true);
			
			AnimationHandler.addHandler("player", 2);
			AnimationHandler.getHandler("player").addAnimation("player", "walk", 100, 1, true);
			AnimationHandler.getHandler("player").addAnimation("player", "stand", 160, 1, true);
//			AnimationHandler.getHandler("player").addAnimation("player1_melee", "melee", 160, 0, false);
//			AnimationHandler.getHandler("player").addAnimation("player1_attack", "attack", 160, 0, false);
//			AnimationHandler.getHandler("player").addAnimation("player1_death", "death", 120, 0, false);
			
	
	
			for (int i = 0; i < 64; i++) {
				HandledBlock b = new HandledBlock(i);
				this.getBlockHandler().addBlock("block_" + i, b, i);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void Init() {
	}

	@Override
	public void RenderInit() {
		Render2D render2d = new Render2D(this);
		render2d.startRender();

	}

	@Override
	public void ControlInit() {
		Controls controls = new Controls(this);
		controls.startControls();
	}

	@Override
	public void GameTickInit() {
		GameTickHandler g = new GameTickHandler(this);
		this.setGameTickHandler(g);
		this.getGameTickHandler().startGameTick();
	}


	public void postInit(){

	}
	private int mapVariable=0;
	
	
	public void load(int mapID) {
		this.loadMap();
		getTextLoader().addIndex();
		this.setTimeRunning(false);
		Render2D.chunkList = new int[1];
		Render2D.chunkList[0] = 0;

		File f = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+"map_" + mapID
				+ ".txm");
		
//		LevelScript levelscript = LevelScript.getLevel(mapID);
		String line = "";
		try {
			if (!f.exists()) {
				System.out.println("File does not exist");
			} else {
				World world = null;
				Location player = new Location(5, StartClass.HEIGHT);
				{
					FileInputStream fi = new FileInputStream(f);
					InputStreamReader isr = new InputStreamReader(fi);
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
//					int index = 0;
					int se = 40;
					List<int[]> blockarray = new ArrayList<int[]>();
					while (line != null) {
						int blocks = line.split(",").length;
						int[] intarray = new int[blocks];
						for (int i = 0; i < blocks; i++)
							intarray[i] = Integer.parseInt(line.split(",")[i]);
						blockarray.add(intarray);
						line = br.readLine();
					}
					world = new World(this, blockarray.get(0).length,
							blockarray.size(), se);
					int sz = 0;
					for (int[] ints : blockarray) {
						int sx = 0;
						if(blockarray.size() - sz - 1 <= 8)
							for (int in : ints) {
								world.setBlockID(sx, blockarray.size() - sz - 1, in);
								sx++;
							}
						sz++;
					}
				}
				

				world.setPlayer(new Player(this, player.x,  player.z));
				Render2D.sunMode=true;
				Render2D.sunUp=true;
				Render2D.sunSpins=-1;
				this.resetTimeTicks();
//				world.calcLight();
				

//				world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
//				for(int i=1;i<10;i++)
//					world.addEntity(new EntityBuilding(this,10f + ((world.getSizeX()-20f)/10)*i,7.25f + random.nextFloat()/2,0,0));
//				world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
				mapVariable=0;
				this.initEntitysIL(world);
				this.setWorld(world);
				reRender(world);
//				rerender = true;
			}

		} catch (Exception e) {
			System.out.println(line);
			e.printStackTrace();
		}
		this.setScore(0);
		initValuesIL();
		
		this.setTimeRunning(true);
		this.setLoaded();
		
		getSavedData().saveToFile("player.txt");
		setDialogIL();
	}

	public static boolean rerender = false;

	private void reRender(World w) {
		Render2D.chunkList = new int[w.getChunkArray().length];
		for (int i = 0; i < w.getChunkArray().length; i++)
			Render2D.chunkList[i] = -1;

	}
	public static void openInventory(MainClass m){
		if(m.getGui() != null)return;
		 GuiScreen gui = new GuiScreen(3);	
		gui.setGuiPart(0, new Button(new Location(6,-10),6,2,0,18,60,100,"Exit"){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				Controls.close(m);
			}
		});
//		gui.setGuiPart(1, new SkillInventory(new Location(-4.5f,1.75f)));
		
		gui.setGuiPart(1, new Button(new Location(-12,-10),6,2,0,18,0,40,"Save"){
			@Override
			public void buttonClick(MainClass m,float mx,float mz){
				
//				Inventory.useItem(m, ((SkillInventory)(m.getGui().getGuiPart(1))).getItemIndex());
			}
		});
		m.setGui(gui);
	}
	public static void closeInventory(MainClass m){m.setGui(null);}
	@Override
	public void WorldInit() {
		LevelScript.addLevel(1, new LevelScript(1));
	
		getTextLoader().loadFromFile("loading.txt");
		if(!this.getLoadGame())
		getSavedData().loadFromFile("player.txt");
		else
		getSavedData().createFromNew();
		
		this.setLevel((int) getSavedData().getData("level"));
		

		load(this.getLevel()+1);
		
		

	}
	
	public void setDialogIL(){
		if(getLevel()==0){
			this.setDialog("Game",
					"Hello and welcome to A Foxs Garden. Use your Mouse to Control. Drop a coin next to the stone to grow a plant. Drop 4 coins to upgrade it. Goal: Reach 200 Score");
		}
		if(getLevel()==1){
			this.setDialog("Game",
					"When the sun rises enemys will appear! Protect your flower and build walls! Goal: Defeat one wave");
		}
		if(getLevel()==2){
			this.setDialog("Game",
					"Defent the plants! More enemies will approach! Goal: Reach 300 Score");
		}
	}
	
	public void initValuesIL(){
		if(getLevel()==0){
			this.getWorld().getPlayer().setEnergy(5);
		}
		if(getLevel()==1){
			this.getWorld().getPlayer().setEnergy(5);
		}
		if(getLevel()==2){
			this.getWorld().getPlayer().setEnergy(5);
		}
	}

	public float getSunSpeedIL() {
		if(getLevel()==0)
			return 100f;
		if(getLevel()==1)
			return 500f;
		if(getLevel()==2)
			return 400f;
		
		return 1000f;
	}
	
	
	public boolean isGoalIL() {
		if(getLevel()==0){
			if(getScore()>=200)
				return true;
			else
				return false;
		}
		if(getLevel()==1){
			if(Render2D.sunSpins >= 2)
				return true;
			else
				return false;
		}
		if(getLevel()==2){
			if(getScore()>=300)
				return true;
			else
				return false;
		}
		return false;
	}
	
	private int countFlowers() {
		int count=0;
		for(int i=0;i<this.getWorld().getEntityArray().length;i++)	{
			if(getWorld().getEntityArray()[i]==null  || !(getWorld().getEntityArray()[i] instanceof EntityBuilding))continue;
			EntityBuilding bu = (EntityBuilding)getWorld().getEntityArray()[i];
			if(bu.getBuild()!=0 && bu.getType()!=1)count++;
		}
		return count;
	}
	public boolean isGameOverIL() {
		if(getLevel()==0)return false;
		if(getLevel()==1){
			return countFlowers()<=0;
		}
		if(getLevel()==2){
			return countFlowers()<=0;
		}
		
		return false;
	}

	public void initEntitysIL(World world){
		Random random = new Random();
		random.setSeed(1L);
		if(getLevel()==0){
			world.addEntity(new EntityBuilding(this,10,7.25f + random.nextFloat()/2,0,0));
		}
		if(getLevel()==1){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
		}
		if(getLevel()==2){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(1);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,0,0);
			flower.setKind(0);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,0,0);
			flower.setKind(0);
			world.addEntity(flower);
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
		}
	}
	
	public void tickEventIL(){
		if(getLevel()==0){}
		if(getLevel()==1){
			if(Render2D.dayStatus()==-1 && mapVariable==0)
				mapVariable=1;
			if(Render2D.dayStatus()==1 && mapVariable==1){
				getWorld().addEntity(new EntityCrow(this,true));
				getWorld().addEntity(new EntityCrow(this,false));
				mapVariable=2;
			}
		}
		if(getLevel()==2){
			if(Render2D.dayStatus()==0 && mapVariable<=0){
				mapVariable=1;
				if(Render2D.sunSpins>2)
					 mapVariable=2;		
			}
			if(Render2D.dayStatus()==1 && mapVariable>0){
				getWorld().addEntity(new EntityCrow(this,true));
				getWorld().addEntity(new EntityCrow(this,false));
				mapVariable--;
			}
		}
	}
	@Override
	public void SoundInit() {

//	getSoundPlayer().addToBuffer("bg",mainFolder+"music.wav", true,0.1f);
//	getSoundPlayer().addToBuffer("bg_long",System.getProperty("user.dir") + StartScreen.urlSplitter+"util"+ StartScreen.urlSplitter+"track_1.wav", true,0.2f);
//	getSoundPlayer().addToBuffer("exp0",System.getProperty("user.dir") + StartScreen.urlSplitter+"util"+ StartScreen.urlSplitter+"exp_0.wav", false,1f);
//	getSoundPlayer().addToBuffer("exp1",System.getProperty("user.dir") + StartScreen.urlSplitter+"util"+ StartScreen.urlSplitter+"exp_1.wav", false,1f);
//	getSoundPlayer().addToBuffer("hit0",System.getProperty("user.dir") + StartScreen.urlSplitter+"util"+ StartScreen.urlSplitter+"hit_0.wav", false,1f);
	}




}
