package com.android.mechawars.map.characters;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import com.android.mechawars.map.LoadAssets;
import com.android.mechawars.map.TilePropertyMatrix;

import android.content.Context;

public class Player extends CharacterNPC{
	
	//Keeps the character movement necessary
	final float movementDuration = LoadAssets.movementTiming;

	public Player(Engine gameMapEngine,Context gameMapContext, String name, CharacterResources resourcesToBeLoaded) {
		super(name, resourcesToBeLoaded);
		this.loadNPCTextures(gameMapEngine, gameMapContext);
	}
	
	public void attachPlayerToScene(Scene gameMapScene){
		gameMapScene.attachChild(this.getCharacterSprite());
	}
	
	public void changePlayerName(String newName){
		this.characterName = newName;
	}
	
	//Use this method when you want the map blocked tile features have not been treated yet.
	public void movePlayer(int nextColumn,int nextRow,TilePropertyMatrix blockedTiles){
		if(!blockedTiles.isBlocked(nextColumn, nextRow)){
			this.characterResources.moveCharacterSprite(movementDuration, nextColumn, nextRow);
		}
	}
	
	//Use this method when you want the map blocked tile features have been treated already.
	public void movePlayer(int nextColumn,int nextRow){
		this.characterResources.moveCharacterSprite(movementDuration, nextColumn, nextRow);
	}
	
	//This next method allows the player sprite to change its orientation according to the necessity.
	public void turnPlayer(int varX, int varY){
		this.characterResources.turnSprite(varX, varY);
	}

}
