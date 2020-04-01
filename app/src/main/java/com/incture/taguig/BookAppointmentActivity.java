package com.incture.taguig;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.adapter.BookingAdapter;
import com.incture.taguig.models.BookingModel;

import java.util.ArrayList;
import java.util.List;

public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgViewUp,imgViewDown;
    private EditText etName,etAge;
    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<BookingModel> bookingModelList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imgViewUp = findViewById(R.id.imgViewUp);
        imgViewDown = findViewById(R.id.imgViewDown);
        imgViewUp.setOnClickListener(this);
        imgViewDown.setOnClickListener(this);
        etName = findViewById(R.id.etName);

        etAge = findViewById(R.id.etAge);

        recyclerView = findViewById(R.id.recyclerView);
        bookingModelList = new ArrayList<>();
        initDatasource();
        adapter = new BookingAdapter(this,bookingModelList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setAdapter(adapter);

    }

    private void initDatasource() {
        BookingModel bookingModel;
        bookingModel = new BookingModel(" 9AM-10AM","normal");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 10AM-11AM","Fast filling");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 11AM-12AM","Available");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 2PM-3PM","normal");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 3PM-4PM","Available");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 4PM-5PM","Available");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 5PM-6PM","Fast filling");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 6PM-7PM","normal");
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 7PM-8PM","normal");
        bookingModelList.add(bookingModel);

    }

    public void onRadioButtonClicked(View view) {

            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.rbMale:
                    if (checked)
                        // Pirates are the best
                        break;
                case R.id.rbFeMale:
                    if (checked)
                        // Ninjas rule
                        break;

        }

    }

    @Override
    public void onClick(View view) {
         switch (view.getId()){
             case R.id.imgViewUp:
                 addAgeCount();
                 break;
             case R.id.imgViewDown:
                 decreaseAgeCount();
                 break;
         }
    }

    private void addAgeCount() {
        if (!etAge.getText().toString().trim().isEmpty()) {

            int age = Integer.parseInt(etAge.getText().toString().trim());
            if (age >= 0) {
                age++;
                etAge.setText(Integer.toString(age));
            } else
                Toast.makeText(this, "Please enter correct Age", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();

        }
    }

    private void decreaseAgeCount() {
        if (!etAge.getText().toString().trim().isEmpty()) {

            int age = Integer.parseInt(etAge.getText().toString().trim());
            if (age > 0) {
                age--;
                etAge.setText(Integer.toString(age));
            } else
                Toast.makeText(this, "Please enter correct Age", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();

        }
    }

    public void BookAppointment(View view) {

        Intent intent = new Intent(this,BookingDetailsActivity.class);
        if(!etName.getText().toString().trim().isEmpty()) {
            intent.putExtra("name", etName.getText().toString());

            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Please type Name", Toast.LENGTH_SHORT).show();
        }
    }
}