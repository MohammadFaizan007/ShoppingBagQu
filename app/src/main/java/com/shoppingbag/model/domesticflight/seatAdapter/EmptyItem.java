package com.shoppingbag.model.domesticflight.seatAdapter;

public class EmptyItem extends AbstractItem {

    public EmptyItem(String label) {
        super(label);
    }

    public EmptyItem(String label, String birthType) {
        super(label, birthType);
    }


    @Override
    public int getType() {
        return TYPE_EMPTY;
    }

}
