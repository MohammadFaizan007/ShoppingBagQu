package com.shoppingbag.utilities.bus;

public abstract class BusAbstractItem {

    public static final int TYPE_BOOKED = 0;
    public static final int TYPE_AVAILABLE = 1;
    public static final int TYPE_EMPTY = 2;
    public static final int TYPE_BLOCK = 0;

    private String label;
    private String birthType;

    private String seatNo;
    private String seatTypeId;
    private String fare;


    public BusAbstractItem(String label, String birthType, String seatNo, String seatTypeId, String fare) {
        this.label = label;
        this.birthType = birthType;
        this.seatNo = seatNo;
        this.seatTypeId = seatTypeId;
        this.fare = fare;
    }


    public String getLabel() {
        return label;
    }

    abstract public int getType();

    public String getBirthType() {
        return birthType;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public String getSeatTypeId() {
        return seatTypeId;
    }

    public String getFare() {
        return fare;
    }
}
