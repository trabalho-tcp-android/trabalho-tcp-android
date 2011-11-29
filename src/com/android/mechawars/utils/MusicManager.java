package com.android.mechawars.utils;
/**
 * @author Rodrigo Dlugokenski
 */
import android.util.Log;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.FileUtils;
import org.helllabs.android.xmp.ModPlayer;

import java.io.IOException;

public class MusicManager {
    private ModPlayer mModPlayer = ModPlayer.getInstance();
    private static MusicManager instance = new MusicManager();
    private static final String MOD_DIRECTORY = "mfx/";
    private static BaseGameActivity activity;

    private MusicManager() {}

    public static MusicManager instance(BaseGameActivity activ) {
        activity = activ;
        return instance;
    }

    public MusicManager play(String musicName) {
        if (FileUtils.isFileExistingOnExternalStorage(activity, MOD_DIRECTORY + musicName)) {
            this.startPlayingMod(musicName);
        } else {
            FileUtils.ensureDirectoriesExistOnExternalStorage(activity, MOD_DIRECTORY);
            try {
                FileUtils.copyToExternalStorage(activity, MOD_DIRECTORY + musicName, MOD_DIRECTORY + musicName);
            } catch (IOException musicException) {
                Log.e("MechaWars", "Music file not found " + musicException.getMessage(), musicException);
            }
            this.startPlayingMod(musicName);
        }

        return this;
    }

    public MusicManager stop() {
        mModPlayer.stop();
        return this;
    }

    private void startPlayingMod(String musicName) {
        mModPlayer.play(FileUtils.getAbsolutePathOnExternalStorage(activity, MOD_DIRECTORY + musicName));
    }

}
