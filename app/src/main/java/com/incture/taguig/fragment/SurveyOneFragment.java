package com.incture.taguig.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.adapter.Survey2Adapter;
import com.incture.taguig.models.Question2Model;

import java.util.ArrayList;
import java.util.List;

public class SurveyOneFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    private List<Question2Model> question2ModelList;
    private Survey2Adapter adapter;
    private Button btnSubmit;
    ImageView backArrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_survey_one, container, false);

        backArrow = (ImageView)view.findViewById(R.id.backArrow);
        btnSubmit = (Button) view.findViewById(R.id.btnSubmit);

        backArrow.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        question2ModelList = new ArrayList<>();
        init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        adapter = new Survey2Adapter(getContext(),question2ModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
       /* DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);*/


    }

    private void init() {
        String[] options = {"Yes","No"};

        Question2Model question2Model;
        question2Model = new Question2Model("Do you have a Fever? (Mayroon ka bang Lagnat?)",options);
        question2ModelList.add(question2Model);
        String[] options1 = {"Rarely","Sometimes","Often","Very Often"};

        question2Model = new Question2Model("How Often you Sneeze? (Madalas ba angiyong pagbahing?) ",options1);
        question2ModelList.add(question2Model);

        question2Model = new Question2Model("Do you feel tired every day even you are not doing " +
                "anything? (Nararamdaman mo ba ang pagod araw araw" +
                "kahit wala ka naman ginagawa?)",options);
        question2ModelList.add(question2Model);

        question2Model = new Question2Model("Is your throat sore and swollen? (Masakit at namamaga" +
                "ba ang iyong lalamunan?)",options);
        question2ModelList.add(question2Model);
        question2Model = new Question2Model("Did you have travel history from other countries in the " +
                "past 2 months? (Mayroon ka bang kasaysayan ng " +
                "paglalakbay mula sa ibang mga bansa sa nakaraang 2" +
                "buwan?)",options);
        question2ModelList.add(question2Model);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backArrow:
                getActivity().onBackPressed();
                break;

            case R.id.btnSubmit:
                getActivity().onBackPressed();
                break;

        }
    }
}
