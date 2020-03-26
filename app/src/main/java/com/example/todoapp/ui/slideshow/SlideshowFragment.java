package com.example.todoapp.ui.slideshow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SlideshowFragment extends Fragment {

EditText editText;
public static final int REQUEST_CODE = 1000;
public static final String EXTRA_CODE = "tyt";
int size;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.ed_text);
        readFile();



    }
   

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 300 && resultCode == Activity.RESULT_OK){
            size = data.getIntExtra("size",5);
            Log.d("pop","SlideShow OAR");
            editText.setTextSize(size);
        }

    }
    private void readFile() {
        StringBuilder sb = new StringBuilder();
        File folder = new File(Environment.getExternalStorageDirectory(), "TodoApp");
        folder.mkdirs();
       File file = new File(folder, "note.txt");

        try {
            FileInputStream fis = new FileInputStream(file);
            if (fis != null) {
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader buf = new BufferedReader(isr);
                String line = null;
                while ((line = buf.readLine()) != null) {
                    sb.append(line + "\n");
                }
                fis.close();
            }
            editText.setText(sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(),"Read",Toast.LENGTH_SHORT).show();
    }
}