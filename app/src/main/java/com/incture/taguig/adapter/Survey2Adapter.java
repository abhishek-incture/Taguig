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

public class Survey2Adapter extends RecyclerView.Adapter<Survey2Adapter.ViewHolder> implements View.OnClickListener {

    /*public interface OnEventListener {
        void onClick(HospitalModel h1, int position);
    }
*/
    private final List<Question2Model> question2ModelList;
    private final Context context;
  // private final OnEventListener eventListener;

    public Survey2Adapter(Context context, List<Question2Model> question2ModelList) {
        this.question2ModelList = question2ModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
        // this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.survey2_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Question2Model q1 = question2ModelList.get(position);
        viewHolder.tvQuestion.setText(q1.getQuestion());
        String[] options =q1.getOptions();
        int size =options.length;
        if(size==2)
        {
            viewHolder.rb1.setText(options[0]);
            viewHolder.rb2.setText(options[1]);
            viewHolder.rb3.setVisibility(View.GONE);
            viewHolder.rb4.setVisibility(View.GONE);
        }
        else
        {
            viewHolder.rb3.setVisibility(View.VISIBLE);
            viewHolder.rb4.setVisibility(View.VISIBLE);
            viewHolder.rb1.setText(options[0]);
            viewHolder.rb2.setText(options[1]);
            viewHolder.rb3.setText(options[2]);
            viewHolder.rb4.setText(options[3]);

        }



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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        RadioButton rb1,rb2,rb3,rb4;

        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView;

            tvQuestion = layout.findViewById(R.id.tvQuestion);
            rb1 = layout.findViewById(R.id.rb1);
            rb2 = layout.findViewById(R.id.rb2);
            rb3 = layout.findViewById(R.id.rb3);
            rb4 = layout.findViewById(R.id.rb4);






        }


    }



}

