package com.android.mechawars.map.characters;

import java.io.IOException;
import java.io.InputStream;

import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mechawars.R;
import com.android.mechawars.SceneManager;
import com.android.mechawars.map.CharacterGroupManager;
import com.android.mechawars.map.CharacterNPC;

public class CharacterGroupParser {
	
	private static CharacterGroupParser instance = new CharacterGroupParser();
	
	private CharacterGroupManager charactersOnMapGroup;
	
	public static CharacterGroupParser instance(){
		return instance;
	}
	
	private CharacterGroupParser(){
	}
	
	private CharacterGroupManager updateGroupManager(CharacterGroupManager newGroupManager){
		charactersOnMapGroup = newGroupManager;
		return newGroupManager;
	}
	
	public CharacterGroupManager parseCharacters(final int mapColumns, final int mapRows){
		return parseCharacters(R.raw.mapcharacters,mapColumns,mapRows);
	}
	
	public CharacterGroupManager parseCharacters(int jsonResource, final int mapColumns, final int mapRows){
		
        try {
            InputStream is = SceneManager.getBase().getResources().openRawResource(jsonResource);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject jsonFile = new JSONObject(jsontext);
            
            JSONArray characters = jsonFile.getJSONArray("characters");

            return this.updateGroupManager(buildCharacterGroupManager(characters,mapColumns,mapRows));
            
            
        } catch (IOException fe) {
            Debug.e("Cannot load the characters file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: ",je);
        }
		
		CharacterGroupManager fallbackGroup = new CharacterGroupManager(10,10);
		System.out.println("An Error occurred while loading the JSON character file.");
		return fallbackGroup;
	}
	
	private CharacterGroupManager buildCharacterGroupManager(JSONArray characters, final int mapColumns, final int mapRows){
		
		CharacterGroupManager createdGroupManager = new CharacterGroupManager(mapColumns,mapRows);
		
		int i;
		for(i = 0; i < characters.length(); i++){

			try {
				JSONObject characterObject = characters.getJSONObject(i);
				JSONArray characterPosition = characterObject.getJSONArray("position");
				
				JSONArray animationObject = characterObject.getJSONArray("animation");
				
				//Animation array initialization
				long[] characterAnimation = characterAnimationInitializer(animationObject);
				
				CharacterNPC newCharacter = new CharacterNPC(characterObject.optString("name","<Unknown>_"+i), characterPosition.getInt(0), characterPosition.getInt(1),(float) characterObject.getLong("tileWidth"), characterObject.optString("sprite","enemy.png"), characterAnimation);
				createdGroupManager.addCharacterNPC(newCharacter);
				
			} catch (JSONException e) {
				Debug.e("Malformed JSON: ",e);
			}

		}
		
		return createdGroupManager;	
	}
	
	private long[] characterAnimationInitializer(JSONArray animationObject) throws JSONException{
		
		long[] characterAnimation = new long[animationObject.length()];
		
		int j;
		for(j = 0; j < animationObject.length(); j++){
			characterAnimation[j] = animationObject.getLong(j);
		}
		
		return characterAnimation;
	}
	
}
