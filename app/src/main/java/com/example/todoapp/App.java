package com.example.todoapp;

import android.app.Application;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import com.example.todoapp.room.AppDataBase;
// нужно в манифестк указать имя для того чтобы он использовался
public class App extends MultiDexApplication {
    private static AppDataBase dataBase;
    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this,AppDataBase.class,"database")
                .allowMainThreadQueries()
                .build();// создание базы даных в интернал сторэч

    }

    public static AppDataBase getDataBase(){
        return dataBase;
    }
}
