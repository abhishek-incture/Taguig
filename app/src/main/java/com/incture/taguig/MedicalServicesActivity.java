package com.incture.taguig;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.adapter.HospitalAdapter;
import com.incture.taguig.adapter.RequestAdapter;
import com.incture.taguig.models.HospitalModel;
import com.incture.taguig.models.RequestList;
import com.incture.taguig.models.RequestModel;

import java.util.ArrayList;
import java.util.List;

public class MedicalServicesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HospitalAdapter adapter;
    private List<HospitalModel> hospitalModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_services);

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

    private List init() {
        hospitalModelList.clear();
        HospitalModel hospitalModel;
        hospitalModel = new HospitalModel(R.drawable.hospitalimage_1,"Paranaque Doctors Hospital",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                        "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);
        hospitalModel = new HospitalModel(R.drawable.hospitalimage_1,"SSMC Medical Center",
                "175 Doña Soledad Ave Better Living Subdivision,\n" +
                                      "Parañaque, 1711 Metro Manila, Philippines");
        hospitalModelList.add(hospitalModel);

        hospitalModel = new HospitalModel(R.drawable.hospitalimage_1,"Paranaque Doctors Hospital",
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
}
