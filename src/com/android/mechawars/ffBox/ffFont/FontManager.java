package com.android.mechawars.ffBox.ffFont;

import android.graphics.Color;
import android.graphics.Typeface;
import com.android.mechawars.SceneManager;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;

public class FontManager {
    protected static Texture mFontTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR);;
    static Texture mTitleFontTexture = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
    static Texture mFontMonoTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR);
    protected static Font mFont = new Font(mFontMonoTexture, Typeface.createFromAsset(SceneManager.getBase().getAssets(), "fonts/UbuntuMono-B.ttf"), 16, true, Color.WHITE);
    protected static Font titleFont = new Font(mTitleFontTexture, Typeface.createFromAsset(SceneManager.getBase().getAssets(), "fonts/FFFAccess.ttf"), 32, true, Color.WHITE);
    protected static Font mFontBold = new Font(mFontTexture, Typeface.createFromAsset(SceneManager.getBase().getAssets(), "fonts/Ubuntu-B.ttf"), 16, true, Color.BLACK);
    protected static Font mFontBoldGray = new Font(mFontTexture, Typeface.createFromAsset(SceneManager.getBase().getAssets(), "fonts/Ubuntu-B.ttf"), 32, true, Color.DKGRAY);

    public static FontManager instance() {
        SceneManager.getBase().getEngine().getTextureManager().loadTexture(mFontTexture);
        SceneManager.getBase().getEngine().getTextureManager().loadTexture(mFontMonoTexture);
        SceneManager.getBase().getEngine().getTextureManager().loadTexture(mTitleFontTexture);
        SceneManager.getBase().getEngine().getFontManager().loadFont(mFont);
        SceneManager.getBase().getEngine().getFontManager().loadFont(titleFont);
        SceneManager.getBase().getEngine().getFontManager().loadFont(mFontBold);
        SceneManager.getBase().getEngine().getFontManager().loadFont(mFontBoldGray);
        return new FontManager();
    }

    public Font getFont() {
        return mFont;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public Font getFontBold() {
        return mFontBold;
    }

    public Font getFontBoldGray() {
        return mFontBoldGray;
    }
}
