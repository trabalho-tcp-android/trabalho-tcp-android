package com.android.mechawars.map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import com.android.mechawars.map.characters.CharacterGroupParser;

import android.content.Context;

public class GameMapEnvironmentManager {
	Player gamePlayer;
	
	MapManager gameMap;
	
	CharacterGroupManager npcGroup;
	
	public void initializeMapEnvironment(Engine gameEngine,Scene gameMapScene,Context gameMapContext){
		//First, initialize the map.
		gameMap = new MapManager(gameMapContext, gameEngine, gameMapScene);

		//Now, we can put the characters on it.
		initializeNPCGroup(gameEngine, gameMapScene, gameMapContext);
		
		gamePlayer = new Player(LoadAssets.playerName, CharacterGroupParser.getPlayerResources());
		
	}
	
	public void initializeNPCGroup(Engine gameEngine,Scene gameMapScene,Context gameMapContext){
		
		npcGroup = new CharacterGroupManager(gameMap.getMapColumns(), gameMap.getMapRows());
		
		npcGroup.addCharactersToScene(gameMapScene, gameEngine, gameMapContext);
		
	}
}
