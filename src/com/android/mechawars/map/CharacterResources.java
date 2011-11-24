package com.android.mechawars.map;

import org.anddev.andengine.engine.Engine;
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
	
	private float offsetX = LoadAssets.playerTileAdjustmentOffsetX;
	
	private float offsetY = LoadAssets.playerTileAdjustmentOffsetY;
	
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
		
		return ((float)getColumn())*mapTileWidth;
		
	}
	
	public float getCharacterInTilePositionY(){
		
		return ((float)getRow())*mapTileHeight;
		
	}
	
	
	//These methods are concerned about the character coordinates on the scenario
	//TODO: Fix the integer positions too!!
	public void updateCharacterCoordinates(final float posX,final float posY){
		
		characterCoordinates[0] = posX;
		characterCoordinates[1] = posY;
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
}
