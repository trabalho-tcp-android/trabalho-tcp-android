package com.android.mechawars;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import com.android.mechawars.battle.BattleField;
import com.android.mechawars.battle.BattleImageContainer;
import com.android.mechawars.battle.BattleRobot;
import com.android.mechawars.battle.BattleWeapon;

import android.widget.Toast;


public class MechawarsBattleActivity extends BaseGameActivity {

        private static final int CAMERA_WIDTH = 720;
        private static final int CAMERA_HEIGHT = 480;

        private Camera mCamera;
        private BattleImageContainer mBattleImageContainer;
        private BattleField mBattleField;

        @Override
        public Engine onLoadEngine() {
                mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
                return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera));
        }

        @Override
        public void onLoadResources() {
        	mBattleImageContainer = new BattleImageContainer(this);
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageWeaponIn("battleGfx/robotWeapon0.png", 0) );
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageWeaponIn("battleGfx/robotWeapon1.png", 1) );
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageWeaponIn("battleGfx/robotWeapon2.png", 2) );
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageBodyIn("battleGfx/robotBody0.png", 0) );
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageBodyIn("battleGfx/robotBody1.png", 1) );
        	this.mEngine.getTextureManager().loadTexture( mBattleImageContainer.putImageBodyIn("battleGfx/robotBody2.png", 2) );
        	
        	BattleRobot mPlayerRobot = new BattleRobot(100, 2, 0);
        	BattleWeapon mWeapon = new BattleWeapon(10,10,0);
        	mPlayerRobot.putWeaponIn(mWeapon, 0);
        	mWeapon = new BattleWeapon(10,10,0);
        	mPlayerRobot.putWeaponIn(mWeapon, 1);
        	
        	BattleRobot mEnemyRobot = new BattleRobot(100, 2, 1);
        	mEnemyRobot.putWeaponIn(mWeapon, 0);
        	mBattleField = new BattleField(mBattleImageContainer, mCamera, mPlayerRobot, mEnemyRobot);
        	
        }

        @Override
        public Scene onLoadScene() {
                this.mEngine.registerUpdateHandler(new FPSLogger());

                Scene scene = mBattleField.getScene();
                
                return scene;
        }

        @Override
        public void onLoadComplete() {
        	//Toast.makeText(this, "Teste", Toast.LENGTH_LONG).show();
        }

}