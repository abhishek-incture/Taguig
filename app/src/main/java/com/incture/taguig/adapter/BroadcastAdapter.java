package com.incture.taguig.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;

import java.util.ArrayList;
import java.util.Map;

public class BroadcastAdapter extends RecyclerView.Adapter<BroadcastAdapter.ViewHolder>{
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Map> arrayListMap;
    Map<String, String> map;

    public BroadcastAdapter(Context context, ArrayList<Map> arrayList) {
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        arrayListMap = arrayList;
    }


    @NonNull
    @Override
    public BroadcastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.broadcast_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        map = arrayListMap.get(position);

        holder.articleTitle.setText(map.get("title"));
        holder.description.setText(map.get("description"));
        holder.date.setText(map.get("date"));
    }



    @Override
    public int getItemCount() {
        return arrayListMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView articleTitle, description, date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleTitle = itemView.findViewById(R.id.articleTitle);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}

