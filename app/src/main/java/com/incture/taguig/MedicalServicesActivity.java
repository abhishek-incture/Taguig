package com.incture.taguig;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.incture.taguig.adapter.HospitalAdapter;
import com.incture.taguig.adapter.RequestAdapter;
import com.incture.taguig.models.HospitalModel;
import com.incture.taguig.models.RequestList;
import com.incture.taguig.models.RequestModel;
import com.incture.taguig.utils.CustomInfoWindowAdapter;
import com.incture.taguig.utils.MapWrapperLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MedicalServicesActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,HospitalAdapter.OnEventListener {

    private RecyclerView recyclerView;
    private HospitalAdapter adapter;
    private List<HospitalModel> hospitalModelList;
    public List<LatLng> latLngList;
    private TextView tvListView,tvMapView;
    private LinearLayout l1Map;

    private GoogleMap mMap;
    private GoogleMap googleMap;
    MapWrapperLayout mapWrapperLayout;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_services);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        latLngList = new ArrayList<>();

        initLatLongList();
        tvListView = findViewById(R.id.tvListView);

        tvMapView = findViewById(R.id.tvMapView);
        l1Map = findViewById(R.id.l1Map);


        recyclerView = findViewById(R.id.recyclerView);
        hospitalModelList = new ArrayList<>();
        hospitalModelList = init();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new HospitalAdapter(this,hospitalModelList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
    }

    private void initLatLongList() {
        LatLng  sydney;
        sydney = new LatLng(12.957815, 77.700601);
        latLngList.add(sydney);
        sydney = new LatLng(12.955280, 77.703211);
        latLngList.add(sydney);
        sydney = new LatLng(12.953445, 77.705740);
        latLngList.add(sydney);
        sydney = new LatLng(12.951674, 77.701938);
        latLngList.add(sydney);



    }

    private List init() {
        hospitalModelList.clear();
        HospitalModel hospitalModel;
        hospitalModel = new HospitalModel(R.drawable.image1,"Paranaque Doctors Hospital",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                        "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);
        hospitalModel = new HospitalModel(R.drawable.image2,"SSMC Medical Center",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                                      "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);

        hospitalModel = new HospitalModel(R.drawable.image3,"Paranaque Doctors Hospital",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                        "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);

        hospitalModel = new HospitalModel(R.drawable.hospitalimage_1,"Army general Hospital",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                        "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);

        return hospitalModelList;
    }

    public void onback(View view) {

        onBackPressed();
    }

    @Override
    public void onMapReady(GoogleMap mMap) {


        googleMap = mMap;

        mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);




        // MapWrapperLayout initialization
        // 39 - default marker height
        // 20 - offset between the default InfoWindow bottom edge and it's content bottom edge
        mapWrapperLayout.init(googleMap, getPixelsFromDp(this, 39 + 20));

        LatLng sydney = new LatLng(12.957815, 77.700601);
        MarkerOptions b = new MarkerOptions().position(sydney).title("Marker in Sydney");
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        moveCamera(sydney,15f);

        /*mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        MarkerOptions b = new MarkerOptions().position(sydney).title("Marker in Sydney");
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        moveCamera(sydney,7f);
        mMap.setOnInfoWindowClickListener(this);
*/
    }

    private void moveCamera(LatLng latLng, float zoom){
        //Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        googleMap.clear();

        googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(this,mapWrapperLayout));

        addMarkers();

        /*String snippet = R.drawable.hospitalimage_1 +"@"+ " Hospital 175 Doña Soledad Ave Better Living Subdivision";
        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("Paranaque Doctors  Hospital")
                .snippet(snippet);
        Marker mMarker = mMap.addMarker(options);*/
        // mMarker.showInfoWindow();

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
      //  Toast.makeText(this, "fhsdlfsdfdfhh", Toast.LENGTH_SHORT).show();
        String snippet= marker.getSnippet();
        String[] snippets = snippet.split("@");
        HospitalModel h1 = new HospitalModel(Integer.parseInt(snippets[0]),snippets[2],snippets[1]);
        Intent intent = new Intent(this,BookAppointmentActivity.class);
        intent.putExtra("hospital", h1);
        intent.putExtra("from","map");
        startActivity(intent);

    }

    public void addMarkers(){
        for(int i=0;i<hospitalModelList.size();i++){
            HospitalModel h1 = hospitalModelList.get(i);
            String snippet =  h1.getHospitalImage()+"@"+ h1.getHospitalDetail()+"@"+h1.getHospitalName();
            MarkerOptions options = new MarkerOptions()
                    .position(latLngList.get(i))
                    .title(h1.getHospitalName())
                    .snippet(snippet);
            googleMap.addMarker(options);

        }
    }

    public void onListClick(View view) {

       /* tvListView.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvListView.setBackgroundResource(R.drawable.bg_button);
        tvMapView.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));
       // tvMapView.setBackgroundResource(R.drawable.bg_buttondarkgrey);*/
        tvListView.setBackgroundResource(R.drawable.bg_buttongrey);
        tvListView.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvListView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvMapView.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));
        tvMapView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        tvMapView.setBackgroundColor(this.getResources().getColor(R.color.buttongrey));
        l1Map.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);


    }

    public void onMapClick(View view) {

        tvMapView.setBackgroundResource(R.drawable.bg_buttongrey);
        tvMapView.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvMapView.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvListView.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));
        tvListView.setTextSize(TypedValue.COMPLEX_UNIT_SP,13);
        tvListView.setBackgroundColor(this.getResources().getColor(R.color.buttongrey));
        recyclerView.setVisibility(View.GONE);
        l1Map.setVisibility(View.VISIBLE);
    }


    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }

    @Override
    public void onClick(HospitalModel h1,int position) {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr=" + latLngList.get(position).latitude + "," +latLngList.get(position).longitude));
        startActivity(intent);

    }
}
