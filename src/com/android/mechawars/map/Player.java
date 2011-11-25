package com.android.mechawars.map;

public class Player extends CharacterNPC{
	
	//Keeps the character movement necessary
	final float movementDuration = LoadAssets.movementTiming;

	public Player(String name, CharacterResources resourcesToBeLoaded) {
		super(name, resourcesToBeLoaded);
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

}
