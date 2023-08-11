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
import com.longdrink.androidapp.activities.CourseDescriptionActivity;
import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQCurso;

import java.util.List;

public class CoursesRecyclerViewAdapter extends RecyclerView.Adapter<CoursesRecyclerViewAdapter.CourseHolder> {

    //El listado de cursos que se obtiene del reponse.body
    List<SQCurso> listadoCursos;
    //Contexto en que se encuentra el adaptador
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
        //Agrega el método para que cada botón envíe la información del respectivo curso
        holder.courseButtonDetails.setOnClickListener(a -> SendData(position));
        Glide.with(holder.itemView)
                .load(listadoCursos.get(position).getFoto())
                .into(holder.courseImage);
    }

    @Override
    public int getItemCount() {
        return listadoCursos.size();
    }

    public class CourseHolder extends RecyclerView.ViewHolder{
        ImageView courseImage;

        TextView courseName, courseDescription;

        Button courseButtonDetails;


        public CourseHolder(@NonNull View view) {
            super(view);
            courseImage = view.findViewById(R.id.course_image);
            courseName = view.findViewById(R.id.course_name);
            courseDescription = view.findViewById(R.id.course_description);
            courseButtonDetails = view.findViewById(R.id.course_button_details);
        }
    }

    //Envia los datos a la nueva actividad y crea la nueva actividad
    public void SendData(int position){
        //Creando el intent para después iniciarlo como una actividad
        Intent intent = new Intent(this.context, CourseDescriptionActivity.class);
        //Envia el curso a la actividad por medio del putExtra
        intent.putExtra("curso",this.listadoCursos.get(position));
        this.context.startActivity(intent);
    }
}
