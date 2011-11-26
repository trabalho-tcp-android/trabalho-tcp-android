package com.android.mechawars.map.characters;

import java.io.IOException;
import java.io.InputStream;

import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.mechawars.R;
import com.android.mechawars.SceneManager;
import com.android.mechawars.map.Animations;
import com.android.mechawars.map.CharacterGroupManager;
import com.android.mechawars.map.CharacterNPC;
import com.android.mechawars.map.CharacterResources;

public class CharacterGroupParser {
	
	private static CharacterGroupParser instance = new CharacterGroupParser();
	
	private static CharacterGroupManager charactersOnMapGroup;
	
	public static CharacterGroupParser instance(){
		return instance;
	}
	
	private CharacterGroupParser(){
	}
	
	private static CharacterGroupManager updateGroupManager(CharacterGroupManager newGroupManager){
		charactersOnMapGroup = newGroupManager;
		return newGroupManager;
	}
	
	public static CharacterGroupManager parseCharacters(final int mapColumns, final int mapRows){
		return parseCharacters(R.raw.mapcharacters,mapColumns,mapRows);
	}
	
	public static CharacterGroupManager parseCharacters(int jsonResource, final int mapColumns, final int mapRows){
		
        try {
            InputStream is = SceneManager.getMapBase().getResources().openRawResource(jsonResource);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject jsonFile = new JSONObject(jsontext);
            
            JSONArray characters = jsonFile.getJSONArray("characters");

            return updateGroupManager(buildCharacterGroupManager(characters,mapColumns,mapRows));
            
            
        } catch (IOException fe) {
            Debug.e("Cannot load the characters file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: ",je);
        }
		
		CharacterGroupManager fallbackGroup = new CharacterGroupManager(10,10);
		System.out.println("An Error occurred while loading the JSON character file.");
		return fallbackGroup;
	}
	
	//Loads the each and every character from the scene (except for the player)
	private static CharacterGroupManager buildCharacterGroupManager(JSONArray characters, final int mapColumns, final int mapRows){
		
		CharacterGroupManager createdGroupManager = new CharacterGroupManager(mapColumns,mapRows);
		
		int i;
		for(i = 0; i < characters.length(); i++){

			try {
				JSONObject characterObject = characters.getJSONObject(i);
				
				//TODO: Fix the character animations loading part - Hope it's been done!
				CharacterResources newResources = getCharacterResources(characterObject);
				
				CharacterNPC newCharacter = new CharacterNPC(characterObject.optString("name","<Unknown>_"+i),
															 newResources);
				
				createdGroupManager.addCharacterNPC(newCharacter);
				
			} catch (JSONException e) {
				Debug.e("Malformed JSON: ",e);
			}

		}
		
		return createdGroupManager;	
	}
	
	//Loads the resources for the given character
	private static CharacterResources getCharacterResources(JSONObject characterObject) throws JSONException{


			JSONArray characterPosition = characterObject.getJSONArray("position");
			
			JSONObject animationObject = characterObject.getJSONObject("characterAnimations");
			
			//Animation array initialization
			Animations characterAnimationsSet = new Animations(animationObject);
			
			String initialAnimation = animationObject.optString("startingAnimation","ANIMATE_FACING_DOWN");
			
			JSONArray spriteSheetDimensions = characterObject.getJSONArray("spriteSheetDimensions");
			
			//TODO: Fix the character animations loading part - Hope it's been done!
			CharacterResources newResources = new CharacterResources(characterPosition.getInt(0), characterPosition.getInt(1),
					 												(float) characterObject.getLong("tileWidth"),
																	(float) characterObject.getLong("tileHeight"),
																	characterObject.optString("sprite","enemy.png"),
																	characterObject.optInt("textureRegionX",512),
																	characterObject.optInt("textureRegionY",512),
																	characterAnimationsSet,initialAnimation,
																	spriteSheetDimensions.getInt(0),
																	spriteSheetDimensions.getInt(1));
			
			System.out.println("Player: " + characterPosition.getInt(0)+  characterPosition.getInt(1)+ 
						(float) characterObject.getLong("tileWidth")+ 
					(float) characterObject.getLong("tileHeight")+ 
					characterObject.optString("sprite","enemy.png")+ 
					characterObject.optInt("textureRegionX",512)+ 
					characterObject.optInt("textureRegionY",512)+ 
					characterAnimationsSet+ initialAnimation+ 
					spriteSheetDimensions.getInt(0)+ 
					spriteSheetDimensions.getInt(1));
			
			return newResources;
			
		
	}
	
	public static CharacterResources getPlayerResources(){
		
		
        try {
            InputStream is = SceneManager.getMapBase().getResources().openRawResource(R.raw.mapcharacters);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject jsonFile = new JSONObject(jsontext);
            
            JSONObject player = jsonFile.getJSONObject("player");
            
            return getCharacterResources(player);
            
            
        } catch (IOException fe) {
            Debug.e("Cannot load the player file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: ",je);
        }
        
        //TODO: make a better fallback?
        //Error moment
		return null;
		
		
	}
	
}
