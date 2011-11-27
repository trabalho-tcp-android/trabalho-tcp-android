package com.android.mechawars;

import com.android.mechawars.map.*;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;




/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class MechaWarsMapActivity extends BaseGameActivity{

	private static final int CAMERA_WIDTH = LoadAssets.CAMERA_WIDTH;
	private static final int CAMERA_HEIGHT = LoadAssets.CAMERA_HEIGHT;


	//private BoundCamera mBoundChaseCamera;
	
	//private GameMapEnvironmentManager gameMapManager;
	
	//private Scene gameMapScene;

	
	private static SceneManager sceneManager;

	@Override
	public void onLoadComplete() {
		
	}

	@Override
	public Engine onLoadEngine() {
		sceneManager = new SceneManager(this);
		
		GameMapActivityManager.setMapEnvironment(this);
		//this.mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return GameMapActivityManager.getMapEngine();
	}

	@Override
	public void onLoadResources() {
		

	}

	@Override
	public Scene onLoadScene() {
		return GameMapActivityManager.getMapScene();
		//gameMapScene = new Scene();
		
		//sceneManager = new SceneManager(this);
		
		//gameMapManager = new GameMapEnvironmentManager(this.mEngine,this.gameMapScene,this.mBoundChaseCamera,this);
		
		//this.mBoundChaseCamera.setChaseEntity(this.gameMapManager.getPlayerSprite());
		
		//return gameMapScene;
	}
	

}
