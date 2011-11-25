package com.android.mechawars;

import java.util.LinkedList;


import javax.microedition.khronos.opengles.GL10;

import com.android.mechawars.map.*;
import com.android.mechawars.map.characters.CharacterGroupParser;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.camera.hud.HUD;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.scene.Scene.ITouchArea;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.constants.Constants;

import android.widget.Toast;



/**
 * (c) 2010 Nicolas Gramlich
 * (c) 2011 Zynga
 *
 * @author Nicolas Gramlich
 * @since 13:58:48 - 19.07.2010
 */
public class MechaWarsMapActivity extends BaseGameActivity implements IOnSceneTouchListener{
	// ===========================================================
	// Constants
	// ===========================================================

	private static final int CAMERA_WIDTH = 400;
	private static final int CAMERA_HEIGHT = 300;

	// ===========================================================
	// Fields
	// ===========================================================

	private BoundCamera mBoundChaseCamera;

	private BitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mPlayerTextureRegion;
	private TMXTiledMap mTMXTiledMap;
	protected int mCactusCount;
	
	AnimatedSprite player;
	

	
	
	//DIGITAL CONTROLLER
	private DigitalOnScreenControl mDigitalScreenController;
	private BitmapTextureAtlas mDigitalScreenControllerTexture;
	private TextureRegion mDigitalControllerBase;
	private TextureRegion mDigitalControllerKnob;
	
	
	
	//DIRECTIONS
	private static final int dUP = 1;
	private static final int dDOWN = -1;
	private static final int dLEFT = -2;
	private static final int dRIGHT = 2;
	
	
	private int playerDirection = dDOWN;

	
	private static SceneManager sceneManager;
	
	//SCENE
	private Scene scene;
	
	
	//ENEMY'S TEXTURES
	private BitmapTextureAtlas mBitmapTAEnemy;
	private TiledTextureRegion mEnemyTextureRegion;
	
	//TMX Map
	//The map has got two different tile layers
	
	private TMXLayer tmxLayer;
	private TMXLayer tmxLayer2;

	//Game Timings
	private static final float offsetTiming = 0.6f;
	
	
	
	//Sprite position adjustment
	private final float offsetSpriteX = LoadAssets.playerTileAdjustmentOffsetX;
	private final float offsetSpriteY = LoadAssets.playerTileAdjustmentOffsetY;
	
	
	
	//Unwalkable tiles matrix
	private TilePropertyMatrix mapUnwalkableTiles;
	
	//Sign positions matrix
	private TilePropertyMatrix mapSignTiles;
	
	
	private CharacterGroupManager characManager;
	
	
	private float TileWidth;
	
	
	
	
	
	//Character animations
	private final static long[] ANIMATE_FACING_DOWN  =  {0,0,0,0,0,0,0,200,0,0,0,0};
	private final static long[] ANIMATE_FACING_UP    =  {0,200,0,0,0,0,0,0,0,0,0,0};
	private final static long[] ANIMATE_FACING_LEFT  =  {0,0,0,0,0,0,0,0,0,0,200,0};
	private final static long[] ANIMATE_FACING_RIGHT =  {0,0,0,0,200,0,0,0,0,0,0,0};
	
	
	
	private LoadAssets assetConstants;
	
	
	
	private BitmapTextureAtlas buttonBitmapAtlas;
	private TiledTextureRegion buttonTiledTextureRegion;
	

	@Override
	public Engine onLoadEngine() {
		

		
		this.mBoundChaseCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		return new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundChaseCamera));
	}

	@Override
	public void onLoadResources() {
		
		assetConstants = new LoadAssets();
		
		this.mBitmapTextureAtlas = new BitmapTextureAtlas(assetConstants.playerBitmapTextureAtlasSize(), assetConstants.playerBitmapTextureAtlasSize(), TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		
		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas, this, assetConstants.playerTexturePath(), assetConstants.playerPositionX(),assetConstants.playerPositionY(), 3, 4); // 72x128
		
		
		//Loading the digital controller
		this.mDigitalScreenControllerTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mDigitalControllerBase = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mDigitalScreenControllerTexture, this, assetConstants.digitalControllerBodyPath(), 0, 0);
		this.mDigitalControllerKnob = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mDigitalScreenControllerTexture, this, assetConstants.digitalControllerBodyKnobPath(), 128, 0);

		this.mBitmapTAEnemy  = new BitmapTextureAtlas(128, 128, TextureOptions.DEFAULT);;
		this.mEnemyTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTAEnemy, this, "enemy.png", 0, 0, 3, 4); // 72x128;
		
		
		
		
		
		//A little testing
		this.buttonBitmapAtlas = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.buttonTiledTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTAEnemy, this, "button.png", 0, 0, 1, 1);
		
		//Final of the little testing
		
		
		
		
		this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
		this.mEngine.getTextureManager().loadTexture(this.mDigitalScreenControllerTexture);
		this.mEngine.getTextureManager().loadTexture(this.mBitmapTAEnemy);
		
		
		sceneManager =  new SceneManager(this);
	}

	@Override
	public Scene onLoadScene() {
		
		
		this.mEngine.registerUpdateHandler(new FPSLogger());

		scene = new Scene();

		//Loading the map:
		loadTMXMap();
		
		
		tmxLayer = this.mTMXTiledMap.getTMXLayers().get(0);
		tmxLayer2  = this.mTMXTiledMap.getTMXLayers().get(1);
		
		
		
		scene.attachChild(tmxLayer);
		scene.attachChild(tmxLayer2);//The order with which the layers and sprites are added matters
		
		
		/* Make the camera not exceed the bounds of the TMXEntity. */
		this.mBoundChaseCamera.setBounds(0, tmxLayer.getWidth(), 0, tmxLayer.getHeight());
		this.mBoundChaseCamera.setBoundsEnabled(true);

		//Setup the player
		setupPlayer();
		
		this.mBoundChaseCamera.setChaseEntity(player);
		
		//Setup for the Digital Controller
		setupController();
		
		
		/* Now we are going to create a rectangle that will  always highlight the tile below the feet of the pEntity. */
		/*final Rectangle currentTileRectangle = new Rectangle(0, 0, this.mTMXTiledMap.getTileWidth(), this.mTMXTiledMap.getTileHeight());
		currentTileRectangle.setColor(1, 0, 0, 0.25f);
		scene.attachChild(currentTileRectangle);

		scene.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() { }

			@Override
			public void onUpdate(final float pSecondsElapsed) {
				final float[] playerFootCordinates = player.convertLocalToSceneCoordinates(12, 31);

				final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
				if(tmxTile != null) {
					// tmxTile.setTextureRegion(null); <-- Rubber-style removing of tiles =D
					currentTileRectangle.setPosition(tmxTile.getTileX(), tmxTile.getTileY());
					
				}
			}
		});*/
		scene.attachChild(player);
		
		
		//Gives us a way to locate the NPCs in the map
		TileWidth = this.mTMXTiledMap.getTileWidth();
		
		loadEnemies();
		
		this.mDigitalScreenController.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		this.mDigitalScreenController.getControlBase().setAlpha(0.5f);
		this.mDigitalScreenController.getControlKnob().setAlpha(0.5f);
		
		
		
		scene.setChildScene(this.mDigitalScreenController);

		AnimatedSprite lol = new AnimatedSprite(this.CAMERA_WIDTH - buttonTiledTextureRegion.getWidth() - 10,this.CAMERA_HEIGHT - buttonTiledTextureRegion.getHeight() - 10,buttonTiledTextureRegion);
		//lol.setScaleY(2);
		lol.setPosition(this.CAMERA_WIDTH - buttonTiledTextureRegion.getWidth() - 10,this.CAMERA_HEIGHT - buttonTiledTextureRegion.getHeight() - 10);
		
		lol.setAlpha(0.5f);
		
		HUD test = new HUD();
		test.attachChild(lol);
		test.setPosition(0,0);
		
		this.mBoundChaseCamera.setHUD(test);
		
		
		
		//lol.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY)
		
		
		//scene.attachChild(test);
		
		//scene.attachChild(lol);
		
		return scene;
	}

	@Override
	public void onLoadComplete() {

	}
	
	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		
		Toast.makeText(this,"LOOOOLL",Toast.LENGTH_SHORT).show();
		return false;
	}
	
	public void setupPlayer(){
		/* Calculate the coordinates for the face, so its centered on the camera. */
		final int centerX = (CAMERA_WIDTH - this.mPlayerTextureRegion.getTileWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mPlayerTextureRegion.getTileHeight()) / 2;

		/* Create the sprite and add it to the scene. */
		player = new AnimatedSprite(centerX, centerY, this.mPlayerTextureRegion);
		player.animate(ANIMATE_FACING_DOWN,false);
		

	}
	
	
	
	
	//Sets up the policy for every digital controller change event
	public void setupController(){
		this.mDigitalScreenController = new DigitalOnScreenControl(0, CAMERA_HEIGHT - this.mDigitalScreenControllerTexture.getHeight(), this.mBoundChaseCamera, this.mDigitalControllerBase, this.mDigitalControllerKnob, 0.1f, new IOnScreenControlListener() {
			@Override
			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {
				if(pValueX == 1) {
					if(!player.isAnimationRunning()){
					playerDirection = dRIGHT;
					MoveModifier pathModifier = new MoveModifier(offsetTiming,player.getX(),returnNextX(),player.getY(),returnNextY());
					player.animate(new long[]{200, 200, 200}, 3, 5, false);
					player.registerEntityModifier(pathModifier);
					}
				} else if(pValueX == -1) {
					if(!player.isAnimationRunning()){
					playerDirection = dLEFT;
					MoveModifier pathModifier = new MoveModifier(offsetTiming,player.getX(),returnNextX(),player.getY(),returnNextY());
					player.animate(new long[]{200, 200, 200}, 9, 11, false);
					player.registerEntityModifier(pathModifier);
					}
				} else if(pValueY == 1) {
					if(!player.isAnimationRunning()){
					playerDirection = dDOWN;
					MoveModifier pathModifier = new MoveModifier(offsetTiming,player.getX(),returnNextX(),player.getY(),returnNextY());
					player.animate(new long[]{200, 200, 200}, 6, 8, false);
					player.registerEntityModifier(pathModifier);
					}
				} else if(pValueY == -1) {
					if(!player.isAnimationRunning()){
					playerDirection = dUP;
					MoveModifier pathModifier = new MoveModifier(offsetTiming,player.getX(),returnNextX(),player.getY(),returnNextY());
					player.animate(new long[]{200, 200, 200}, 0, 2, false);
					player.registerEntityModifier(pathModifier);
					
					
					
					}
			}
		}});
	}
	
	

	
	
	//Calculates the next coordinates according to the player's decision
	public float returnNextX() {
		float nextX = player.getX();
		
		final float[] playerFootCordinates = player.convertLocalToSceneCoordinates(12, 31);
		//Creating a tile with the info of the player's current position
		final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
		
		//tmxLayer.getTMXTile(1,1).
		
		switch(playerDirection){
			case (dDOWN):
			{
				break;
			}
			case (dUP):
			{
				break;
			}
			case (dLEFT):
			{
				if(tmxTile.getTileColumn() > 0 && (!this.mapUnwalkableTiles.isBlocked(tmxTile.getTileColumn() - 1, tmxTile.getTileRow()))&& (!this.characManager.isOccupied(tmxTile.getTileColumn() - 1, tmxTile.getTileRow())))
					nextX = tmxTile.getTileX() - tmxTile.getTileWidth() + offsetSpriteX;
				
				if((this.mapSignTiles.isBlocked(tmxTile.getTileColumn() - 1, tmxTile.getTileRow())))
					System.out.println("Sign Found!");
				break;
			}
			case (dRIGHT):
			{
				if(tmxTile.getTileColumn() < tmxLayer.getTileColumns() - 1  && (!this.mapUnwalkableTiles.isBlocked(tmxTile.getTileColumn() + 1, tmxTile.getTileRow()))&& (!this.characManager.isOccupied(tmxTile.getTileColumn() + 1, tmxTile.getTileRow())))
					nextX = tmxTile.getTileX() + tmxTile.getTileWidth() + offsetSpriteX;
				
				if((this.mapSignTiles.isBlocked(tmxTile.getTileColumn() + 1, tmxTile.getTileRow())))
					System.out.println("Sign Found!");				
				break;
			}
		}
		
		return nextX;
	}
	
	public float returnNextY(){
		float nextY = player.getY();
		final float[] playerFootCordinates = player.convertLocalToSceneCoordinates(12, 31);
		final TMXTile tmxTile = tmxLayer.getTMXTileAt(playerFootCordinates[Constants.VERTEX_INDEX_X], playerFootCordinates[Constants.VERTEX_INDEX_Y]);
		
		switch(playerDirection){
			case (dDOWN):
			{
				if(tmxTile.getTileRow() < tmxLayer.getTileRows() - 1  && (!this.mapUnwalkableTiles.isBlocked(tmxTile.getTileColumn(), tmxTile.getTileRow() + 1)) && (!this.characManager.isOccupied(tmxTile.getTileColumn(), tmxTile.getTileRow()+1)))
					nextY = tmxTile.getTileY() + tmxTile.getTileHeight() - offsetSpriteY;
				
				if((this.mapSignTiles.isBlocked(tmxTile.getTileColumn(), tmxTile.getTileRow() + 1)))
					System.out.println("Sign Found!");
				break;
			}
			case (dUP):
			{
				if(tmxTile.getTileRow() > 0 && (!this.mapUnwalkableTiles.isBlocked(tmxTile.getTileColumn(), tmxTile.getTileRow() - 1)) && (!this.characManager.isOccupied(tmxTile.getTileColumn(), tmxTile.getTileRow()-1)))
					nextY = tmxTile.getTileY() - tmxTile.getTileHeight() - offsetSpriteY;
				
				if((this.mapSignTiles.isBlocked(tmxTile.getTileColumn(), tmxTile.getTileRow() - 1)))
					System.out.println("Sign Found!");
				break;
			}
			case (dLEFT):
			{
				break;
			}
			case (dRIGHT):
			{
				break;
			}
		}
		return nextY;		
	}
	
	
	//Initializes the map alongside with its 
	private void loadTMXMap(){
		
		//Initializing linked lists containing block properties to be consulted while moving the character through the environment
		final LinkedList<BlockedArea> listOfBlockedTiles = new LinkedList<BlockedArea>();
		final LinkedList<BlockedArea> listOfInteractionTiles = new LinkedList<BlockedArea>();
		
		
		try {
			final TMXLoader tmxLoader = new TMXLoader(this, this.mEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {
					/* We are going to count the tiles that have the property "cactus=true" set. */
					/*if(pTMXTileProperties.containsTMXProperty("cactus", "true")) {
						AndEngineTestActivity.this.mCactusCount++;
					}*/
					
					//Adding to the linked list a block which
					if(pTMXTileProperties.containsTMXProperty("Block","T")){
						BlockedArea blockedTile = new BlockedArea(pTMXTile.getTileColumn(),pTMXTile.getTileRow());//X then Y
						listOfBlockedTiles.add(blockedTile);
					}
					if(pTMXTileProperties.containsTMXProperty("Sign","T")){
						BlockedArea sign = new BlockedArea(pTMXTile.getTileColumn(),pTMXTile.getTileRow());
						listOfInteractionTiles.add(sign);
					}
				}
			});
			this.mTMXTiledMap = tmxLoader.loadFromAsset(this, assetConstants.gameMapPath());
			//Compress the map with gzip!!

		} catch (final TMXLoadException tmxle) {
			Debug.e(tmxle);
		}
		
		if(listOfBlockedTiles.isEmpty() == false){
			
		}
		
		this.mapUnwalkableTiles = new TilePropertyMatrix(listOfBlockedTiles, this.mTMXTiledMap.getTileColumns()+1, this.mTMXTiledMap.getTileRows()+1);
		this.mapSignTiles = new TilePropertyMatrix(listOfInteractionTiles, this.mTMXTiledMap.getTileColumns()+1, this.mTMXTiledMap.getTileRows()+1);
		

		
		
	}

	
	public void loadEnemies(){
		
		characManager = CharacterGroupParser.parseCharacters(this.mTMXTiledMap.getTileColumns()+1, this.mTMXTiledMap.getTileRows()+1);
		
		
		characManager.addCharactersToScene(this.scene,this.mEngine,this);
		
		System.out.println("TILE WIDTH: " + this.mTMXTiledMap.getTileWidth() + "; TILE HEIGHT: " + this.mTMXTiledMap.getTileHeight());
		
	}	
	
	
}
