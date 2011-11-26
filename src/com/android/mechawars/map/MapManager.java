package com.android.mechawars.map;

import java.util.LinkedList;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.anddev.andengine.entity.layer.tiled.tmx.util.exception.TMXLoadException;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.Debug;

import android.content.Context;

public class MapManager {
	
	TMXTiledMap gameMap;
		
	//TODO: Load map through a JSON file!
	String gameMapPath = LoadAssets.mapPath;
	
	TilePropertyMatrix blockedAreasOnMap;
	
	
	//Loads the map from the files.
	public MapManager(Context gameContext,Engine gameEngine, Scene mapScene){
		
		final LinkedList<BlockedArea> listOfBlockedTiles = new LinkedList<BlockedArea>(); //Holds the blocked positions while loading the map
		try {
			final TMXLoader tmxLoader = new TMXLoader(gameContext, gameEngine.getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA, new ITMXTilePropertiesListener() {
				@Override
				public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {

					if(pTMXTileProperties.containsTMXProperty("Block","T")){
						BlockedArea blockedTile = new BlockedArea(pTMXTile.getTileColumn(),pTMXTile.getTileRow());//X then Y
						listOfBlockedTiles.add(blockedTile);
					}
				}
			});
	   
		this.gameMap = tmxLoader.loadFromAsset(gameContext, gameMapPath);
		//Compress the map with gzip!!
		} catch (final TMXLoadException tmxle) {
		Debug.e(tmxle);
		}
		
		this.blockedAreasOnMap = new TilePropertyMatrix(listOfBlockedTiles, getMapColumns(), getMapRows());
		
		addMapLayers(mapScene);
	}
	//TODO: How are we going to handle the scenes?
	private void addMapLayers(Scene mapScene){
		
		int mapLayersIterator;
		final int quantityOfLayers = this.gameMap.getTMXLayers().size();
		
		//Adding each layer to the scene
		for(mapLayersIterator = 0; mapLayersIterator < quantityOfLayers; mapLayersIterator++){
			mapScene.attachChild(this.gameMap.getTMXLayers().get(mapLayersIterator));
		}
	}
	
	//This method is used for querying about the walkability of a given position on the map.
	public Boolean isTheTileBlocked(int column,int row){
		return (this.blockedAreasOnMap.isBlocked(column,row));
	}
	
	//Testing whether some coordinates trespass the map frontiers.
	public Boolean isItBeyondFrontier(int column, int row){
		
		//Gets the "up" and "left" frontiers.
		Boolean minimumFrontier = ((column < 0) || (row < 0));
		
		//Gets the "down" and "right" frontiers. Here, we don't have to make the adjustment to the gameMap methods around the map size.
		Boolean maximumFrontier = (((getMapColumns() - 1) < column)||((getMapRows() - 1) < row));
		
		return (minimumFrontier || maximumFrontier);
	}
	
	//The next constants (+1) are that way thanks to the way the used engine implements those methods
	public int getMapColumns(){
		return this.gameMap.getTileColumns()+1;
	}
	
	public int getMapRows(){
		return this.gameMap.getTileRows()+1;
	}

}
