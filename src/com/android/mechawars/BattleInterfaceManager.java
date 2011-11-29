package com.android.mechawars;

import com.android.mechawars.battle.BattleRobot;

public class BattleInterfaceManager {
	
	private static BattleInterfaceManager instance = new BattleInterfaceManager();
	
	private static BattleRobot playerRobot;
	
	private static BattleRobot enemyRobot;
	
	
	
	private static Boolean wonBattle = false;
	
	
	public static BattleInterfaceManager instance(){
		return instance;
	}
	
	private BattleInterfaceManager(){
	}
	
	public static  void setPlayerRobot(BattleRobot newPlayerRobot){
		playerRobot = newPlayerRobot;
	}
	
	public static  void setEnemyRobot(BattleRobot newEnemyRobot){
		enemyRobot = newEnemyRobot;
	}
	
	public static BattleRobot getPlayerRobot(){
		return playerRobot;
	}
	
	public static BattleRobot getEnemyRobot(){
		return enemyRobot;
	}
	
	public static void finishBattle(){
		SceneManager.battleActivity.finish();
	}
	
	public static void setBattleCondition(Boolean wasItWon){
		wonBattle = wasItWon;
	}
	
	public static Boolean playerWonBattle(){
		return wonBattle;
	}
}
