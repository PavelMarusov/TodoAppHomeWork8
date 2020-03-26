package com.example.todoapp.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;

import java.util.ArrayList;

public class AdapterRecyclerHolder extends RecyclerView.Adapter<RecyclerHolder> {
    ArrayList<String> names;

    public AdapterRecyclerHolder() {
        names = new ArrayList<>();
        names.add("Кубат");
        names.add("Кундуз");
        names.add("Айгерим");
        names.add("Нуржамал");
        names.add("Нурсултан");
        names.add("Данияр");
        names.add("Павел");
    }

    @NonNull
    @Override
    public RecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recycler_holder, parent, false);
        return new RecyclerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerHolder holder, int position) {
        holder.recTextView.setText(names.get(position));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }
}
