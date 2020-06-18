package com.shoppingbag.utilities.domesticflight.seatAdapter;

public class AvaliableItem extends AbstractItem {

    public AvaliableItem(String label) {
        super(label);
    }

    public AvaliableItem(String label, String birthType) {
        super(label, birthType);
    }

    @Override
    public int getType() {
        return TYPE_AVAILABLE;
    }

}
