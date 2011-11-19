package com.android.mechawars.ffBox.ffDialog;

import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.ffBox.Box;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;

import java.util.ArrayList;

public class Dialog {

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
    private Scene dialogScene;
    private String label;

    public Dialog(int numLines,int position,Font font,ArrayList<String> itemsText, String label) {
        this.font = font;
        this.numLines = numLines;
        this.width = MechawarsActivity.getCameraWidth();
        this.label = label;

        this.height = this.getLineHeight() * numLines;

        float centerX = MechawarsActivity.getCenterX() - this.width / 2;
        float centerY = MechawarsActivity.getCenterY() - this.height / 2;
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
                pX = centerX; pY = MechawarsActivity.getCameraHeight() - width;
                break;
        }

        this.box = Box.createEntity(pX, pY, width, lineHeight * numLines + boxPadding * 2);
        this.letterWidth = new Float(new Text(0, 0, font, "QWER").getWidth() / 4).intValue();

        //this.dialogScene.attachChild(this.box);
        SceneManager.getBase().getEngine().getScene().attachChild(this.box);

        for(String itemText : itemsText) {
            String text = "["+label+"] "+itemText;
            DialogItem dialogItem = new DialogItem(box.getX()+boxPadding,box.getY()+boxPadding,text, new ArrayList<ArrayList<String>>());
            dialogItems.size();
            dialogItems.add(dialogItem);
            String x = "";
        }

        //this.dialogScene.attachChild(dialogItems.get(0));
        //SceneManager.getBase().getEngine().getScene().attachChild(dialogItems.get(0));

        //SceneManager.getBase().getEngine().getScene().attachChild(dialogScene);
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
        SceneManager.getBase().getEngine().getScene().setChildScene(this.dialogScene, true, true, true);
    }
}
