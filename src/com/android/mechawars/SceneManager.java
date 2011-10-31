package com.android.mechawars;

import com.android.mechawars.ffBox.Box;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.util.HorizontalAlign;

public class SceneManager {
    private MechawarsActivity base;

	public SceneManager(MechawarsActivity base) {
		this.base = base;
	}

	public void loadMain() {
		//Nova cena, menu principal
		final Scene scene = new Scene();
        scene.setBackground(new ColorBackground(0f, 0f, 0f));

        final Text textCenter = new Text(MechawarsActivity.getCenterX(), 10, base.titleFont, "MECHAWARS", HorizontalAlign.LEFT);
        textCenter.setPosition(MechawarsActivity.getCenterX()-textCenter.getWidth()/2,10);
        scene.attachChild(textCenter);
        base.getEngine().setScene(scene);

        Rectangle menu = Box.createEntity(0, 0, 700, 200);
        menu.setPosition(MechawarsActivity.getCenterX()-menu.getWidth()/2,MechawarsActivity.getCenterY()-menu.getHeight()/2);
        scene.attachChild(menu);
	}
}
