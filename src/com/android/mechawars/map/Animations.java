package com.android.mechawars.map;

import java.util.ArrayList;

import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*ANIMATIONS*/
/*Implements certain level of encapsulation for the character's animations.*/
/*This class provides enough abstraction for the character animations we are going to load from the JSON files.*/

public class Animations {
	
	private final String animUp = LoadAssets.ANIMATE_FACING_UP;
	private final int animUpIndex = 0;
	
	private final String animDown = LoadAssets.ANIMATE_FACING_DOWN;
	private final int animDownIndex = 1;
		
	private final String animRight = LoadAssets.ANIMATE_FACING_RIGHT;
	private final int animRightIndex = 2;
	
	private final String animLeft = LoadAssets.ANIMATE_FACING_LEFT;
	private final int animLeftIndex = 3;
	
	private final String animWalkUp = LoadAssets.ANIMATE_WALKING_FACING_UP;
	private final int animWalkUpIndex = 4;
	
	private final String animWalkDown = LoadAssets.ANIMATE_WALKING_FACING_DOWN;
	private final int animWalkDownIndex = 5;
	
	private final String animWalkRight = LoadAssets.ANIMATE_WALKING_FACING_RIGHT;
	private final int animWalkRightIndex = 6;
	
	private final String animWalkLeft = LoadAssets.ANIMATE_WALKING_FACING_LEFT;
	private final int animWalkLeftIndex = 7;
	
	
	private	ArrayList<long[]> characterAnimations;
	
	
	
	public Animations(final long[] animUp,final long[] animRight,final long[] animLeft,final long[] animDown,final long[] animWalkUp,final long[] animWalkRight,final long[] animWalkLeft,final long[] animWalkDown){
		
		characterAnimations = new ArrayList<long[]>();
		
		//FACING UP
		characterAnimations.add(animUp);

		//FACING DOWN		
		characterAnimations.add(animDown);
		
		//FACING RIGHT		
		characterAnimations.add(animRight);
		
		//FACING LEFT		
		characterAnimations.add(animLeft);
		
		//FACING UP WALKING		
		characterAnimations.add(animWalkUp);
		
		//FACING DOWN WALKING	
		characterAnimations.add(animWalkDown);
		
		//FACING RIGHT WALKING	
		characterAnimations.add(animWalkRight);
		
		//FACING LEFT WALKING			
		characterAnimations.add(animWalkLeft);
		
		
		
	}
	
	public Animations(JSONObject animationObject){
		
		characterAnimations = new ArrayList<long[]>();
		
		try{
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingUp")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingDown")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingRight")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingLeft")));
		
		
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingUpWalking")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingDownWalking")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingRightWalking")));
		characterAnimations.add(characterAnimationArrayInitializer(animationObject.getJSONArray("animationFacingLeftWalking")));
	
		} catch (JSONException e) {
			Debug.e("Malformed JSON: ",e);
			
		}
		
	}
	
	
	private long[]  characterAnimationArrayInitializer(JSONArray animationObject) throws JSONException{
		
		long[] characterAnimation = new long[animationObject.length()];
		
		int j;
		for(j = 0; j < animationObject.length(); j++){
			characterAnimation[j] = animationObject.getLong(j);
		}
		
		return characterAnimation;
	}
	
	//Retrieves one of the NPC's possible animations
	public long[] getAnimation(String requestedAnimation){
		
		if(requestedAnimation.equals(animUp))
			return characterAnimations.get(animUpIndex);
		
		if(requestedAnimation.equals(animDown))
			return characterAnimations.get(animDownIndex);
	
		if(requestedAnimation.equals(animRight))
			return characterAnimations.get(animRightIndex);
	
		if(requestedAnimation.equals(animLeft))
			return characterAnimations.get(animLeftIndex);
		
		if(requestedAnimation.equals(animWalkUp))
			return characterAnimations.get(animWalkUpIndex);

		if(requestedAnimation.equals(animWalkDown))
			return characterAnimations.get(animWalkDownIndex);
			
		if(requestedAnimation.equals(animWalkRight))
			return characterAnimations.get(animWalkRightIndex);
			
		if(requestedAnimation.equals(animWalkLeft))
			return characterAnimations.get(animWalkLeftIndex);
			
		
		//FALLBACK

		return characterAnimations.get(animDownIndex);
	}
	
	public void printAnimations(){
		int i;
		for(i = 0; i < 8; i++){
			long[] lol = characterAnimations.get(i);
			System.out.println("Animation " + i + " : " + lol[0] + "/" + lol[1] + "/" + lol[2] + "/" + lol[3] + "/" + lol[4] + "/" + lol[5] + "/" + lol[6] + "/" + lol[7] + "/" + lol[8] + "/" + lol[9] + "/" + lol[10] + "/" + lol[11] + ";");
		}
	}
}


//if(requestedAnimation == animUp)
//	return characterAnimations.get(animUpIndex);
//
//if(requestedAnimation == animDown)
//	return characterAnimations.get(animDownIndex);
//
//if(requestedAnimation == animRight)
//	return characterAnimations.get(animRightIndex);
//
//if(requestedAnimation == animLeft)
//	return characterAnimations.get(animLeftIndex);
//
//if(requestedAnimation == animWalkUp)
//	return characterAnimations.get(animWalkUpIndex);
//
//if(requestedAnimation == animWalkDown)
//	return characterAnimations.get(animWalkDownIndex);
//
//if(requestedAnimation == animWalkRight)
//	return characterAnimations.get(animWalkRightIndex);
//
//if(requestedAnimation == animWalkLeft)
//	return characterAnimations.get(animWalkLeftIndex);