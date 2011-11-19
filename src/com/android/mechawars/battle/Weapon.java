package com.android.mechawars.battle;

public class Weapon {
	
	private int damage;teste;
	private int maxAmmunition;
	private int currentAmmunition;
	private String imagePath;
	
	public Weapon (int _damage, int _maxAmmunition, String _imagePath ) {
		damage = _damage;
		maxAmmunition = _maxAmmunition;
		imagePath = _imagePath;
		currentAmmunition = maxAmmunition;
	}
	
	public void reload() {
		currentAmmunition = maxAmmunition;
	}
	
	public int doDamage() {
		return damage;
	}
	
}
