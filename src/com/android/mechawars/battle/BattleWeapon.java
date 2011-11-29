package com.android.mechawars.battle;
/* 
 * Classe responsável pelas informações sobre as armas
 * */
public class BattleWeapon {
	
	private int damage;            //dano que a arma vai causar
	private int maxAmmunition;     //máximo de munição que uma arma pode carregar
	private int currentAmmunition; //munição atual da arma
	private int imageNumber;       //número da imagem de arma
	
	/*
	 * Contrutor:
	 * int initialDamage: dano que a arma vai causar
	 * int initialMaxAmmunition: máximo de munição que uma arma pode carregar
	 * int initialImageNumber: número da imagem de arma
	 * */
	public BattleWeapon (int initialDamage, int initialMaxAmmunition, int initialImageNumber ) {
		damage = initialDamage;
		maxAmmunition = initialMaxAmmunition;
		imageNumber = initialImageNumber;
		currentAmmunition = maxAmmunition;
	}
	
	//recarre a arma
	public void reload() {
		currentAmmunition = maxAmmunition;
	}
	
	//retorna o dano da arma se houver munição
	public int doDamage() {
		if ( currentAmmunition > 0 ){
			currentAmmunition--;
			return damage;
		}
		else{
			return 0;
		}
	}
	
	//número da imagem da arma
	public int getImageNumber() {
		return imageNumber;
	}

}