package com.android.mechawars.ffBox.ffMenu;

import android.util.Log;
import android.widget.Toast;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.actionsRunner.ActionsManager;
import com.android.mechawars.ffBox.ffFont.FontManager;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Rodrigo Dlugokenski
 */
public class ffMenuItem extends ChangeableText implements IMenuItem {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final int mID;
    private final ArrayList<ArrayList<String>> onSelectActions = new ArrayList<ArrayList<String>>();
    private final ArrayList<ArrayList<String>> onUnselectActions = new ArrayList<ArrayList<String>>();

	// ===========================================================
	// Constructors
	// ===========================================================

	public ffMenuItem(final int pID, final Font pFont, final String pText) {
		super(0, 0, pFont, pText);
		this.mID = pID;
	}

	public ffMenuItem(final int pID, final Font pFont, final String pText, final int pMaxLenght) {
		super(0, 0, pFont, pText, pMaxLenght);
		this.mID = pID;
	}
    
    public static ffMenuItem itemFromJSON(int pID, JSONObject itemJSON) {
        String label = itemJSON.optString("label","labelError");
        ffMenuItem menuItem = new ffMenuItem(pID, FontManager.instance().getFont(),label);
        menuItem.setActionsFromJSON(itemJSON);
        return menuItem;
    }

    public ffMenuItem updateFromJSON(JSONObject itemJSON) {
        String label = itemJSON.optString("label","labelError");
        this.setText(label);
        this.setActionsFromJSON(itemJSON);
        return this;
    }

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Sets the text of the changeableText object
	 * @param pText Text to set
	 */
    @Override
	public void setText(String pText) {
		super.setText(pText);
	}

    protected void setActionsFromJSON(JSONObject itemJSON) {
        if(!itemJSON.isNull("onSelected")) {
            setOnSelectActions(itemJSON.optJSONArray("onSelected"));
        }
        
        if(!itemJSON.isNull("onUnselected")) {
            setOnUnselectActions(itemJSON.optJSONArray("onUnselected"));
        }
        
    }

    public void setOnSelectActions(ArrayList<ArrayList<String>> actions) {
        onSelectActions.addAll(actions);
    }

    public void setOnSelectActions(JSONArray selectActions) {
        this.setOnSelectActions(actionsFromJSONArray(selectActions));
    }

    public void setOnUnselectActions(ArrayList<ArrayList<String>> actions) {
        onUnselectActions.addAll(actions);
    }

    public void setOnUnselectActions(JSONArray unselectActions) {
        this.setOnUnselectActions(actionsFromJSONArray(unselectActions));
    }

    protected ArrayList<ArrayList<String>> actionsFromJSONArray(JSONArray menuActions) {
        ArrayList<ArrayList<String>> actions = new ArrayList<ArrayList<String>>();
        try {
            for(int i=0;i<menuActions.length();i++) {
                JSONArray jsonParameters = menuActions.getJSONArray(i);
                ArrayList<String> parameters = new ArrayList<String>();
                for(int j=0;j<jsonParameters.length();j++) {
                    parameters.add(j,jsonParameters.getString(j));
                }
                actions.add(i,parameters);
            }
        } catch (JSONException e) {
            Log.e("MechaWars","Couldn't retrieve actions from JSONArray on menuItem "+this.getText(),e);
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

	@Override
	public int getID() {
		return this.mID;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	public void onSelected() {
        if(!onSelectActions.isEmpty()) 
            new ActionsManager(cloneList(onSelectActions)).runList();
	}

	@Override
	public void onUnselected() {
        if(!onUnselectActions.isEmpty())
            new ActionsManager(cloneList(onUnselectActions)).runList();
        //Toast.makeText(SceneManager.getBase(), this.getID() + " Unselected: " + this.getText(), Toast.LENGTH_LONG).show();
	}

	// ===========================================================
	// Methods
	// ===========================================================


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
