package com.example.todoapp.ui.onBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.todoapp.MainActivity;
import com.example.todoapp.Prefs;
import com.example.todoapp.R;
import com.google.android.material.tabs.TabLayout;

public class OnBoardActivity extends AppCompatActivity {
  private  ViewPager  pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout =findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager,true);




    }




    public void onSkipClick(View view) {
        Prefs.getInstance(this).saveShown();
        startActivity(new Intent(OnBoardActivity.this,MainActivity.class));
        finish();
    }
}
