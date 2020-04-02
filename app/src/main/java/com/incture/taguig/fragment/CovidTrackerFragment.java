package com.incture.taguig.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.BroadcastAdapter;
import com.incture.taguig.adapter.NewsAdapter;
import com.incture.taguig.models.RequestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CovidTrackerFragment extends Fragment implements View.OnClickListener {

    ImageView backArrow;
    TextView trackerButton, newsButton;
    LinearLayout trackerLinearLayout, newsLinearLayout;
    RecyclerView newsRecyclerView;
    Map<String, String> newsMap;
    ArrayList<Map> newsArrayList = new ArrayList<>();
    NewsAdapter newsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_covid_tracker, container, false);

        backArrow = (ImageView)view.findViewById(R.id.backArrow);
        trackerButton = (TextView)view.findViewById(R.id.trackerButton);
        newsButton = (TextView)view.findViewById(R.id.newsButton);
        trackerLinearLayout = (LinearLayout)view.findViewById(R.id.trackerLinearLayout);
        newsLinearLayout = (LinearLayout)view.findViewById(R.id.newsLinearLayout);
        newsRecyclerView = (RecyclerView)view.findViewById(R.id.newsRecyclerView);


        Map<String, String> newsMap1 = new HashMap<>();
        newsMap1.put("title", "More tests needed as Philippines unlikely to reach COVID-19 peak soon – expert");
        newsMap1.put("description", "The Philippines has yet to reach its peak in COVID-19 infections, a health expert said Wednesday as the public continues to clamor for mass testing for the highly-contagious disease.");
        newsMap1.put("areaname", "Phillipines");
        newsMap1.put("date", "2hr Ago");
        newsArrayList.add(newsMap1);

        Map<String, String> newsMap2 = new HashMap<>();
        newsMap2.put("title", "Philippines' COVID-19 death toll nears 100, recoveries at 50");
        newsMap2.put("description", "Eight more Filipinos have died from the coronavirus disease in the country, raising the nationwide death toll to 96, the Department of Health reported Wednesday. Meanwhile, a 59-year-old man from Taguig City is the 50th patient to recover from the viral disease. The country now has has 2,311 cases of COVID-19 as the Department of Health also announced 227 new patients.");
        newsMap2.put("areaname", "Global");
        newsMap2.put("date", "4hr Ago");
        newsArrayList.add(newsMap2);

        Map<String, String> newsMap3 = new HashMap<>();
        newsMap3.put("title", "Philippines’ Covid-19 cases soar");
        newsMap3.put("description", "The country recorded its largest daily increase in coronavirus deaths and infections, as it ramped up testing with the arrival of thousands of kits from abroad and the opening of new laboratories.Ten more deaths took the toll to 88 yesterday, while the tally of infections rose to 2,084, with 538 additional cases, said health ministry official Maria Rosario Vergeire.");
        newsMap3.put("areaname", "Phillipines");
        newsMap3.put("date", "8hr Ago");
        newsArrayList.add(newsMap3);

        Map<String, String> newsMap4 = new HashMap<>();
        newsMap4.put("title", "DOH announces 227 new COVID-19 cases; total at 2,311");
        newsMap4.put("description", "The additional recovered patient is PH179, a 59-year-old Filipino male from Taguig City with no travel history. He experienced symptoms on March 11, tested positive for COVID-19 on March 16, and was discharged on March 29 after receiving 2 negative test results. Meanwhile, all 8 new fatalities are senior citizens. The youngest was 65 years old, and the oldest was 84 years old.");
        newsMap4.put("areaname", "Global");
        newsMap4.put("date", "1day Ago");
        newsArrayList.add(newsMap4);

        Map<String, String> newsMap5 = new HashMap<>();
        newsMap5.put("title", "Last 80 Filipino crew from virus-hit Japan cruise ship now back in PH");
        newsMap5.put("description", "MANILA, Philippines – All 80 Filipinos infected with the novel coronavirus aboard the MV Diamond Princess cruise ship are now back in the Philippines. Philippine Deputy Chief of Mission to Japan Robespierre Bolivar confirmed this in a message to Rappler on Wednesday, April 1, saying all Filipinos were successfully treated and discharged from hospitals in Tokyo.");
        newsMap5.put("areaname", "Phillipines");
        newsMap5.put("date", "2day Ago");
        newsArrayList.add(newsMap5);



        newsAdapter = new NewsAdapter(getActivity(), newsArrayList);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        newsRecyclerView.setHasFixedSize(true);
        newsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        newsRecyclerView.setAdapter(newsAdapter);

        backArrow.setOnClickListener(this);
        trackerButton.setOnClickListener(this);
        newsButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.backArrow:
                getActivity().onBackPressed();
                break;

            case R.id.trackerButton:
                trackerButton.setBackgroundResource(R.drawable.bg_buttongrey);
                trackerButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                trackerButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                newsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.defaultscreen));
                newsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                newsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);

                trackerLinearLayout.setVisibility(View.VISIBLE);
                newsLinearLayout.setVisibility(View.GONE);
                break;

            case R.id.newsButton:
                newsButton.setBackgroundResource(R.drawable.bg_buttongrey);
                newsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                newsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

                trackerButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                trackerButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
                trackerButton.setBackgroundColor(getActivity().getResources().getColor(R.color.defaultscreen));

                newsLinearLayout.setVisibility(View.VISIBLE);
                trackerLinearLayout.setVisibility(View.GONE);

                break;
        }
    }


}
