package com.shoppingbag.utilities.bus;

public class BlockedItem extends BusAbstractItem {


    public BlockedItem(String label, String birthType, String seatNo, String seatTypeId, String fare) {
        super(label, birthType, seatNo, seatTypeId, fare);
    }


    @Override
    public int getType() {
        return TYPE_BLOCK;
    }

}
