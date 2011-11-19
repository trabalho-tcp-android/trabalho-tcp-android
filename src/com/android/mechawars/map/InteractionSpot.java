package com.android.mechawars.map;

public class InteractionSpot extends IntegerPointCoordinates{
	
	String interactionDescription;
	
	public InteractionSpot(int x, int y, String event){
		super(x,y);
		this.interactionDescription = event;
	}
}
