package com.android.mechawars.battle;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.input.touch.TouchEvent;

import com.android.mechawars.battle.BattleImageContainer;
import com.android.mechawars.battle.BattleRobot;
import com.android.mechawars.battle.BattleWeapon;

public class BattleField {
	
	private BattleImageContainer myContainer;
	private Camera myCamera;
	
	private BattleRobot playerRobot;
	private BattleRobot enemyRobot;
	
	private Scene myScene;
	private Sprite playerRobotBody;
	private Sprite[] playerRobotWeapons;
	private Sprite enemyRobotBody;
	private Sprite[] enemyRobotWeapons;
	private Sprite[] buttons;
	private Rectangle playerRobotHpBar;
	private Rectangle enemyRobotHpBar;
	
	public BattleField (BattleImageContainer initContainer, Camera initCamera, BattleRobot initPlayerRobot, BattleRobot initEnemyRobot) {
		myContainer = initContainer;
		myCamera = initCamera;
		
		playerRobot = initPlayerRobot;
		enemyRobot = initEnemyRobot;
		
		myScene = new Scene();
		
		makeScenePlayer();
		makeSceneEnemy();
		makeSceneButtons();
		makeSceneLiveBar();
	}
	
	public Scene getScene() {
		return myScene;
	}
		
	private void makeScenePlayer() {
		playerRobotBody = new Sprite(0, 0, myContainer.getImageBodyIn(playerRobot.getImageNumber()));
		
		playerRobotWeapons = new Sprite[BattleRobot.MAX_NUMBER_WEAPON];
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			playerRobotWeapons[i] = null;
			if(playerRobot.getWeaponIn(i) != null) {
				playerRobotWeapons[i] = new Sprite(0, BattleImageContainer.BODY_HEIGHT + i*BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(i).getImageNumber()));
			}
		}
		
		myScene.attachChild(playerRobotBody);
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			if(playerRobotWeapons[i] != null) {
				myScene.attachChild(playerRobotWeapons[i]);
			}
		}
	}
	
	private void makeSceneEnemy() {
		enemyRobotBody = new Sprite(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH, 0, myContainer.getImageBodyIn(enemyRobot.getImageNumber()));
		
		enemyRobotWeapons = new Sprite[BattleRobot.MAX_NUMBER_WEAPON];
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			enemyRobotWeapons[i] = null;
			if(enemyRobot.getWeaponIn(i) != null) {
				enemyRobotWeapons[i] = new Sprite(myCamera.getWidth() - BattleImageContainer.WEAPON_WIDTH, BattleImageContainer.BODY_HEIGHT + i*BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(i).getImageNumber()));
			}
		}
		
		myScene.attachChild(enemyRobotBody);
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			if(enemyRobotWeapons[i] != null) {
				myScene.attachChild(enemyRobotWeapons[i]);				
			}
		}
	}
	
	private void makeSceneButtons () {
		buttons = new Sprite[BattleRobot.MAX_NUMBER_WEAPON];
		
		buttons[0] = new Sprite(0*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(0).getImageNumber())) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	    playerRobot.takeDamage(10);
            	    upDateLiveBar();
            		return true;
            }
		};
		
		buttons[1] = new Sprite(1*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(0).getImageNumber())) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	    playerRobot.takeDamage(10);
            		return true;
            }
		};
		
		buttons[2] = new Sprite(2*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(0).getImageNumber())) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	    playerRobot.takeDamage(10);
            		return true;
            }
		};
		
		buttons[3] = new Sprite(3*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(0).getImageNumber())) {
            @Override
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
            	    playerRobot.takeDamage(10);
            		return true;
            }
		};
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			if(buttons[i] != null) {
				myScene.attachChild(buttons[i]);
				myScene.registerTouchArea(buttons[i]);
			}
		}
		myScene.setTouchAreaBindingEnabled(true);	
	}
	
	private void makeSceneLiveBar() {
		playerRobotHpBar = new Rectangle(BattleImageContainer.BODY_WIDTH, 0, playerRobot.getHp(), 10);
		playerRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(playerRobotHpBar);
		
		enemyRobotHpBar = new Rectangle(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH - enemyRobot.getHp(), 0, enemyRobot.getHp(), 10);
		enemyRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(enemyRobotHpBar);
	}
	
	private void upDateLiveBar() {
		myScene.detachChild(playerRobotHpBar);
		playerRobotHpBar = new Rectangle(BattleImageContainer.BODY_WIDTH, 0, playerRobot.getHp(), 10);
		playerRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(playerRobotHpBar);
		
		myScene.detachChild(enemyRobotHpBar);
		enemyRobotHpBar = new Rectangle(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH - enemyRobot.getHp(), 0, enemyRobot.getHp(), 10);
		enemyRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(enemyRobotHpBar);
	}
}
