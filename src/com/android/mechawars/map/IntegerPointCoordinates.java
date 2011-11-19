package com.android.mechawars.map;

public class IntegerPointCoordinates {
	
	private int point_x;
	private int point_y;
	
	public IntegerPointCoordinates(int x,int y){
		this.point_x = x;
		this.point_y = y;
	}
	
	public void reassignPoints(int x,int y){
		this.point_x = x;
		this.point_y = y;
	}
	
	public int getX(){
		return this.point_x;
	}
	
	public int getY(){
		return this.point_y;
	}
}
