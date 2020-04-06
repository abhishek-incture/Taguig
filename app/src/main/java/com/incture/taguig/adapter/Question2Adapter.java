package com.incture.taguig.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.models.Question2Model;

import java.util.List;

public class Question2Adapter extends RecyclerView.Adapter<Question2Adapter.ViewHolder> implements View.OnClickListener {

    /*public interface OnEventListener {
        void onClick(HospitalModel h1, int position);
    }
*/
    private final List<Question2Model> question2ModelList;
    private final Context context;
  // private final OnEventListener eventListener;

    public Question2Adapter(Context context, List<Question2Model> question2ModelList) {
        this.question2ModelList = question2ModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
        // this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardView layout = (CardView) inflater.inflate(R.layout.question_item2, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Question2Model q1 = question2ModelList.get(position);
        viewHolder.tvQuestion.setText(q1.getQuestion());

        ArrayAdapter dataAdapterForSpinner = new ArrayAdapter<>(context,android.R.layout.simple_dropdown_item_1line, q1.getOptions());
        dataAdapterForSpinner.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        viewHolder.spinnerOptions.setAdapter(dataAdapterForSpinner);
        viewHolder.spinnerOptions.setSelected(false);  // must
        viewHolder.spinnerOptions.setSelection(0,false);




    }




       @Override
    public int getItemCount() {
        return question2ModelList.size();
    }

    public void updateList(List<Question2Model> newList){
        question2ModelList.clear();
        question2ModelList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
       /* switch (view.getId()){
            case R.id.rb1:
                onRadioButtonClicked(view);
                break;
        }*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements AdapterView.OnItemSelectedListener {

        TextView tvQuestion;
        Spinner spinnerOptions;
        EditText etOptions;


        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (CardView) itemView;

            tvQuestion = layout.findViewById(R.id.tvQuestion);
            spinnerOptions = layout.findViewById(R.id.spinnerOptions);
            etOptions = layout.findViewById(R.id.etOptions);
            spinnerOptions.setOnItemSelectedListener(this);
            //spinnerOptions.setSelection(0,false);
            //spinnerOptions.setSelected(false);  // must


            etOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    spinnerOptions.setVisibility(View.VISIBLE);

                    spinnerOptions.performClick();
                }
            });
            spinnerOptions.setSelected(false);  // must
            etOptions.setKeyListener(null);



        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            etOptions.setText(spinnerOptions.getSelectedItem().toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

    }

/*
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rb1:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rb2:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.rb3:
                if (checked)
                    // Pirates are the best
                    break;
            case R.id.rb4:
                if (checked)
                    // Ninjas rule
                    break;
            case R.id.rb5:
                if (checked)
                    // Ninjas rule
                    break;
        }
    }
*/

}

