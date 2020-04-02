package com.incture.taguig.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.RequestAdapter;
import com.incture.taguig.models.RequestList;
import com.incture.taguig.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class RequestsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RequestAdapter adapter;
    private List<RequestModel> requestModelList;
    private TextView tvPending,tvApproved,tvAll;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requests, container, false);
        tvApproved = view.findViewById(R.id.tvApproved);
        tvPending = view.findViewById(R.id.tvPending);
        tvAll = view.findViewById(R.id.tvAll);

        tvApproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvApproved.setBackgroundResource(R.drawable.bg_buttongrey);
                tvApproved.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                tvApproved.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
                tvAll.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvAll.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvAll.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);


                tvPending.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvPending.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvPending.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);


                updateList("Approved");
            }
        });
        tvPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPending.setBackgroundResource(R.drawable.bg_buttongrey);
                tvPending.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                tvPending.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                tvAll.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvAll.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                tvAll.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvApproved.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvApproved.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvApproved.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);


                updateList("Pending");
            }
        });
        tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvAll.setBackgroundResource(R.drawable.bg_buttongrey);
                tvAll.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                tvAll.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                tvApproved.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvApproved.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                tvApproved.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvPending.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                tvPending.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                tvPending.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                updateList("");
            }
        });


        return view;
    }

    public void updateList(String status){
        requestModelList = init();
        List<RequestModel> newList;


         newList = new ArrayList<>();
        for (RequestModel model : requestModelList) {
            if (model.getStatus().contains(status)) {
                newList.add(model);
            }
        }
        adapter.updateList(newList);


    }

    private List init() {
        requestModelList.clear();
        RequestModel requestModel;
        requestModel = new RequestModel(R.drawable.medical_icon,"Medical Appointment Approved",
                "21 Feb 2017","Approved");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.education_grey,"Scholarshp  Appointment Pending",
                "21 Feb 2017","Pending");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.education_grey,"Medical Appointment Pending",
                "21 Feb 2017","Pending");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.medical_icon,"Medical Appointment Rejected",
                "21 Feb 2017","Rejected");
        requestModelList.add(requestModel);
        requestModel = new RequestModel(R.drawable.medical_icon,"Medical Appointment Approved",
                "21 Feb 2017","Approved");
        requestModelList.add(requestModel);

         return requestModelList;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        requestModelList = RequestList.getInstance().requestModelList;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new RequestAdapter(getContext(),requestModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        tvAll.performClick();


    }


}
