package com.shoppingbag.model.response.bus_response.bookingHistory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookedHistoryOutput {

    @SerializedName("BookedTickets")
    private List<BookedTicketsItem> bookedTickets;

    public List<BookedTicketsItem> getBookedTickets() {
        return bookedTickets;
    }
}