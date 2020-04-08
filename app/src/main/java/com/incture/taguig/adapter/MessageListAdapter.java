package com.incture.taguig.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.models.BaseMessage;
import com.incture.taguig.network.ChatbotListener;
import com.incture.taguig.network.RecyclerItemClickListener;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    private Context mContext;
    private List<BaseMessage> mMessageList;
    private BaseMessage baseMessage;
    ChatbotListener chatbotListener;

    public MessageListAdapter(Context context, List<BaseMessage> messageList,ChatbotListener listener) {
        mContext = context;
        mMessageList = messageList;
        chatbotListener=listener;
    }

    @Override
    public int getItemViewType(int position) {
        baseMessage = mMessageList.get(position);

        if (baseMessage.getType().equalsIgnoreCase("Send")) {
            return VIEW_TYPE_MESSAGE_SENT;
        } else if (baseMessage.getType().equalsIgnoreCase("Recieve")) {
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_sent, viewGroup, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_message_received, viewGroup, false);
            return new ReceivedMessageHolder(view);
        }
        view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_message_received, viewGroup, false);
        return new ReceivedMessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        baseMessage = mMessageList.get(i);
        if (viewHolder instanceof SentMessageHolder) {
            SentMessageHolder holder = (SentMessageHolder) viewHolder;
            holder.messageText.setText(baseMessage.getMessage());

        }
        if (viewHolder instanceof ReceivedMessageHolder) {

            ReceivedMessageHolder holder = (ReceivedMessageHolder) viewHolder;
            holder.messageText.setText(baseMessage.getMessage());

            if (mMessageList.get(i).isVisible())
            {
                holder.button_recyclerviews.setVisibility(View.VISIBLE);
            }else {
                holder.button_recyclerviews.setVisibility(View.GONE);
            }

            if (baseMessage.getActions()!=null)
            {

                holder.button_recyclerviews.setAdapter(new ActionsAdapter(mContext,baseMessage.getActions(),chatbotListener));


                holder.button_recyclerviews.addOnItemTouchListener(
                        new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override public void onItemClick(View view, int position) {
                                // TODO Handle item click


                                mMessageList.get(i).setVisible(false);
                                notifyItemChanged(i);


                                //chatbotListener.performAction(baseMessage.getActions().get(position),"");

                                //holder.button_recyclerviews.setVisibility(View.GONE);


                            }
                        })
                );

            }

        }

    }

    @Override
    public int getItemCount() {
        return mMessageList == null ? 0 : mMessageList.size();
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;

        SentMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_sent);
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        RecyclerView button_recyclerviews;
        ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.text_message_recived);
            button_recyclerviews=itemView.findViewById(R.id.buttonrecyclerview);


           /// button_recyclerviews.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
            button_recyclerviews.setItemAnimator(new DefaultItemAnimator());

        }


    }
}