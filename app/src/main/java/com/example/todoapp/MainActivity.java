package com.example.todoapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.todoapp.ui.onBoard.OnBoardActivity;
import com.example.todoapp.ui.slideshow.SlideshowFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private final int RC_WRITE_EXTERNAL = 101;
    private File file;
    private File foldet;
    private EditText editText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        boolean isShow = Prefs.getInstance(this).isShown();
        if (!isShow) {
            startActivity(new Intent(this, OnBoardActivity.class));
            finish();
            return;
        }
        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            startActivity(new Intent(this,PhoneActivity.class));
            finish();
            return;
        }*/
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, 100);
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send,R.id.nav_firestore)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }
    public void onItemClick(View view0){
        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
    }

// создание файла и запись его в память способ 1 через метод
    @AfterPermissionGranted(RC_WRITE_EXTERNAL)// если файл создастся по новой запустит метот по реквест коду
    private void initFile(String content) {
        // разрешение пишем в манивесте
        String [] premision = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        // проверка
        if (EasyPermissions.hasPermissions(this, premision)) {
            // создаем файл
            file = new File(Environment.getExternalStorageDirectory(), "TodoApp");
            file.mkdirs();//обязательно написать этот метод он создаст папку в памяти
            foldet = new File(file, "note.txt");// в нашу папку помешаем файл
            try {
                foldet.createNewFile();// метод создает файл
                FileOutputStream fos = new FileOutputStream(foldet);// метод записывает в фаил инфу
                fos.write(content.getBytes());// через метод write() записываем данные,
                fos.close();// заканчивааем запись
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            EasyPermissions.requestPermissions(this, "", RC_WRITE_EXTERNAL, premision);
        }
    }

    @Override
    public void onBackPressed() {
        editText  = findViewById(R.id.ed_text);
        initFile(editText.getText().toString());
        super.onBackPressed();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        for(Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()){
            fragment.onActivityResult(requestCode,resultCode,data);
            Log.d("pop","OAR Main");
        }
        if (resultCode == RESULT_OK && requestCode == 100) {
            String text = data.getStringExtra("title");

        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Prefs.getInstance(this).deleteAll();
                finish();
            case R.id.text_size:
                Intent intent = new Intent(this, SizeActivity.class);
                startActivityForResult(intent,300);
            case R.id.sing_out:
                android.app.AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Выход");
                dialog.setMessage("Хотиде выйти?");
                dialog.setPositiveButton("Да", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this,PhoneActivity.class));
                        finish();

                    }
                });
                dialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialog.create().show();
            case R.id.uploads:
                startActivity(new Intent(MainActivity.this,UploadActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
