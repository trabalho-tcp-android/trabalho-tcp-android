package com.android.mechawars.ffBox.ffMenu;

import com.android.mechawars.MechawarsActivity;
import org.anddev.andengine.opengl.font.Font;

public class SimpleMenu extends Menu {


    /**
     * SimpleMenu constructor
     *
     * @param widthPercent Percentage of the screen width to be occupied by the menu
     * @param numLines     Number of menu lines
     * @param position     Posicao de acordo com a tabela 3x3 de 0 a 8
     */
    public SimpleMenu(float widthPercent, int numLines, int position) {
        super(widthPercent, numLines, position);
    }

    /**
     * Construtor de menu com proposito de teste de texto
     *
     * @param widthPercent
     * @param numLines
     * @param base
     */
    public SimpleMenu(float widthPercent, int numLines, int position, MechawarsActivity base) {
        super(widthPercent, numLines, position, base);
    }

    /**
     * Menu Constructor
     *
     * @param width    Fixed size in pixels
     * @param numLines Number of menu lines
     * @param font     Loaded font to create the texts
     * @param base     Activity base
     */
    public SimpleMenu(int width, int numLines, Font font, int position, MechawarsActivity base) {
        super(width, numLines, font, position, base);
    }

    @Override
    protected int calculateMaxChars() {
        return (width - boxPadding * 2) / letterWidth;
    }

    @Override
    protected int calculateLineHeight() {
        return new Float(getFont().getLineHeight() * 1.2f).intValue();
    }
}
