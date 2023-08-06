package com.longdrink.androidapp.adapter;

import android.content.Context;
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
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQAlumno;

import java.util.List;

public class AdmStudentsRVAdapter extends RecyclerView.Adapter<AdmStudentsRVAdapter.StudentHolder> {
    final String DEFAULT_STUDENT_IMAGE = "https://i.imgur.com/qyJ80RC.png";
    List<SQAlumno> studentsList;
    Context context;

    public AdmStudentsRVAdapter(List<SQAlumno> studentsList, Context context) {
        this.studentsList = studentsList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_admin_students,parent,false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdmStudentsRVAdapter.StudentHolder holder, int position) {
        SQAlumno getS = studentsList.get(position);
        String active = "";
        if(getS.getActivo() == 1){ active= "ACTIVO"; } else{ active = "INACTIVO"; }

        holder.studentName.setText(getS.getNombre()+"\n"+getS.getAp_paterno()+" "+getS.getAp_materno());
        holder.studentData.setText("ID: "+getS.getId_alumno()+"\n"+"DNI: "+getS.getDni()+"\n"+"E-MAIL: \n"+getS.getEmail()+"\nESTADO: "+active);
        Glide.with(context).load(DEFAULT_STUDENT_IMAGE).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.studentImage);
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class StudentHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentData;
        ImageView studentImage;
        Button studentEditButton, studentDeleteButton;
        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            studentImage = itemView.findViewById(R.id.admin_student_image);
            studentName = itemView.findViewById(R.id.admin_student_name);
            studentData = itemView.findViewById(R.id.admin_student_data);
            studentEditButton = itemView.findViewById(R.id.admin_btn_sEditar);
            studentDeleteButton = itemView.findViewById(R.id.admin_btn_sEliminar);
        }
    }
}
