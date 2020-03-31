package com.incture.taguig.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.BroadcastAdapter;
import com.incture.taguig.adapter.ImpUpdatesAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class HomeFragment extends Fragment implements View.OnClickListener {

    RecyclerView impUpdatesRecycler, broadcastAnnouncementRecycler;
    ImpUpdatesAdapter impUpdatesAdapter;
    BroadcastAdapter broadcastAdapter;
    Map<String, String> updateMap;
    Map<String, String> broadcastMap;
    ArrayList<Map> arrayList = new ArrayList<>();
    ArrayList<Map> broadcastArrayList = new ArrayList<>();

    TextView knowMore, viewAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        knowMore = (TextView)view.findViewById(R.id.knowMore);
        viewAll = (TextView)view.findViewById(R.id.viewAll);
        impUpdatesRecycler = (RecyclerView) view.findViewById(R.id.impUpdatesRecycler);
        broadcastAnnouncementRecycler = (RecyclerView) view.findViewById(R.id.broadcastAnnouncementRecycler);

        arrayList.clear();
        for(int i=0; i<3; i++){
            updateMap = new HashMap<>();
            updateMap.put("date", "Today");
            updateMap.put("msg", "Use the online image color picker right to select a color and get the HTML Color Code of this pixel. Also you get the HEX color code value, RGB value and HSV value.");
            arrayList.add(updateMap);
        }



        impUpdatesAdapter = new ImpUpdatesAdapter(getActivity(), arrayList);
        impUpdatesRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        impUpdatesRecycler.setHasFixedSize(true);
        impUpdatesRecycler.setItemAnimator(new DefaultItemAnimator());
        impUpdatesRecycler.setAdapter(impUpdatesAdapter);


        broadcastArrayList.clear();
        for(int i=0; i<4; i++){
            broadcastMap = new HashMap<>();
            broadcastMap.put("title", "Taguig Cultural and Historical Awareness Week");
            broadcastMap.put("description", "Use the online image color picker right to select a color and get the HTML Color Code of this pixel. Also you get the HEX color code value, RGB value and HSV value.");
            broadcastMap.put("date", "22 dec 2019");
            broadcastArrayList.add(broadcastMap);
        }


        broadcastAdapter = new BroadcastAdapter(getActivity(), broadcastArrayList);
        broadcastAnnouncementRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        broadcastAnnouncementRecycler.setHasFixedSize(true);
        broadcastAnnouncementRecycler.setItemAnimator(new DefaultItemAnimator());
        broadcastAnnouncementRecycler.setAdapter(broadcastAdapter);


        knowMore.setOnClickListener(this);
        viewAll.setOnClickListener(this);

        return  view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.knowMore:
                CovidTrackerFragment covidTrackerFragment = new CovidTrackerFragment();
                replaceFragment(covidTrackerFragment);
                break;

            case R.id.viewAll:
                break;
        }
    }


    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
