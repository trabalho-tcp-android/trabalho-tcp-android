package com.android.mechawars.ffBox.ffMenu;

import android.util.Log;
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

/**
 * Created by IntelliJ IDEA.
 * User: Rodrigo
 * Date: 11/11/11
 * Time: 12:23
 */
public final class MenuManager {

    private static MenuManager instance = new MenuManager();

    private static HashMap<String, Menu> Menus = new HashMap<String, Menu>();

    public static MenuManager instance() {
        return instance;
    }

    private MenuManager() {
    }

    public Menu putMenu(String id, Menu menuObject) {
        Menus.put(id, menuObject);
        return menuObject;
    }

    public Menu getMenu(String id) {
        return Menus.get(id);
    }

    /**
     * Loads the menu from Menu File "raw/menus.json"
     *
     * @param jsonNode String do nó pai no arquivo raw/menus.json
     * @return Menu
     */
    public Menu fromJSON(String jsonNode) {
        return fromJSON(jsonNode, R.raw.menus);
    }

    /**
     * Loads a menu from a custom jsonFile
     *
     * @param jsonNode     String do no pai no arquivo
     * @param jsonResource O numero do recurso na classe R (R.pasta.arquivo)
     * @return Menu
     */
    public Menu fromJSON(String jsonNode, int jsonResource) {
        try {
            String x = "";
            InputStream is = SceneManager.getBase().getResources().openRawResource(jsonResource);
            byte[] buffer = new byte[is.available()];
            while (is.read(buffer) != -1) ;
            String jsontext = new String(buffer);
            JSONObject entries = new JSONObject(jsontext);
            JSONObject entry = entries.getJSONObject(jsonNode);
            JSONArray menuItems = entry.getJSONArray("items");
            String menuType = entry.optString("type","SimpleMenu");

            if(menuType.equals("SimpleMenu")) {
                return this.putMenu(jsonNode,buildSimpleMenu(entry,menuItems));
            }
        } catch (IOException fe) {
            Debug.e("Cannot load the menu file", fe);
        } catch (JSONException je) {
            Debug.e("Malformed JSON: "+jsonNode,je);
        }

        //Fallback para imprimir um menu genérico na tela (e o cabra buscar no log a causa)
        SimpleMenu menu = new SimpleMenu(0.8f,1,Menu.CENTER_CENTER);
        menu.setMenuLineText(0,"Menu exception! "+jsonNode);
        return this.putMenu(jsonNode,menu);
    }
    
    private SimpleMenu buildSimpleMenu(JSONObject entry, JSONArray menuItems) throws JSONException {
        SimpleMenu menu = new SimpleMenu((float) entry.optDouble("width", 0.8f),menuItems.length(),entry.optInt("position", 4));
        for(int i=0;i<menuItems.length();i++) {
            menu.getMenuLine(i).updateFromJSON(menuItems.getJSONObject(i));
        }
        return menu;
    }


}
