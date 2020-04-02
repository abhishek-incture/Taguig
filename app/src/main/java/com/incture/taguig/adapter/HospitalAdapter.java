package com.incture.taguig.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.BookAppointmentActivity;
import com.incture.taguig.R;
import com.incture.taguig.models.HospitalModel;
import com.incture.taguig.models.RequestModel;

import java.sql.BatchUpdateException;
import java.util.List;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {

    public interface OnEventListener {
        void onClick(RequestModel f1);
    }

    private final List<HospitalModel> hospitalModelList;
    private final Context context;
  //  private final OnEventListener eventListener;

    public HospitalAdapter(Context context, List<HospitalModel> hospitalModelList) {
        this.hospitalModelList = hospitalModelList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
         //this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        CardView layout = (CardView) inflater.inflate(R.layout.hospital_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final HospitalModel h1 = hospitalModelList.get(position);
        viewHolder.tvHospitalName.setText(h1.getHospitalName());
        viewHolder.tvHospitalDetail.setText( h1.getHospitalDetail());
        viewHolder.imgViewHospital.setImageResource(h1.getHospitalImage());




        viewHolder.btnGoToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context, "Cliked on: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                //eventListener.onClick(f1);

                Intent intent = new Intent(context, BookAppointmentActivity.class);
                intent.putExtra("hospital", h1);

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return hospitalModelList.size();
    }

    public void updateList(List<HospitalModel> newList){
        hospitalModelList.clear();
        hospitalModelList.addAll(newList);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgViewHospital;
        TextView tvHospitalName;
        TextView tvHospitalDetail;
        Button btnGoToBook;


        CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (CardView) itemView;

            btnGoToBook = layout.findViewById(R.id.btnGoToBook);
            imgViewHospital = layout.findViewById(R.id.imgViewHospital);
            tvHospitalName = layout.findViewById(R.id.tvHospitalName);
            tvHospitalDetail = layout.findViewById(R.id.tvHospitalDetail);
        }
    }

}

