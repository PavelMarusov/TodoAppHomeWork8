package com.example.todoapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todoapp.App;
import com.example.todoapp.FormActivity;
import com.example.todoapp.IOnItemClick;
import com.example.todoapp.MainActivity;
import com.example.todoapp.R;
import com.example.todoapp.models.Work;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.ViewHolder> {
    private List<Work> list;
    private IOnItemClick iOnItemClick;


    public WorkAdapter(List<Work> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_work, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public  void setiOnItemClick( IOnItemClick iOnItemClick){
        this.iOnItemClick = iOnItemClick;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageView;

        public ViewHolder(@NonNull final View itemView) {

            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            textTitle = itemView.findViewById(R.id.TextTitle);
            textDesc = itemView.findViewById(R.id.TextDesk);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    setAlertDialog(v,getAdapterPosition());
                    return false;
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnItemClick.onClic(getAdapterPosition());
                }
            });
        }


        public void bind(Work work) {
            textTitle.setText(work.getTitle());
            textDesc.setText(work.getDescription());
            Glide.with(itemView.getContext()).load(String.valueOf(work.getImage())).into(imageView);
        }

        public void setAlertDialog(View view, final int position) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getContext());
            dialog.setTitle("Удалить");
            dialog.setMessage("Хотиде удалить?");
            dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                 App.getDataBase().workDao().delete(list.get(position));

                }
            });
            dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            dialog.create().show();
        }

    }
}
