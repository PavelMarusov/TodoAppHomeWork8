package com.example.todoapp.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.todoapp.models.Work;

@Database(entities = {Work.class},version = 1, exportSchema = false)
// в этй строчке мы указали что база данных будет работать с компонентом Work
public abstract class AppDataBase extends RoomDatabase {
    public abstract WorkDao workDao();
//     это метод обращается именно к этой таблице
}