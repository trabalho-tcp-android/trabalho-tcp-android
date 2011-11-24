package com.android.mechawars.map;

import java.util.ArrayList;

/*ANIMATIONS*/
/*Implements certain level of encapsulation for the character's animations.*/
/*This class provides enough abstraction for the character animations we are going to load from the JSON files.*/

public class Animations {
	
	private final String animUp = "ANIMATE_FACING_UP";
	private final int animUpIndex = 0;
	
	private final String animDown = "ANIMATE_FACING_DOWN";
	private final int animDownIndex = 1;
		
	private final String animRight = "ANIMATE_FACING_RIGHT";
	private final int animRightIndex = 2;
	
	private final String animLeft = "ANIMATE_FACING_LEFT";
	private final int animLeftIndex = 3;
	
	private final String animWalkUp = "ANIMATE_WALKING_FACING_UP";
	private final int animWalkUpIndex = 4;
	
	private final String animWalkDown = "ANIMATE_WALKING_FACING_DOWN";
	private final int animWalkDownIndex = 5;
	
	private final String animWalkRight = "ANIMATE_WALKING_FACING_RIGHT";
	private final int animWalkRightIndex = 6;
	
	private final String animWalkLeft = "ANIMATE_WALKING_FACING_LEFT";
	private final int animWalkLeftIndex = 7;
	
	
	private	ArrayList<long[]> characterAnimations;
	
	
	
	public Animations(final long[] animUp,final long[] animRight,final long[] animLeft,final long[] animDown,final long[] animWalkUp,final long[] animWalkRight,final long[] animWalkLeft,final long[] animWalkDown){
		
		characterAnimations = new ArrayList<long[]>();
		
		//FACING UP
		characterAnimations.add(animUpIndex,animUp);

		//FACING DOWN		
		characterAnimations.add(animDownIndex,animDown);
		
		//FACING RIGHT		
		characterAnimations.add(animRightIndex,animRight);
		
		//FACING LEFT		
		characterAnimations.add(animLeftIndex,animLeft);
		
		//FACING UP WALKING		
		characterAnimations.add(animWalkUpIndex,animWalkUp);
		
		//FACING DOWN WALKING	
		characterAnimations.add(animWalkDownIndex,animWalkDown);
		
		//FACING RIGHT WALKING	
		characterAnimations.add(animWalkRightIndex,animWalkRight);
		
		//FACING LEFT WALKING			
		characterAnimations.add(animWalkLeftIndex,animWalkLeft);
		
		

		
		
	}
	
	//Retrieves one of the NPC's possible animations
	public long[] getAnimation(String requestedAnimation){
		
		if(requestedAnimation == animUp)
			return characterAnimations.get(animUpIndex);

		if(requestedAnimation == animDown)
			return characterAnimations.get(animDownIndex);
		
		if(requestedAnimation == animRight)
			return characterAnimations.get(animRightIndex);
		
		if(requestedAnimation == animLeft)
			return characterAnimations.get(animLeftIndex);
		
		if(requestedAnimation == animWalkUp)
			return characterAnimations.get(animWalkUpIndex);
		
		if(requestedAnimation == animWalkDown)
			return characterAnimations.get(animWalkDownIndex);
		
		if(requestedAnimation == animWalkRight)
			return characterAnimations.get(animWalkRightIndex);
		
		if(requestedAnimation == animWalkLeft)
			return characterAnimations.get(animWalkLeftIndex);
		
		//FALLBACK
		
		return characterAnimations.get(animDownIndex);
	}
}
