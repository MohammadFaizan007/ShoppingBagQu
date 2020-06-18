package com.shoppingbag.utilities.domesticflight.seatAdapter;

public class Bookedtem extends AbstractItem {

    public Bookedtem(String label) {
        super(label);
    }


    public Bookedtem(String label, String birthType) {
        super(label, birthType);
    }


    @Override
    public int getType() {
        return TYPE_BOOKED;
    }

}
