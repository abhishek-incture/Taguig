package com.incture.taguig.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.ViewHolder> {

    public interface OnEventListener {
        void onClick(RequestModel f1);
    }

    private final List<RequestModel> requestModelList;
    private final Context context;
  //  private final OnEventListener eventListener;

    public RequestAdapter(Context context, List<RequestModel> requestModelList) {
        this.requestModelList = requestModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
         //this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.request_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final RequestModel r1 = requestModelList.get(position);
        viewHolder.tvRequestSubject.setText(r1.getRequestSubject());
        viewHolder.tvRequestDate.setText( r1.getRequestDate());
        viewHolder.imgView.setImageResource(r1.getImage());
        if(r1.getStatus().equals("Approved"))
        {
            viewHolder.sideBar.setBackgroundColor(context.getResources().getColor(R.color.lightGreen));
        }
        else if(r1.getStatus().equals("Pending"))
        {
            viewHolder.sideBar.setBackgroundColor(context.getResources().getColor(R.color.lightGrey));
        }
        else
            viewHolder.sideBar.setBackgroundColor(context.getResources().getColor(R.color.lightRed));



        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Cliked on: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                //eventListener.onClick(f1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestModelList.size();
    }

    public void updateList(List<RequestModel> newList){
        requestModelList.clear();
        requestModelList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View sideBar;
        ImageView imgView;
        TextView tvRequestSubject;
        TextView tvRequestDate;


        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView;

            sideBar = layout.findViewById(R.id.sideBar);
            imgView = layout.findViewById(R.id.imgView);
            tvRequestSubject = layout.findViewById(R.id.tvRequestSubject);
            tvRequestDate = layout.findViewById(R.id.tvRequestDate);
        }
    }

}

