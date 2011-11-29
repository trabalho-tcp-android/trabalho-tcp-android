package com.android.mechawars.ffBox.ffMenu;

import android.util.Log;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.ffBox.Box;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.menu.MenuScene;
import org.anddev.andengine.entity.scene.menu.item.IMenuItem;
import org.anddev.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;

import javax.microedition.khronos.opengles.GL10;
import java.util.ArrayList;

/**
 * @author Rodrigo Dlugokenski
 */
public abstract class Menu implements MenuScene.IOnMenuItemClickListener {
    private BaseGameActivity base;
    private Font font;
    private int numLines;
    protected int width;
    private int lineHeight;
    private int height;
    private Rectangle box;
    protected int letterWidth;
    private int maxChars;
    protected int boxPadding = Box.getLinePadding() * 4;
    private final ArrayList<IMenuItem> mMenuItems = new ArrayList<IMenuItem>();
    private final ArrayList<ffMenuItem> mMenuTextLines = new ArrayList<ffMenuItem>();
    protected final MenuScene menuScene;

    //Constants
    public static final int TOP_LEFT = 0;
    public static final int TOP_CENTER = 1;
    public static final int TOP_RIGHT = 2;
    public static final int CENTER_CENTER = 4;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_CENTER = 7;
    public static final int BOTTOM_RIGHT = 8;



    /**
     * Menu constructor
     *
     * @param widthPercent Percentage of the screen width to be occupied by the menu
     * @param numLines     Number of menu lines
     */
    public Menu(float widthPercent, int numLines, int position) {
        this(new Float(widthPercent * MechawarsActivity.getCameraWidth()).intValue(), numLines, MechawarsActivity.getBasicFont(), position, SceneManager.getBase());
    }

    /**
     * Construtor de menu com proposito de teste de texto
     *
     * @param widthPercent
     * @param numLines
     * @param base
     */
    public Menu(float widthPercent, int numLines, int position, BaseGameActivity base) {
        this(new Float(widthPercent * MechawarsActivity.getCameraWidth()).intValue(), numLines, MechawarsActivity.getBasicFont(), position, base);
    }

    /**
     * Menu Constructor
     *
     * @param width    Fixed size in pixels
     * @param numLines Number of menu lines
     * @param font     Loaded font to create the texts
     * @param base     Activity base
     */
    public Menu(int width, int numLines, Font font, int position, BaseGameActivity base) {
        this.base = base;
        this.font = font;
        this.numLines = numLines;
        this.width = width;

        this.height = this.getLineHeight() * numLines;
        this.box = Box.createEntity(0, 0, width, lineHeight * numLines + boxPadding * 2);
        this.letterWidth = new Float(new Text(0, 0, font, "QWER").getWidth() / 4).intValue();

        SceneManager.getBase().getEngine().getScene().attachChild(this.box);
        //base.getEngine().getScene().attachChild(this.box);
        this.menuScene = new MenuScene(base.getEngine().getCamera(), this);

        this.createDummyLines(numLines);

        menuScene.buildAnimations();

        menuScene.setBackgroundEnabled(false);

        this.setPosition(position);

    }

    protected void createDummyLines(int numLines) {
        String text = "";
        for (int k = 0; k < this.maxChars; k++) {
            text = text + k % 10;
        }

        for (int i = 0; i < numLines; i++) {
            this.attachLine(i, text);
        }
    }

    protected void attachLine(int i, String text) {
        this.attachLine(i, new ffMenuItem(i, this.font, text, this.getMaxChars()));
    }

    protected void attachLine(int i, ffMenuItem item) {
        float posY = this.box.getY() + this.boxPadding + (i * this.lineHeight);
        mMenuTextLines.add(i, item);
        item.setWidth(width - boxPadding);
        item.setHeight(getLineHeight());
        IMenuItem line = new ColorMenuItemDecorator(item, 1, 1, 1, 0.8f, 0.8f, 0.8f);
        line.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        mMenuItems.add(i, line);
        menuScene.addMenuItem(line);
        line.setPosition(this.box.getX() + this.boxPadding, posY);
    }

    protected int getMaxChars() {
        if(maxChars == 0) {
            this.maxChars = calculateMaxChars();
        }
        return this.maxChars;
    }

    protected int getLineHeight() {
        if(lineHeight == 0) {
            this.lineHeight = calculateLineHeight();
        }
        return this.lineHeight;
    }

    //(width - boxPadding * 2) / letterWidth;
    protected abstract int calculateMaxChars();

     //new Float(font.getLineHeight() * 1.2f).intValue();
    protected abstract int calculateLineHeight();

    /**
     * Attach the menu to the scene
     */
    public void attachToScene() {
        base.getEngine().getScene().setChildScene(this.menuScene, false, true, true);
    }

    public void setPosition(int position) {
        float centerX = MechawarsActivity.getCenterX() - this.box.getWidth() / 2;
        float centerY = MechawarsActivity.getCenterY() - this.box.getHeight() / 2;
        float menuZeroX = (base.getEngine().getCamera().getWidth() - width - boxPadding) / -2;
        float menuZeroY = (base.getEngine().getCamera().getHeight() - height - boxPadding * 2) / -2;
        //TODO: As outras posicoes
        switch (position) {
            case 0:
                box.setPosition(0, 0);
                menuScene.setPosition(menuZeroX, menuZeroY);
                break;
            case 4:
                box.setPosition(centerX, centerY);
                menuScene.setPosition(boxPadding / 2, 0);
                break;
            default:
                //Bottom-Center
                float posY = MechawarsActivity.getCameraHeight() - this.box.getHeight();
                box.setPosition(centerX, posY);
                menuScene.setPosition(boxPadding / 2, menuZeroY * -1);
                break;
        }
    }

    public ffMenuItem getMenuLine(int lineNum) {
        return mMenuTextLines.get(lineNum);
    }

    public ffMenuItem setMenuLineText(int lineNum, String text) {
        mMenuTextLines.get(lineNum).setText(text);
        return getMenuLine(lineNum);
    }

    public MenuScene getMenuScene() {
        return menuScene;
    }

    protected Font getFont() {
        return font;
    }

    /**
     * Override so pra mostrar que o menuFunciona
     *
     * @param pMenuScene
     * @param pMenuItem
     * @param pMenuItemLocalX
     * @param pMenuItemLocalY
     * @return
     */
    @Override
    public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {
        //Toast.makeText(base, pMenuItem.getID()+": "+this.getMenuLine(pMenuItem.getID()).getText(), Toast.LENGTH_LONG).show();
        return true;
    }
}
