package com.android.mechawars.map;


public class LoadAssets {
	
	//Camera details
	
	
	//Map details
	private String mapPath;
	private String characterImagePath;
	
	private int playerStartingPositionX;
	private int playerStartingPositionY;
	
	private int playerTextureAtlasSize; //Must be a power of two
	
	//Digital controller details
	private String controllerBody;
	private String controllerBodyKnob;
	
	public LoadAssets(){
		
		mapPath = "tmx/myTest.tmx";
		characterImagePath = "player.png";
		playerStartingPositionX = 0;
		playerStartingPositionY = 0;
		
		playerTextureAtlasSize = 128;
		
		
		controllerBody = "onscreen_control_base.png";//lolmybad.png";
		controllerBodyKnob = "onscreen_control_knob.png";		
		
		
	}
	
	public final String playerTexturePath(){
		return characterImagePath;
	}
	
	public final String gameMapPath(){
		return mapPath;
	}
	
	public final String digitalControllerBodyPath(){
		return controllerBody;
	}
	

	public final String digitalControllerBodyKnobPath(){
		return controllerBodyKnob;
	}
	
	public final int playerPositionX(){
		return playerStartingPositionX;
	}
	
	public final int playerPositionY(){
		return playerStartingPositionY;
	}
	
	public final int playerBitmapTextureAtlasSize(){
		return playerTextureAtlasSize;
	}
}
