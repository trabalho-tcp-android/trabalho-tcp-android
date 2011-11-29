package com.android.mechawars;

import javax.microedition.khronos.opengles.GL10;


import com.android.mechawars.ffBox.ffFont.FontManager;
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

import java.io.IOException;

/**
 * @author Rodrigo Dlugokenski
 */
public class MechawarsActivity extends BaseGameActivity {

    private static final int CAMERA_WIDTH = 400;
    private static final int CAMERA_HEIGHT = 300;
    private static final String MOD_FILENAME = "2nd_pm.s3m";
    private Camera mCamera;
    private BitmapTextureAtlas mBitmap;
    private TextureRegion mRobotTextureRegion;
    private static SceneManager sceneManager;


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
        final Text textCenter = new Text(CAMERA_WIDTH, CAMERA_HEIGHT + 50, FontManager.instance().getFontBoldGray(), "RobotCreations!", HorizontalAlign.LEFT);
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

    public static SceneManager getSceneManager() {
        return sceneManager;
    }

}