package com.android.mechawars;

import com.android.mechawars.ffBox.ffDialog.DialogManager;
import com.android.mechawars.ffBox.ffMenu.Menu;
import com.android.mechawars.ffBox.ffMenu.MenuManager;
import com.android.mechawars.inventory.InventoryItem;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

/**
 * @author Rodrigo Dlugokenski
 */
public class SceneManager {
    protected static BaseGameActivity base;

    public SceneManager(BaseGameActivity base) {
		SceneManager.base = base;
	}
    
    public static BaseGameActivity getBase() {
        return base;
    }

    /**
     * Menu principal
     */
    public static void loadMain() {
		final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0f, 0f, 0f));

        final Text textCenter = new Text(MechawarsActivity.getCenterX(), 10, MechawarsActivity.titleFont, "MECHAWARS", HorizontalAlign.LEFT);
        textCenter.setPosition(MechawarsActivity.getCenterX()-textCenter.getWidth()/2,10);
        scene.attachChild(textCenter);

        base.getEngine().setScene(scene);

        MenuManager.instance().fromJSON("mainMenu").attachToScene();
        InventoryItem test = new InventoryItem("testWeapon");
                test.activate();

	}

    public static void loadOptions() {
        final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0f, 0.31f, 0.53f));
        
        final Text sceneTitle = new Text(MechawarsActivity.getCenterX(),4,MechawarsActivity.mFont,"Opções do Jogo",HorizontalAlign.CENTER);
        sceneTitle.setPosition(MechawarsActivity.getCenterX()-sceneTitle.getWidth()/2,10);
        scene.attachChild(sceneTitle);

        base.getEngine().setScene(scene);

        MenuManager.instance().fromJSON("optionsMenu").attachToScene();
    }

    public static void loadCredits() {
        final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0f, 0f, 0f));
        final Text sceneTitle = new Text(MechawarsActivity.getCenterX(),4,MechawarsActivity.mFont,"Teste dialogo 1",HorizontalAlign.CENTER);
        sceneTitle.setPosition(MechawarsActivity.getCenterX()-sceneTitle.getWidth()/2,10);
        scene.attachChild(sceneTitle);

        base.getEngine().setScene(scene);
        MenuManager.instance().fromJSON("optionsMenu").attachToScene();

        DialogManager.instance().fromJSON("credits").attachToScene();
    }
}
