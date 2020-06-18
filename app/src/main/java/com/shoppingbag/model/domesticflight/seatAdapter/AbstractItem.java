package com.shoppingbag.model.domesticflight.seatAdapter;

public abstract class AbstractItem {

    public static final int TYPE_BOOKED = 0;
    public static final int TYPE_AVAILABLE = 1;
    public static final int TYPE_EMPTY = 2;
    public static final int TYPE_BLOCK = 0;

    private String label;
    private String birthType;


    public AbstractItem(String label) {
        this.label = label;
    }

    public AbstractItem(String label, String birthType) {
        this.label = label;
        this.birthType = birthType;
    }


    public String getLabel() {
        return label;
    }

    abstract public int getType();


    public String getBirthType() {
        return birthType;
    }
}
