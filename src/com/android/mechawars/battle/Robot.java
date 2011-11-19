package com.android.mechawars.battle;

public class Robot {
	private int maxHp;teste;
	private int currentHp;
	private int maxWeapon;
	private String imagePath;
	private Weapon[] weapons;
	
	public Robot(int _maxHp, int _maxWeapon, String _imagePath) {
		maxHp = _maxHp;
		currentHp = maxHp;
		maxWeapon = _maxWeapon;
		imagePath = _imagePath;
		weapons = new Weapon[4];
		weapons[0] = null;
		weapons[1] = null;
		weapons[2] = null;
		weapons[3] = null;
	}
	
	public void putWeaponIn(Weapon newWeapon, int slot) {
		if(slot <= maxWeapon && slot >= 1) {
			weapons[slot - 1] = newWeapon;
		}
	}
	
	public void removeWeaponIn(int slot) {
		if(slot <= maxWeapon && slot >= 1) {
			weapons[slot - 1] = null;
		}	
	}
	
	public void recovery() {
		currentHp = maxHp;
	}
	
	public void takeDamage(int damage) {
		if(damage > currentHp) {
			currentHp -= damage;
		}
	}
	
}