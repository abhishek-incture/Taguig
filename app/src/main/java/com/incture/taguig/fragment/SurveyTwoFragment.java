package com.incture.taguig.fragment;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.Question2Adapter;
import com.incture.taguig.adapter.QuestionAdapter;
import com.incture.taguig.models.Question2Model;

import java.util.ArrayList;
import java.util.List;


public class SurveyTwoFragment extends Fragment implements View.OnClickListener {


    private RecyclerView recyclerView;
    private QuestionAdapter adapter;
    private List<String> questionModelList;
    private RecyclerView recyclerView2;
    private Question2Adapter adapter2;
    private List<Question2Model> question2ModelList;
    ImageView backArrow;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey_two, container, false);

        backArrow = (ImageView)view.findViewById(R.id.backArrow);

        recyclerView = view.findViewById(R.id.recyclerView);
        questionModelList= new ArrayList<>();
        init();

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        adapter = new QuestionAdapter(getActivity(),questionModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        question2ModelList= new ArrayList<>();
        init2();
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        adapter2 = new Question2Adapter(getActivity(),question2ModelList);
        recyclerView2.setLayoutManager(layoutManager2);
        recyclerView2.setAdapter(adapter2);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider2));
        recyclerView2.addItemDecoration(itemDecorator);

        backArrow.setOnClickListener(this);

        return view;
    }


    private void init2() {
        String[] options1 = {"Citizen Engagement","Bussiness Engagement","Process Improvement","Open Government", "Public Information", "Outreach and Awarness Not Using"};

        Question2Model question2Model;
        question2Model = new Question2Model("What are the Primary reason you think our state govt. is " +
                "social media technologoies?",options1);
        question2ModelList.add(question2Model);

        String[] options2 = {"External Platforms(Facebook, Youtube, etc)","Off the self, Purchased software", "Custom Application developed internally"};

        question2Model = new Question2Model("What do you think, Our city Govt. Media adoption is" +
                "primarily through?",options2);
        question2ModelList.add(question2Model);

        String[] options3 = {"Daily","One or twice in a week","Monthly", "Rarely", "Never"};

        question2Model = new Question2Model("How often you use social media?",options3);
        question2ModelList.add(question2Model);

        String[] options4 = {"Facebook","Youtube","Twitter", "Other"};

        question2Model = new Question2Model("Which Social Media, you get mostly of the daily news" +
                "from?",options4);
        question2ModelList.add(question2Model);
    }

    private void init() {
        questionModelList.add("Our Govt has clear direction in using social media for " +
                "Public Policy.");
        questionModelList.add("Our Govt is standarded in keeping the official matters" +
                "from Social Media.");
        questionModelList.add("Our Govt has best practices in managing from social" +
                "media.");
    }

    public void Submit(View view) {
        getActivity().onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrow:
                getActivity().onBackPressed();
                break;
        }
    }
}
