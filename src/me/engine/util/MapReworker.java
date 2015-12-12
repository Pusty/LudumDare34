package me.engine.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import me.engine.render.PictureLoader;
import me.engine.render.SheetLoader;
import me.game.startscreen.StartScreen;

public class MapReworker {
	int mapID;
	
	int[] mainArray;
	int[] subArray;
	String map_Name;
	public MapReworker(String mapName,int mid){
		mapID=mid;
		map_Name = mapName;
	}
	public String getMapName(){return map_Name;}
	public static void main(String[] args){
		MapReworker map = new MapReworker("map_2",2);
		map.loadTextures();
		map.load(map.getMapName());
		map.correct();
		map.enhance();
		map.subenhance();
		map.export(map.getMapName(),false);
		map.export(map.getMapName(),true);
	}
	
	
	public void correct(){
		for(int index=0;index<mainArray.length;index++){
			int id = mainArray[index];
			if(id<=0)mainArray[index]=getNextID(index);
		}
	}
	
	public void enhance(){
		for(int index=0;index<mainArray.length;index++){
			mainArray[index]=getToPlaceID(index);
		}
	}
	public void subenhance(){
		for(int index=0;index<subArray.length;index++){
			boolean bool = collisionByID(mainArray[index]);
			if(bool)subArray[index]=1;
		}
	}
	Random random = new Random();
	public int getToPlaceID(int index){
		int width = (int) Math.sqrt(mainArray.length);
		int x = index%width;
		int y = index/width;
		int id = getTypeID(mainArray[index]);
		
		if(id == 1){
			int value=random.nextInt(10);
			if(value==0 || value == 1)return 9;
			if(value==9)return 17;
			return 1;
		}else if(id == 2){
			if(gTI(x,y)==2 && gTI(x+1,y) != 2 && gTI(x,y+1) != 2) return 20;
			if(gTI(x,y)==2 && gTI(x-1,y) != 2 && gTI(x,y+1) != 2) return 18;
			if(gTI(x,y)==2 && gTI(x-1,y) != 2 && gTI(x,y-1) != 2) return 2;
			if(gTI(x,y)==2 && gTI(x+1,y) != 2 && gTI(x,y-1) != 2) return 4;
			
			if(gTI(x,y)==2 && gTI(x+1,y) == 2 && gTI(x,y+1) == 2 && gTI(x-1,y) == 2 && gTI(x,y-1) == 2 && gTI(x+1,y+1) != 2) return 25;
			if(gTI(x,y)==2 && gTI(x+1,y) == 2 && gTI(x,y+1) == 2 && gTI(x-1,y) == 2 && gTI(x,y-1) == 2 && gTI(x-1,y+1) != 2)  return 26;
			if(gTI(x,y)==2 && gTI(x+1,y) == 2 && gTI(x,y+1) == 2 && gTI(x-1,y) == 2 && gTI(x,y-1) == 2 && gTI(x-1,y-1) != 2)  return 28;
			if(gTI(x,y)==2 && gTI(x+1,y) == 2 && gTI(x,y+1) == 2 && gTI(x-1,y) == 2 && gTI(x,y-1) == 2 && gTI(x+1,y-1) != 2)  return 27;
			

			if(gTI(x,y)==2 && gTI(x+1,y) != 2) return 12;
			if(gTI(x,y)==2 && gTI(x-1,y) != 2) return 10;
			if(gTI(x,y)==2 && gTI(x,y-1) != 2) return 3;
			if(gTI(x,y)==2 && gTI(x,y+1) != 2) return 19;
		
			return 11;
		}else if(id == 3){
			
			if(gTI(x,y)==3 && gTI(x+1,y) == 1 && gTI(x,y+1) == 1) return 51;
			if(gTI(x,y)==3 && gTI(x-1,y) == 1 && gTI(x,y+1) == 1) return 49;
			if(gTI(x,y)==3 && gTI(x-1,y) == 1 && gTI(x,y-1) == 1) return 33;
			if(gTI(x,y)==3 && gTI(x+1,y) == 1 && gTI(x,y-1) == 1) return 35;
			
			if(gTI(x,y)==3 && gTI(x+1,y) == 3 && gTI(x,y+1) == 3  && gTI(x+1,y+1) == 1) return 52;
			if(gTI(x,y)==3 && gTI(x,y+1) == 3 && gTI(x-1,y) == 3 && gTI(x-1,y+1) == 1)  return 53;
			if(gTI(x,y)==3 && gTI(x-1,y) == 3 && gTI(x,y-1) == 3 && gTI(x-1,y-1) == 1)  return 61;
			if(gTI(x,y)==3 && gTI(x+1,y) == 3 && gTI(x,y-1) == 3 && gTI(x+1,y-1) == 1)  return 60;
			

			if(gTI(x,y)==3 && gTI(x+1,y) == 1) return 43;
			if(gTI(x,y)==3 && gTI(x-1,y) == 1) return 41;
			if(gTI(x,y)==3 && gTI(x,y-1) == 1) return 34;
			if(gTI(x,y)==3 && gTI(x,y+1) == 1) return 50;
		
			return 8;
		}
		
		
		
		return 0;
	}
	public int gTI(int x,int z){
		return getTypeID(getIDByXY(x,z));
	}
	public int getTypeID(int id){
		if(id==1||id==9||id==17||id==42)return 1;
		if(id==2||id==3||id==4||id==10||id==11||id==12||id==18||id==19||id==20||id==25||id==26||id==27||id==28)return 2;
		if(id==8||id==33||id==34||id==35||id==36||id==37||id==41||id==43||id==44||id==45||id==49||id==50||id==51||id==52||id==53||id==60||id==61)return 3;
				return 0;
	}
	
	private int getNextID(int id){
		int width = (int) Math.sqrt(mainArray.length);
		int x = id%width;
		int y = id/width;
		int nextID=0;
		if(nextID==0&&getIDByXY(x+1,y)!=-1)nextID=getIDByXY(x+1,y);
		if(nextID==0&&getIDByXY(x-1,y)!=-1)nextID=getIDByXY(x-1,y);
		if(nextID==0&&getIDByXY(x,y+1)!=-1)nextID=getIDByXY(x,y+1);
		if(nextID==0&&getIDByXY(x,y-1)!=-1)nextID=getIDByXY(x,y-1);
		return nextID;
		
	}
	public int getIDByXY(int x,int y){
		int width = (int) Math.sqrt(mainArray.length);
		if(x<0||y<0||x>=width||y>=width||x+y*width>=mainArray.length)return -1;
		int id = mainArray[x+y*width];
		return id;
	}
	
	public void export(String mapName,boolean imageOutput){
		try{
	
			if(!imageOutput){
		File f = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+mapName
				+ ".txm");
		File f2 = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+"s" + mapName
				+ ".txm");
		if (!f.exists()) {
			f.createNewFile();
		} else {
			f.delete();
			f.createNewFile();
		}
		if (!f2.exists()) {
			f2.createNewFile();
		} else {
			f2.delete();
			f2.createNewFile();
		}
		
		writeFile(f,mainArray);
		writeFile(f2,subArray);
			}else{
				File f = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+"output"+mapName
						+ ".png");
				File f2 = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+"outputs" + mapName
						+ ".png");
				if(!f.exists())f.createNewFile();
				if(!f2.exists())f2.createNewFile();
				BufferedImage img1 = toBufferedImage("block",mainArray);
				BufferedImage img2 = toBufferedImage("sblock",subArray);
				ImageIO.write(img1, "PNG", f);
				ImageIO.write(img2, "PNG", f2);
			}
		}catch(Exception e){e.printStackTrace();}
	}
	PictureLoader imageLoader;
	public void loadTextures(){
		try{
		imageLoader=new PictureLoader();
		SheetLoader sheetloader = new SheetLoader(System.getProperty("user.dir")
				+  StartScreen.urlSplitter+"img"+ StartScreen.urlSplitter+"tiles.png", 8, 8, 16, 16);
		for (int a = 0; a < 8 * 8; a++) {
			imageLoader.ImportFromSheet("block_" + String.valueOf(a),
					sheetloader, a % 8, a / 8);
		}
		
		 sheetloader = new SheetLoader(System.getProperty("user.dir")
					+  StartScreen.urlSplitter+"img"+ StartScreen.urlSplitter+"special_tiles.png", 8, 8, 16, 16);
			for (int a = 0; a < 8 * 8; a++) {
				imageLoader.ImportFromSheet("sblock_" + String.valueOf(a),
						sheetloader, a % 8, a / 8);
			}
		}catch(Exception e){
			
		}
	}
	public void writeFile(File f,int[] array){
		int width = (int) Math.sqrt(array.length);
		try{
		FileOutputStream fw = new FileOutputStream(f);
		{
			for(int index=0;index<array.length;index++){
				if(index>0 && index%width==0)fw.write("\n".getBytes());
				else if(index>0)
				fw.write(",".getBytes());
				fw.write((array[index]+"").getBytes());
				if(index==array.length-1)fw.write("\n".getBytes());
			}
		}
		fw.close();
		}catch(Exception e){}
	}
	public BufferedImage toBufferedImage(String prefix,int[] array){
		int width = (int) Math.sqrt(array.length);
		BufferedImage bimg = new BufferedImage(width*16,width*16,3);
	    Graphics2D graphics2D = bimg.createGraphics();
	    graphics2D.setComposite(AlphaComposite.SrcOver);
	    for(int x=0;x<width;x++){
	    	for(int z=0;z<width;z++){
	    graphics2D.drawImage(imageLoader.getImage(prefix+"_"+array[x+z*width]), x*16, z*16, 16, 16, null);
	    	}
	    }
	     graphics2D.dispose();
	        return bimg;
	}
	public void load(String mapName) {
		String name = mapName;
		File in = new File(System.getProperty("user.dir") +  StartScreen.urlSplitter+"data"+ StartScreen.urlSplitter+name
				+ ".png");
	

		
		if(!in.exists()){System.out.println(in.getAbsolutePath()+"|| File not found");return;}
		try {
			BufferedImage bimg = ImageIO.read(in);
			int[] mainIDs = new int[bimg.getWidth()*bimg.getHeight()];
			int[] subIDs = new int[bimg.getWidth()*bimg.getHeight()];
			for(int x=0;x<bimg.getWidth();x++){
				for(int z=0;z<bimg.getHeight();z++){
					int pixel = bimg.getRGB(x, z);
					int red = ((pixel>>16)&0xFF);
					int green = ((pixel>>8)&0xFF);
					int blue= (pixel&0xFF);
//					int id=getIDByColor(red,green,blue);
					mainIDs[x+z*bimg.getWidth()] = getIDByColor(red,green,blue);
					subIDs[x+z*bimg.getWidth()] = getSIDByColor(red,green,blue);
				}	
				mainArray=mainIDs;
				subArray=subIDs;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int getIDByColor(int r,int g,int b){
		if(r==35 && g==35 && b==35){
			//Grass
			return 1;
		}else if(r==62 && g==62 && b==62){
			//Stone
			return 11;
		}else if(r==89 && g==89 && b==89){
			//Highground
			return 8;
		}else if(r==255 && g==0 && b==0){
			//Spawn
		}else if(r==255 && g==0 && b==255){
			//Portal
		}else if(r==0 && g==255 && b==0){
			//Tree
		}else if(r==255 && g==255 && b==0){
			//Enemy
		}else if(r==0 && g==255 && b==255){
			//S1
		}return 0;
	}
	private boolean collisionByID(int id){
		if(id==33||id==34||id==35)return true;
		if(id==41||id==34||id==43)return true;
		if(id==49||id==50||id==51)return true;
		if(id==52||id==53||id==60||id==61)return true;
		return false;
	}
	
	private int getSIDByColor(int r,int g,int b){
		if(r==35 && g==35 && b==35){
			//Grass
		}else if(r==62 && g==62 && b==62){
			//Stone
		}else if(r==89 && g==89 && b==89){
			//Highground
		}else if(r==255 && g==0 && b==0){
			//Spawn
			return mapID+7;
		}else if(r==255 && g==0 && b==255){
			//Portal
			return mapID+1+7;
		}else if(r==0 && g==255 && b==0){
			//Tree
			return 3;
		}else if(r==255 && g==255 && b==0){
			//Enemy
			return 5;
		}else if(r==0 && g==255 && b==255){
			//S1
			return 6;
		}return 0;
	}
	/*
	private boolean replaceIDByColor(int r,int g,int b){
		if(r==35 && g==35 && b==35){
			//Grass 
			return false;
		}else if(r==62 && g==62 && b==62){
			//Stone
			return false;
		}else if(r==89 && g==89 && b==89){
			//Highground
			return false;
		}else if(r==255 && g==0 && b==0){
			//Spawn
			return true;
		}else if(r==255 && g==0 && b==255){
			//Portal
			return true;
		}else if(r==0 && g==255 && b==0){
			//Tree
			return true;
		}else if(r==255 && g==255 && b==0){
			//Enemy
			return true;
		}else if(r==0 && g==255 && b==255){
			//S1
			return true;
		}return true;
	}*/
}
