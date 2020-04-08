package com.incture.taguig.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incture.taguig.R;
import com.incture.taguig.Register2Activity;

import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.ViewHolder> implements View.OnClickListener {

    /*public interface OnEventListener {
        void onClick(HospitalModel h1, int position);
    }
*/
    private final List<String> fileNameList;
    private final Context context;
  // private final OnEventListener eventListener;

    public FileAdapter(Context context, List<String> fileNameList) {
        this.fileNameList = fileNameList;
        this.context = context;

        // context => MovieListActivity -> OnEventListener
        // this.eventListener = (OnEventListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.file_item, null);
        return new ViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final String s1 = fileNameList.get(position);
        String[] splits= s1.split(".");

        String s2 =""+ s1.charAt(s1.length()-1)+s1.charAt(s1.length()-2)+s1.charAt(s1.length()-3);
        viewHolder.tvFileName.setText(s1);

        String s3="jgjgh";
        if(s2.equals("Pdf")){
             viewHolder.imgView.setImageResource(R.drawable.ic_insert_drive_file_black_24dp);
        }
       else if(s2.equals("mp4")){
            viewHolder.imgView.setImageResource(R.drawable.ic_videocam_black_24dp);
        }
       else
        {
            viewHolder.imgView.setImageResource(R.drawable.ic_insert_photo_black_24dp);

        }


        viewHolder.tvFileName.setText(s1);

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fileNameList.remove(position);
                notifyDataSetChanged();
                Register2Activity.noOfFile--;
            }
        });

    }




       @Override
    public int getItemCount() {
        return fileNameList.size();
    }

    public void updateList(List<String> newList){
        fileNameList.clear();
        fileNameList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
       /* switch (view.getId()){
            case R.id.rb1:
                onRadioButtonClicked(view);
                break;
        }*/
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFileName;
        ImageView btnDelete,imgView;



        LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout = (LinearLayout) itemView;

            tvFileName = layout.findViewById(R.id.tvFileName);
            btnDelete = layout.findViewById(R.id.btnDelete);
            imgView = layout.findViewById(R.id.imgView);
                    }

    }



}

