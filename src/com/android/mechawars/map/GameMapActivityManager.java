package com.android.mechawars.map;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;

import com.android.mechawars.SceneManager;

import android.content.Context;

public class GameMapActivityManager {
	
	private static GameMapActivityManager instance = new GameMapActivityManager();
	
	private static Engine mapEngine;
	
	private static Scene mapScene;
	
	private static BoundCamera mapCamera;
	
	private static GameMapEnvironmentManager gameMapEnvironment;
	
	private static Context mapContext;
	
	public static GameMapActivityManager instance(){
		return instance;
	}
	
	private GameMapActivityManager(){
		
	}
	
	//Initializes the map environment.
	public static void setMapEnvironment(Context activityContext){
		mapContext = activityContext;
		mapCamera = new BoundCamera(0, 0, LoadAssets.CAMERA_WIDTH, LoadAssets.CAMERA_HEIGHT);
		mapEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(LoadAssets.CAMERA_WIDTH, LoadAssets.CAMERA_HEIGHT), mapCamera));
		mapScene  = new Scene();
		gameMapEnvironment = new GameMapEnvironmentManager(mapEngine, mapScene, mapCamera, mapContext);
	}
	
	public static Engine getMapEngine(){
		return mapEngine;
	}
	
	public static Scene getMapScene(){
		return mapScene;
	}
	
	public static void setMapScene(){
		
		mapEngine.setScene(mapScene);
	}
	
	public static BoundCamera getCamera(){
		
		return mapCamera;
	}
}
