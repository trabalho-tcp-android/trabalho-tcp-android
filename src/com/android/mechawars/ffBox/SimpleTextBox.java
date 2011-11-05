package com.android.mechawars.ffBox;

import org.anddev.andengine.entity.scene.Scene;

public class SimpleTextBox extends TextBox {
    public SimpleTextBox(float widthPercent, int numLines, Scene scene) {
        super(widthPercent, numLines,scene);
    }

    public SimpleTextBox(int width, int numLines, Scene scene) {
        super(width, numLines, scene);
    }

    @Override
    public void setText(String text, int lineNum) {
        super.setText(text, lineNum);
    }
}
