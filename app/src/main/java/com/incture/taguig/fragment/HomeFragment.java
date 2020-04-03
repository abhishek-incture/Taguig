package com.incture.taguig.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incture.taguig.MedicalServicesActivity;
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

    LinearLayout viewAllServices,MedicalAid;
    TextView knowMore, viewAll;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        knowMore = (TextView)view.findViewById(R.id.knowMore);
        viewAll = (TextView)view.findViewById(R.id.viewAll);
        viewAllServices = (LinearLayout) view.findViewById(R.id.viewAllServices);
        MedicalAid = view.findViewById(R.id.MedicalAid);
        impUpdatesRecycler = (RecyclerView) view.findViewById(R.id.impUpdatesRecycler);
        broadcastAnnouncementRecycler = (RecyclerView) view.findViewById(R.id.broadcastAnnouncementRecycler);

        arrayList.clear();

            Map<String, String>  updateMap1 = new HashMap<>();
            updateMap1.put("date", "Today");
            updateMap1.put("msg", "The Philippines has yet to reach its peak in COVID-19 infections, a health expert said Wednesday as the public continues to clamor for mass testing for the highly-contagious disease.");
            arrayList.add(updateMap1);

            Map<String, String>  updateMap2 = new HashMap<>();
            updateMap2.put("date", "Today");
            updateMap2.put("msg", "The eight new fatalities are all elderly patients: a 65-year-old woman from Quezon City, a 79-year-old woman from Makati City, a 73-year-old man from Pasig City, a 66-year-old man from Parañaque City, a 77-year-old man from Manila, an 84-year-old man from Mandaluyong City, a 70-year-old man from Cebu City, and an 80-year-old man from San Juan City.");
            arrayList.add(updateMap2);

            Map<String, String>  updateMap3 = new HashMap<>();
            updateMap3.put("date", "Yesterday");
            updateMap3.put("msg", "The additional recovered patient is PH179, a 59-year-old Filipino male from Taguig City with no travel history. He experienced symptoms on March 11, tested positive for COVID-19 on March 16, and was discharged on March 29 after receiving 2 negative test results.");
            arrayList.add(updateMap3);




        impUpdatesAdapter = new ImpUpdatesAdapter(getActivity(), arrayList);
        impUpdatesRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        impUpdatesRecycler.setHasFixedSize(true);
        impUpdatesRecycler.setItemAnimator(new DefaultItemAnimator());
        impUpdatesRecycler.setAdapter(impUpdatesAdapter);


        broadcastArrayList.clear();

        Map<String, String> newsMap1 = new HashMap<>();
        newsMap1.put("title", "More tests needed as Philippines unlikely to reach COVID-19 peak soon – expert");
        newsMap1.put("description", "The Philippines has yet to reach its peak in COVID-19 infections, a health expert said Wednesday as the public continues to clamor for mass testing for the highly-contagious disease.");
        newsMap1.put("date", "30 March 2020");
        newsMap1.put("thumbnail", "https://img-s-msn-com.akamaized.net/tenant/amp/entityid/BB10vhvO.img?h=0&w=720&m=6&q=60&u=t&o=f&l=f&x=322&y=170");
        broadcastArrayList.add(newsMap1);

        Map<String, String> newsMap2 = new HashMap<>();
        newsMap2.put("title", "Philippines' COVID-19 death toll nears 100, recoveries at 50");
        newsMap2.put("description", "Eight more Filipinos have died from the coronavirus disease in the country, raising the nationwide death toll to 96, the Department of Health reported Wednesday. Meanwhile, a 59-year-old man from Taguig City is the 50th patient to recover from the viral disease. The country now has has 2,311 cases of COVID-19 as the Department of Health also announced 227 new patients.");
        newsMap2.put("date", "28 March 2020");
        newsMap2.put("thumbnail", "https://imagevars.gulfnews.com/2020/03/10/Coronavirus-Manila_170c4a83f13_medium.jpg");
        broadcastArrayList.add(newsMap2);

        Map<String, String> newsMap3 = new HashMap<>();
        newsMap3.put("title", "Philippines’ Covid-19 cases soar");
        newsMap3.put("description", "The country recorded its largest daily increase in coronavirus deaths and infections, as it ramped up testing with the arrival of thousands of kits from abroad and the opening of new laboratories.Ten more deaths took the toll to 88 yesterday, while the tally of infections rose to 2,084, with 538 additional cases, said health ministry official Maria Rosario Vergeire.");
        newsMap3.put("date", "26 March 2020");
        newsMap3.put("thumbnail", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS6wRJ4TDl0pHRiEeUJsQev90Y-ATPkibekgCzxBie2By6323O2&usqp=CAU");
        broadcastArrayList.add(newsMap3);

        Map<String, String> newsMap4 = new HashMap<>();
        newsMap4.put("title", "DOH announces 227 new COVID-19 cases; total at 2,311");
        newsMap4.put("description", "The additional recovered patient is PH179, a 59-year-old Filipino male from Taguig City with no travel history. He experienced symptoms on March 11, tested positive for COVID-19 on March 16, and was discharged on March 29 after receiving 2 negative test results. Meanwhile, all 8 new fatalities are senior citizens. The youngest was 65 years old, and the oldest was 84 years old.");
        newsMap4.put("areaname", "Global");
        newsMap4.put("date", "22 March 2020");
        newsMap4.put("thumbnail", "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSws_SZdRwFF-SXkijtFwB0Igp_6Ih2xKQu4WcJ-37AveSwNDfi&usqp=CAU");
        broadcastArrayList.add(newsMap4);



        broadcastAdapter = new BroadcastAdapter(getActivity(), broadcastArrayList);
        broadcastAnnouncementRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        broadcastAnnouncementRecycler.setHasFixedSize(true);
        broadcastAnnouncementRecycler.setItemAnimator(new DefaultItemAnimator());
        broadcastAnnouncementRecycler.setAdapter(broadcastAdapter);


        knowMore.setOnClickListener(this);
        viewAll.setOnClickListener(this);
        viewAllServices.setOnClickListener(this);
        MedicalAid.setOnClickListener(this);

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

            case R.id.viewAllServices:
                AllServicesFragment allServicesFragment = new AllServicesFragment();
                replaceFragment(allServicesFragment);
                break;
            case R.id.MedicalAid:
                Intent intent = new Intent(getActivity(), MedicalServicesActivity.class);
                startActivity(intent);
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
