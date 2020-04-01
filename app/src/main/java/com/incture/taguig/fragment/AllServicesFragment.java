package com.incture.taguig.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.incture.taguig.R;


public class AllServicesFragment extends Fragment implements View.OnClickListener {

    ImageView backArrow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_services, container, false);

        backArrow = (ImageView)view.findViewById(R.id.backArrow);


        backArrow.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backArrow:
                getActivity().onBackPressed();
                break;
        }
    }
}
