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

public class ImpUpdatesAdapter extends RecyclerView.Adapter<ImpUpdatesAdapter.ViewHolder>{
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Map> arrayListMap;
    Map<String, String> map;

    public ImpUpdatesAdapter(Context context, ArrayList<Map> arrayList) {
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        arrayListMap = arrayList;
    }


    @NonNull
    @Override
    public ImpUpdatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.imp_updates_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImpUpdatesAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        map = arrayListMap.get(position);

        holder.dayTextView.setText(map.get("date"));
        holder.dayContent.setText(map.get("msg"));
    }

    @Override
    public int getItemCount() {
        return arrayListMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView dayTextView, dayContent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            dayTextView = itemView.findViewById(R.id.dayTextView);
            dayContent = itemView.findViewById(R.id.dayContent);
        }
    }
}
