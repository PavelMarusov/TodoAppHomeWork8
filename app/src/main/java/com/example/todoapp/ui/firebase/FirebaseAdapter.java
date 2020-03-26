package com.example.todoapp.ui.firebase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.todoapp.FormActivity;
import com.example.todoapp.IOnItemClick;
import com.example.todoapp.R;
import com.example.todoapp.models.Work;
import com.example.todoapp.ui.home.WorkAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class FirebaseAdapter extends RecyclerView.Adapter<FirebaseAdapter.Holder> {
    List<Work> data;
    IOnItemClick iOnItemClick;
    DocumentSnapshot documentSnapshot;

    public FirebaseAdapter(List<Work> data) {
        this.data = data;
    }

    public void setiOnItemClick(IOnItemClick i) {
        this.iOnItemClick = i;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_farebase, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView titleText, descText;
        ImageView imageView;

        public Holder(@NonNull final View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.fb_title);
            descText = itemView.findViewById(R.id.fb_desc);
            imageView = itemView.findViewById(R.id.image2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnItemClick.onClic(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    TODO Нужно дожелать удаление
                    AlertDialog.Builder bilder = new AlertDialog.Builder(itemView.getContext());
                    bilder.setTitle("Удалить");
                    bilder.setMessage("Задича выполнена?");
                    bilder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseFirestore.getInstance().collection("works")
                                    .document().delete();
                        }

                    });
                    bilder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    bilder.create().show();
                    return false;
                }
            });
        }

        public void bind(Work work) {
            titleText.setText(work.getTitle());
            descText.setText(work.getDescription());
            Glide.with(itemView.getContext()).load(String.valueOf(work.getImage())).into(imageView);

        }


    }
}
