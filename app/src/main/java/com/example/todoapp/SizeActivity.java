package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.todoapp.ui.slideshow.SlideshowFragment;

public class SizeActivity extends AppCompatActivity {
    int size;
    RadioButton r14,r22,r28;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
       r14 = findViewById(R.id.RB_size_14);
       r22 = findViewById(R.id.RB_size_22);
       r28 = findViewById(R.id.RB_size_28);
       r14.setOnClickListener(listener);
       r22.setOnClickListener(listener);
       r28.setOnClickListener(listener);



    }



   View.OnClickListener listener =  new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           switch (v.getId()) {
               case R.id.RB_size_14:
                   size = 14;
                   break;
               case R.id.RB_size_22:
                   size = 22;
                   break;
               case R.id.RB_size_28:
                   size = 28;
                   break;

           }
           Intent intent = new Intent();
           intent.putExtra("size",size);
           setResult(RESULT_OK,intent);
           finish();
           Log.d("pop"," Size Activity  on checed");
       }
   };

}
