package com.android.mechawars.ffBox.ffDialog;

import android.widget.Toast;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.HorizontalAlign;

import java.util.ArrayList;

public class DialogItem extends TickerText implements IMenuItem {

    private ArrayList<ArrayList<String>> onEndActions = new ArrayList<ArrayList<String>>();
    
    public DialogItem(float pX, float pY, String pText, ArrayList<ArrayList<String>> onEndActions) {
        this(pX,pY, MechawarsActivity.getBasicFont(),pText,HorizontalAlign.LEFT,10,onEndActions);
    }

    public DialogItem(float pX, float pY, Font pFont, String pText, HorizontalAlign pHorizontalAlign, float pCharactersPerSecond, ArrayList<ArrayList<String>> onEndActions) {
        super(pX, pY, pFont, pText, pHorizontalAlign, pCharactersPerSecond);
        this.onEndActions = onEndActions;
    }

    @Override
    protected void onManagedUpdate(final float pSecondsElapsed) {
        super.onManagedUpdate(pSecondsElapsed);

        if((this.getCharactersMaximum() == this.getCharactersVisible()) ||(this.isReverse() && this.getCharactersVisible() == 0)) {
            this.executeOnEnd();
        }

    }

    private void executeOnEnd() {

    }

    @Override
    public int getID() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onSelected() {
        Toast.makeText(SceneManager.getBase(), this.getID() + " Unselected: " + this.getText(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUnselected() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
