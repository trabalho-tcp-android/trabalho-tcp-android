package com.android.mechawars.battle;

/*
 * Classe que guarda as texturas usadas palos robo e pelas armas
 * */
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class BattleImageContainer {
	
	public static final int MAX_WEAPON_NUMBER = 10; //numero maximo de slot para armas
	public static final int MAX_BODY_NUMBER = 10;   //numero maximo de slot para corpos
    
	public static final int WEAPON_WIDTH = 32;  //largura maxima para a imagem da arma
    public static final int WEAPON_HEIGHT = 32; //altura maxima para a imagem da arma
    
    public static final int BODY_WIDTH = 128;  //largura maxima para a imagem de um corpo
    public static final int BODY_HEIGHT = 128; //altura maxima para a imagem de um corpo
	
	private BaseGameActivity mBaseGameActivity;
	
	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TextureRegion[] robotWeaponTextureRegion;
	private TextureRegion[] robotBodyTextureRegion;
	
	/*
	 * Contrutor:
	 * BaseGameActivity initialBaseGameActivity: BaseGameActivity do jogo
	 * */
	public BattleImageContainer(BaseGameActivity initialBaseGameActivity) {
		mBaseGameActivity = initialBaseGameActivity;
		
		robotWeaponTextureRegion = new TextureRegion[MAX_WEAPON_NUMBER];
		for(int i = 0; i < MAX_WEAPON_NUMBER; i++) {
			robotWeaponTextureRegion[i] = null;
		}
		
		robotBodyTextureRegion = new TextureRegion[MAX_BODY_NUMBER];
		for(int i = 0; i < MAX_BODY_NUMBER; i++) {
			robotBodyTextureRegion[i] = null;
		}
	}
	
	//coloca a textura de uma arma de um arquivo em um determinado slot
	public BitmapTextureAtlas putImageWeaponIn(String path, int slot) {
		if( slot < MAX_WEAPON_NUMBER && slot >= 0) {
			mBitmapTextureAtlas = new BitmapTextureAtlas(WEAPON_WIDTH, WEAPON_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			robotWeaponTextureRegion[slot] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, mBaseGameActivity, path, 0, 0);
			return mBitmapTextureAtlas;
		}
		else
			return null;
	}
	
	//coloca a textura de um corpo de um arquivo em um determinado slot
	public BitmapTextureAtlas putImageBodyIn(String path, int slot) {
		if( slot < MAX_BODY_NUMBER && slot >= 0) {
			mBitmapTextureAtlas = new BitmapTextureAtlas(BODY_WIDTH, BODY_HEIGHT, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			robotBodyTextureRegion[slot] = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBitmapTextureAtlas, mBaseGameActivity, path, 0, 0);
			return mBitmapTextureAtlas;
		}
		else
			return null;
	}
	
	//retorna a textura de uma arma de um slot
	public TextureRegion getImageWeaponIn(int slot) {
		if( slot < MAX_WEAPON_NUMBER && slot >= 0) {
			return robotWeaponTextureRegion[slot];
		}
		else {
			return null;
		}
	}
	
	//retorna a textura de um corpo de um slot
	public TextureRegion getImageBodyIn(int slot) {
		if( slot < MAX_BODY_NUMBER && slot >= 0) {
			return robotBodyTextureRegion[slot];
		}
		else {
			return null; 
		}
	}

}
