package com.android.mechawars;

import com.android.mechawars.battle.BattleRobot;
import com.android.mechawars.map.GameMapActivityManager;


/*
 * THIS CLASS ALLOWS US TO COMMUNICATE WITH THE BATTLE ACTIVITY,
 * IN ORDER TO GET INFORMATION ABOUT THE BATTLE RESULTS AND PASS
 * THE BATTLE PARAMETERS.
 * */
public class BattleInterfaceManager {
	
	private static BattleInterfaceManager instance = new BattleInterfaceManager();
	
	//This field keeps the player robot.
	private static BattleRobot playerRobot;

	//This field keeps the enemy robot, and is constantly accessed thanks to the greater variety of enemies.	
	private static BattleRobot enemyRobot;
	
	//This field keeps the result of the battle.
	private static Boolean wonBattle = false;
	
	

	public static BattleInterfaceManager instance(){
		return instance;
	}
	
	private BattleInterfaceManager(){
	}
	
	public static  void setPlayerRobot(BattleRobot newPlayerRobot){
		playerRobot = newPlayerRobot;
	}
	
	//This method sets the enemy robot, in order to make it playable in the battle activity.
	public static  void setEnemyRobot(BattleRobot newEnemyRobot){
		enemyRobot = newEnemyRobot;
	}
	
	public static BattleRobot getPlayerRobot(){
		return playerRobot;
	}
	
	public static BattleRobot getEnemyRobot(){
		return enemyRobot;
	}
	
	//This methods performs the required operations in order to finish the battle.
	public static void finishBattle(){
		SceneManager.battleActivity.finish();
		GameMapActivityManager.getMapEnvironment().showWon();
	}
	
	//This method allows us set the final result of the battle.
	public static void setBattleCondition(Boolean wasItWon){
		wonBattle = wasItWon;
	}

	//This method allows us to tell the Map activity the result of the battle.
	public static Boolean playerWonBattle(){
		return wonBattle;
	}
}
