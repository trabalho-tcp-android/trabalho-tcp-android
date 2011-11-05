package com.android.mechawars;

import com.android.mechawars.ffBox.Box;
import com.android.mechawars.ffBox.SimpleTextBox;
import com.android.mechawars.ffBox.TextBox;
import org.anddev.andengine.entity.primitive.Rectangle;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.util.HorizontalAlign;

public class SceneManager {
    public MechawarsActivity base;

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

        SimpleTextBox menu = new SimpleTextBox(0.5f, 3, base.getEngine().getScene());

        menu.setPosition(SimpleTextBox.CENTER_CENTER);
        scene.attachChild(menu.getDrawableEntity());
	}
}
