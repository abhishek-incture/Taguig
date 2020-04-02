package com.incture.taguig.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.Global;
import com.incture.taguig.R;
import com.incture.taguig.utils.CircleImageView;

import java.util.ArrayList;
import java.util.Map;

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.ViewHolder>{
    Context mContext;
    LayoutInflater inflater;
    ArrayList<Map> arrayListMap;
    Map<String, String> map;

    public FriendsListAdapter(Context context, ArrayList<Map> arrayList) {
        this.mContext = context;
        inflater = LayoutInflater.from(this.mContext);
        arrayListMap = arrayList;
    }


    @NonNull
    @Override
    public FriendsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.friends_list_item, parent, false);
        FriendsListAdapter.ViewHolder viewHolder = new FriendsListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsListAdapter.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        map = arrayListMap.get(position);

        holder.friendsName.setText(map.get("name"));
        holder.address.setText(map.get("address"));
        new Global(mContext).loadPicasa(map.get("profileimage"), holder.profileImage);

    }



    @Override
    public int getItemCount() {
        return arrayListMap.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView friendsName, address;
        CircleImageView profileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            friendsName = itemView.findViewById(R.id.friendsName);
            address = itemView.findViewById(R.id.address);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}

