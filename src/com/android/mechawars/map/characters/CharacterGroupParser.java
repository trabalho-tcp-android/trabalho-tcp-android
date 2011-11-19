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
	
	private CharacterGroupManager parseCharacters(){
		return parseCharacters(R.raw.mapcharacters);
	}
	
	private CharacterGroupManager parseCharacters(int jsonResource){
		
        try {
            InputStream is = SceneManager.getBase().getResources().openRawResource(jsonResource);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject jsonFile = new JSONObject(jsontext);
            
            JSONArray characters = jsonFile.getJSONArray("characters");

            return this.updateGroupManager(buildCharacterGroupManager(characters));
            
            
        } catch (IOException fe) {
            Debug.e("Cannot load the characters file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: ",je);
        }
		
		CharacterGroupManager x = new CharacterGroupManager(10,10);
		return x;
	}
	
	private CharacterGroupManager buildCharacterGroupManager(JSONArray characters){
		
		CharacterGroupManager x = new CharacterGroupManager(10,10);
		
		int i;
		for(i = 0; i < characters.length(); i++){

			try {
				JSONObject characterObject = characters.getJSONObject(i);
				JSONArray characterPosition = characterObject.getJSONArray("position");
				
				final long[] characterAnimation = new long[]{};
				CharacterNPC newCharacter = new CharacterNPC(characterObject.optString("name","<Unknown>_"+i), characterPosition.getInt(0), characterPosition.getInt(1),(float) characterObject.getLong("tileWidth"), characterObject.optString("sprite","enemy.png"), characterAnimation);
				x.addCharacterNPC(newCharacter);
				
			} catch (JSONException e) {
				Debug.e("Malformed JSON: ",e);
			}

		}
		
		return x;
		
	}
	
	
}
