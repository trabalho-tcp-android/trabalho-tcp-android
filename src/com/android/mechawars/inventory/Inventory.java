package com.android.mechawars.inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    protected ArrayList<InventoryItem> itemsList = new ArrayList<InventoryItem>();
    protected ArrayList<Integer> itemsQuantity = new ArrayList<Integer>();
    //Mapeia o nome do item versus a posicao no array (Like a Database index)
    protected HashMap<String,Integer> itemsPositions = new HashMap<String, Integer>();

    public InventoryItem getItem(int position) {
        return this.itemsList.get(position);
    }

    public InventoryItem getItem(String name) {
        return this.itemsList.get(itemsPositions.get(name));
    }
    
    public Inventory addItem(InventoryItem item, int quantity) {
        itemsList.add(item);
        itemsQuantity.add(quantity);
        itemsPositions.put(item.getName(),itemsList.size()-1);
        return this;
    }
    
    public Inventory subtractItem(String itemName, int quantity) {
        int itemPosition = itemsPositions.get(itemName);
        int itemQuantity = itemsQuantity.get(itemPosition);
        itemsQuantity.set(itemPosition,itemQuantity-1);
        
        if(itemQuantity<=0) {
            this.removeItem(itemName);
        }

        return this;
    }

    /**
     * Recalcula o indice de String vs Posicoes
     */
    private void recalculatePositions() {
        itemsPositions.clear();
        for(int i=0;i<itemsList.size();i++) {
           itemsPositions.put(itemsList.get(i).getName(),i);
        }
    }
    
    protected Inventory removeItem(String itemName) {
        int itemPosition = itemsPositions.get(itemName);
        itemsList.remove(itemPosition);
        itemsQuantity.remove(itemPosition);
        itemsPositions.remove(itemName);
        recalculatePositions();
        return this;
    }

    public Inventory useItem(int itemPosition) {
        this.useItem(itemsList.get(itemPosition).getName());
        return this;
    }
    
    public Inventory useItem(String itemName) {
        this.getItem(itemName).activate();
        return this;
    }

}
