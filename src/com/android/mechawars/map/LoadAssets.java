package com.android.mechawars.map;


public class LoadAssets {
	
	//Camera details
	
	
	//Map details
	private String mapPath;
	private String characterImagePath;
	
	private int playerStartingPositionX;
	private int playerStartingPositionY;
	
	private int playerTextureAtlasSize; //Must be a power of two
	
	public static final float playerTileAdjustmentOffsetX = 5;
	public static final float playerTileAdjustmentOffsetY = 7;
	
	//Animation identifiers
	public final static String ANIMATE_FACING_UP = "ANIMATE_FACING_UP";
	
	public final static String ANIMATE_FACING_DOWN = "ANIMATE_FACING_DOWN";
		
	public final static String ANIMATE_FACING_RIGHT = "ANIMATE_FACING_RIGHT";
	
	public final static String ANIMATE_FACING_LEFT = "ANIMATE_FACING_LEFT";
	
	public final static String ANIMATE_WALKING_FACING_UP = "ANIMATE_WALKING_FACING_UP";
	
	public final static String ANIMATE_WALKING_FACING_DOWN = "ANIMATE_WALKING_FACING_DOWN";
	
	public final static String ANIMATE_WALKING_FACING_RIGHT = "ANIMATE_WALKING_FACING_RIGHT";
	
	public final static String ANIMATE_WALKING_FACING_LEFT = "ANIMATE_WALKING_FACING_LEFT";
	
	//Player movement duration
	public final static float movementTiming = 0.6f;
	
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
