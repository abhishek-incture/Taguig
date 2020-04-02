package com.incture.taguig.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.incture.taguig.R;

/**
 * Created by User on 10/2/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.hospital_item, null);
    }

    private void rendowWindowText(Marker marker, View view){

        String title = marker.getTitle();
        TextView tvTitle = (TextView) view.findViewById(R.id.btnGetDirection);

            tvTitle.setText("Get Direction");


        ImageView imgViewHospital = view.findViewById(R.id.imgViewHospital);
        TextView tvHospitalName = view.findViewById(R.id.tvHospitalName);
        TextView tvHospitalDetail = view.findViewById(R.id.tvHospitalDetail);
        tvHospitalName.setText(title);
        String snippet = marker.getSnippet().toString();
        String[] snippets = snippet.split("@");
        int image=Integer.parseInt(snippets[0]);
        imgViewHospital.setImageResource(image);
        tvHospitalDetail.setText(snippets[1]);


        Button btnGoToBook= view.findViewById(R.id.btnGoToBook);
        btnGoToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "You clicked 2nd button", Toast.LENGTH_SHORT).show();
            }
        });
        tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "You clicked 1st button", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        return mWindow;
    }
}
