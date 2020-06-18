package com.shoppingbag.utilities.domesticflight.seatAdapter;

public class BlockedItem extends AbstractItem {

    public BlockedItem(String label) {
        super(label);
    }

    public BlockedItem(String label, String birthType) {
        super(label, birthType);
    }


    @Override
    public int getType() {
        return TYPE_BLOCK;
    }

}
