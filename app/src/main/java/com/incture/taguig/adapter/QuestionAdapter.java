package com.incture.taguig.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.BookAppointmentActivity;
import com.incture.taguig.R;
import com.incture.taguig.models.HospitalModel;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> implements View.OnClickListener {

    /*public interface OnEventListener {
        void onClick(HospitalModel h1, int position);
    }
*/
    private final List<String> questionModelList;
    private final Context context;
  // private final OnEventListener eventListener;

    public QuestionAdapter(Context context, List<String> questionModelList) {
        this.questionModelList = questionModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
        // this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.question_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String s1 = questionModelList.get(position);
        viewHolder.tvQuestion.setText(s1);
        /*viewHolder.rb1.setOnClickListener(this);
        viewHolder.rb2.setOnClickListener(this);
        viewHolder.rb3.setOnClickListener(this);
        viewHolder.rb4.setOnClickListener(this);
        viewHolder.rb5.setOnClickListener(this);
*/
    }




       @Override
    public int getItemCount() {
        return questionModelList.size();
    }

    public void updateList(List<String> newList){
        questionModelList.clear();
        questionModelList.addAll(newList);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        RadioButton rb1,rb2,rb3,rb4,rb5;



        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView;

            tvQuestion = layout.findViewById(R.id.tvQuestion);
           /* rb1 = layout.findViewById(R.id.rb1);
            rb2 = layout.findViewById(R.id.rb2);
            rb3 = layout.findViewById(R.id.rb3);
            rb4 = layout.findViewById(R.id.rb4);
            rb5 = layout.findViewById(R.id.rb5);*/



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

