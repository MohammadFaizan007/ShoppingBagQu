package com.shoppingbag.utilities.bus;

public class Bookedtem extends BusAbstractItem {


    public Bookedtem(String label, String birthType, String seatNo, String seatTypeId, String fare) {
        super(label, birthType, seatNo, seatTypeId, fare);
    }


    @Override
    public int getType() {
        return TYPE_BOOKED;
    }

}
