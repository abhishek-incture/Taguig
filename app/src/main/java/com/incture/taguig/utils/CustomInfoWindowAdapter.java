package com.incture.taguig.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.incture.taguig.BookAppointmentActivity;
import com.incture.taguig.R;
import com.incture.taguig.models.HospitalModel;

/**
 * Created by User on 10/2/2017.
 */

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;
    private OnInfoWindowElemTouchListener infoButtonListener;
    private MapWrapperLayout mapWrapperLayout;

    public CustomInfoWindowAdapter(Context context,MapWrapperLayout mapWrapperLayout1) {
        mContext = context;
        mWindow = LayoutInflater.from(context).inflate(R.layout.hospital_item, null);
       this.mapWrapperLayout = mapWrapperLayout1;

    }

    private void rendowWindowText(final Marker marker, View view){

        String title = marker.getTitle();
        Button btnGetDirection = view.findViewById(R.id.btnGetDirection);



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



        this.infoButtonListener = new OnInfoWindowElemTouchListener(btnGoToBook, mContext.getResources().getDrawable(R.drawable.bg_buttonred),mContext.getResources().getDrawable(R.drawable.bg_buttonred)){
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
                // Here we can perform some action triggered after clicking the button
                //Toast.makeText(mContext, "click on button 1", Toast.LENGTH_SHORT).show();
            }
        };
        btnGoToBook.setOnTouchListener(infoButtonListener);

        infoButtonListener = new OnInfoWindowElemTouchListener(btnGetDirection, mContext.getResources().getDrawable(R.drawable.bg_button),mContext.getResources().getDrawable(R.drawable.bg_button)){
            @Override
            protected void onClickConfirmed(View v, Marker marker) {
               // Toast.makeText(mContext, "click on button 2"+marker.getSnippet(), Toast.LENGTH_LONG).show();
            }
        };
        btnGetDirection.setOnTouchListener(infoButtonListener);


        btnGoToBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String snippet= marker.getSnippet();
                String[] snippets = snippet.split("@");
                HospitalModel h1 = new HospitalModel(Integer.parseInt(snippets[0]),snippets[2],snippets[1]);
                Intent intent = new Intent(mContext, BookAppointmentActivity.class);
                intent.putExtra("hospital", h1);
                intent.putExtra("from","map");
                mContext.startActivity(intent);
            }
        });
        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "You clicked 1st button"+marker.getSnippet(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr=" + marker.getPosition().latitude + "," +marker.getPosition().longitude));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        rendowWindowText(marker, mWindow);
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        rendowWindowText(marker, mWindow);
        infoButtonListener.setMarker(marker);

        // We must call this to set the current marker and infoWindow references
        // to the MapWrapperLayout
        this.mapWrapperLayout.setMarkerWithInfoWindow(marker, mWindow);
        return mWindow;
    }
}
