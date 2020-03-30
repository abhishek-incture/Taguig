package com.incture.taguig.models;

import com.incture.taguig.R;

import java.util.ArrayList;
import java.util.List;

public class RequestList {
    private static RequestList requestList=null;
    public  List<RequestModel> requestModelList;

    private RequestList()
    {
        requestModelList = new ArrayList<>();
        init();

    }

    public static RequestList getInstance(){
        if(requestList == null)
        {
            requestList = new RequestList();
            return requestList;
        }
        else
            return requestList;

    }

    private void init() {
        RequestModel requestModel;
        requestModel = new RequestModel(R.drawable.medical_grey,"Medical Appointment Approved",
                "21 Feb 2017","Approved");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.education_grey,"Scholarshp  Appointment Pending",
                "21 Feb 2017","Pending");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.education_grey,"Medical Appointment Pending",
                "21 Feb 2017","Pending");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.medical_grey,"Medical Appointment Rejected",
                "21 Feb 2017","Rejected");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.medical_grey,"Medical Appointment Approved",
                "21 Feb 2017","Approved");
        requestModelList.add(requestModel);


    }
}
