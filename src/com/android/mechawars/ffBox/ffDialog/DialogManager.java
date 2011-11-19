package com.android.mechawars.ffBox.ffDialog;

import com.android.mechawars.R;
import com.android.mechawars.SceneManager;
import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DialogManager {
    private static DialogManager instance = new DialogManager();

    private static HashMap<String, Dialog> Dialogs = new HashMap<String, Dialog>();

    public static DialogManager instance() {
        return instance;
    }

    private DialogManager() {
    }

    public Dialog putMenu(String id, Dialog dialogObject) {
        Dialogs.put(id, dialogObject);
        return dialogObject;
    }

    public Dialog getMenu(String id) {
        return Dialogs.get(id);
    }
    
    public Dialog fromJSON(String jsonNode) {
        return fromJSON(jsonNode, R.raw.chapter1);
    }
    
    public Dialog fromJSON(String jsonNode, int jsonRes) {
        try {
            String x = "";
            InputStream is = SceneManager.getBase().getResources().openRawResource(jsonRes);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject entries = new JSONObject(jsontext);
            JSONObject entry = entries.getJSONObject(jsonNode);
            JSONArray dialogLines = entry.getJSONArray("lines");
            JSONArray onEndActions = entry.getJSONArray("onEnd");

            ArrayList<String> itemsText = new ArrayList<String>();
            for(int i=0;i<dialogLines.length();i++) {
                itemsText.add(i,dialogLines.getString(i));
            }

            return new Dialog(dialogLines.length(),7,itemsText,entry.optString("label","NoName"));
        } catch (IOException fe) {
            Debug.e("Cannot load the menu file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: "+jsonNode,je);
        }

        //Fallback para imprimir um menu genérico na tela (e o cabra buscar no log a causa)
        ArrayList<String> fallbackText = new ArrayList<String>();
        fallbackText.add("Dialog Exception on "+jsonNode);
        return new Dialog(1,7,fallbackText,"NoName");
    }

}
