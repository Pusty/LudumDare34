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
import me.engine.main.MainClass;
import me.engine.block.HandledBlock;
import me.engine.entity.EntityBuilding;
import me.engine.entity.EntityDestroyer;
import me.engine.entity.EntityShear;
import me.engine.entity.EntityShoe;
import me.engine.entity.EntityText;
import me.engine.entity.Player;
import me.engine.gui.Button;
import me.engine.gui.GuiScreen;
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
			
			sheetloader = new SheetLoader(mainFolder+"shear.png", 1, 4, 32, 32);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("shear_walk_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			sheetloader = new SheetLoader(mainFolder+"destroyer.png", 1, 4, 32, 32);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("destroyer_walk_" + String.valueOf(a),
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
			
			
			sheetloader = new SheetLoader(mainFolder+"plant-b.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("plantbonus_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			sheetloader = new SheetLoader(mainFolder+"plant-b.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("plantbonus_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			sheetloader = new SheetLoader(mainFolder+"plant-e.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("plantbomb_" + String.valueOf(a),
						sheetloader, a, 0);
			}
			
			sheetloader = new SheetLoader(mainFolder+"plant-n.png", 1, 4, 32, 64);
			for (int a = 0; a < 4; a++) {
				getPictureLoader().ImportFromSheet("plantnight_" + String.valueOf(a),
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
			
			AnimationHandler.addHandler("shear", 1);
			AnimationHandler.getHandler("shear").addAnimation("shear", "walk", 100, 1, true);
			
			AnimationHandler.addHandler("destroyer", 1);
			AnimationHandler.getHandler("destroyer").addAnimation("destroyer", "walk", 100, 1, true);
			
			
			
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
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(isr);
					line = br.readLine();
//					int index = 0;
					int se = 60;
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
				if(getLevel()<9){
					this.resetTimeTicks();
				}else {
					this.setTimeTick((int) (Math.PI*1500));
					world.addEntity(new EntityText(this,world.getSizeX()+1f,"Thank you for playing my game! This game was made for Ludum Dare 34 by Pusty. I hope you liked it! :D"));
				}
				
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
				m.getSavedData().saveToFile("player.txt");
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
	
	private int countFlowers() {
		int count=0;
		for(int i=0;i<this.getWorld().getEntityArray().length;i++)	{
			if(getWorld().getEntityArray()[i]==null  || !(getWorld().getEntityArray()[i] instanceof EntityBuilding))continue;
			EntityBuilding bu = (EntityBuilding)getWorld().getEntityArray()[i];
			if(bu.getBuild()!=0 && bu.getType()==0)count++;
		}
		return count;
	}
	
	public void setDialogIL(){
		if(getLevel()==0){
			this.setDialog("Game",
					"Hello and welcome to A Foxs Garden. Use your Mouse to Control. Drop a coin next to the stone to grow a plant. Drop 4 coins to upgrade it. Goal: Reach 200 Score");
		}else if(getLevel()==1){
			this.setDialog("Game",
					"When the sun rises enemys will appear! Protect your flower and build walls! Goal: Defeat one wave");
		}else if(getLevel()==2){
			this.setDialog("Game",
					"Defend the plants! More enemies will approach! Goal: Reach 300 Score");
		}else if(getLevel()==3){
			this.setDialog("Game",
					"You have got a new flower kind! Upgrading them gives 100 Score. Goal: Reach 2000 Score");
		}else if(getLevel()==4){
			this.setDialog("Game",
					"If a wall reaches the fourth upgrade shoes and shears can not destory it. Goal: Survive 4 waves");
		}else if(getLevel()==5){
			this.setDialog("Game",
					"Destroyer can not pass through fully upgrades walls. Goal: Survive 4 waves");
		}else if(getLevel()==6){
			this.setDialog("Game",
					"Night flowers drop double the amount you gave them. Goal: Survive 5 waves");
		}else if(getLevel()==7){
			this.setDialog("Game",
					"Bomb flowers will break a Destroyer if they are fully grown. Goal: Survive 5 waves");
		}else if(getLevel()==8){
			this.setDialog("Game",
					"Last Level! Goal: Reach 5.000 Score");
		}else if(getLevel()==9){
			this.setDialog("Game",
					"You reached the end of the game! Thank you for playing");
		}
	}
	
	public void initValuesIL(){
		if(getLevel()==0){
			this.getWorld().getPlayer().setEnergy(5);
		}else if(getLevel()==1){
			this.getWorld().getPlayer().setEnergy(5);
		}else if(getLevel()==2){
			this.getWorld().getPlayer().setEnergy(5);
		}else if(getLevel()==3){
			this.getWorld().getPlayer().setEnergy(3);
		}else if(getLevel()==4){
			this.getWorld().getPlayer().setEnergy(20);
		}else if(getLevel()==5){
			this.getWorld().getPlayer().setEnergy(25);
		}else if(getLevel()==6){
			this.getWorld().getPlayer().setEnergy(16);
		}else if(getLevel()==7){
			this.getWorld().getPlayer().setEnergy(10);
		}else if(getLevel()==8){
			this.getWorld().getPlayer().setEnergy(20);
		}else if(getLevel()==9){
			this.getWorld().getPlayer().setEnergy(0);
		}
	}
	
	public String getInfoTextIL(){
		if(getLevel()==0)
			return "Upgrade the plant! Reach 200 Score";
		else if(getLevel()==1)
			return "Sun: 2 Shoes. Defead a wave";
		else if(getLevel()==2){
			if((Render2D.sunSpins+1)>2)
				return "Sun: 4 Shoes. Reach 300 Score";
			else
				return "Sun: 2 Shoes. Reach 300 Score";
		}else if(getLevel()==3){
			if((Render2D.sunSpins+1)>2 && ((Render2D.sunSpins+1)/2)%2==0)
				return "Sun: 4 Shoes. Reach 2000 Score";
			else
				return "Sun: 2 Shoes. Reach 2000 Score";
		}else if(getLevel()==4){
			if(Render2D.sunSpins < 2)
				return "Sun: 2 Shoes. Survive 4 more waves";
			else if(Render2D.sunSpins < 4)
				return "Sun: 4 Shoes. Survive 3 more waves";
			else if(Render2D.sunSpins < 6)
				return "Night: 1 Shear. Survive 2 more waves";
			else if(Render2D.sunSpins < 8)
				return "Night: 2 Shoes. Survive 1 more waves";
		}else if(getLevel()==5){
			if(Render2D.sunSpins < 2)
				return "Sun: 2 Shoes. Survive 4 more waves";
			else if(Render2D.sunSpins < 4)
				return "Sun: 4 Shoes. Survive 3 more waves";
			else if(Render2D.sunSpins < 6)
				return "Sun: 1 Destroyer. Survive 2 more waves";
			else if(Render2D.sunSpins < 8)
				return "Sun: 2 Shoes. Survive 1 more waves";
		}else if(getLevel()==6){
			if(Render2D.sunSpins < 2)
				return "Sun: 2 Shoes. Survive 5 more waves";
			else if(Render2D.sunSpins < 4)
				return "Sun: 2 Destroyer. Survive 4 more waves";
			else if(Render2D.sunSpins < 6)
				return "Sun: 2 Shoes. Survive 3 more waves";
			else if(Render2D.sunSpins < 8)
				return "Sun: 2 Shears. Survive 2 more waves";
			else
				return "Sun: 2 Shoes. Survive 1 more waves";
		}else if(getLevel()==7){
			if(Render2D.sunSpins < 2)
				return "Sun: 2 Shoes. Survive 5 more waves";
			else if(Render2D.sunSpins < 4)
				return "Survive 4 more waves";
			else if(Render2D.sunSpins < 6)
				return "Sun: 2 Shear. Survive 3 more waves";
			else if(Render2D.sunSpins < 8)
				return "Sun: 4 Destroyer. Survive 2 more waves";
			else
				return "Sun: 2 Shoes. Survive 1 more waves";
		}else if(getLevel()==8){
			if(Render2D.sunSpins < 2)
				return "Sun: 2 Shoes. Reach 5.000 Score";
			else if(Render2D.sunSpins < 4)
				return "Sun: 2 Shears. Reach 5.000 Score";
			else if(Render2D.sunSpins < 6)
				return "Sun: 2 Shoes. Reach 5.000 Score";
			else
				return "Sun: 2 Destroyer. Reach 5.000 Score";
		}else if(getLevel()==9){
			
		}
		return "";
	}

	public float getSunSpeedIL() {
		if(getLevel()==0)
			return 100f;
		else if(getLevel()==1)
			return 500f;
		else if(getLevel()==2)
			return 400f;
		else if(getLevel()==3)
			return 400f;
		else if(getLevel()==4)
			return 600f;
		else if(getLevel()==5)
			return 600f;
		else if(getLevel()==6)
			return 600f;
		else if(getLevel()==7)
			return 600f;
		else if(getLevel()==8)
			return 1000f;
		else if(getLevel()==9)
			return 1000f;
		
		return 1000f;
	}
	
	
	public boolean isGoalIL() {
		if(getLevel()==0){
			if(getScore()>=200)
				return true;
			else
				return false;
		}else if(getLevel()==1){
			if(Render2D.sunSpins >= 2)
				return true;
			else
				return false;
		}else if(getLevel()==2){
			if(getScore()>=300)
				return true;
			else
				return false;
		}else if(getLevel()==3){
			if(getScore()>=2000)
				return true;
			else
				return false;
		}else if(getLevel()==4){
			if(Render2D.sunSpins >= 8)
				return true;
			else
				return false;
		}else if(getLevel()==5){
			if(Render2D.sunSpins >= 8)
				return true;
			else
				return false;
		}else if(getLevel()==6){
			if(Render2D.sunSpins >= 10)
				return true;
			else
				return false;
		}else if(getLevel()==7){
			if(Render2D.sunSpins >= 10)
				return true;
			else
				return false;
		}else if(getLevel()==8){
			if(getScore()>=5000)
				return true;
			else
				return false;
		}else if(getLevel()==9)
			return false;
		return false;
	}
	
	public boolean isGameOverIL() {
		if(getLevel()==0)return false;
		else if(getLevel()==1){
			return countFlowers()<=0;
		}else if(getLevel()==2){
			return countFlowers()<=0;
		}else if(getLevel()==3){
			return countFlowers()<=0;
		}else if(getLevel()==4){
			return countFlowers()<=0;
		}else if(getLevel()==5){
			return countFlowers()<=0;
		}else if(getLevel()==6){
			return countFlowers()<=0;
		}else if(getLevel()==7){
			return countFlowers()<=0;
		}else if(getLevel()==8){
			return countFlowers()<=0;
		}else if(getLevel()==9){
			return false;
		}
		return false;
	}

	public void initEntitysIL(World world){
		Random random = new Random();
		random.setSeed(1L);
		if(getLevel()==0){
			world.addEntity(new EntityBuilding(this,10,7.25f + random.nextFloat()/2,0,0));
		}
		else if(getLevel()==1){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
		}
		else if(getLevel()==2){
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
		else if(getLevel()==3){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,0,2));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,2,1));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,2,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 7.5f,7.25f + random.nextFloat()/2,2,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 7.5f,7.25f + random.nextFloat()/2,2,1));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
		}
		else if(getLevel()==4){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,15f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 7.5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 7.5f,7.25f + random.nextFloat()/2,1,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()-15f,7.25f + random.nextFloat()/2,1,0));
		}else if(getLevel()==5){
			world.addEntity(new EntityBuilding(this,15f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,0,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 2.5f,7.25f + random.nextFloat()/2,0,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,1,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()-15f,7.25f + random.nextFloat()/2,1,0));
		}else if(getLevel()==6){
			world.addEntity(new EntityBuilding(this,15f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 2.5f,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(1);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(1);
			world.addEntity(flower);
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 7.5f,7.25f + random.nextFloat()/2,0,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 7.5f,7.25f + random.nextFloat()/2,0,0));
			
	
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,3,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()-15f,7.25f + random.nextFloat()/2,1,0));
		}else if(getLevel()==7){
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,4,0));
			world.addEntity(new EntityBuilding(this,15f,7.25f + random.nextFloat()/2,1,0));
			
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 2.5f,7.25f + random.nextFloat()/2,3,0));
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 7.5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 7.5f,7.25f + random.nextFloat()/2,1,0));
			
	
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,3,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()-15f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,4,0));
		}else if(getLevel()==8){
			world.addEntity(new EntityBuilding(this,7.5f,7.25f + random.nextFloat()/2,4,0));
			world.addEntity(new EntityBuilding(this,10f,7.25f + random.nextFloat()/2,4,0));
//			world.addEntity(new EntityBuilding(this,15f,7.25f + random.nextFloat()/2,1,0));
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			

			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 2.5f,7.25f + random.nextFloat()/2,3,0));
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 10,7.25f + random.nextFloat()/2,2,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 10,7.25f + random.nextFloat()/2,2,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 15,7.25f + random.nextFloat()/2,0,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 15,7.25f + random.nextFloat()/2,0,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 17.5f,7.25f + random.nextFloat()/2,2,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 -17.5f,7.25f + random.nextFloat()/2,2,0));
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 20f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 20f,7.25f + random.nextFloat()/2,3,0));
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 25f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 -25f,7.25f + random.nextFloat()/2,1,0));
			
			
			
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 7.5f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 7.5f,7.25f + random.nextFloat()/2,1,0));
			
	
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 + 5f,7.25f + random.nextFloat()/2,3,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()/2 - 5f,7.25f + random.nextFloat()/2,3,0));
			
//			world.addEntity(new EntityBuilding(this,world.getSizeX()-15f,7.25f + random.nextFloat()/2,1,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()-10f,7.25f + random.nextFloat()/2,4,0));
			world.addEntity(new EntityBuilding(this,world.getSizeX()-7.5f,7.25f + random.nextFloat()/2,4,0));
		}else if(getLevel()==9){
			EntityBuilding flower = new EntityBuilding(this,world.getSizeX()/2,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 2.5f,7.25f + random.nextFloat()/2,2,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 2.5f,7.25f + random.nextFloat()/2,3,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 5,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 4.5f,7.25f + random.nextFloat()/2,4,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 7.3f,7.25f + random.nextFloat()/2,2,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 6.7f,7.25f + random.nextFloat()/2,3,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 9f,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 8.2f,7.25f + random.nextFloat()/2,3,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 13.6f,7.25f + random.nextFloat()/2,3,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 14.2f,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 17.3f,7.25f + random.nextFloat()/2,2,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 18f,7.25f + random.nextFloat()/2,0,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 + 20.5f,7.25f + random.nextFloat()/2,4,2);
			flower.setKind(3);
			world.addEntity(flower);
			flower = new EntityBuilding(this,world.getSizeX()/2 - 23.3f,7.25f + random.nextFloat()/2,2,2);
			flower.setKind(3);
			world.addEntity(flower);
		}
	}
	
	public void tickEventIL(){
		Random random = new Random();
		if(Render2D.sunSpins==-1)return;
		if(getLevel()==0){}
		else if(getLevel()==1){
			if(Render2D.dayStatus()==-1 && mapVariable==0)
				mapVariable=1;
			if(Render2D.dayStatus()==1 && mapVariable==1){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable=2;
			}
		}
		else if(getLevel()==2){
			if(Render2D.dayStatus()==0 && mapVariable<=0){
				mapVariable=1;
				if(Render2D.sunSpins>2)
					 mapVariable=2;		
			}
			if(Render2D.dayStatus()==1 && mapVariable>0){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable--;
			}
		}
		if(getLevel()==3){
			if(Render2D.dayStatus()==0 && mapVariable==0){
				mapVariable=1;
				if(Render2D.sunSpins>2 && ((Render2D.sunSpins+1)/2)%2==0)
					 mapVariable=2;		
			}
			if(Render2D.dayStatus()==1 && mapVariable>0){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable--;
			}
		}
		else if(getLevel()==4){
			if(Render2D.dayStatus()==1 && mapVariable==0 && Render2D.sunSpins < 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
			else if(Render2D.dayStatus()==1 && mapVariable>0 && mapVariable<=2 && Render2D.sunSpins < 4 && Render2D.sunSpins >= 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==-1 && mapVariable==3 && Render2D.sunSpins < 6 && Render2D.sunSpins >= 4){
				getWorld().addEntity(new EntityShear(this,random.nextBoolean()));
				mapVariable++;
			}else if(Render2D.dayStatus()==-1 && mapVariable==4 && Render2D.sunSpins < 8 && Render2D.sunSpins >= 6){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
		}
		else if(getLevel()==5){
			if(Render2D.dayStatus()==1 && mapVariable==0 && Render2D.sunSpins < 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
			else if(Render2D.dayStatus()==1 && mapVariable>0 && mapVariable<=2 && Render2D.sunSpins < 4 && Render2D.sunSpins >= 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==3 && Render2D.sunSpins < 6 && Render2D.sunSpins >= 4){
				getWorld().addEntity(new EntityDestroyer(this,random.nextBoolean()));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==4 && Render2D.sunSpins < 8 && Render2D.sunSpins >= 6){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
		} else if(getLevel()==6){
			if(Render2D.dayStatus()==1 && mapVariable==0 && Render2D.sunSpins < 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			} else if(Render2D.dayStatus()==1 && mapVariable==1 && Render2D.sunSpins < 4 && Render2D.sunSpins >= 2){
				getWorld().addEntity(new EntityDestroyer(this,true));
				getWorld().addEntity(new EntityDestroyer(this,false));

				mapVariable++;
			} else if(Render2D.dayStatus()==1 && mapVariable==2 && Render2D.sunSpins < 6 && Render2D.sunSpins >= 4){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==3 && Render2D.sunSpins < 8 && Render2D.sunSpins >= 6){
				getWorld().addEntity(new EntityShear(this,true));
				getWorld().addEntity(new EntityShear(this,false));
				mapVariable++;
			}else if(mapVariable > 3){
				if(Render2D.dayStatus()==-1 && mapVariable==4)
					mapVariable=6;
				if(Render2D.dayStatus()==1 && mapVariable>4){
					getWorld().addEntity(new EntityShoe(this,true));
					getWorld().addEntity(new EntityShoe(this,false));
					mapVariable--;
				}
			}
		}else if(getLevel()==7){
			if(Render2D.dayStatus()==1 && mapVariable==0 && Render2D.sunSpins < 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
			else if(Render2D.dayStatus()==1 && mapVariable==1 && Render2D.sunSpins < 4 && Render2D.sunSpins >= 2){
				
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==2 && Render2D.sunSpins < 6 && Render2D.sunSpins >= 4){
				getWorld().addEntity(new EntityShear(this,true));
				getWorld().addEntity(new EntityShear(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable>2 && mapVariable<=4 && Render2D.sunSpins < 8 && Render2D.sunSpins >= 6){
				getWorld().addEntity(new EntityDestroyer(this,true));
				getWorld().addEntity(new EntityDestroyer(this,false));
				mapVariable++;
			}else if(mapVariable > 4){
				if(Render2D.dayStatus()==-1 && mapVariable==5)
					mapVariable=7;
				if(Render2D.dayStatus()==1 && mapVariable>5){
					getWorld().addEntity(new EntityShoe(this,true));
					getWorld().addEntity(new EntityShoe(this,false));
					mapVariable--;
				}
			}
		}else if(getLevel()==8){
			if(Render2D.dayStatus()==1 && mapVariable==0 && Render2D.sunSpins < 2){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}
			else if(Render2D.dayStatus()==1 && mapVariable==1 && Render2D.sunSpins < 4 && Render2D.sunSpins >= 2){
				getWorld().addEntity(new EntityShear(this,true));
				getWorld().addEntity(new EntityShear(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==2 && Render2D.sunSpins < 6 && Render2D.sunSpins >= 4){
				getWorld().addEntity(new EntityShoe(this,true));
				getWorld().addEntity(new EntityShoe(this,false));
				mapVariable++;
			}else if(Render2D.dayStatus()==1 && mapVariable==3 &&  Render2D.sunSpins < 8 && Render2D.sunSpins >= 6){
				getWorld().addEntity(new EntityDestroyer(this,true));
				getWorld().addEntity(new EntityDestroyer(this,false));
				mapVariable++;
			}else if(mapVariable > 3){
				if(Render2D.dayStatus()==-1 && mapVariable==4)
					mapVariable=5;
				if(Render2D.dayStatus()==1 && mapVariable==5){
					getWorld().addEntity(new EntityDestroyer(this,true));
					getWorld().addEntity(new EntityDestroyer(this,false));
					mapVariable=4;
				}
			}
		}
	}
	@Override
	public void SoundInit() {

	getSoundPlayer().addToBuffer("bg",mainFolder+"music.wav", true,0.1f);
	//Sounds: "coindrop","coinpickup","enemydeath","walldeath","wallattack","win","gameover","upgrade","plantdeath", "bomb"
	
//	getSoundPlayer().addToBuffer("bg_long",mainFolder+"track_1.wav", true,0.2f);
	getSoundPlayer().addToBuffer("coindrop",mainFolder+"coindrop.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("coinpickup",mainFolder+"coinpickup.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("enemydeath",mainFolder+"enemydeath.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("walldeath",mainFolder+"walldeath.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("wallattack",mainFolder+"wallattack.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("win",mainFolder+"win.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("gameover",mainFolder+"gameover.wav", false,0.3f);
	getSoundPlayer().addToBuffer("upgrade",mainFolder+"upgrade.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("plantdeath",mainFolder+"plantdeath.wav", false,0.3f);//
	getSoundPlayer().addToBuffer("bomb",mainFolder+"bomb.wav", false,0.3f);//
	}




}
