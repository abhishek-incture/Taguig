package com.incture.taguig;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
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
import com.incture.taguig.models.HospitalModel;
import com.incture.taguig.utils.PhotoDecorator;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookAppointmentActivity extends AppCompatActivity implements View.OnClickListener,BookingAdapter.OnEventListener {
    public static final String FULL = "full";
    public static final String FAST_FILLING = "Fast filling" ;
    public static final String AVAILABLE = "Available";
    private Toolbar toolbar;
    private ImageView imgView,imgViewUp,imgViewDown;
    private TextView tvToday,tvTommorrow,tv1,tv2,tv3,tv4,tvName;
    private EditText etName,etPatientAge;
    private HospitalModel h1;
    private String slotTime,gender;

    private RecyclerView recyclerView;
    private BookingAdapter adapter;
    private List<BookingModel> bookingModelList;
    public static final String PATIENT_NAME="PatientName";
    public static final String PATIENT_AGE="PatientAge";
    public static final String PATIENT_SLOTTIME="PatientSlotTime";
    public static final String PATIENT_GENDER="PatientGender";
    public static final String APPOINTMENT_DAY="AppointmentDay";
    private String appointmentDay="Today";

    public static final String HOSPITAL="hospital";


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
        tvToday = findViewById(R.id.tvToday);
        tvTommorrow = findViewById(R.id.tvTommorrow);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tvName = findViewById(R.id.tvName);


        imgView = findViewById(R.id.imgView);

        etName = findViewById(R.id.etName);



        etPatientAge = findViewById(R.id.etPatientAge);

        recyclerView = findViewById(R.id.recyclerView);
        slotTime="notSelected";
        gender="Male";

        Date date;
        Calendar calendar = Calendar.getInstance();


        calendar.add(Calendar.DATE, 2);
        date = calendar.getTime();
        int day = date.getDate();
       String month= calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );

        tv3.setText(day+" "+ month);

        calendar.add(Calendar.DATE, 1);
        date = calendar.getTime();
         day = date.getDate();
        month= calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH );


        tv4.setText(day+" "+ month);


        Intent intent = getIntent();
        h1 = (HospitalModel) intent.getSerializableExtra("hospital");
        tv1.setText(h1.getHospitalName());
        tv2.setText(h1.getHospitalDetail());
        tvName.setText(h1.getHospitalName());
        imgView.setImageResource(h1.getHospitalImage());
        bookingModelList = new ArrayList<>();

        adapter = new BookingAdapter(this,bookingModelList);
        initDatasource();

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));

        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new PhotoDecorator(
                getResources().getDimensionPixelSize(R.dimen.photos_list_spacing),
                getResources().getInteger(R.integer.photo_list_preview_columns)));


    }

    private void initDatasource() {
        bookingModelList.clear();
        BookingModel bookingModel;
        bookingModel = new BookingModel(" 9AM-10AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 10AM-11AM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 11AM-12AM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 2PM-3PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 3PM-4PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 4PM-5PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 5PM-6PM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 6PM-7PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 7PM-8PM",FULL);
        bookingModelList.add(bookingModel);
        adapter.notifyDataSetChanged();

    }

    public void onRadioButtonClicked(View view) {

            // Is the button now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radio button was clicked
            switch(view.getId()) {
                case R.id.rbMale:
                    if (checked)
                        gender="Male";
                        break;
                case R.id.rbFeMale:
                    if (checked)
                        gender="Female";
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
        if (!etPatientAge.getText().toString().trim().isEmpty()) {

            int age = Integer.parseInt(etPatientAge.getText().toString().trim());
            if (age >= 0) {
                age++;
                etPatientAge.setText(Integer.toString(age));
            } else
                Toast.makeText(this, "Please enter correct Age", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();

        }
    }

    private void decreaseAgeCount() {
        if (!etPatientAge.getText().toString().trim().isEmpty()) {

            int age = Integer.parseInt(etPatientAge.getText().toString().trim());
            if (age > 0) {
                age--;
                etPatientAge.setText(Integer.toString(age));
            } else
                Toast.makeText(this, "Please enter correct Age", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter Age", Toast.LENGTH_SHORT).show();

        }
    }

    public void BookAppointment(View view) {

        if(etName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Please fill Patient Name", Toast.LENGTH_SHORT).show();
        }

        else if(slotTime.equals("notSelected"))
        {
            Toast.makeText(this, "Please Select Slot Time", Toast.LENGTH_SHORT).show();

        }
        else if(etPatientAge.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Select Age", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent = new Intent(this,BookingDetailsActivity.class);
            String age="";
            intent.putExtra(PATIENT_NAME, etName.getText().toString());
            intent.putExtra(PATIENT_SLOTTIME,slotTime);
            if(!etPatientAge.getText().toString().isEmpty())
            {
                age = etPatientAge.getText().toString();}
            intent.putExtra(PATIENT_AGE,age);
            intent.putExtra(PATIENT_GENDER,gender);
            intent.putExtra(APPOINTMENT_DAY,appointmentDay);
            intent.putExtra(HOSPITAL,h1);


            startActivity(intent);        }
    }

    public void onback(View view) {
        onBackPressed();
    }

    @Override
    public void onClick(BookingModel b1,int position) {

        if(b1.getBookingStatus().equals("full"))
        {
            Toast.makeText(this, "Slot Full", Toast.LENGTH_SHORT).show();
        }
        else {
            if (!b1.getSelected()) {
                 slotTime=b1.getTime();
                for(int i=0;i<bookingModelList.size();i++){
                    if(i==position)
                        bookingModelList.get(i).setSelected(true);
                    else
                        bookingModelList.get(i).setSelected(false);
                }
                adapter.notifyDataSetChanged();
            }
        }

        //Toast.makeText(this, "You selected " + b1.getTime(), Toast.LENGTH_SHORT).show();
    }


    public void onClickToday(View view) {
        initDatasource();
        adapter.notifyDataSetChanged();
        appointmentDay="Today";
        tvToday.setBackgroundResource(R.drawable.bg_button);
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvToday.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvTommorrow.setBackgroundResource(0);
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv3.setBackgroundResource(0);
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv4.setBackgroundResource(0);
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));



    }
    public void onClickTomorrow(View view) {
         initDatasource2();
         appointmentDay="Tommorrow";
        tvTommorrow.setBackgroundResource(R.drawable.bg_button);
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvTommorrow.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvToday.setBackgroundResource(0);
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv3.setBackgroundResource(0);
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv4.setBackgroundResource(0);
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

    }


    public void onClickSecondDay(View view) {
        appointmentDay=tv3.getText().toString();
        initDatasource3();
        tv3.setBackgroundResource(R.drawable.bg_button);
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv3.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvTommorrow.setBackgroundResource(0);
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tvToday.setBackgroundResource(0);
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv4.setBackgroundResource(0);
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

    }

    public void onClickThirdDay(View view) {
        appointmentDay=tv4.getText().toString();
        initDatasource4();
        tv4.setBackgroundResource(R.drawable.bg_button);
        tv4.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv4.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);

        tvTommorrow.setBackgroundResource(0);
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvTommorrow.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tv3.setBackgroundResource(0);
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tv3.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));

        tvToday.setBackgroundResource(0);
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkRed));
        tvToday.setTextColor(this.getResources().getColor(R.color.colorDarkGrey));
    }


    private void initDatasource2() {
        bookingModelList.clear();

        BookingModel bookingModel;
        bookingModel = new BookingModel(" 9AM-10AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 10AM-11AM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 11AM-12AM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 2PM-3PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 3PM-4PM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 4PM-5PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 5PM-6PM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 6PM-7PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 7PM-8PM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        adapter.notifyDataSetChanged();


    }

    private void initDatasource3() {
        bookingModelList.clear();
        BookingModel bookingModel;
        bookingModel = new BookingModel(" 9AM-10AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 10AM-11AM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 11AM-12AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 2PM-3PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 3PM-4PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 4PM-5PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 5PM-6PM",FAST_FILLING);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 6PM-7PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 7PM-8PM",FAST_FILLING);
        bookingModelList.add(bookingModel);

        adapter.notifyDataSetChanged();


    }

    private void initDatasource4() {
        bookingModelList.clear();

        BookingModel bookingModel;
        bookingModel = new BookingModel(" 9AM-10AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 10AM-11AM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 11AM-12AM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 2PM-3PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 3PM-4PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 4PM-5PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 5PM-6PM",AVAILABLE);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 6PM-7PM",FULL);
        bookingModelList.add(bookingModel);
        bookingModel = new BookingModel(" 7PM-8PM",FAST_FILLING);
        bookingModelList.add(bookingModel);

        adapter.notifyDataSetChanged();


    }

}
