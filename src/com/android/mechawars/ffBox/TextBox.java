package com.android.mechawars.ffBox;

import android.util.Log;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.ffBox.ffFont.FontManager;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
/**
 * @author Rodrigo Dlugokenski
 */
public class TextBox {
    //Constants
    public static final int TOP_LEFT = 0;
    public static final int TOP_CENTER = 1;
    public static final int TOP_RIGHT = 2;
    public static final int CENTER_CENTER = 4;
    public static final int BOTTOM_LEFT = 6;
    public static final int BOTTOM_CENTER = 7;
    public static final int BOTTOM_RIGHT = 8;

    protected Rectangle box;
    protected Font font;
    protected int lineHeight;
    protected int maxChars;
    protected int boxPadding = Box.linePadding*4;
    private int numLines;
    private int width;
    private ChangeableText[] lines;
    private int letterWidth;
    protected Scene scene;

    public TextBox(int width,int numLines,Font font,Scene scene,boolean visible) {
        this.scene = scene;
        this.font = font;
        this.numLines = numLines;
        this.lines = new ChangeableText[numLines];
        this.width = width;
        this.lineHeight = new Float(font.getLineHeight()*1.2f).intValue();
        this.box = Box.createEntity(0,0,width,lineHeight*numLines+boxPadding*2);
        this.letterWidth = new Float(new Text(0,0,font,"QWER").getWidth()/4).intValue();
        this.maxChars = (width-boxPadding*2)/letterWidth;
        Log.w("MechaWars","MaxChars from textBox "+this.maxChars);

        scene.attachChild(this.box);
        this.box.setVisible(visible);
        for(int i=0;i<numLines;i++) {
            float posY = this.box.getY()+this.boxPadding+(i*this.lineHeight);
            ChangeableText line = new ChangeableText(this.box.getX()+this.boxPadding,posY,this.font,"1234567890123456789012",this.maxChars);
            line.setPosition(this.box.getX()+this.boxPadding,posY);
            lines[i] = line;
            scene.attachChild(line);
            //line.setVisible(visible);
        }
        this.setPosition(BOTTOM_CENTER);
    }

    public TextBox(float widthPercent, int numLines, Scene scene, boolean visible) {
        this(new Float(widthPercent*(SceneManager.getBase().getEngine().getCamera().getWidth())).intValue(),numLines, FontManager.instance().getFont(),scene,visible);
    }

    public TextBox(int width,int numLines, Scene scene) {
        this(width, numLines, FontManager.instance().getFont(),scene,true);
    }

    public void setVisibility(boolean visibility) {
        this.box.setVisible(visibility);
        for(int i=0;i<numLines;i++) {
            float posY = this.box.getY()+this.boxPadding+(i*this.lineHeight);
            ChangeableText line = new ChangeableText(this.box.getX()+this.boxPadding,posY,this.font,"1234567890123456789012",this.maxChars);
            line.setPosition(this.box.getX()+this.boxPadding,posY);
            lines[i] = line;
            scene.attachChild(line);
            line.setVisible(visibility);
        }
    }

    public boolean isVisible() {
        return this.box.isVisible();
    }

    public int getLetterWidth() {
        return letterWidth;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public int getNumLines() {
        return numLines;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return this.box.getHeight();
    }

    public void setPosition(float pX, float pY) {
        this.box.setPosition(pX, pY);
    }

    public ChangeableText getLine(int line) {
        if(line>numLines) throw new ArrayStoreException("Tentando pegar uma linha que nao existe "+line);
        return this.lines[line];
    }

    public void setPosition(int position) {
        float centerX = SceneManager.getBase().getEngine().getCamera().getCenterX()-this.box.getWidth()/2;
        float centerY = SceneManager.getBase().getEngine().getCamera().getCenterY()-this.box.getHeight()/2;
        //TODO: As outras posicoes
        switch (position) {
            case 0:
                this.setPosition(0,0);
                break;
            case 4:
                this.setPosition(centerX,centerY);
                break;
            default:
                //Bottom-Center
                float posY = SceneManager.getBase().getEngine().getCamera().getHeight()-this.box.getHeight();
                this.setPosition(centerX,posY);
                break;
        }

        //Reseta o posicionamento das linhas
        for(int i=0;i<numLines;i++) {
            float posY = this.box.getY()+this.boxPadding+(i*this.lineHeight);
            ChangeableText line = this.lines[i];
            line.setPosition(this.box.getX()+this.boxPadding,posY);
        }
    }

    public Rectangle getDrawableEntity() {
        return this.box;
    }

    protected void setText(String text,int lineNum) {
        ChangeableText line = this.lines[lineNum];
        line.setText(text);
    }
}
