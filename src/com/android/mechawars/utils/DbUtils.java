package com.android.mechawars.utils;
/**
 * @author Rodrigo Dlugokenski
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.android.mechawars.SceneManager;
import org.anddev.andengine.util.FileUtils;

import java.io.*;


public class DbUtils {
    private static final String DB_PATH = "databases/";
    private static final String DB_NAME = "mechawars.db";
    private static final SQLiteDatabase db = SQLiteDatabase.openDatabase(FileUtils.getAbsolutePathOnExternalStorage(SceneManager.getBase(),DB_PATH + DB_NAME), null, SQLiteDatabase.OPEN_READONLY);

    public static SQLiteDatabase getDatabase() {
        return db;
    }

    public static String getDbPath() {
        return DB_PATH;
    }

    public static String getDbName() {
        return DB_NAME;
    }
}