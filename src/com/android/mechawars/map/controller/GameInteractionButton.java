package com.android.mechawars.map.controller;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.TiledSprite;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;

import com.android.mechawars.map.LoadAssets;


//Class used to implement the in-game interaction button.
public class GameInteractionButton{
	
	private float cameraHeight;
	private float cameraWidth;
	
	BitmapTextureAtlas buttonBitmapAtlas;
	TiledTextureRegion buttonTextureRegion;
	TiledSprite buttonSprite;
	Boolean touched = false;
	
	public GameInteractionButton(Engine gameEngine, Scene gameMapScene,Context gameMapContext, final BoundCamera gameMapCamera) {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//Forming the Bitmap Texture Atlas.
		buttonBitmapAtlas = new BitmapTextureAtlas(LoadAssets.buttonWidth,LoadAssets.buttonHeight, TextureOptions.DEFAULT);
		buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(buttonBitmapAtlas,gameMapContext,LoadAssets.buttonPath,0,0,1,1);
		
		//Loading the texture from the file
		gameEngine.getTextureManager().loadTexture(buttonBitmapAtlas);

		buttonSprite = new TiledSprite(gameMapCamera.getWidth() - buttonTextureRegion.getWidth(),gameMapCamera.getHeight() - buttonTextureRegion.getHeight(),buttonTextureRegion)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				//this.setPosition(gameMapCamera.getCenterX() - (gameMapCamera.),gameMapCamera.getCenterY() - (gameMapCamera.getHeight()/2));
				if(pSceneTouchEvent.isActionDown()){
					buttonSprite.setAlpha(1f);
					touched = true;
				}
				if(pSceneTouchEvent.isActionUp()){
					buttonSprite.setAlpha(0.5f);
					touched = false;
				}
				
				return touched;
			}
		};
		
		this.buttonSprite.setAlpha(0.5f);
		
		gameMapScene.attachChild(this.buttonSprite);
		
		cameraHeight = gameMapCamera.getHeight();
		
		cameraWidth = gameMapCamera.getWidth();
		
	}
	
	public void setButtonPosition(float playerPositionX, float playerPositionY){
		
		this.buttonSprite.setPosition(playerPositionX + cameraWidth/2 - this.buttonSprite.getWidth(), playerPositionY + cameraHeight/2 - this.buttonSprite.getHeight());
		
	}
	
}
