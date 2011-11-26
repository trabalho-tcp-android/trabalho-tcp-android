package com.android.mechawars.map.characters;

import java.util.HashMap;
import java.util.LinkedList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;

import com.android.mechawars.map.TileOccupiedMatrix;
import com.android.mechawars.map.TilePropertyMatrix;

import android.content.Context;

public class CharacterGroupManager {
	
	private HashMap<String,CharacterNPC> hashTableOfCharacters; //Useful for querying for a given character on the scene
	private LinkedList<CharacterNPC> listOfCharacters;          //Useful for iterating 
	private TileOccupiedMatrix characterPositionMatrix;
	
	
	//Constructor
	public CharacterGroupManager(final int mapColumns, final int mapRows){
		
		hashTableOfCharacters = new HashMap<String,CharacterNPC>();
		listOfCharacters = new LinkedList<CharacterNPC>();
		
		characterPositionMatrix = new TileOccupiedMatrix(mapColumns, mapRows);
	}
	
	
	//Character Adder
	public void addCharacterNPC(CharacterNPC newCharacter){
		
		hashTableOfCharacters.put(newCharacter.characterName(), newCharacter);
		listOfCharacters.add(newCharacter);
		
		characterPositionMatrix.setTileValue(newCharacter.getCharacterColumn(), newCharacter.getCharacterRow(),true);
	}
	
	//Character Returner
	public CharacterNPC getCharacter(String characterNPCName){
		
		return hashTableOfCharacters.get(characterNPCName);
		
	}
	
	//Hides characters in scene
	public void hideCharacterFromScene(final String characterNPCName){
		
		CharacterNPC characterToBeKilled = getCharacter(characterNPCName);
		
		if(characterToBeKilled != null){
			
			//Resetting the position on which the character was on the property map
			characterPositionMatrix.setTileValue(characterToBeKilled.getCharacterColumn(), characterToBeKilled.getCharacterRow(),false);
		
			characterToBeKilled.detachCharacterSprite();
		
		}
				
	}
	
	//Adds each and every character in the list to the scene
	public void addCharactersToScene(Scene gameScene,Engine gameEngine,Context gameContext){
		int listIndex;
		CharacterNPC currentCharacter;
		
		for(listIndex = 0; listIndex < listOfCharacters.size(); listIndex++){
			
			currentCharacter = listOfCharacters.get(listIndex);
			
			currentCharacter.loadNPCTextures(gameEngine, gameContext);
			
			gameScene.attachChild(currentCharacter.getCharacterSprite());
			
			currentCharacter.changeAnimation(currentCharacter.getInitialAnimation(), false);
			
			System.out.println("Character " + listOfCharacters.get(listIndex).characterName() + " added successfully to the scene.");
		}
	}
	
	public void animateSprites(){
		int listIndex;
		CharacterNPC currentCharacter;
		
		for(listIndex = 0; listIndex < listOfCharacters.size(); listIndex++){
			
			currentCharacter = listOfCharacters.get(listIndex);
			
			currentCharacter.changeAnimation(currentCharacter.getInitialAnimation(), false);
		}
	}
	
	//This method returns the information whether a 
	public Boolean isOccupied(final int posColumn, final int posRow){
	
		return characterPositionMatrix.isBlocked(posColumn, posRow);
	}
	
	public void removeCharactersFromMap(){
		
		int listIndex;//List iterator
		
		for(listIndex = 0; listIndex < listOfCharacters.size(); listIndex++){
			listOfCharacters.get(listIndex).detachCharacterSprite();
		}
		
		listOfCharacters.clear();
		hashTableOfCharacters.clear();
		
	}
	
	//Character Returner
	public CharacterNPC getCharacter(final int characterPosX,final int characterPosY){
		
		int listIndex = 0;
		
		while(listIndex < listOfCharacters.size()){
			if(listOfCharacters.get(listIndex).getCharacterColumn() == characterPosX && listOfCharacters.get(listIndex).getCharacterRow() == characterPosY){
				return listOfCharacters.get(listIndex);
			}
			else{
				listIndex++;
			}
		}
		return null;
		
	}
	
	public String getCharacterAt(int column,int row){
		
		return getCharacter(column,row).characterName;
		
	}
	
}
