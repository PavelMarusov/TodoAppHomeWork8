package com.example.todoapp.ui.gallery;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.R;

public class RecyclerHolder extends RecyclerView.ViewHolder {
    TextView recTextView;
    public RecyclerHolder(@NonNull final View itemView) {
        super(itemView);
        recTextView = itemView.findViewById(R.id.recycler_textView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(),recTextView.getText().toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

}
