package com.longdrink.androidapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longdrink.androidapp.AdmStudentDeleteActivity;
import com.longdrink.androidapp.AdmTeacherDeleteActivity;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQProfesor;

import java.util.List;

public class AdmTeachersRVAdapter extends RecyclerView.Adapter<AdmTeachersRVAdapter.TeacherHolder> {
    final String DEFAULT_TEACHER_IMAGE = "https://i.imgur.com/DcFIx0D.png";
    List<SQProfesor> teachersList;
    Context context;

    public AdmTeachersRVAdapter(List<SQProfesor> teachersList, Context context) {
        this.teachersList = teachersList;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_admin_teachers,parent,false);
        return new TeacherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmTeachersRVAdapter.TeacherHolder holder, int position) {
        SQProfesor getT = teachersList.get(position);
        String active = "";
        if(getT.getActivo() == 1){ active= "ACTIVO"; } else{ active = "INACTIVO";}

        holder.teacherName.setText(getT.getNombre()+"\n"+getT.getAp_paterno()+" "+getT.getAp_materno());
        holder.teacherData.setText("ID: "+getT.getId_profesor()+"\nDNI: "+getT.getDni()+"\nE-MAIL: \n"+getT.getEmail()+"\nESTADO: "+active);

        String image = "";
        if(getT.getFoto().equals("NULL")){ image = DEFAULT_TEACHER_IMAGE; } else{ image = getT.getFoto(); }
        Glide.with(context).load(image).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.teacherImage);
        holder.teacherDeleteButton.setOnClickListener(e -> SendDataToDelete(position));
    }

    public void SendDataToDelete(int position){
        Intent intent = new Intent(this.context, AdmTeacherDeleteActivity.class);
        intent.putExtra("teacher_data",this.teachersList.get(position));
        this.context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return teachersList.size();
    }

    public class TeacherHolder extends RecyclerView.ViewHolder {
        TextView teacherName, teacherData;
        ImageView teacherImage;
        Button teacherEditButton, teacherDeleteButton;
        public TeacherHolder(@NonNull View itemView){
            super(itemView);
            teacherImage = itemView.findViewById(R.id.admin_teacher_image);
            teacherName = itemView.findViewById(R.id.admin_teacher_name);
            teacherData = itemView.findViewById(R.id.admin_teacher_data);
            teacherEditButton = itemView.findViewById(R.id.admin_btn_tEditar);
            teacherDeleteButton = itemView.findViewById(R.id.admin_btn_tEliminar);
        }
    }
}
