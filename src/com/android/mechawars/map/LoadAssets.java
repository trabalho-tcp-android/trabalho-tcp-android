package com.android.mechawars.map;


public class LoadAssets {
	
	//Camera details
	
	public static final int CAMERA_WIDTH = 400;
	public static final int CAMERA_HEIGHT = 300;
	
	//Controller refresh updates timing
	public final static float controllerRefreshTime = 0.1f;
	
	//Map details
	//TODO: fix these two things
	public final static String mapPath = "tmx/myTest.tmx";
	
	public final static String playerName = "Dovahkiin";
	
	
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
	public final static String controllerBody = "onscreen_control_base.png";
	public final static String controllerBodyKnob = "onscreen_control_knob.png";
	
	public LoadAssets(){
	
		characterImagePath = "player.png";
		playerStartingPositionX = 0;
		playerStartingPositionY = 0;
		
		playerTextureAtlasSize = 128;
		
		
		
		
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
