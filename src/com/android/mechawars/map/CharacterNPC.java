package com.android.mechawars.map;

import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

public class CharacterNPC {
	
	private final float offsetX = 5;
	private final float offsetY = 7;
	
	private String characterName;
	public AnimatedSprite characterSprite;
	
	private float characterX;
	private int characterColumn;
	
	private float characterY;
	private int characterRow;
	
	private float mapTileWidth;
	
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
}
