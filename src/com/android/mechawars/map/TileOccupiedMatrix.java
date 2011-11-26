package com.android.mechawars.map;

public class TileOccupiedMatrix extends TilePropertyMatrix{
	public TileOccupiedMatrix(int columns,int rows){
		super(columns,rows);
	}
	
	@Override
	public Boolean isBlocked(final int columnIndex,final int rowIndex){
		if((columnIndex < columnBounds - 1) && (columnIndex >= 0) && (rowIndex < rowBounds - 1) && (rowIndex >= 0)){
			
			return mapMatrix[columnIndex][rowIndex];
			
		}
		else
		{
			System.out.println("Coordinates for requested tile are out of bounds!");
			return false;
			
		}
	}

}
