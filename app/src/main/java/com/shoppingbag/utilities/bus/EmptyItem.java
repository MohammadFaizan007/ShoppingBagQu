package com.shoppingbag.utilities.bus;

public class EmptyItem extends BusAbstractItem {


    public EmptyItem(String label) {
        super(label, "", "", "", "");
    }


    @Override
    public int getType() {
        return TYPE_EMPTY;
    }

}
