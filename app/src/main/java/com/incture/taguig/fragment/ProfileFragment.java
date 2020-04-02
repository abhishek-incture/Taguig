package com.incture.taguig.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incture.taguig.R;
import com.incture.taguig.adapter.BroadcastAdapter;
import com.incture.taguig.adapter.FriendsListAdapter;
import com.incture.taguig.utils.AutoFitGridLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ProfileFragment extends Fragment {

    RecyclerView friendsListRecycler;
    FriendsListAdapter friendsListAdapter;

    Map<String, String> friendsListMap;
    ArrayList<Map> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        friendsListRecycler = (RecyclerView) view.findViewById(R.id.friendsListRecycler);

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




        return view;
    }

}
