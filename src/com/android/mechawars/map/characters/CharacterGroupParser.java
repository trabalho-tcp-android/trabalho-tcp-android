package com.android.mechawars.map.characters;

import com.android.mechawars.R;
import com.android.mechawars.map.CharacterGroupManager;

public class CharacterGroupParser {
	
	private static CharacterGroupParser instance = new CharacterGroupParser();
	
	
	
	public static CharacterGroupParser instance(){
		return instance;
	}
	
	private CharacterGroupParser(){
	}
	
	private CharacterGroupManager parseCharacters(String jsonNode){
		return parseCharacters(jsonNode,R.raw.mapcharacters);
	}
	
	private CharacterGroupManager parseCharacters(String jsonNode,int jsonResource){
		CharacterGroupManager x = new CharacterGroupManager(10,10);
		return x;
	}
	
	
}
