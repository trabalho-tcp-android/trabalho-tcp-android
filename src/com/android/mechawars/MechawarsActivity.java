package com.android.mechawars;

import javax.microedition.khronos.opengles.GL10;

import android.content.Intent;

import com.android.mechawars.map.GameMapActivityManager;
import com.android.mechawars.map.LoadAssets;
import com.android.mechawars.utils.DbUtils;
import com.android.mechawars.utils.MusicManager;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.Debug;
import org.anddev.andengine.util.FileUtils;
import org.anddev.andengine.util.HorizontalAlign;
import org.anddev.andengine.util.modifier.IModifier;
import org.anddev.andengine.util.modifier.ease.EaseLinear;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.IEntity;
import org.anddev.andengine.entity.modifier.FadeInModifier;
import org.anddev.andengine.entity.modifier.FadeOutModifier;
import org.anddev.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.util.FPSCounter;

import android.util.Log;
import android.graphics.Color;
import android.graphics.Typeface;
import org.helllabs.android.xmp.ModPlayer;

import java.io.IOException;

/**
 * @author Rodrigo Dlugokenski
 */
public class MechawarsActivity extends BaseGameActivity {

    private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private static final String MOD_FILENAME = "2nd_pm.s3m";
    private Camera mCamera;
    protected Texture mFontTexture;
    protected static Font mFont;
    protected static Font titleFont;
    protected Font mFontBold;
    private BitmapTextureAtlas mBitmap;
    private TextureRegion mRobotTextureRegion;
    protected Font mFontBoldGray;
    private static SceneManager sceneManager;
    private ModPlayer mModPlayer = ModPlayer.getInstance();
    public Intent mapActivity;


    @Override
    public Engine onLoadEngine() {
        Debug.setDebugTag("MechaWars");
        Debug.setDebugLevel(Debug.DebugLevel.WARNING);
        
        
        
        this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        
        this.mEngine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));
        
        return this.mEngine;
    }

    @Override
    public void onLoadResources() {
        this.mFontTexture = new BitmapTextureAtlas(1024, 1024, TextureOptions.BILINEAR);
        Texture mTitleFontTexture = new BitmapTextureAtlas(512, 512, TextureOptions.BILINEAR);
        Texture mFontMonoTexture = new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR);

        mFont = new Font(mFontMonoTexture, Typeface.createFromAsset(getAssets(), "fonts/UbuntuMono-B.ttf"), 32, true, Color.WHITE);
        this.titleFont = new Font(mTitleFontTexture, Typeface.createFromAsset(getAssets(), "fonts/FFFAccess.ttf"), 64, true, Color.WHITE);
        this.mFontBold = new Font(this.mFontTexture, Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf"), 32, true, Color.BLACK);
        this.mFontBoldGray = new Font(this.mFontTexture, Typeface.createFromAsset(getAssets(), "fonts/Ubuntu-B.ttf"), 64, true, Color.DKGRAY);

        this.getEngine().getTextureManager().loadTexture(this.mFontTexture);
        this.getEngine().getTextureManager().loadTexture(mFontMonoTexture);
        this.getEngine().getTextureManager().loadTexture(mTitleFontTexture);
        this.getEngine().getFontManager().loadFont(mFont);
        this.getEngine().getFontManager().loadFont(titleFont);
        this.getEngine().getFontManager().loadFont(this.mFontBold);
        this.getEngine().getFontManager().loadFont(this.mFontBoldGray);

        this.mBitmap = new BitmapTextureAtlas(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mRobotTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBitmap, this, "robotCreations.png", 0, 0);

        this.getEngine().getTextureManager().loadTexture(this.mBitmap);

        //Musica da abertura
        MusicManager.instance(this).play(MOD_FILENAME);
        sceneManager = new SceneManager(this);
    }

    @Override
    public Scene onLoadScene() {
        this.getEngine().registerUpdateHandler(new FPSCounter());
        final int centerX = (CAMERA_WIDTH - this.mRobotTextureRegion.getWidth()) / 2;
        final int centerY = (CAMERA_HEIGHT - this.mRobotTextureRegion.getHeight()) / 2;

        // Create the scene we'll be using for the loading screen
        final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(1f, 1f, 1f));

        // Cria o simbolo do robo e Texto
        final Sprite robotCreations = new Sprite(centerX, centerY - 100, this.mRobotTextureRegion);
        final Text textCenter = new Text(CAMERA_WIDTH, CAMERA_HEIGHT + 50, this.mFontBoldGray, "RobotCreations!", HorizontalAlign.LEFT);
        textCenter.setPosition((CAMERA_WIDTH / 2) - textCenter.getWidth() / 2f, centerY + 25);

        //Cria um timer para segurar a tela em branco e evitar um pulo de lag do aparelho
        this.getEngine().registerUpdateHandler(new TimerHandler(0.5f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                scene.attachChild(textCenter);
                scene.attachChild(robotCreations);
            }
        }));

        //Copia o banco de dados
        if (true || !FileUtils.isFileExistingOnExternalStorage(this,"databases/mechawars.db")) {
            FileUtils.ensureDirectoriesExistOnExternalStorage(this, "databases/");
            try {
                FileUtils.copyToExternalStorage(this, "databases/mechawars.db", "databases/mechawars.db");
            } catch (IOException dbEx) {
                Debug.e("Database copy failed: " + dbEx.getMessage(), dbEx);
            }
        }

        /* Modificador para o efeito de aparecer e desaparecer */
        final FadeInModifier prFadeInModifier = new FadeInModifier(2f, new IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

            }
        }, EaseLinear.getInstance());

        final FadeOutModifier prFadeOutModifier = new FadeOutModifier(1f, new IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {

            }
        }, EaseLinear.getInstance());

        final FadeOutModifier prFadeOutModifierChangeScene = new FadeOutModifier(1f, new IEntityModifierListener() {
            @Override
            public void onModifierStarted(IModifier<IEntity> pModifier, IEntity pItem) {
            }

            @Override
            public void onModifierFinished(IModifier<IEntity> pModifier, IEntity pItem) {
                //Troca de cena
                SceneManager.loadMain();
            }
        }, EaseLinear.getInstance());


        //Aplica o fadeIn
        robotCreations.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        textCenter.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        robotCreations.registerEntityModifier(prFadeInModifier);
        textCenter.registerEntityModifier(prFadeInModifier);

        //Cria um timer para o fadeOut
        this.getEngine().registerUpdateHandler(new TimerHandler(1.5f, new ITimerCallback() {
            @Override
            public void onTimePassed(final TimerHandler pTimerHandler) {
                robotCreations.registerEntityModifier(prFadeOutModifier);
                textCenter.registerEntityModifier(prFadeOutModifierChangeScene);

            }
        }));

        return scene;
    }

    @Override
    public void onLoadComplete() {

    }

    @Override
    protected void onDestroy() {
        MusicManager.instance(this).stop();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        MusicManager.instance(this).stop();
        super.onPause();
    }


    public static int getCameraWidth() {
        return CAMERA_WIDTH;
    }

    public static int getCenterX() {
        return CAMERA_WIDTH / 2;
    }

    public static int getCenterY() {
        return CAMERA_HEIGHT / 2;
    }

    public static int getCameraHeight() {
        return CAMERA_HEIGHT;
    }

    public static Font getBasicFont() {
        return mFont;
    }

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

}