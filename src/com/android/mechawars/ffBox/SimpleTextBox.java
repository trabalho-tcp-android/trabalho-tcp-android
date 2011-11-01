package com.android.mechawars.ffBox;

public class SimpleTextBox extends TextBox {
    public SimpleTextBox(float widthPercent, int numLines) {
        super(widthPercent, numLines);
    }

    public SimpleTextBox(int width, int numLines) {
        super(width, numLines);
    }

    @Override
    public void setText(String text, int lineNum) {
        super.setText(text, lineNum);
    }
}
