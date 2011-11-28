package com.android.mechawars.map.controller;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
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

/*
 * Class used to implement an in-game interaction button.
 * Was meant to become some sort of action button.
 * Not used in the final release :(
*/

public class GameInteractionButton{
	
	private float cameraHeight;
	private float cameraWidth;
	
	private float prevX = 0;
	private float prevY = 0;
	
	AnalogOnScreenControl buttonHolder;
	BitmapTextureAtlas buttonBitmapAtlas;
	TextureRegion buttonTextureRegion;
	TextureRegion buttonBlankKnob;
	Boolean touched = false;
	

	public GameInteractionButton(Engine gameEngine, Scene gameMapScene,Context gameMapContext, final BoundCamera gameMapCamera) {
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		//Forming the Bitmap Texture Atlas.
		buttonBitmapAtlas = new BitmapTextureAtlas(LoadAssets.buttonWidth,LoadAssets.buttonHeight, TextureOptions.DEFAULT);
		buttonTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonBitmapAtlas,gameMapContext,LoadAssets.buttonPath,0,0);
		buttonBlankKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset(buttonBitmapAtlas,gameMapContext,"blank.png",128,0);
		//Loading the texture from the file
		gameEngine.getTextureManager().loadTexture(buttonBitmapAtlas);
		
		
		cameraHeight = gameMapCamera.getHeight();
		
		cameraWidth = gameMapCamera.getWidth();
		
		this.buttonHolder = new AnalogOnScreenControl(cameraWidth - this.buttonTextureRegion.getWidth(),cameraHeight - this.buttonTextureRegion.getHeight(), gameMapCamera, this.buttonTextureRegion, this.buttonBlankKnob, 0.5f, new IAnalogOnScreenControlListener(){
			
			@Override
			public void onControlClick(AnalogOnScreenControl arg0) {
				System.out.println("LOOOOOOOOOOOOOOOOOL FOI");
				
			}

			@Override
			public void onControlChange(BaseOnScreenControl arg0, float arg1,
					float arg2) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gameMapScene.attachChild(buttonHolder);
		
		buttonHolder.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		buttonHolder.getControlBase().setAlpha(0.5f);
		buttonHolder.getControlKnob().setAlpha(0.5f);
		
		gameMapScene.setChildScene(buttonHolder);
		
		
	}
	
}
