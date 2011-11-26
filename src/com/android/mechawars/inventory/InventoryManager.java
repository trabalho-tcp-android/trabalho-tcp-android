package com.android.mechawars.inventory;

import com.android.mechawars.R;
import com.android.mechawars.SceneManager;
import com.android.mechawars.ffBox.ffMenu.Menu;
import com.android.mechawars.ffBox.ffMenu.SimpleMenu;
import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class InventoryManager {

    public static final int PLAYER_HUMAN = 0;
    public static final int PLAYER_NPC_FIGHTER = 1;
    public static final int PLAYER_NPC_SALESMAN = 2;

    private static InventoryManager instance = new InventoryManager();

    private static HashMap<String, Inventory> inventories = new HashMap<String, Inventory>();

    public static InventoryManager instance() {
        return instance;
    }

    private InventoryManager() {}

    public static Inventory getInventory(String charName) {
        return inventories.get(charName);
    }

    /**
     * Loads a inventory from a json Object
     * @param charName
     * @param playerType
     * @param jsonArrayItems
     * @return
     */
    public Inventory fromJSON(String charName,int playerType, JSONArray jsonArrayItems) {
        try {
            Inventory inventory;
            switch (playerType) {
                case 0:
                    inventory=new BackpackInventory();
                    break;
                case 1:
                    inventory=new FighterInventory();
                    break;
                case 2:
                    inventory=new SalesInventory();
                    break;
                default:
                    inventory=new Inventory();
                    break;
            }

            for(int i=0;i<jsonArrayItems.length();i++) {
                JSONObject jsonObjItem = jsonArrayItems.getJSONObject(i);
                inventory.addItem(new InventoryItem(jsonObjItem.getString("name")),jsonObjItem.getInt("quantity"));
            }

            inventories.put(charName,inventory);
            return inventory;
        } catch (JSONException je) {
            Debug.e("Malformed Player JSON: "+charName,je);
        }

        return new Inventory();
    }
}
