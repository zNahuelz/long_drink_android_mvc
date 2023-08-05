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
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQCurso;
import com.longdrink.androidapp.fragments.CoursesFragment;

import java.util.List;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.CourseHolder> {

    List<SQCurso> listadoCursos;
    Context context;

    public CoursesRecyclerViewAdapter(List<SQCurso> listadoCursos, Context context) {
        this.listadoCursos = listadoCursos;
        this.context = context;
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_courses, parent, false);
        return new CourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int position) {
        holder.courseName.setText(listadoCursos.get(position).getNombre());
        holder.courseDescription.setText(listadoCursos.get(position).getDescripcion());
    }

    @Override
    public int getItemCount() {
        return listadoCursos.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        ImageView courseImage;

        TextView courseName, courseDescription;

        Button courseButtonDetails, courseButtonInscription;


        public CourseHolder(@NonNull View view) {
            super(view);
            courseImage = view.findViewById(R.id.course_image);
            courseName = view.findViewById(R.id.course_name);
            courseDescription = view.findViewById(R.id.course_description);
            courseButtonDetails = view.findViewById(R.id.course_button_details);
            courseButtonInscription = view.findViewById(R.id.course_button_inscription);
        }
    }
}
