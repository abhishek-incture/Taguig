package com.incture.taguig.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.models.BookingModel;
import com.incture.taguig.models.RequestModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    public interface OnEventListener {
        void onClick(RequestModel f1);
    }

    private final List<BookingModel> bookingModelList;
    private final Context context;
  //  private final OnEventListener eventListener;

    public BookingAdapter(Context context, List<BookingModel> bookingModelList) {
        this.bookingModelList = bookingModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
         //this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardView layout = (CardView) inflater.inflate(R.layout.booking_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final BookingModel b1 = bookingModelList.get(position);
        viewHolder.tvTime.setText(b1.getTime());
        viewHolder.tvStatus.setText( b1.getBookingStatus());
        if(b1.getBookingStatus().equals("Available"))
        {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWhite));

            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.tvGreen));
        }
        else  if(b1.getBookingStatus().equals("Fast filling"))
        {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWhite));

            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.tvRed));
        }
        else {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.darkBlack));
            viewHolder.tvStatus.setVisibility(View.INVISIBLE);
            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.greydark));
        }



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
        return bookingModelList.size();
    }

    public void updateList(List<BookingModel> newList){
        bookingModelList.clear();
        bookingModelList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvTime;
        TextView tvStatus;

        RelativeLayout r1;
        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (CardView) itemView;

            tvTime = layout.findViewById(R.id.tvTime);
            tvStatus = layout.findViewById(R.id.tvStatus);
            r1 = layout.findViewById(R.id.r1);
        }
    }

}

