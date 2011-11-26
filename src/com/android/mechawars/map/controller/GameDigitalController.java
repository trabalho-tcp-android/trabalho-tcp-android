package com.android.mechawars.map.controller;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.content.Context;

import com.android.mechawars.map.GameMapEnvironmentManager;
import com.android.mechawars.map.LoadAssets;

public class GameDigitalController {
	
	private DigitalOnScreenControl digitalController;
	
	private BitmapTextureAtlas controllerTexture;
	
	private TextureRegion digitalControllerBase;
	
	private TextureRegion digitalControllerKnob;
	
	private int moveHorizontally;
	
	private int moveVertically;
	
	private Boolean lockController; 
	
	
	
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
	

}
