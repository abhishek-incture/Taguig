package com.incture.taguig.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.BookAppointmentActivity;
import com.incture.taguig.R;
import com.incture.taguig.models.BookingModel;

import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {

    public interface OnEventListener {
        void onClick(BookingModel b1,int position);
    }

    private final List<BookingModel> bookingModelList;
    private final Context context;
    private final OnEventListener eventListener;

    public BookingAdapter(Context context, List<BookingModel> bookingModelList) {
        this.bookingModelList = bookingModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
         this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardView layout = (CardView) inflater.inflate(R.layout.booking_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final BookingModel b1 = bookingModelList.get(position);
        viewHolder.tvTime.setText(b1.getTime());
        viewHolder.tvStatus.setText( b1.getBookingStatus());
        if(b1.getBookingStatus().equals(BookAppointmentActivity.AVAILABLE))
        {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWhite));
            viewHolder.tvStatus.setVisibility(View.VISIBLE);

            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.tvGreen));
        }
        else  if(b1.getBookingStatus().equals(BookAppointmentActivity.FAST_FILLING))
        {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.colorWhite));
            viewHolder.tvStatus.setVisibility(View.VISIBLE);

            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.tvRed));
        }
        else {
            viewHolder.tvTime.setTextColor(context.getResources().getColor(R.color.darkBlack));
            viewHolder.tvStatus.setVisibility(View.INVISIBLE);
            viewHolder.r1.setBackgroundColor(context.getResources().getColor(R.color.greydark));
        }

        if(b1.getSelected())
        {
            /*FrameLayout.LayoutParams relativeParams = (FrameLayout.LayoutParams)viewHolder.r1.getLayoutParams();
            relativeParams.setMargins(3, 3, 3, 0);  // left, top, right, bottom
            viewHolder.r1.setLayoutParams(relativeParams);*/

            if(b1.getBookingStatus().equals(BookAppointmentActivity.AVAILABLE)){
                viewHolder.r1.setBackgroundResource(R.drawable.bg_buttondarkgreen);
                viewHolder.tvStatus.setVisibility(View.VISIBLE);

            }
            else
            {  viewHolder.r1.setBackgroundResource(R.drawable.bg_buttondarkred);
                viewHolder.tvStatus.setVisibility(View.VISIBLE);

            }


        }





        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Cliked on: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                eventListener.onClick(b1,position);
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

