package com.incture.taguig.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.incture.taguig.Global;
import com.incture.taguig.R;
import com.incture.taguig.adapter.FriendsListAdapter;
import com.incture.taguig.utils.CircleImageView;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static androidx.core.content.PermissionChecker.checkSelfPermission;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    RecyclerView friendsListRecycler;
    FriendsListAdapter friendsListAdapter;
    CircleImageView profileImage, editProfileImage;

    Map<String, String> friendsListMap;
    ArrayList<Map> arrayList = new ArrayList<>();

    TextView friendsButton, pollsButton, surveysButton, tvVote1, tvVote2, tvVote3, tvVote4,tvVote5,tvVote6,tvVote7,tvVote8;
    LinearLayout surveysLinear, pollsLinear, l1, l2, l3, l4,l5,l6,l7,l8;
    RadioButton radioButtonA1, radioButtonA2, radioButtonA3, radioButtonA4,radioButtonB1,radioButtonB2,radioButtonB3,radioButtonB4;
    RadioGroup radioGroup1;
    Button btnFirstSurvey,btnSecondSurvey;
    private static int RESULT_LOAD_IMAGE = 1;
    public static String base64_image_upload = null;

    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private static final int CAMERA_REQUEST = 1888;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage= view.findViewById(R.id.profileImage);
        editProfileImage= view.findViewById(R.id.editProfileImage);

        btnSecondSurvey= view.findViewById(R.id.btnStartSurvey2);
        btnFirstSurvey = view.findViewById(R.id.btnStartSurvey1);
        friendsListRecycler = (RecyclerView) view.findViewById(R.id.friendsListRecycler);
        friendsButton = (TextView) view.findViewById(R.id.friendsButton);
        pollsButton = (TextView) view.findViewById(R.id.pollsButton);
        surveysButton = (TextView) view.findViewById(R.id.surveysButton);
        surveysLinear = (LinearLayout) view.findViewById(R.id.surveysLinear);
        pollsLinear = (LinearLayout) view.findViewById(R.id.pollsLinear);

        l1 = (LinearLayout) view.findViewById(R.id.l1);
        l2 = (LinearLayout) view.findViewById(R.id.l2);
        l3 = (LinearLayout) view.findViewById(R.id.l3);
        l4 = (LinearLayout) view.findViewById(R.id.l4);
        l5 = (LinearLayout) view.findViewById(R.id.l5);
        l6 = (LinearLayout) view.findViewById(R.id.l6);
        l7 = (LinearLayout) view.findViewById(R.id.l7);
        l8 = (LinearLayout) view.findViewById(R.id.l8);
        tvVote1 = view.findViewById(R.id.tvVote1);
        tvVote2 = view.findViewById(R.id.tvVote2);
        tvVote3 = view.findViewById(R.id.tvVote3);
        tvVote4 = view.findViewById(R.id.tvVote4);
        tvVote5 = view.findViewById(R.id.tvVote5);
        tvVote6 = view.findViewById(R.id.tvVote6);
        tvVote7 = view.findViewById(R.id.tvVote7);
        tvVote8 = view.findViewById(R.id.tvVote8);
        radioButtonA1 = view.findViewById(R.id.radioButtonA1);
        radioButtonA2 = view.findViewById(R.id.radioButtonA2);
        radioButtonA3 = view.findViewById(R.id.radioButtonA3);
        radioButtonA4 = view.findViewById(R.id.radioButtonA4);
        radioButtonB1 = view.findViewById(R.id.radioButtonB1);
        radioButtonB2 = view.findViewById(R.id.radioButtonB2);
        radioButtonB3 = view.findViewById(R.id.radioButtonB3);
        radioButtonB4 = view.findViewById(R.id.radioButtonB4);
        radioGroup1 = view.findViewById(R.id.radioGroup1);

        radioButtonA1.setOnClickListener(this);
        radioButtonA2.setOnClickListener(this);
        radioButtonA3.setOnClickListener(this);
        radioButtonA4.setOnClickListener(this);
        radioButtonB1.setOnClickListener(this);
        radioButtonB2.setOnClickListener(this);
        radioButtonB3.setOnClickListener(this);
        radioButtonB4.setOnClickListener(this);


        tvVote1.setOnClickListener(this);
        tvVote2.setOnClickListener(this);
        tvVote3.setOnClickListener(this);
        tvVote4.setOnClickListener(this);

        tvVote5.setOnClickListener(this);
        tvVote6.setOnClickListener(this);
        tvVote7.setOnClickListener(this);
        tvVote8.setOnClickListener(this);

        btnFirstSurvey.setOnClickListener(this);

        btnSecondSurvey.setOnClickListener(this);
        editProfileImage.setOnClickListener(this);






       /* for(int i=0; i<=20; i++){
            friendsListMap = new HashMap<>();
            friendsListMap.put("name","Jasmine K");
            friendsListMap.put("address","Manila, Phillipines");
            friendsListMap.put("profileimage","https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcSMtqzp8_5fpLQXzM6SMjMXzEAOiIaILgbkUQfBPOF61vUwn81D&usqp=CAU");
            arrayList.add(friendsListMap);
        }*/

        init();
        //init();

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder(); StrictMode.setVmPolicy(builder.build());

        friendsListAdapter = new FriendsListAdapter(getActivity(), arrayList);
        friendsListRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        friendsListRecycler.setAdapter(friendsListAdapter);

        friendsButton.setOnClickListener(this);
        pollsButton.setOnClickListener(this);
        surveysButton.setOnClickListener(this);




        return view;
    }


    public void init() {

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Rosa");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec8));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Althea");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec4));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Andrea");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec2));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Mary");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec6));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Jessa");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec7));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Nicole");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec9));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Angel");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec5));
        arrayList.add(friendsListMap);


        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Prince");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec1));
        arrayList.add(friendsListMap);

        friendsListMap = new HashMap<>();
        friendsListMap.put("name", "Samantha");
        friendsListMap.put("address", "Manila, Philippines");
        friendsListMap.put("profileimage", String.valueOf(R.drawable.rec4));
        arrayList.add(friendsListMap);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.friendsButton:
                friendsButton.setBackgroundResource(R.drawable.bg_buttongrey);
                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                pollsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));

                surveysButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                friendsListRecycler.setVisibility(View.VISIBLE);
                pollsLinear.setVisibility(View.GONE);
                surveysLinear.setVisibility(View.GONE);

                break;

            case R.id.pollsButton:
                pollsButton.setBackgroundResource(R.drawable.bg_buttongrey);
                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                friendsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));

                surveysButton.setBackgroundColor(getActivity().getResources().getColor(R.color.buttongrey));
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                friendsListRecycler.setVisibility(View.GONE);
                pollsLinear.setVisibility(View.VISIBLE);
                surveysLinear.setVisibility(View.GONE);

                break;

            case R.id.surveysButton:
                surveysButton.setBackgroundResource(R.drawable.bg_buttongrey);
                surveysButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkRed));
                surveysButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                friendsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                friendsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                friendsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);


                pollsButton.setBackgroundColor(getActivity().getResources().getColor(R.color.tvdarkGrey));
                pollsButton.setTextColor(getActivity().getResources().getColor(R.color.colorDarkGrey));
                pollsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);

                friendsListRecycler.setVisibility(View.GONE);
                pollsLinear.setVisibility(View.GONE);
                surveysLinear.setVisibility(View.VISIBLE);

                break;


            case R.id.radioButtonA1:
                onRadioButtonClicked(radioButtonA1);
                break;
            case R.id.radioButtonA2:
                onRadioButtonClicked(radioButtonA2);
                break;

            case R.id.radioButtonA3:
                onRadioButtonClicked(radioButtonA3);
                break;
            case R.id.radioButtonA4:
                onRadioButtonClicked(radioButtonA4);
                break;
            case R.id.tvVote1:
                onTextViewClick(tvVote1);
                break;
            case R.id.tvVote2:
                onTextViewClick(tvVote2);
                break;
            case R.id.tvVote3:
                onTextViewClick(tvVote3);
                break;
            case R.id.tvVote4:
                onTextViewClick(tvVote4);
                break;
            case R.id.radioButtonB1:
                onRadioButtonClicked2(radioButtonB1);
                break;
            case R.id.radioButtonB2:
                onRadioButtonClicked2(radioButtonB2);
                break;

            case R.id.radioButtonB3:
                onRadioButtonClicked2(radioButtonB3);
                break;
            case R.id.radioButtonB4:
                onRadioButtonClicked2(radioButtonB4);
                break;
            case R.id.tvVote5:
                onTextViewClick2(tvVote5);
                break;
            case R.id.tvVote6:
                onTextViewClick2(tvVote6);
                break;
            case R.id.tvVote7:
                onTextViewClick2(tvVote7);
                break;
            case R.id.tvVote8:
                onTextViewClick2(tvVote8);
                break;
            case R.id.btnStartSurvey1:
                SurveyOneFragment surveyOneFragment = new SurveyOneFragment();
                replaceFragment(surveyOneFragment);
                break;
            case R.id.btnStartSurvey2:
                SurveyTwoFragment surveyTwoFragment = new SurveyTwoFragment();
                replaceFragment(surveyTwoFragment);
                break;

            case R.id.editProfileImage:
                selectImage(getContext());
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonA1:
                if (checked)
                    // Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                    l1.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote1.setVisibility(View.VISIBLE);

                l2.setBackgroundResource(0);
                tvVote2.setVisibility(View.GONE);
                radioButtonA2.setChecked(false);
                l3.setBackgroundResource(0);
                tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l4.setBackgroundResource(0);
                tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Pirates are the best
                break;
            case R.id.radioButtonA2:
                if (checked)

                    l2.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote2.setVisibility(View.VISIBLE);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                l3.setBackgroundResource(0);
                tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l4.setBackgroundResource(0);
                tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Ninjas rule
                break;

            case R.id.radioButtonA3:
                if (checked)
                    // Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                    l3.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote3.setVisibility(View.VISIBLE);

                l2.setBackgroundResource(0);
                tvVote2.setVisibility(View.GONE);
                radioButtonA2.setChecked(false);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                l4.setBackgroundResource(0);
                tvVote4.setVisibility(View.GONE);
                radioButtonA4.setChecked(false);

                // Pirates are the best
                break;
            case R.id.radioButtonA4:
                if (checked)

                    l4.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote4.setVisibility(View.VISIBLE);

                l2.setBackgroundResource(0);
                tvVote2.setVisibility(View.GONE);
                radioButtonA2.setChecked(false);

                l3.setBackgroundResource(0);
                tvVote3.setVisibility(View.GONE);
                radioButtonA3.setChecked(false);

                l1.setBackgroundResource(0);
                tvVote1.setVisibility(View.GONE);
                radioButtonA1.setChecked(false);

                // Ninjas rule
                break;
        }
    }

    public void onTextViewClicked(View view) {

    }

    public void onRadioButtonClicked2(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButtonB1:
                if (checked)
                    l5.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                    tvVote5.setVisibility(View.VISIBLE);

                    l6.setBackgroundResource(0);
                    tvVote6.setVisibility(View.GONE);
                    radioButtonB2.setChecked(false);
                    l7.setBackgroundResource(0);
                    tvVote7.setVisibility(View.GONE);
                    radioButtonB3.setChecked(false);

                    l8.setBackgroundResource(0);
                    tvVote8.setVisibility(View.GONE);
                    radioButtonB4.setChecked(false);

                break;
            case R.id.radioButtonB2:
                if (checked)

                    l6.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote6.setVisibility(View.VISIBLE);

                l5.setBackgroundResource(0);
                tvVote5.setVisibility(View.GONE);
                radioButtonB1.setChecked(false);

                l7.setBackgroundResource(0);
                tvVote7.setVisibility(View.GONE);
                radioButtonB3.setChecked(false);

                l8.setBackgroundResource(0);
                tvVote8.setVisibility(View.GONE);
                radioButtonB4.setChecked(false);

                // Ninjas rule
                break;

            case R.id.radioButtonB3:
                if (checked)
                    // Toast.makeText(getActivity(), "You clicked", Toast.LENGTH_SHORT).show();
                    l7.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote7.setVisibility(View.VISIBLE);

                l6.setBackgroundResource(0);
                tvVote6.setVisibility(View.GONE);
                radioButtonB2.setChecked(false);

                l5.setBackgroundResource(0);
                tvVote5.setVisibility(View.GONE);
                radioButtonB1.setChecked(false);

                l8.setBackgroundResource(0);
                tvVote8.setVisibility(View.GONE);
                radioButtonB4.setChecked(false);

                // Pirates are the best
                break;
            case R.id.radioButtonB4:
                if (checked)

                    l8.setBackgroundResource(R.drawable.bg_buttonlightgreen);
                tvVote8.setVisibility(View.VISIBLE);

                l6.setBackgroundResource(0);
                tvVote6.setVisibility(View.GONE);
                radioButtonB2.setChecked(false);

                l7.setBackgroundResource(0);
                tvVote7.setVisibility(View.GONE);
                radioButtonB3.setChecked(false);

                l5.setBackgroundResource(0);
                tvVote5.setVisibility(View.GONE);
                radioButtonB1.setChecked(false);

                // Ninjas rule
                break;
        }
    }



    public void onTextViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvVote1:
                l1.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote1.setText("Thanks for voting");
                break;
            case R.id.tvVote2:
                l2.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote2.setText("Thanks for voting");
                break;
            case R.id.tvVote3:
                l3.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote3.setText("Thanks for voting");
                break;
            case R.id.tvVote4:
                l4.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote4.setText("Thanks for voting");
                break;

        }
        radioButtonA1.setClickable(false);
        radioButtonA2.setClickable(false);
        radioButtonA3.setClickable(false);
        radioButtonA4.setClickable(false);

    }

    public void onTextViewClick2(View view) {
        switch (view.getId()) {
            case R.id.tvVote5:
                l5.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote5.setText("Thanks for voting");
                break;
            case R.id.tvVote6:
                l6.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote6.setText("Thanks for voting");
                break;
            case R.id.tvVote7:
                l7.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote7.setText("Thanks for voting");
                break;
            case R.id.tvVote8:
                l8.setBackgroundResource(R.drawable.bg_buttongreen);
                tvVote8.setText("Thanks for voting");
                break;

        }
        radioButtonB1.setClickable(false);
        radioButtonB2.setClickable(false);
        radioButtonB3.setClickable(false);
        radioButtonB4.setClickable(false);

    }


    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getActivity(), "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(getActivity(), "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {


                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        profileImage.setImageBitmap(selectedImage);

                       // Uri selectedImageURI = getImageUri(getContext(), selectedImage);
                      //Picasso.with(getActivity()).load(selectedImageURI).noPlaceholder().centerCrop().fit().into(profileImage);

                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                Picasso.with(getActivity()).load(selectedImage).noPlaceholder().centerCrop().fit()
                                        .into(profileImage);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

}
