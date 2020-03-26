package com.example.todoapp.ui.onBoard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.todoapp.MainActivity;
import com.example.todoapp.Prefs;
import com.example.todoapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    SharedPreferences preferences;
    Button btnSave;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSave = view.findViewById(R.id.btnStart);
        TextView textTitle = view.findViewById(R.id.textTitle);
        ImageView imageView = view.findViewById(R.id.imageView);
        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                textTitle.setText("Привет");
                imageView.setImageResource(R.drawable.im1);
                btnSave.setVisibility(View.INVISIBLE);
                break;
            case 1:
                textTitle.setText("Как дела?");
                imageView.setImageResource(R.drawable.im2);
                btnSave.setVisibility(View.INVISIBLE);
                break;
            case 2:
                textTitle.setText("Отлично");
                imageView.setImageResource(R.drawable.im3);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Prefs.getInstance(getContext()).saveShown();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();

                    }
                });
                break;
        }

    }
}
