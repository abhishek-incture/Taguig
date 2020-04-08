package com.incture.taguig.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.incture.taguig.R;
import com.incture.taguig.network.ChatbotListener;
import com.incture.taguig.network.Connector;

import java.util.ArrayList;
import java.util.HashMap;

public class ActionsAdapter extends RecyclerView.Adapter<ActionsAdapter.MyActionHolder> {

    Context context;
    ArrayList<HashMap<String,String>> actions;
    Connector connector;


    ChatbotListener chatbotListener;


    private static final int POST = Request.Method.POST;

    public ActionsAdapter(Context context, ArrayList<HashMap<String,String>> actions, ChatbotListener listener) {
        this.context = context;
        this.actions = actions;
        connector= new Connector(context);
        this.chatbotListener=listener;

    }

    @NonNull
    @Override
    public MyActionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyActionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyActionHolder holder, final int position) {

        holder.button.setText(""+actions.get(position).get("title"));

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chatbotListener.performAction(actions.get(position).get("title"),actions.get(position).get("value"));

            }
        });

    }

    @Override
    public int getItemCount() {
        return actions.size();
    }

    class MyActionHolder extends RecyclerView.ViewHolder{

        TextView button;

        public MyActionHolder(View itemView) {
            super(itemView);
            button=itemView.findViewById(R.id.item_button);
        }
    }


}
