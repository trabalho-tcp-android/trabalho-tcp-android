package com.android.mechawars.map.characters;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;


import com.android.mechawars.map.Animations;
import com.android.mechawars.map.LoadAssets;

import android.content.Context;

public class CharacterResources {

	//Character texture resources
	private LoadCharacterSprites characterResourceLoader = new LoadCharacterSprites();	
	
	private AnimatedSprite characterSprite;
	
	private String characterTexturePath;
	
	private final float offsetX = LoadAssets.playerTileAdjustmentOffsetX;
	
	private final float offsetY = LoadAssets.playerTileAdjustmentOffsetY;
	
	private float mapTileWidth;
	
	private float mapTileHeight;
	
	
	private Animations characterAnimationSet;
	
	private int characterSpritePixelWidth;
	
	private int characterSpritePixelHeight;
	
	private int spriteSheetColumns;
	
	private int spriteSheetRows;
	
	private int[] characterPosition = {0,0};
	
	private float[] characterCoordinates = {0,0};
	
	private String characterInitialAnimation;
		
	
	//Initializes the informations regarding the character to be exhibited on the map.
	public CharacterResources(final int posX, final int posY, final float tileWidth,final float tileHeight, final String npcTexturePath,final int spriteSizeX,final int spriteSizeY,Animations animationSet,String initialAnimation, int spriteSheetColumns, int spriteSheetRows){
		
		characterCoordinates[0] = ((float)posX)*tileWidth + offsetX;
		characterCoordinates[1] = ((float)posY)*tileHeight - offsetY;
		
		characterPosition[0] = posX;
		characterPosition[1] = posY;
		
		mapTileWidth = tileWidth;
		
		mapTileHeight = tileHeight;
		
		characterTexturePath = npcTexturePath;
		
		characterAnimationSet = animationSet;
		
		characterInitialAnimation = initialAnimation;
		
		characterSpritePixelWidth = spriteSizeX;
		
		characterSpritePixelHeight = spriteSizeY;
		
		this.spriteSheetColumns = spriteSheetColumns;
		
		this.spriteSheetRows = spriteSheetRows;
		
	}
	
	public void loadTexture(Engine gameEngine,Context callerContext){
		
		characterResourceLoader.loadTexture(gameEngine, this.characterTexturePath, callerContext, characterSpritePixelWidth, characterSpritePixelHeight, spriteSheetColumns, spriteSheetRows);
		initializeSprite(characterInitialAnimation);
	}
	

	
	//These methods are concerned about the character position in the TMX Map	
	private void updateCharacterPosition(final int column,final int row){
		
		characterPosition[0] = column;
		characterPosition[1] = row;
		
		characterCoordinates[0] = ((float)column)*mapTileWidth + offsetX;
		characterCoordinates[1] = ((float)row)*mapTileHeight - offsetY;
				
	}
	
	public int getColumn(){
		
		return characterPosition[0];
	}
	
	public int getRow(){
		
		return characterPosition[1];
		
	}
	
	public float getCharacterInTilePositionX(){
		
		return ((float)getColumn())*mapTileWidth + offsetX;
		
	}
	
	public float getCharacterInTilePositionY(){
		
		return ((float)getRow())*mapTileHeight - offsetX;
		
	}
	
	public float getPosX(){
		
		return characterCoordinates[0];
	}
	
	public float getPosY(){
		
		return characterCoordinates[1];
		
	}
	
	//Sprite methods
	//Initializes the Sprite	
	public void initializeSprite(String initialAnimation){
		characterSprite = new AnimatedSprite(characterCoordinates[0],characterCoordinates[1],characterResourceLoader.getTextureRegion());
		characterSprite.animate(characterAnimationSet.getAnimation(initialAnimation),false);
	}
	

	public void initializeSprite(){
		characterSprite = new AnimatedSprite(characterCoordinates[0],characterCoordinates[1],characterResourceLoader.getTextureRegion());
		characterSprite.animate(characterAnimationSet.getAnimation(characterInitialAnimation),false);
	}
	
	
	
	public void detachCharacterNPC(){
		this.characterSprite.detachSelf();
	}
	
	public AnimatedSprite getCharacterSprite(){
		return this.characterSprite;
	}
	
	public void setCharacterPosition(int column, int row){
		
		this.characterCoordinates[0] = ((float)column)*mapTileWidth + offsetX;
		this.characterCoordinates[1] = ((float)row)*mapTileHeight - offsetY;
		
		this.characterSprite.setPosition(characterCoordinates[0],characterCoordinates[1]);

		
		this.characterPosition[0] = column;
		this.characterPosition[1] = row;
	}
	
	
	//TODO: fix the integer position!!!! It must be relative to t
	public void setCharacterPosition(float posX, float posY){
		this.characterSprite.setPosition(posX, posY);
	}
	
	public String getCharacterTexturePath(){
		return characterTexturePath;
	}
	
	//Sets a new animation for the character
	public void changeCharacterAnimation(String animation,Boolean loopAnimation){
		
		this.characterSprite.animate(this.characterAnimationSet.getAnimation(animation), loopAnimation);
		
	}
	
	public void moveCharacterSprite(float duration, int column, int row){
		
		
		float finalPositionX = (float)column*mapTileWidth  + offsetX;
		float finalPositionY = (float)row*mapTileHeight - offsetY;
		
		MoveModifier pathModifier = new MoveModifier(duration,this.characterSprite.getX(),finalPositionX,this.characterSprite.getY(),finalPositionY);
		
		if(!this.characterSprite.isAnimationRunning()){
		//Testing the correct animation to be executed
			if(finalPositionX < this.characterSprite.getX())
				changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_LEFT, false);
			if(finalPositionX > this.characterSprite.getX())
				changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_RIGHT, false);
			if(finalPositionY < this.characterSprite.getY())
				changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_UP, false);
			if(finalPositionY > this.characterSprite.getY())
				changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_DOWN, false);
			
			this.characterSprite.registerEntityModifier(pathModifier);
			
			updateCharacterPosition(column, row);
		}
		
	}
	
	public void turnSprite(int varX, int varY){
		if(!this.characterSprite.isAnimationRunning()){
		//Testing the correct orientation to be shown.
			if(varX < 0)
				changeCharacterAnimation(LoadAssets.ANIMATE_FACING_LEFT, false);
			if(varX > 0)
				changeCharacterAnimation(LoadAssets.ANIMATE_FACING_RIGHT, false);
			if(varY < 0)
				changeCharacterAnimation(LoadAssets.ANIMATE_FACING_UP, false);
			if(varY > 0)
				changeCharacterAnimation(LoadAssets.ANIMATE_FACING_DOWN, false);
		}
	}
	
	public String getInitialAnimationLabel(){
		
		return this.characterInitialAnimation;
	}
}
