package com.android.mechawars;

import com.android.mechawars.ffBox.ffDialog.DialogManager;
import com.android.mechawars.map.*;

import com.android.mechawars.utils.MusicManager;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

/*
 * THIS CLASS IMPLEMENTS THE MAP ACTIVITY, WHICH IN THIS CASE
 * HAS BEEN EXTENDED IN ORDER TO RUN THE ENGINE.
 * */

public class MechaWarsMapActivity extends BaseGameActivity{
	
	private static SceneManager sceneManager;

	@Override
	public void onLoadComplete() {
		DialogManager.instance().fromJSON("credits").attachToScene();
	}

	@Override
	public Engine onLoadEngine() {
		//Initializing the engine.
		sceneManager = new SceneManager(this);
		
		GameMapActivityManager.setMapEnvironment(this);
		return GameMapActivityManager.getMapEngine();
	}

	@Override
	public void onLoadResources() {
        //Starting the map song.
		MusicManager.instance(this).play("ingame.mod");
	}

	@Override
	public Scene onLoadScene() {
		return GameMapActivityManager.getMapScene();

	}

    @Override
    protected void onDestroy() {
        MusicManager.instance(this).stop();
        super.onDestroy();
    }
	

}
