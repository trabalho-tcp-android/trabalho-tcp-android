package com.android.mechawars.ffBox.ffDialog;

import android.widget.Toast;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.ffBox.Box;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.CameraScene;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.util.Debug;

import java.util.ArrayList;

public class Dialog implements MenuScene.IOnMenuItemClickListener {

    private Font font;
    private int numLines;
    protected int width;
    private int lineHeight;
    private int height;
    private Rectangle box;
    protected int letterWidth;
    private int maxChars;
    protected int boxPadding = Box.getLinePadding() * 4;
    private final ArrayList<String> itemsText = new ArrayList<String>();
    private final ArrayList<DialogItem> dialogItems = new ArrayList<DialogItem>();

    //Constants
    public static final int TOP_CENTER = 1;
    public static final int CENTER_CENTER = 4;
    public static final int BOTTOM_CENTER = 7;
    private MenuScene dialogScene;
    private String label;

    public Dialog(int numLines,int position,Font font,ArrayList<String> itemsText, String label) {
        this.font = font;
        this.numLines = numLines;
        this.width = new Float(SceneManager.getBase().getEngine().getCamera().getWidth()).intValue();
        this.label = label;

        this.height = this.getLineHeight() * numLines + boxPadding * 2;


        float centerX = SceneManager.getBase().getEngine().getCamera().getCenterX() - this.width / 2;
        float centerY = SceneManager.getBase().getEngine().getCamera().getCenterY() - this.height / 2;
        float pX, pY;
        switch (position) {
            case 1:
                pX = centerX; pY = 0;
                break;
            case 4:
                pX = centerX; pY = centerY;
                break;
            default:
                //Bottom-Center
                pX = centerX; pY = SceneManager.getBase().getEngine().getCamera().getHeight() - this.height;
                //pY = centerY;
                break;
        }

        this.box = Box.createEntity(pX, pY, width, height);
        this.letterWidth = new Float(new Text(0, 0, font, "QWER").getWidth() / 4).intValue();

        //Cria uma cena para ser utilizada como cena filho
        this.dialogScene = new MenuScene(SceneManager.getBase().getEngine().getCamera(),this);
        dialogScene.setBackgroundEnabled(false);
        this.dialogScene.attachChild(this.box);

        for(String itemText : itemsText) {
            String text = "["+label+"] "+itemText;
            DialogItem dialogItem = new DialogItem(box.getX()+boxPadding,box.getY()+boxPadding,text, new ArrayList<ArrayList<String>>());
            dialogItems.size();
            dialogItems.add(dialogItem);
        }

        //this.dialogScene.attachChild(dialogItems.get(0));
        this.dialogScene.addMenuItem(dialogItems.get(0));
    }

    public Dialog(int numLines, int position, ArrayList<String> itemsText, String label) {
        this(numLines, position, MechawarsActivity.getBasicFont(), itemsText,label);
    }

    public int getLineHeight() {
        return this.font.getLineHeight();
    }

    /**
     * Attach the menu to the scene
     */
    public void attachToScene() {
        SceneManager.getBase().getEngine().getScene().setChildScene(this.dialogScene, false, true, true);
    }

    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        Toast.makeText(SceneManager.getBase(), pMenuItem.getID() + ": Dialogo clicado", Toast.LENGTH_LONG).show();
        Debug.w("Dialog Clicked");
        return true;
    }
}
