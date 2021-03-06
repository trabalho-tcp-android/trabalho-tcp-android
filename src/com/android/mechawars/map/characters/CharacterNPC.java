package com.android.mechawars.map.characters;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.android.mechawars.battle.BattleRobot;
import com.android.mechawars.battle.BattleWeapon;
import com.android.mechawars.map.Animations;

public class CharacterNPC {
	
	protected String characterName;
	
	protected CharacterResources characterResources;
	
	private Boolean battled = false;
	
	private BattleRobot characterRobot;
	
	public CharacterNPC(final String name, final int posX, final int posY, final float tileWidth,final float tileHeight, final String npcTexturePath,final int spriteSizeX,final int spriteSizeY,Animations animationSet,String initialAnimation, int spriteSheetColumns, int spriteSheetRows){
		this.characterName = name;
		
		this.characterResources = new CharacterResources(posX, posY, tileWidth, tileHeight, npcTexturePath, spriteSizeX, spriteSizeY, animationSet, initialAnimation, spriteSheetColumns, spriteSheetRows);
		//Add the character to the scene after this method is computed
	}
	
	//TODO: see if initialMaxWeapon already limits the quantity of weapons;
	public void setBattleRobot(int initialMaxHp, int initialMaxWeapon, int initialImageNumber, int[][] characterWeapons,int qntWeapons){
		characterRobot = new BattleRobot(initialMaxHp, initialMaxWeapon, initialImageNumber);
		
		int i;
		
		for(i = 0; i < qntWeapons; i++){
			BattleWeapon newCharacterWeapon = new BattleWeapon(characterWeapons[i][0], characterWeapons[i][1],characterWeapons[i][2]);
			characterRobot.putWeaponIn(newCharacterWeapon, i);
		}
	}
	
	public CharacterNPC(final String name,CharacterResources resourcesToBeLoaded){
		
		this.characterName = name;
		
		this.characterResources = resourcesToBeLoaded;
		
	}
	
	public String characterName(){
		return characterName;
	}
	
	public void setPosition(float pX, float pY){
		this.characterResources.setCharacterPosition(pX, pY);
	}
	
	public void setPositionOnMap(int column, int row){
		this.characterResources.setCharacterPosition(column, row);
	}
	
	public int getCharacterColumn(){
		return this.characterResources.getColumn();
	}
	
	public int getCharacterRow(){
		return this.characterResources.getRow();
	}
	
	public String getCharacterTexturePath(){
		return this.characterResources.getCharacterTexturePath();
	}
	
	public void loadNPCTextures(Engine gameEngine, Context gameContext){
		
		this.characterResources.loadTexture(gameEngine, gameContext);
	}
	
	public void detachCharacterSprite(){
		this.characterResources.detachCharacterNPC();
	}
	
	public AnimatedSprite getCharacterSprite(){
		return this.characterResources.getCharacterSprite();
	}
	
	public void changeCharacterName(String newName){
		this.characterName = newName;
	}
	
	public void changeAnimation(String newAnimation,Boolean loopAnimation){
		
		this.characterResources.changeCharacterAnimation(newAnimation, loopAnimation);
		
	}
	
	public String getInitialAnimation(){
		
		return this.characterResources.getInitialAnimationLabel();
		
	}
	
	public Boolean metAlready(){
		return battled;
	}
	
	public void setMetAlready(){
		this.battled = true;
	}
	
	public BattleRobot getRobot(){
		return this.characterRobot;
	}
}