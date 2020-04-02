package com.incture.taguig.models;

public class HospitalModel {

    private int hospitalImage;
    private String hospitalName;
    private String hospitalDetail;

    public HospitalModel(int hospitalImage, String hospitalName, String hospitalDetail) {
        this.hospitalImage = hospitalImage;
        this.hospitalName = hospitalName;
        this.hospitalDetail = hospitalDetail;
    }

    public int getHospitalImage() {
        return hospitalImage;
    }

    public void setHospitalImage(int hospitalImage) {
        this.hospitalImage = hospitalImage;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalDetail() {
        return hospitalDetail;
    }

    public void setHospitalDetail(String hospitalDetail) {
        this.hospitalDetail = hospitalDetail;
    }
}
