package com.shoppingbag.model.domesticflight.seatAdapter.seatConfirm;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveSeatMap {

    @SerializedName("BookingSegments")
    private List<BookingSegmentsItem> bookingSegments;

    public List<BookingSegmentsItem> getBookingSegments() {
        return bookingSegments;
    }

    public void setBookingSegments(List<BookingSegmentsItem> bookingSegments) {
        this.bookingSegments = bookingSegments;
    }

    @Override
    public String toString() {
        return
                "SaveSeatMap{" +
                        "bookingSegments = '" + bookingSegments + '\'' +
                        "}";
    }
}