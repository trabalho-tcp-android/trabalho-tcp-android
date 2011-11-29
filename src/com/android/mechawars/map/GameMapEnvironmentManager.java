package com.android.mechawars.map;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;

import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;

import com.android.mechawars.BattleInterfaceManager;
import com.android.mechawars.SceneManager;
import com.android.mechawars.MechawarsBattleActivity;
import com.android.mechawars.ffBox.ffDialog.DialogManager;
import com.android.mechawars.map.characters.CharacterGroupManager;
import com.android.mechawars.map.characters.CharacterGroupParser;
import com.android.mechawars.map.characters.Player;
import com.android.mechawars.map.controller.GameDigitalController;
import org.anddev.andengine.engine.handler.timer.TimerHandler;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


/*
 * THIS CLASS LOADS THE MAP, ALONG WITH ITS MAIN CHARACTER AND NPCS.
 */
public class GameMapEnvironmentManager {
	private Player gamePlayer;
	
	private MapManager gameMap;
	
	private CharacterGroupManager npcGroup;
	
	public GameDigitalController digitalController;
	
	//Constructor for this class, which initializes the map environment features.
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
	
	//This method both loads the NPCs from the files and adds them to the scene.
	public void initializeNPCGroup(Engine gameEngine,Scene gameMapScene,Context gameMapContext){
		
		npcGroup = CharacterGroupParser.parseCharacters(gameMap.getMapColumns(), gameMap.getMapRows());
				
		npcGroup.addCharactersToScene(gameMapScene, gameEngine, gameMapContext);
		
		
	}
	
	//This method implements the character movement through the map scene.
	public void moveGamePlayer(){
		int characterNextColumn = gamePlayer.getCharacterColumn() + digitalController.getXVariation();
		int characterNextRow = gamePlayer.getCharacterRow() + digitalController.getYVariation();
		
		//Testing whether the passage is clear for the character to walk.
		Boolean canMove = !gameMap.isTheTileBlocked(characterNextColumn,characterNextRow) && !npcGroup.isOccupied(characterNextColumn, characterNextRow);
		
		if(canMove){
					gamePlayer.movePlayer(characterNextColumn, characterNextRow);
					
		}
		else{
			gamePlayer.turnPlayer(digitalController.getXVariation(),digitalController.getYVariation());
			//Testing whether the next tile is occupied by another character.
			if(npcGroup.isOccupied(characterNextColumn, characterNextRow)){
				System.out.println("Character found! (" + npcGroup.getCharacterAt(characterNextColumn, characterNextRow) + ")");
				
				
				//Meeting up, maybe it is time to battle!
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
	
	//This method is called whenever a battle is finished.
	public void showWon(){
		
		TimerHandler myTimerHandler = new TimerHandler(2f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
    			
        		if(BattleInterfaceManager.playerWonBattle()){
        			DialogManager.instance().fromJSON("youWonMessage").attachToScene();
        			System.out.println("VENCEU A BATALHA!");
        		}
        		else{
        			DialogManager.instance().fromJSON("youLostMessage").attachToScene();
        			System.out.println("PERDEU A BATALHA! DROGA...");
        			BattleInterfaceManager.getPlayerRobot().recovery();
        		}
            	
            }
		});
		GameMapActivityManager.getMapScene().registerUpdateHandler(myTimerHandler);

	}
	

}
