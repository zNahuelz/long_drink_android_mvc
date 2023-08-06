package com.longdrink.androidapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.longdrink.androidapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.longdrink.androidapp.api_model.SQCurso;

import java.util.List;

public class AdmCoursesRVAdapter extends RecyclerView.Adapter<AdmCoursesRVAdapter.CourseHolder>{
    final String DEFAULT_COURSE_IMAGE = "https://i.imgur.com/PSYefjX.png";
    List<SQCurso> coursesList;
    Context context;

    public AdmCoursesRVAdapter(List<SQCurso> coursesList, Context context) {
        this.coursesList = coursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_admin_courses,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        SQCurso getC = coursesList.get(position);
        String active = "";
        if(getC.getActivo() == 1){ active = "VERDADERO"; } else{ active = "FALSO"; }

        holder.courseName.setText(getC.getNombre());
        holder.courseData.setText("ID: "+getC.getId_curso()+"\n"+"VISIBLE: "+active+"\n"+"COSTO: "+getC.getCosto()+" SOLES\n"+getC.getDuracion()+" Semanas");
        Glide.with(context).load(DEFAULT_COURSE_IMAGE).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseData;
        ImageView courseImage;
        Button courseEditButton, courseDeleteButton;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.admin_course_image);
            courseName = itemView.findViewById(R.id.admin_course_name);
            courseData = itemView.findViewById(R.id.admin_course_desc);
            courseEditButton = itemView.findViewById(R.id.admin_btn_cEditar);
            courseDeleteButton = itemView.findViewById(R.id.admin_btn_cEliminar);
        }
    }
}
