package com.android.mechawars.battle;

public class BattleWeapon {
	
	private int damage;
	private int maxAmmunition;
	private int currentAmmunition;
	private int imageNumber;
	
	public BattleWeapon (int initialDamage, int initialMaxAmmunition, int initialImageNumber ) {
		damage = initialDamage;
		maxAmmunition = initialMaxAmmunition;
		imageNumber = initialImageNumber;
		currentAmmunition = maxAmmunition;
	}
	
	public void reload() {
		currentAmmunition = maxAmmunition;
	}
	
	public int doDamage() {
		if ( currentAmmunition > 0 ){
			currentAmmunition--;
			return damage;
		}
		else{
			return 0;
		}
	}
	
	public int getImageNumber() {
		return imageNumber;
	}

}