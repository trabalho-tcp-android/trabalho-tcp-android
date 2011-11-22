package com.android.mechawars.map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.android.mechawars.map.characters.LoadCharacterSprites;

public class CharacterNPC {
	
	private final float offsetX = LoadAssets.playerTileAdjustmentOffsetX;
	private final float offsetY = LoadAssets.playerTileAdjustmentOffsetY;
	
	private String characterName;
	
	public AnimatedSprite characterSprite;
	
	private String characterTexturePath;
	
	private float characterX;
	
	private int characterColumn;
	
	private float characterY;
	
	private int characterRow;
	
	private float mapTileWidth;
	
	
	private long[] characterAnimation;
	
	private int characterSpritePixelWidth;
	private int characterSpritePixelHeight;
	
	private LoadCharacterSprites npcResourceLoader;
	
	//Character constructor
	public CharacterNPC(final String name, final int posX, final int posY, final float tileWidth, final TiledTextureRegion npcTexture,final long[] animation){
		this.characterName = name;
		
		characterX = ((float)posX)*tileWidth;
		characterY = ((float)posY)*tileWidth;
		
		characterColumn = posX;
		characterRow = posY;
		
		mapTileWidth = tileWidth;
		
		characterSprite = new AnimatedSprite(characterX + offsetX, characterY - offsetY, npcTexture);
		
		characterSprite.animate(animation);
		//Add the character to the scene after this method is computed
	}
	
	//TODO : Handle this case, when we have to load the texture!!!!! (THE TEXTURE AIN'T BEEN INITIALIZED!)
	public CharacterNPC(final String name, final int posX, final int posY, final float tileWidth, final String npcTexturePath,final int spriteSizeX,final int spriteSizeY,final long[] animation){
		this.characterName = name;
		
		characterX = ((float)posX)*tileWidth;
		characterY = ((float)posY)*tileWidth;
		
		characterColumn = posX;
		characterRow = posY;
		
		mapTileWidth = tileWidth;
		
		characterTexturePath = npcTexturePath;
		
		characterAnimation = animation;
		//Add the character to the scene after this method is computed
	}	
	
	public String characterName(){
		return characterName;
	}
	
	public void setPosition(float pX, float pY){
		this.characterSprite.setPosition(pX, pY);
	}
	
	public void setPositionOnMap(int column, int row){
		this.characterSprite.setPosition(((float)column)*mapTileWidth + offsetX, ((float)row)*mapTileWidth - offsetY);
	}
	
	public int getCharacterColumn(){
		return characterColumn;
	}
	
	public int getCharacterRow(){
		return characterRow;
	}
	
	public String getCharacterTexturePath(){
		return characterTexturePath;
	}
	
	public void loadNPCTextures(Engine gameEngine, Context gameContext){
		
		npcResourceLoader.loadTexture(gameEngine, this.characterTexturePath, gameContext, characterSpritePixelWidth, characterSpritePixelHeight, spriteSheetColumns, spriteSheetRows)
	}
}
