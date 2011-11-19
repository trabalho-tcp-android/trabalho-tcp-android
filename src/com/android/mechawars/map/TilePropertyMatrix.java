package com.android.mechawars.map;

import java.util.LinkedList;

//This class holds a matrix containing the "walkability" property of the map tiles.
public class TilePropertyMatrix {
	
	private Boolean mapMatrix[][];
	private int columnBounds;
	private int rowBounds;
	
	
	//Constructor for when we have the list of blocked areas 
	public TilePropertyMatrix(LinkedList<BlockedArea> listOfBlocks, int mapColumns, int mapRows){
		
		int i,j;
		
		//Setting up the matrix which represents the map area
		mapMatrix = new Boolean[mapColumns][mapRows];
		
		columnBounds = mapColumns;
		rowBounds = mapRows;
		
		//Initializing the matrix
		for(i = 0; i < mapColumns; i++)
		{
			for(j = 0; j < mapRows; j++)
			{
				mapMatrix[i][j] = false;
			}
		}
		
		final int listSize = listOfBlocks.size();
		int k;
		
		for(k = 0; k < listSize; k++)
		{
			//Assigning a true value for each tile referenced in the list
			mapMatrix[listOfBlocks.get(k).getX()][listOfBlocks.get(k).getY()] = true;
		}
	}
	
	//Constructor for initializing the matrix
	public TilePropertyMatrix(final int mapColumns, final int mapRows){
		
		mapMatrix = new Boolean[mapColumns][mapRows];
		columnBounds = mapColumns;
		rowBounds = mapRows;
		
		int i,j;//Indexes for getting through the matrix
		
		for(i = 0; i < mapColumns; i++)
		{
			for(j = 0; j < mapRows; j++)
			{
				mapMatrix[i][j] = false;
			}
		}
		
		
	}
	
	public void setTileValue(final int columnIndex,final int rowIndex,final Boolean newValue){
		
		if((columnIndex < columnBounds - 1) && (columnIndex >= 0) && (rowIndex < rowBounds - 1) && (rowIndex >= 0)){
			mapMatrix[columnIndex][rowIndex] = newValue;
		}
		else
		{
			System.out.println("Coordinates for new tile block assignment are out of bounds!");
		}
	}
	
	public Boolean isBlocked(final int columnIndex,final int rowIndex){
		if((columnIndex < columnBounds - 1) && (columnIndex >= 0) && (rowIndex < rowBounds - 1) && (rowIndex >= 0)){
			
			return mapMatrix[columnIndex][rowIndex];
			
		}
		else
		{
			System.out.println("Coordinates for requested tile are out of bounds!");
			
		}
		return false;
	}
}
