package com.android.mechawars.actionsRunner;

import org.anddev.andengine.util.Debug;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ActionsManager {

    private ArrayList<ArrayList<String>> actionsList = new ArrayList<ArrayList<String>>();

    public ActionsManager(ArrayList<ArrayList<String>> actionsList) {
        this.actionsList = actionsList;
    }

    public int runAction(String name, ArrayList<String> parameters) {
        JSONArray nextLineToBeExecuted;
        try {
            nextLineToBeExecuted = new JSONArray(parameters.get(parameters.size() - 1));
            parameters.remove(parameters.size() - 1);
        } catch (JSONException e) {
            nextLineToBeExecuted = new JSONArray().put(1).put(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            nextLineToBeExecuted = new JSONArray().put(1).put(1);
        }

        try {
            Method action = ActionsList.class.getMethod(name, parameters.getClass());
            Boolean actionResponse = new Boolean(false);
            action.invoke(actionResponse, parameters);
            if (actionResponse) {
                return nextLineToBeExecuted.optInt(0, 1);
            } else {
                return nextLineToBeExecuted.optInt(1, 1);
            }
        } catch (SecurityException e) {
            Debug.e("SecurityException getting method: " + name, e);
        } catch (NoSuchMethodException e) {
            Debug.e("Method not found: " + name, e);
        } catch (IllegalArgumentException e) {
            Debug.e("Invalid argument calling: " + name, e);
        } catch (IllegalAccessException e) {
            Debug.e("Invalid access on method: " + name, e);
        } catch (InvocationTargetException e) {
            Debug.e("Exception on method: " + name, e);
        }
        return 1;
    }

    public ActionsManager runList() {
        int i = 0;
        do {
            String actionName = actionsList.get(i).get(0);
            actionsList.get(i).remove(0);
            i = i + runAction(actionName, actionsList.get(i));
        } while (i < actionsList.size());
        return this;
    }
}
