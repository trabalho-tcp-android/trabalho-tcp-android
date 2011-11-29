package com.android.mechawars.map;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;

import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import com.android.mechawars.BattleInterfaceManager;
import com.android.mechawars.MechaWarsMapActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.MechawarsBattleActivity;
import com.android.mechawars.ffBox.ffDialog.DialogManager;
import com.android.mechawars.map.characters.CharacterGroupManager;
import com.android.mechawars.map.characters.CharacterGroupParser;
import com.android.mechawars.map.characters.Player;
import com.android.mechawars.map.controller.GameInteractionButton;


import android.content.Context;
import android.content.Intent;

public class GameMapEnvironmentManager {
	Player gamePlayer;
	
	MapManager gameMap;
	
	CharacterGroupManager npcGroup;
	//BUTTON
	private GameInteractionButton gameIntButton;
	
	//CONTROLLER
	private DigitalOnScreenControl digitalController;
	
	private BitmapTextureAtlas controllerTexture;
	
	private TextureRegion digitalControllerBase;
	
	private TextureRegion digitalControllerKnob;
	
	private int moveHorizontally;
	
	private int moveVertically;
	
	private Boolean lockController;
	
	private Boolean initializedAnimations = false;
	
	//end CONTROLLER	
	
	public GameMapEnvironmentManager(Engine gameMapEngine,Scene gameMapScene, BoundCamera gameCamera, Context gameMapContext){
		
		initializeMapEnvironment(gameMapEngine, gameMapScene, gameMapContext);
		setController(gameMapEngine, gameMapScene, gameMapContext, gameCamera);
		gameCamera.setChaseEntity(this.gamePlayer.getCharacterSprite());
		
	}
	
	public void initializeMapEnvironment(Engine gameEngine,Scene gameMapScene,Context gameMapContext){
		//First, initialize the map.
		gameMap = new MapManager(gameMapContext, gameEngine, gameMapScene);

		//Now, we can put the characters on it.
		initializeNPCGroup(gameEngine, gameMapScene, gameMapContext);
		
		gamePlayer = new Player(gameEngine,gameMapContext,LoadAssets.playerName, CharacterGroupParser.getPlayerResources());
		
		gamePlayer.attachPlayerToScene(gameMapScene);
		
	}
	
	public void initializeNPCGroup(Engine gameEngine,Scene gameMapScene,Context gameMapContext){
		
		npcGroup = CharacterGroupParser.parseCharacters(gameMap.getMapColumns(), gameMap.getMapRows());
				
		npcGroup.addCharactersToScene(gameMapScene, gameEngine, gameMapContext);
		
		
	}
	
	private void moveGamePlayer(){
		int characterNextColumn = gamePlayer.getCharacterColumn() + getXVariation();
		int characterNextRow = gamePlayer.getCharacterRow() + getYVariation();
		Boolean canMove = !gameMap.isTheTileBlocked(characterNextColumn,characterNextRow) && !npcGroup.isOccupied(characterNextColumn, characterNextRow);
		
		if(canMove){
					gamePlayer.movePlayer(characterNextColumn, characterNextRow);
					
		}
		else{
			gamePlayer.turnPlayer(getXVariation(),getYVariation());
			//Testing whether the next tile is occupied by another character.
			if(npcGroup.isOccupied(characterNextColumn, characterNextRow)){
				System.out.println("Character found! (" + npcGroup.getCharacterAt(characterNextColumn, characterNextRow) + ")");
				

				//Meeting up, maybe time to battle!
				if(!npcGroup.getCharacter(characterNextColumn, characterNextRow).metAlready()){
					npcGroup.getCharacter(characterNextColumn, characterNextRow).setMetAlready();
					BattleInterfaceManager.setPlayerRobot(this.gamePlayer.getRobot());
					BattleInterfaceManager.setEnemyRobot(npcGroup.getCharacter(characterNextColumn, characterNextRow).getRobot());
					Intent battleActivity = new Intent(SceneManager.getBase(), MechawarsBattleActivity.class);
			        SceneManager.getBase().startActivity(battleActivity);
				}
			}
			
		}
	}
	
	public void setController(Engine gameMapEngine, Scene gameMapScene,Context gameMapContext, BoundCamera gameCamera){
		
		loadTextures(gameMapEngine,gameMapContext);
		
		
		lockController = false;
		
		digitalController = new DigitalOnScreenControl(0, LoadAssets.CAMERA_HEIGHT - this.controllerTexture.getHeight(), gameCamera, this.digitalControllerBase, this.digitalControllerKnob, LoadAssets.controllerRefreshTime, new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if(!lockController){
					if(!initializedAnimations)
					{
						initializeAnimations();
						initializedAnimations = true;
					}
					if(pValueX != 0 || pValueY != 0){
						moveHorizontally = (int)pValueX;
						moveVertically = (int)pValueY;
						moveGamePlayer();
					}
				}
			}
		});
		
		gameMapScene.attachChild(digitalController);
		
		digitalController.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		digitalController.getControlBase().setAlpha(0.5f);
		digitalController.getControlKnob().setAlpha(0.5f);
		
		gameMapScene.setChildScene(digitalController);
	}
	
	
	//Loading the textures for the controller.
	private void loadTextures(Engine gameMapEngine, Context gameMapContext){
		
		controllerTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		digitalControllerBase = BitmapTextureAtlasTextureRegionFactory.createFromAsset(controllerTexture, gameMapContext, LoadAssets.controllerBody, 0, 0);
		digitalControllerKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset(controllerTexture, gameMapContext, LoadAssets.controllerBodyKnob, 128, 0);

		gameMapEngine.getTextureManager().loadTexture(controllerTexture);
	}
	
	private int getXVariation(){
		return moveHorizontally;
	}
	
	private int getYVariation(){
		return moveVertically;
	}
	
	public DigitalOnScreenControl getController(){
		return digitalController;
	}
	
	public void lockGameController(){
		lockController = true;
		moveVertically = 0;
		moveHorizontally = 0;
	}
	
	public void unlockGameController(){
		lockController = false;
	}
	
	public AnimatedSprite getPlayerSprite(){
		return this.gamePlayer.getCharacterSprite();
	}
	
	public void initializeAnimations(){
		
		gamePlayer.changeAnimation(gamePlayer.getInitialAnimation(), false);
		npcGroup.animateSprites();
		
	}
	

}
