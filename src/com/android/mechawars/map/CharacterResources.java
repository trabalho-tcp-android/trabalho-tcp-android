package com.android.mechawars.map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import com.android.mechawars.map.characters.LoadCharacterSprites;

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
	
	
	
	public CharacterResources(final int posX, final int posY, final float tileWidth,final float tileHeight, final String npcTexturePath,final int spriteSizeX,final int spriteSizeY,Animations animationSet,String initialAnimation, int spriteSheetColumns, int spriteSheetRows){
		
		characterCoordinates[0] = ((float)posX)*tileWidth + offsetX;
		characterCoordinates[1] = ((float)posY)*tileWidth - offsetY;
		
		characterPosition[0] = posX;
		characterPosition[1] = posY;
		
		mapTileWidth = tileWidth;
		
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
	public void updateCharacterPosition(final int column,final int row){
		
		characterPosition[0] = column;
		characterPosition[1] = row;
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
		characterSprite.animate(characterAnimationSet.getAnimation(initialAnimation));
	}
	

	public void initializeSprite(){
		characterSprite = new AnimatedSprite(characterCoordinates[0],characterCoordinates[1],characterResourceLoader.getTextureRegion());
		characterSprite.animate(characterAnimationSet.getAnimation(characterInitialAnimation));
	}
	
	
	
	public void detachCharacterNPC(){
		this.characterSprite.detachSelf();
	}
	
	public AnimatedSprite getCharacterSprite(){
		return this.characterSprite;
	}
	
	public void setCharacterPosition(int Column, int Row){
		
		this.characterCoordinates[0] = ((float)Column)*mapTileWidth + offsetX;
		this.characterCoordinates[1] = ((float)Row)*mapTileHeight - offsetY;
		
		this.characterSprite.setPosition(characterCoordinates[0],characterCoordinates[1]);

		
		this.characterPosition[0] = Column;
		this.characterPosition[1] = Row;
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
		
		float finalPositionX = (float)column*mapTileWidth + offsetX;
		float finalPositionY = (float)column*mapTileHeight + offsetY;
		
		MoveModifier pathModifier = new MoveModifier(duration,this.characterSprite.getX(),finalPositionX,this.characterSprite.getY(),finalPositionY);
		
		
		//Testing the correct animation to be executed
		if(column < this.characterPosition[0])
			changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_LEFT, false);
		if(column > this.characterPosition[0])
			changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_RIGHT, false);
		if(row < this.characterPosition[0])
			changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_UP, false);
		if(row > this.characterPosition[0])
			changeCharacterAnimation(LoadAssets.ANIMATE_WALKING_FACING_DOWN, false);
		
		this.characterSprite.registerEntityModifier(pathModifier);
		
		setCharacterPosition(column, row);
		
		
	}
}
