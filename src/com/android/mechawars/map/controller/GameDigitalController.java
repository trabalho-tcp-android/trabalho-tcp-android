package com.android.mechawars.map.controller;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.android.mechawars.map.GameMapActivityManager;
import com.android.mechawars.map.LoadAssets;

//This class implements the in-game directional controller.
public class GameDigitalController {
	
	private DigitalOnScreenControl digitalController;
	
	private BitmapTextureAtlas controllerTexture;
	
	private TextureRegion digitalControllerBase;
	
	private TextureRegion digitalControllerKnob;
	
	private int moveHorizontally;
	
	private int moveVertically;
	
	private Boolean lockController;
	
	private Boolean initializedAnimations = false;
	
	
	public GameDigitalController(Engine gameMapEngine, Scene gameMapScene,Context gameMapContext, BoundCamera gameCamera){
		
		loadTextures(gameMapEngine,gameMapContext);
		
		
		lockController = false;
		
		//Declaring the controller on the screen.
		digitalController = new DigitalOnScreenControl(0, LoadAssets.CAMERA_HEIGHT - this.controllerTexture.getHeight(), gameCamera, this.digitalControllerBase, this.digitalControllerKnob, LoadAssets.controllerRefreshTime, new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if(!lockController){
					if(!initializedAnimations)
					{
						GameMapActivityManager.getMapEnvironment().initializeAnimations();
						initializedAnimations = true;
					}
					if(pValueX != 0 || pValueY != 0){
						moveHorizontally = (int)pValueX;
						moveVertically = (int)pValueY;
						GameMapActivityManager.getMapEnvironment().moveGamePlayer();
					}
				}
			}
		});
		
		gameMapScene.attachChild(digitalController);
		
		digitalController.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		digitalController.getControlBase().setAlpha(0.5f);
		digitalController.getControlKnob().setAlpha(0.5f);
		
		gameMapScene.setChildScene(digitalController);
	}
	
	public void setControllerToScene(Scene gameMapScene){
		gameMapScene.setChildScene(digitalController);
	}
	
	
	//Loading the textures for the controller.
	private void loadTextures(Engine gameMapEngine, Context gameMapContext){
		
		controllerTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		digitalControllerBase = BitmapTextureAtlasTextureRegionFactory.createFromAsset(controllerTexture, gameMapContext, LoadAssets.controllerBody, 0, 0);
		digitalControllerKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset(controllerTexture, gameMapContext, LoadAssets.controllerBodyKnob, 128, 0);

		gameMapEngine.getTextureManager().loadTexture(controllerTexture);
	}
	
	public int getXVariation(){
		return moveHorizontally;
	}
	
	public int getYVariation(){
		return moveVertically;
	}
	
	public DigitalOnScreenControl getController(){
		return digitalController;
	}
	
	public void lockGameController(){
		lockController = true;
		moveVertically = 0;
		moveHorizontally = 0;
	}
	
	public void unlockGameController(){
		lockController = false;
	}
	
	/*
	public GameDigitalController(Engine gameMapEngine,Scene gameMapScene, BoundCamera gameCamera, Context gameMapContext){
		
		//loadTextures(gameMapEngine,gameMapContext);
		
		lockController = false;
		
		digitalController = new DigitalOnScreenControl(0, LoadAssets.CAMERA_HEIGHT - this.controllerTexture.getHeight(), gameCamera, this.digitalControllerBase, this.digitalControllerKnob, LoadAssets.controllerRefreshTime, new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if(!lockController){
					moveHorizontally = (int)pValueX;
					moveVertically = (int)pValueY;
				}
			}
		});
		
		gameMapScene.attachChild(digitalController);

	}
	*/
	

}
