package com.android.mechawars.ffBox;

import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.primitive.Rectangle;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * Box - Cria uma textura em forma de 'caixa' para textos e outros (Interface com o usuario)
 * @author Rodrigo Dlugokenski
 */
public class Box {

    protected static float lineColorR = 1f;
    protected static float lineColorG = 1f;
    protected static float lineColorB = 1f;
    protected static float alpha = 0.7f;
    protected static int linePadding = 5;
    protected static int lineSize = 2;
    protected static Map<String,Rectangle> instances = new HashMap<String, Rectangle>();

    public static Rectangle createEntity(final float posX, final float posY, final float width, final float height) {
		Rectangle mBox = new Rectangle(posX, posY, width, height);
        mBox.setColor(0f, 0.31f, 0.53f, alpha);

        //Linha superior
        Line supLine = new Line(posX+linePadding, posY+linePadding, posX+width-(linePadding*2), posY+linePadding, lineSize);
        supLine.setColor(lineColorR,lineColorG,lineColorB,alpha);
        mBox.attachChild(supLine);
        supLine.setPosition(supLine.getX1()+linePadding,supLine.getY1()+linePadding,supLine.getX2(),supLine.getY1()+linePadding);

        //Linha inferior - relativa a linha superior
        Line infLine = new Line(posX+linePadding,posY+height-(linePadding*2),posX+width-(linePadding*2),posY+height-(linePadding*2),lineSize);
        infLine.setColor(lineColorR, lineColorG, lineColorB, alpha);
        mBox.attachChild(infLine);
        infLine.setPosition(supLine.getX1(),infLine.getY1(),supLine.getX2(),infLine.getY1());

        //Linha esquerda - comeca na superior esq (X1 e Y1) e termina na inferior esq (X1 e Y1)
		Line leftLine = new Line(posX+linePadding, posY+linePadding, posX+linePadding,posY+height-(linePadding*2), lineSize);
        leftLine.setColor(lineColorR, lineColorG, lineColorB, alpha);
        mBox.attachChild(leftLine);
        leftLine.setPosition(supLine.getX1(),supLine.getY1(),supLine.getX1(),infLine.getY1());

        //Linha direita
        Line rightLine = new Line(posX+width-(linePadding*2),posY+linePadding,posX+width-(linePadding*2),posY+height-(linePadding*2),lineSize);
        rightLine.setColor(lineColorR, lineColorG, lineColorB, alpha);
        mBox.attachChild(rightLine);
        rightLine.setPosition(supLine.getX2(),supLine.getY2(),supLine.getX2(),infLine.getY1());

        return mBox;
    }

    public static Rectangle createEntity(final float posX, final float posY, final float width, final float height,final String name) {
        if(instances.containsKey(name)) {
            instances.put(name,Box.createEntity(posX,posY,width,height));
        }
        return instances.get(name);
    }

    public static Rectangle getEntity(String name) {
        return instances.get(name);
    }

    public static int getLinePadding() {
        return linePadding;
    }


}
