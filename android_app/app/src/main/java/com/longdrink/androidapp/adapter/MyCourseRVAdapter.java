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

import com.longdrink.androidapp.R;
import com.longdrink.androidapp.api_model.SQTema;

import java.util.ArrayList;
import java.util.List;

public class MyCourseRVAdapter extends RecyclerView.Adapter<MyCourseRVAdapter.MyCourseHolder>{

    ArrayList<String> listadoNombresTemas;

    Context context;
    ArrayList<String> guias;

    public MyCourseRVAdapter(ArrayList<String> listadoNombresTemas, ArrayList<String> guias, Context context){
        this.listadoNombresTemas = listadoNombresTemas;
        this.context = context;
        this.guias = guias;
    }

    @NonNull
    @Override
    public MyCourseRVAdapter.MyCourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_my_course_classes, parent, false);
        return new MyCourseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCourseRVAdapter.MyCourseHolder holder, int position) {
        holder.myCourseName.setText(listadoNombresTemas.get(position));
    }

    @Override
    public int getItemCount() {
        return listadoNombresTemas.size();
    }

    public class MyCourseHolder extends RecyclerView.ViewHolder{
        TextView myCourseName;

        Button myCourseButton;


        public MyCourseHolder(@NonNull View view) {
            super(view);
            myCourseName = view.findViewById(R.id.class_name);
            myCourseButton = view.findViewById(R.id.class_button);
        }
    }
}
