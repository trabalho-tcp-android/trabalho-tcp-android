package com.android.mechawars.actionsRunner;

import android.content.Intent;
import android.widget.Toast;
import android.os.Bundle;
import android.app.Activity;

import com.android.mechawars.MechaWarsMapActivity;
import com.android.mechawars.MechawarsActivity;
import com.android.mechawars.SceneManager;
import com.android.mechawars.map.GameMapActivityManager;

import com.android.mechawars.utils.MusicManager;
import org.anddev.andengine.util.Debug;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ActionsList {

    /**
     * testAction: retorna em booleano o valor true ou false do primeiro parametro da lista
     *
     * @param parameters arrayList de parametros
     * @return boolean
     */
    public static boolean testAction(ArrayList<String> parameters) {
        Toast.makeText(SceneManager.getBase(), parameters.get(0), Toast.LENGTH_SHORT).show();
        Debug.i(parameters.get(0));
        return new Boolean(parameters.get(0));
    }

    /**
     * @param parameters arrayList de parametros
     * @return boolean
     */
    public static boolean changeScene(ArrayList<String> parameters) {
        try {
            Method scene = MechawarsActivity.getSceneManager().getClass().getMethod("load"+parameters.get(0));
            scene.invoke(null);
        } catch (SecurityException e) {
            Debug.e("SecurityException changing scene: " + parameters.get(0), e);
        } catch (NoSuchMethodException e) {
            Toast.makeText(SceneManager.getBase(),"Scene not found: "+parameters.get(0),Toast.LENGTH_LONG).show();
            Debug.e("Scene not found: " + parameters.get(0), e);
        } catch (IllegalArgumentException e) {
            Debug.e("Invalid argument calling Scene: " + parameters.get(0), e);
        } catch (IllegalAccessException e) {
            Debug.e("Invalid access trying to change Scene: " + parameters.get(0), e);
        } catch (InvocationTargetException e) {
            Debug.e("Exception on changeScene: " + parameters.get(0), e);
        }
        return true;
    }

    public static boolean quitGame(ArrayList<String> parameters) {
        Debug.i("Called quitGame");
        SceneManager.getBase().finish();
        return true;
    }


    public static boolean callMapActivity(ArrayList<String> parameters) {
        Debug.i("Trying to call map activity");
        MusicManager.instance(SceneManager.getBase()).stop();
        Intent mapActivity = new Intent(SceneManager.getBase(), MechaWarsMapActivity.class);
        SceneManager.getBase().startActivity(mapActivity);
        return true;
    }

    public static boolean inflictDamage(ArrayList<String> parameters) {
        Toast.makeText(SceneManager.getBase(), "Trying to inflict"+parameters.get(1)+" damage on someone: "+parameters.get(0), Toast.LENGTH_SHORT).show();
        return true;
    }


}
