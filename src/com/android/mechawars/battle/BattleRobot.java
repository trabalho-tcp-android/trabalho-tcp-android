package com.android.mechawars.battle;

import com.android.mechawars.battle.BattleWeapon;

public class BattleRobot {
	public static final int MAX_NUMBER_WEAPON = 4;
	
	private int maxHp;
	private int currentHp;
	private boolean live;
	
	private int numberWeapon;
	private BattleWeapon[] weapons;
	
	private int imageNumber;

	public BattleRobot(int initialMaxHp, int initialMaxWeapon, int initialImageNumber) {
		maxHp = initialMaxHp;
		currentHp = maxHp;
		live  = true;
		
		numberWeapon = initialMaxWeapon;
		weapons = new BattleWeapon[MAX_NUMBER_WEAPON];
		for(int i = 0; i < MAX_NUMBER_WEAPON; i++) {
			weapons[i] = null;
		}
		
		imageNumber = initialImageNumber;
	}
	
	public int getHp() {
		return currentHp;
	}
	
	public void putWeaponIn(BattleWeapon newWeapon, int slot) {
		if(slot < numberWeapon && slot >= 0) {
			weapons[slot] = newWeapon;
		}
	}
	
	public BattleWeapon getWeaponIn(int slot) {
		if(slot < numberWeapon && slot >= 0) {
			return weapons[slot];
		}
		else {
			return null;
		}
	}
	
	public void removeWeaponIn(int slot) {
		if(slot < numberWeapon && slot >= 0) {
			weapons[slot] = null;
		}	
	}
	
	public void recovery() {
		currentHp = maxHp;
	}
	
	public void takeDamage(int damage) {
		if(damage < currentHp) {
			currentHp -=  damage;
		}
		else {
			currentHp = 0;
			live = false;
		}
	}
	
	public boolean isLive(){
		return live;
	}
	
	public int getImageNumber() {
		return imageNumber;
	}
	
}