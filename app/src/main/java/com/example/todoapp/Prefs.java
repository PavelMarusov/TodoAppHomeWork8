package com.example.todoapp;

import android.content.Context;
import android.content.SharedPreferences;

public class Prefs  {
    private SharedPreferences sharedPreferences;
    private  static volatile Prefs instance;
    public Prefs (Context context){
        instance =this;
        sharedPreferences = context.getSharedPreferences("settings",Context.MODE_PRIVATE);
    }
    public static Prefs getInstance(Context context){
        if (instance == null) new Prefs(context);
        return instance;
    }

    public boolean isShown(){
       return sharedPreferences.getBoolean("isShown",false);
    }
    public void saveShown(){
sharedPreferences.edit().putBoolean("isShown",true).apply();
    }
    public void deleteAll(){
        sharedPreferences.edit().clear().apply();
    }
}
