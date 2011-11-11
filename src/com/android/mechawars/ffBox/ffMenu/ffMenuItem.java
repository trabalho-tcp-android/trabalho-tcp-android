package com.android.mechawars.ffBox.ffMenu;

import android.widget.Toast;
import com.android.mechawars.SceneManager;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.opengl.font.Font;

import java.util.ArrayList;

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

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	/**
	 * Sets the text of the changeableText object
	 * @param pText Text to set
	 */
	public void setText(String pText) {
		super.setText(pText);
	}

    public void setOnSelectActions(ArrayList<ArrayList<String>> actions) {
        onSelectActions.addAll(actions);
    }

    public void setOnUnselectActions(ArrayList<ArrayList<String>> actions) {
        onUnselectActions.addAll(actions);
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
		/* Nothing. */
        Toast.makeText(SceneManager.getBase(), this.getID() + ": " + this.getText(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onUnselected() {
		/* Nothing. */
	}

	// ===========================================================
	// Methods
	// ===========================================================


	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
