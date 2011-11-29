package com.android.mechawars.battle;

/*
 * Classe responsável pelas informações sobre os robôs
 * */
import com.android.mechawars.battle.BattleWeapon;

public class BattleRobot {
	public static final int MAX_NUMBER_WEAPON = 4; //número máximo de armas que QUALQUER robô pode carregar
	
	private int maxHp;    //HP máximo
	private int currentHp;//HP atual
	private boolean live; //se esta vivo
	
	private int numberWeapon;      //número máximo de armas que ESTE robô pode carregar
	private BattleWeapon[] weapons;//array das armas
	
	private int imageNumber;
	/*
	 * Contrutor:
	 * int initialMaxHp: HP máximo
	 * int initialMaxWeapon: número de armas que o robô pode carregar
	 * int initialImageNumber: número da imagem do corpo do robô
	 * */
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
	
	//retorna o HP atual
	public int getHp() {
		return currentHp;
	}
	
	//coloca uma arma em um determinado slot
	public void putWeaponIn(BattleWeapon newWeapon, int slot) {
		if(slot < numberWeapon && slot >= 0) {
			weapons[slot] = newWeapon;
		}
	}
	
	//retorna uma arma de um determinado slot
	public BattleWeapon getWeaponIn(int slot) {
		if(slot < numberWeapon && slot >= 0) {
			return weapons[slot];
		}
		else {
			return null;
		}
	}
	
	//remove uma arma de um determinado slot
	public void removeWeaponIn(int slot) {
		if(slot < numberWeapon && slot >= 0) {
			weapons[slot] = null;
		}	
	}
	
	//recupera o HP
	public void recovery() {
		currentHp = maxHp;
	}
	
	//toma um determinado dano
	public void takeDamage(int damage) {
		if(damage < currentHp) {
			currentHp -=  damage;
		}
		else {
			currentHp = 0;
			live = false;
		}
	}
	
	//retorna se esta vivo
	public boolean isLive(){
		return live;
	}
	
	//número da imagem do corpo do robô
	public int getImageNumber() {
		return imageNumber;
	}
	
}