package com.incture.taguig.models;

public class RequestModel {

    private int image;
    private String requestSubject,requestDate,status;

    public RequestModel(int image, String requestSubject, String requestDate, String status) {
        this.image = image;
        this.requestSubject = requestSubject;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getRequestSubject() {
        return requestSubject;
    }

    public void setRequestSubject(String requestSubject) {
        this.requestSubject = requestSubject;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
