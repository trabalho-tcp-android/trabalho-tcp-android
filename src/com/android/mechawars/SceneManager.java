package com.android.mechawars;

import com.android.mechawars.ffBox.ffMenu.Menu;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.util.HorizontalAlign;

/**
 * @author Rodrigo Dlugokenski
 */
public class SceneManager {
    protected static MechawarsActivity base;

    public SceneManager(MechawarsActivity base) {
		SceneManager.base = base;
	}

    public static MechawarsActivity getBase() {
        return base;
    }

    public void loadMain() {
		//Nova cena, menu principal
		final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0f, 0f, 0f));

        final Text textCenter = new Text(MechawarsActivity.getCenterX(), 10, base.titleFont, "MECHAWARS", HorizontalAlign.LEFT);
        textCenter.setPosition(MechawarsActivity.getCenterX()-textCenter.getWidth()/2,10);
        scene.attachChild(textCenter);

        base.getEngine().setScene(scene);

        Menu menu = new Menu(0.5f,4,base);
        menu.setPosition(Menu.CENTER_CENTER);
        menu.attachToScene();
        menu.setMenuLineText(2,"> TROLOLO");
	}
}
