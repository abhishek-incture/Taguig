package com.incture.taguig.models;

public class BookingModel {

    private String time;
    private String bookingStatus;

    public BookingModel(String time, String bookingStatus) {
        this.time = time;
        this.bookingStatus = bookingStatus;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
}
