package com.android.mechawars.map.characters;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

public class LoadCharacterSprites {
	
	BitmapTextureAtlas characterBitmapTextureAtlas;
	TiledTextureRegion characterTextureRegion;
	
	public static final int PLAYER_INITIAL_POSITION_X = 0;
	public static final int PLAYER_INITIAL_POSITION_Y = 0;
	
	
	//Will "automatically" load the NPC resources.
	public void loadTexture(Engine gameEngine,String texturePath,Context callerContext,final int bitmapTextureAtlasWidth,final int bitmapTextureAtlasHeight,final int spriteSheetColumns,final int spriteSheetRows){
		
		//Setting the root path to load the resources.
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//Forming the Bitmap Texture Atlas.
		characterBitmapTextureAtlas = new BitmapTextureAtlas(bitmapTextureAtlasWidth,bitmapTextureAtlasHeight, TextureOptions.DEFAULT);
		
		
		characterTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(characterBitmapTextureAtlas,callerContext,texturePath,PLAYER_INITIAL_POSITION_X,PLAYER_INITIAL_POSITION_Y,spriteSheetColumns,spriteSheetRows); // 72x128
		
		
		//Making the engine load the textures.
		gameEngine.getTextureManager().loadTexture(characterBitmapTextureAtlas);
	}
}
