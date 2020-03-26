package com.example.todoapp.ui.notes;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.todoapp.R;

import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NotesFragment extends Fragment {

    EditText editText;
    private File file;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        File folder = new File(Environment.getExternalStorageDirectory(),"TodoApp");
        folder.mkdirs();// создает папку метод
        file = new File(folder,"note.txt");// записываем файл в папку
        try {
            String text =FileUtils.readFileToString(file,"utf-8");
            editText.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("pop","notefragment read");
    }


    @Override
    // вызываем в онПауз для того чтобы сохраняло даже ести пользователь смахнет приложение
    public void onPause() {
        save();//вызываем метод
        super.onPause();
        }

// второй метод записи данных implementation 'commons-io:commons-io:2.6' через библиотеку
    private void save() {
        String text = editText.getText().toString();// берем текст
        // создаем папку (обязательно потому что папку могут удалить и будет у нас ошибка)

        try {
            // FileUtils обязательно должен быть от апач
            FileUtils.writeStringToFile(file,text,"utf-8");// последний параметр это тип кода можно посмотреть в манифест файле
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(),"Saved",Toast.LENGTH_LONG).show();
    }
    public void read(){
        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard,"note.txt");
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
        }


        editText.setText(text.toString());
    }

}