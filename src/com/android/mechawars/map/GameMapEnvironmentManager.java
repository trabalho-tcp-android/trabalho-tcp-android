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
import com.android.mechawars.map.controller.GameDigitalController;
import com.android.mechawars.map.controller.GameInteractionButton;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GameMapEnvironmentManager {
	Player gamePlayer;
	
	MapManager gameMap;
	
	CharacterGroupManager npcGroup;
	//BUTTON
	//private GameInteractionButton gameIntButton;
	
	//CONTROLLER
	/*
	private DigitalOnScreenControl digitalController;
	
	private BitmapTextureAtlas controllerTexture;
	
	private TextureRegion digitalControllerBase;
	
	private TextureRegion digitalControllerKnob;
	
	private int moveHorizontally;
	
	private int moveVertically;
	
	private Boolean lockController;
	
	private Boolean initializedAnimations = false;*/
	
	//end CONTROLLER
	
	GameDigitalController digitalController;
	
	public GameMapEnvironmentManager(Engine gameMapEngine,Scene gameMapScene, BoundCamera gameCamera, Context gameMapContext){
		
		initializeMapEnvironment(gameMapEngine, gameMapScene, gameMapContext);
		digitalController = new GameDigitalController(gameMapEngine, gameMapScene, gameMapContext, gameCamera);
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
	
	public void moveGamePlayer(){
		int characterNextColumn = gamePlayer.getCharacterColumn() + digitalController.getXVariation();
		int characterNextRow = gamePlayer.getCharacterRow() + digitalController.getYVariation();
		Boolean canMove = !gameMap.isTheTileBlocked(characterNextColumn,characterNextRow) && !npcGroup.isOccupied(characterNextColumn, characterNextRow);
		
		if(canMove){
					gamePlayer.movePlayer(characterNextColumn, characterNextRow);
					
		}
		else{
			gamePlayer.turnPlayer(digitalController.getXVariation(),digitalController.getYVariation());
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
	

	
	public AnimatedSprite getPlayerSprite(){
		return this.gamePlayer.getCharacterSprite();
	}
	
	public void initializeAnimations(){
		
		gamePlayer.changeAnimation(gamePlayer.getInitialAnimation(), false);
		npcGroup.animateSprites();
		
	}
	
	public void showWon(){
		if(BattleInterfaceManager.playerWonBattle()){
			//Toast.makeText(SceneManager.getBase(), "You won the battle! Congratulations!",Toast.LENGTH_SHORT).show();
			System.out.println("VENCEU A BATALHA!");
		}
		else{
			//Toast.makeText(SceneManager.getBase(), "You lost the battle! So bad... Getting revived!",Toast.LENGTH_SHORT).show();
			System.out.println("PERDEU A BATALHA! DROGA...");
			BattleInterfaceManager.getPlayerRobot().recovery();
		}
	}
	

}
