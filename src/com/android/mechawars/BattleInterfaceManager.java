package com.android.mechawars;

import com.android.mechawars.battle.BattleRobot;

public class BattleInterfaceManager {
	
	private static BattleInterfaceManager instance = new BattleInterfaceManager();
	
	private static BattleRobot playerRobot;
	
	private static BattleRobot enemyRobot;
	
	public static BattleInterfaceManager instance(){
		return instance;
	}
	
	private BattleInterfaceManager(){
	}
	
	public  void setBattleRobots(){
		
	}
	
	public BattleRobot getPlayerRobot(){
		return playerRobot;
	}
	
	public BattleRobot getEnemyRobot(){
		return enemyRobot;
	}
}
