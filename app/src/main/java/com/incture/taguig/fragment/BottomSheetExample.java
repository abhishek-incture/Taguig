package com.incture.taguig.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.incture.taguig.R;

public class BottomSheetExample extends BottomSheetDialogFragment {
        private BottomSheetListener mListener;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.bottomsheet_dialog, container, false);

            Button btnBottomLogin = v.findViewById(R.id.btnBottomLogin);
            final EditText username= v.findViewById(R.id.username);
            final EditText etBottomPassword= v.findViewById(R.id.etBottomPassword);
            btnBottomLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(username.getText().toString().isEmpty()){
                        mListener.onButtonClicked("Pleaase Enter username");
                    }
                    else if(etBottomPassword.getText().toString().isEmpty())
                    {
                        mListener.onButtonClicked("Pleaase Enter password");

                    }
                    else {
                        mListener.onButtonClicked("true");
                        dismiss();
                    }
                }
            });
            ImageView btnDelete = v.findViewById(R.id.btnDelete);
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });


            return v;
        }

        public interface BottomSheetListener {
            void onButtonClicked(String text);
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);

            try {
                mListener = (BottomSheetListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString()
                        + " must implement BottomSheetListener");
            }
        }

}
