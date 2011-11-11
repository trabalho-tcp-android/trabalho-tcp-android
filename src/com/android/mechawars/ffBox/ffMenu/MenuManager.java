package com.android.mechawars.ffBox.ffMenu;

import android.util.Log;
import com.android.mechawars.R;
import com.android.mechawars.SceneManager;
import org.json.JSONObject;

import java.io.InputStream;
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
            JSONObject jsonObject = entries.getJSONObject(jsonNode);
            JSONObject menuItems = entries.getJSONObject("items");
            // builtMenu = this((float) jsonObject.getDouble("width"), menuItems.length(), SceneManager.getBase());
            for (int i = 0; i < menuItems.length(); i++) {

            }
            return builtMenu;
        } catch (Exception je) {
            Log.e("MechaWars", "Cannot load the menu file", je);
        }
    }


}
