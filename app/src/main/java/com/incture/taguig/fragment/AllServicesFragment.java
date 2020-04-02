package com.incture.taguig.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.incture.taguig.MedicalServicesActivity;
import com.incture.taguig.R;


public class AllServicesFragment extends Fragment implements View.OnClickListener {

    ImageView backArrow;
    LinearLayout l1Medical;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_services, container, false);

        backArrow = (ImageView)view.findViewById(R.id.backArrow);

        l1Medical = view.findViewById(R.id.l1Medical);
        l1Medical.setOnClickListener(this);
        backArrow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backArrow:
                getActivity().onBackPressed();
                break;
            case R.id.l1Medical:
                Intent intent = new Intent(getActivity(), MedicalServicesActivity.class);
                startActivity(intent);
                break;

        }
    }
}
