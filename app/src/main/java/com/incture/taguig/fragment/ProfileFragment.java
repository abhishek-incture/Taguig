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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

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

    TextView friendsButton, pollsButton, surveysButton,tvVote1,tvVote2,tvVote3,tvVote4;
    LinearLayout surveysLinear, pollsLinear,l1,l2,l3,l4;
    RadioButton radioButtonA1,radioButtonA2,radioButtonA3,radioButtonA4;


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

        l1 = (LinearLayout) view.findViewById(R.id.l1);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l3 = (LinearLayout) view.findViewById(R.id.l3);
        l4 = (LinearLayout) view.findViewById(R.id.l4);
        tvVote1 = view.findViewById(R.id.tvVote1);
        tvVote2 = view.findViewById(R.id.tvVote2);
        tvVote3 = view.findViewById(R.id.tvVote3);
        tvVote4 = view.findViewById(R.id.tvVote4);
        radioButtonA1 = view.findViewById(R.id.radioButtonA1);
        radioButtonA2 = view.findViewById(R.id.radioButtonA2);
        radioButtonA3 = view.findViewById(R.id.radioButtonA3);
        radioButtonA4 = view.findViewById(R.id.radioButtonA4);

        radioButtonA1.setOnClickListener(this);
        radioButtonA2.setOnClickListener(this);
        radioButtonA3.setOnClickListener(this);
        radioButtonA4.setOnClickListener(this);








       /* for(int i=0; i<=20; i++){
            friendsListMap = new HashMap<>();
            friendsListMap.put("name","Jasmine K");
            friendsListMap.put("address","Manila, Phillipines");
            friendsListMap.put("profileimage","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSMtqzp8_5fpLQXzM6SMjMXzEAOiIaILgbkUQfBPOF61vUwn81D&usqp=CAU");
            arrayList.add(friendsListMap);
        }*/

       init();
       init();

        friendsListAdapter = new FriendsListAdapter(getActivity(), arrayList);
        friendsListRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        friendsListRecycler.setAdapter(friendsListAdapter);

        friendsButton.setOnClickListener(this);
        pollsButton.setOnClickListener(this);
        surveysButton.setOnClickListener(this);


        return view;
    }


    public void init(){

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Rosa");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec8));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Althea");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec4));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Andrea");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec2));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Mary");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec6));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Jessa");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec7));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Nicole");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec9));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Angel");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec5));
        arrayList.add(friendsListMap);


        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Prince");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec1));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name","Samantha");
        friendsListMap.put("address","Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec4));
        arrayList.add(friendsListMap);




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


            case R.id.radioButtonA1:
                    onRadioButtonClicked(radioButtonA1);
                    break;
            case R.id.radioButtonA2:
                onRadioButtonClicked(radioButtonA2);
                break;

            case R.id.radioButtonA3:
                onRadioButtonClicked(radioButtonA3);
                break;
            case R.id.radioButtonA4:
                onRadioButtonClicked(radioButtonA4);
                break;
        }
    }



    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButtonA1:
                if (checked)
                   // Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                    l1.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                    tvVote1.setVisibility(View.VISIBLE);

                    l2.setBackgroundResource(0);
                    tvVote2.setVisibility(View.GONE);
                    radioButtonA2.setChecked(false);
                    l3.setBackgroundResource(0);
                    tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l4.setBackgroundResource(0);
                    tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Pirates are the best
                    break;
            case R.id.radioButtonA2:
                if (checked)

                    l2.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote2.setVisibility(View.VISIBLE);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                l3.setBackgroundResource(0);
                tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l4.setBackgroundResource(0);
                tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Ninjas rule
                    break;

            case R.id.radioButtonA3:
                if (checked)
                   // Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                l3.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote3.setVisibility(View.VISIBLE);

                l2.setBackgroundResource(0);
                tvVote2.setVisibility(View.GONE);
                radioButtonA2.setChecked(false);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                l4.setBackgroundResource(0);
                tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Pirates are the best
                break;
            case R.id.radioButtonA4:
                if (checked)

                    l4.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote4.setVisibility(View.VISIBLE);

                l2.setBackgroundResource(0);
                tvVote2.setVisibility(View.GONE);
                radioButtonA2.setChecked(false);

                l3.setBackgroundResource(0);
                tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                // Ninjas rule
                break;
        }
    }


}
