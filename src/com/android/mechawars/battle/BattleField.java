package com.android.mechawars.battle;

import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;

import org.anddev.andengine.input.touch.TouchEvent;

import com.android.mechawars.battle.BattleImageContainer;
import com.android.mechawars.battle.BattleRobot;
import com.android.mechawars.battle.BattleWeapon;

public class BattleField {
	
	private static final int PLAYER_TURN = 0;
	private static final int ENEMY_TURN = 1;
	private static final int END_TURN = 2;
	private int currentTurn;
	
	private BattleImageContainer myContainer;
	private Camera myCamera;
	
	private BattleRobot playerRobot;
	private BattleRobot enemyRobot;
	
	private Scene myScene;
	private TimerHandler myTimerHandler;
	private Sprite playerRobotBody;
	private Sprite[] playerRobotWeapons;
	private Sprite enemyRobotBody;
	private Sprite[] enemyRobotWeapons;
	private Sprite[] buttons;
	private Rectangle playerRobotHpBar;
	private Rectangle enemyRobotHpBar;
	
	private boolean buttonBugFix;
	
	public BattleField (BattleImageContainer initContainer, Camera initCamera, BattleRobot initPlayerRobot, BattleRobot initEnemyRobot) {
		currentTurn = PLAYER_TURN;
		
		myContainer = initContainer;
		myCamera = initCamera;
		
		playerRobot = initPlayerRobot;
		enemyRobot = initEnemyRobot;
		
		myScene = new Scene();
		
		buttonBugFix = true;
		
		makeScenePlayer();
		makeSceneEnemy();
		makeSceneButtons();
		makeSceneLiveBar();
		makeSceneTimer();
	}
	
	public Scene getScene() {
		return myScene;
	}
			
	private void makeScenePlayer() {
		playerRobotBody = new Sprite(0, myCamera.getHeight() - BattleImageContainer.BODY_HEIGHT - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageBodyIn(playerRobot.getImageNumber()));
		
		playerRobotWeapons = new Sprite[BattleRobot.MAX_NUMBER_WEAPON];
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			playerRobotWeapons[i] = null;
			if(playerRobot.getWeaponIn(i) != null) {
				playerRobotWeapons[i] = new Sprite(BattleImageContainer.BODY_WIDTH + i*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.BODY_HEIGHT - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(i).getImageNumber()));
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
				enemyRobotWeapons[i] = new Sprite(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH - (i+1)*BattleImageContainer.WEAPON_WIDTH, 0, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(i).getImageNumber()));
			}
		}
		
		myScene.attachChild(enemyRobotBody);
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			if(enemyRobotWeapons[i] != null) {
				myScene.attachChild(enemyRobotWeapons[i]);				
			}
		}
	}
	
	private void makeSceneLiveBar() {
		playerRobotHpBar = new Rectangle(BattleImageContainer.BODY_WIDTH, myCamera.getHeight() - BattleImageContainer.BODY_HEIGHT - BattleImageContainer.WEAPON_HEIGHT + BattleImageContainer.WEAPON_HEIGHT , playerRobot.getHp(), 10);
		playerRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(playerRobotHpBar);
		
		enemyRobotHpBar = new Rectangle(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH - enemyRobot.getHp(), BattleImageContainer.WEAPON_HEIGHT, enemyRobot.getHp(), 10);
		enemyRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(enemyRobotHpBar);
	}
	
	private void makeSceneButtons () {
		buttons = new Sprite[BattleRobot.MAX_NUMBER_WEAPON];
		
		
		if(playerRobot.getWeaponIn(0) != null && 0 < BattleRobot.MAX_NUMBER_WEAPON) {
			buttons[0] = new Sprite(0*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(0).getImageNumber())) {
	            @Override
	            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	            	if(currentTurn == PLAYER_TURN) {	
		            	enemyRobot.takeDamage( playerRobot.getWeaponIn(0).doDamage() );
		            	currentTurn = ENEMY_TURN;
		            	upDateAll();
	            	}
	            	return true;
	            }
			};
		}
		
		if(playerRobot.getWeaponIn(1) != null && 1 < BattleRobot.MAX_NUMBER_WEAPON) {
			buttons[1] = new Sprite(1*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(1).getImageNumber())) {
	            @Override
	            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	            	if(currentTurn == PLAYER_TURN) {	
		            	enemyRobot.takeDamage( playerRobot.getWeaponIn(1).doDamage() );
		            	currentTurn = ENEMY_TURN;
		            	upDateAll();
	            	}
	            	return true;
	            }
			};
		}
		
		if(playerRobot.getWeaponIn(2) != null && 2 < BattleRobot.MAX_NUMBER_WEAPON) {
			buttons[2] = new Sprite(2*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(2).getImageNumber())) {
	            @Override
	            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	            	if(buttonBugFix) {
	            		buttonBugFix = false;
		            	enemyRobot.takeDamage( playerRobot.getWeaponIn(2).doDamage() );
		            	upDateLiveBar();
	            	} else {
	            		buttonBugFix = true;
	            	}
	            	return true;
	            }
			};
		}
		
		if(playerRobot.getWeaponIn(3) != null && 3 < BattleRobot.MAX_NUMBER_WEAPON) {
			buttons[3] = new Sprite(3*BattleImageContainer.WEAPON_WIDTH, myCamera.getHeight() - BattleImageContainer.WEAPON_HEIGHT, myContainer.getImageWeaponIn(playerRobot.getWeaponIn(3).getImageNumber())) {
	            @Override
	            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
	            	if(buttonBugFix) {
	            		buttonBugFix = false;
		            	enemyRobot.takeDamage( playerRobot.getWeaponIn(3).doDamage() );
		            	upDateLiveBar();
	            	} else {
	            		buttonBugFix = true;
	            	}
	            	return true;
	            }
			};
		}
		
		for(int i = 0; i < BattleRobot.MAX_NUMBER_WEAPON; i++) {
			if(buttons[i] != null) {
				myScene.attachChild(buttons[i]);
				myScene.registerTouchArea(buttons[i]);
			}
		}
		myScene.setTouchAreaBindingEnabled(true);	
	}
		
	private void makeSceneTimer() {
		myTimerHandler = new TimerHandler(3f, true, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
            	upDateAll();
            	
            	switch(currentTurn) {
            		case PLAYER_TURN:
            		break;
            		case ENEMY_TURN:
            			makeEnemyMove();
            			currentTurn = PLAYER_TURN;
            		break;
            		case END_TURN:
            		break;
            	}
            }
		});
		myScene.registerUpdateHandler(myTimerHandler);
	}
	
	private void upDatePlayer() {
		if(playerRobot.getHp() == 0) {
			myScene.detachChild(playerRobotBody);
		}
	}
	
	private void upDateEnemy() {
		if(enemyRobot.getHp() == 0) {
			myScene.detachChild(enemyRobotBody);
		}
	}
	
	private void upDateLiveBar() {
		myScene.detachChild(playerRobotHpBar);
		playerRobotHpBar = new Rectangle(BattleImageContainer.BODY_WIDTH, myCamera.getHeight() - BattleImageContainer.BODY_HEIGHT - BattleImageContainer.WEAPON_HEIGHT + BattleImageContainer.WEAPON_HEIGHT , playerRobot.getHp(), 10);
		playerRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(playerRobotHpBar);
		
		myScene.detachChild(enemyRobotHpBar);
		enemyRobotHpBar = new Rectangle(myCamera.getWidth() - BattleImageContainer.BODY_WIDTH - enemyRobot.getHp(), BattleImageContainer.WEAPON_HEIGHT, enemyRobot.getHp(), 10);
		enemyRobotHpBar.setColor(0f, 1f, 0f);
		myScene.attachChild(enemyRobotHpBar);
	}
	
	private void upDateAll() {
    	upDatePlayer();
    	upDateEnemy();
    	upDateLiveBar();
    	if( (playerRobot.getHp() == 0) || (enemyRobot.getHp() == 0) ) {
    		currentTurn = END_TURN;
    	}
    	
	}
	
	private void makeEnemyMove() {
		playerRobot.takeDamage( enemyRobot.getWeaponIn(0).doDamage() );
	}
}
