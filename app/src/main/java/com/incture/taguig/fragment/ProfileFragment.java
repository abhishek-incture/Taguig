package com.incture.taguig.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.FriendsListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    RecyclerView friendsListRecycler;
    FriendsListAdapter friendsListAdapter;

    Map<String, String> friendsListMap;
    ArrayList<Map> arrayList = new ArrayList<>();

    TextView friendsButton, pollsButton, surveysButton;
    LinearLayout surveysLinear, pollsLinear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        friendsListRecycler = (RecyclerView) view.findViewById(R.id.friendsListRecycler);
        friendsButton = (TextView) view.findViewById(R.id.friendsButton);
        pollsButton = (TextView) view.findViewById(R.id.pollsButton);
        surveysButton = (TextView) view.findViewById(R.id.surveysButton);
        surveysLinear = (LinearLayout) view.findViewById(R.id.surveysLinear);
        pollsLinear = (LinearLayout) view.findViewById(R.id.pollsLinear);

        for(int i=0; i<=20; i++){
            friendsListMap = new HashMap<>();
            friendsListMap.put("name","Jasmine K");
            friendsListMap.put("address","Manila, Phillipines");
            friendsListMap.put("profileimage","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSMtqzp8_5fpLQXzM6SMjMXzEAOiIaILgbkUQfBPOF61vUwn81D&usqp=CAU");
            arrayList.add(friendsListMap);
        }

        friendsListAdapter = new FriendsListAdapter(getActivity(), arrayList);
        friendsListRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        friendsListRecycler.setAdapter(friendsListAdapter);

        friendsButton.setOnClickListener(this);
        pollsButton.setOnClickListener(this);
        surveysButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.friendsButton:
                friendsButton.setBackgroundResource(R.drawable.bg_buttongrey);
                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                pollsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));

                surveysButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                friendsListRecycler.setVisibility(View.VISIBLE);
                pollsLinear.setVisibility(View.GONE);
                surveysLinear.setVisibility(View.GONE);

                break;

            case R.id.pollsButton:
                pollsButton.setBackgroundResource(R.drawable.bg_buttongrey);
                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                friendsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));

                surveysButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                friendsListRecycler.setVisibility(View.GONE);
                pollsLinear.setVisibility(View.VISIBLE);
                surveysLinear.setVisibility(View.GONE);

                break;

            case R.id.surveysButton:
                surveysButton.setBackgroundResource(R.drawable.bg_buttongrey);
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                friendsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);


                pollsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                friendsListRecycler.setVisibility(View.GONE);
                pollsLinear.setVisibility(View.GONE);
                surveysLinear.setVisibility(View.VISIBLE);

                break;
        }
    }
}
