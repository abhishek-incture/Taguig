package com.incture.taguig.models;

public class BookingModel {

    private String time;
    private String bookingStatus;
    private Boolean isSelected;


    public BookingModel(String time, String bookingStatus) {
        this.time = time;
        this.bookingStatus = bookingStatus;
        this.isSelected=false;
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

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }
}
