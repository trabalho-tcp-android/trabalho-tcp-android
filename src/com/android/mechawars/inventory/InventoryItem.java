package com.android.mechawars.inventory;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.android.mechawars.actionsRunner.ActionsManager;
import com.android.mechawars.utils.DbUtils;
import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InventoryItem {
    
    protected int id;
    protected String name;
    protected String prettyName;
    protected String description;

    private final ArrayList<ArrayList<String>> itemActions = new ArrayList<ArrayList<String>>();

    public InventoryItem(String name) {
        SQLiteDatabase db = DbUtils.getDatabase();
        Cursor results = db.rawQuery("SELECT * FROM items WHERE name = \'" + name +"\'", null);
        results.moveToFirst();
        this.id = results.getInt(0);
        this.name = results.getString(1);
        this.prettyName = results.getString(2);
        this.description = results.getString(3);

        try {
            JSONArray actions = new JSONArray(results.getString(4));
            setActions(actions);
        } catch (JSONException e) {
            Debug.e("Parsing error on actions from item "+name);
        }
    }

    public InventoryItem(int id, String name, String prettyName) {
        this(id,name,prettyName,"");
    }

    public InventoryItem(int id,String name,String prettyName, String description) {
        this.id = id;
        this.name = name;
        this.prettyName = prettyName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setActions(ArrayList<ArrayList<String>> actions) {
        itemActions.addAll(actions);
    }


    public void setActions(JSONArray itemActions) {
        this.setActions(actionsFromJSONArray(itemActions));
    }

    protected ArrayList<ArrayList<String>> actionsFromJSONArray(JSONArray itemActions) {
        ArrayList<ArrayList<String>> actions = new ArrayList<ArrayList<String>>();
        try {
            for(int i=0;i<itemActions.length();i++) {
                JSONArray jsonParameters = itemActions.getJSONArray(i);
                ArrayList<String> parameters = new ArrayList<String>();
                for(int j=0;j<jsonParameters.length();j++) {
                    parameters.add(j,jsonParameters.getString(j));
                }
                actions.add(i,parameters);
            }
        } catch (JSONException e) {
            Debug.e("Couldn't retrieve actions from JSONArray on inventoryItem " + this.name, e);
        }
        return actions;
    }

    protected ArrayList<ArrayList<String>> cloneList(ArrayList<ArrayList<String>> list) {
        ArrayList<ArrayList<String>> clone = new ArrayList<ArrayList<String>>(list.size());
        for(ArrayList<String> item: list) {
            ArrayList<String> clonedItem = new ArrayList<String>(item.size());
            for(String parameter: item) {
                clonedItem.add(parameter);
            }
            clone.add(clonedItem);
        }
        return clone;
    }

    public InventoryItem activate() {
        if(!itemActions.isEmpty())
            new ActionsManager(cloneList(itemActions)).runList();
        return this;
   	}
}
