package com.longdrink.androidapp.adapter;

import android.content.Context;
import android.util.Log;
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
import com.longdrink.androidapp.api_model.FrontFrecuenciaTurno;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.api_model.SQFrecuencia;
import com.longdrink.androidapp.api_model.SQTurno;

import java.util.List;



public class TCoursesRVAdapter extends RecyclerView.Adapter<TCoursesRVAdapter.CourseHolder>{
    final String DEFAULT_COURSE_IMAGE = "https://i.imgur.com/PSYefjX.png";
    final String BASE_URL = "http://10.0.2.2:8080";
    List<SQCurso> coursesList;
    Context context;
    List<SQFrecuencia> freqList;
    List<SQTurno> turnList;


    public TCoursesRVAdapter(List<SQCurso> coursesList, Context context) {
        this.coursesList = coursesList;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_teacher_courses,parent,false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        SQCurso getC = coursesList.get(position);
        String active = "";
        if(getC.getActivo() == 1){ active = "VERDADERO"; } else{ active = "FALSO"; }
        holder.courseName.setText(getC.getNombre());
        holder.courseDesc.setText(getC.getDescripcion());
        Glide.with(context).load(getC.getFoto()).centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView courseName, courseDesc;
        ImageView courseImage;
        Button courseEditDetails;
        public CourseHolder(@NonNull View itemView) {
            super(itemView);
            courseImage = itemView.findViewById(R.id.teacher_course_image);
            courseName = itemView.findViewById(R.id.teacher_course_name);
            courseDesc = itemView.findViewById(R.id.teacher_course_desc);
            courseEditDetails = itemView.findViewById(R.id.teacher_btn_details);
        }
    }

}
