package com.android.mechawars.battle;
/* 
 * Classe responsavel pelas informacoes sobre as armas
 * */
public class BattleWeapon {
	
	private int damage;            //dano que a arma vai causar
	private int maxAmmunition;     //m�ximo de municao que uma arma pode carregar
	private int currentAmmunition; //muni��o atual da arma
	private int imageNumber;       //n�mero da imagem de arma
	
	/*
	 * Contrutor:
	 * int initialDamage: dano que a arma vai causar
	 * int initialMaxAmmunition: maximo de municao que uma arma pode carregar
	 * int initialImageNumber: numero da imagem de arma
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
	
	//retorna o dano da arma se houver municao
	public int doDamage() {
		if ( currentAmmunition > 0 ){
			currentAmmunition--;
			return damage;
		}
		else{
			return 0;
		}
	}
	
	//n�mero da imagem da arma
	public int getImageNumber() {
		return imageNumber;
	}

}