package com.android.mechawars.inventory;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventory {
    protected ArrayList<InventoryItem> itemsList = new ArrayList<InventoryItem>();

    public InventoryItem getItem(int position) {
        return this.itemsList.get(position);
    }

}
