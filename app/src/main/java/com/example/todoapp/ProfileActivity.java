package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    ImageView imageView;
    EditText editname;
    Button button;
    FirebaseFirestore db;
    SharedPreferences preferences;
    FirebaseStorage storage;
    StorageReference storageRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageView = findViewById(R.id.profile_image);

        editname =findViewById(R.id.profile_editText);
        button = findViewById(R.id.prof_btn);
        preferences = getPreferences(MODE_PRIVATE);
        storage = FirebaseStorage.getInstance();
         storageRef = storage . getReference ();
        read();
        db =FirebaseFirestore.getInstance();
        String avatar = preferences.getString("ava", "");
        Glide.with(this).load(avatar).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,Object> name = new HashMap<>();
                name.put("name", editname.getText().toString());
                db.collection("user").document("users")
                        .set(name)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("pop", "Сохранено");
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("pop", "Не сохранено", e);
                            }
                        });

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 01);
                Log.d("pop", "PRofili clicked");


            }
        });
    }

    public void read() {
        FirebaseFirestore.getInstance().collection("user").document("users")
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    String name = documentSnapshot.getString("name");
                    editname.setText(name);

                }
            }
        });

    }
        @Override
        protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data){
            ProfileActivity.super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 01 && resultCode == RESULT_OK) {
                Log.d("pop", "PRofili onActivity result ");
                final Uri imageUri = data.getData();
                String avatar = imageUri.toString();
                preferences.edit().putString("ava", avatar).apply();
                Glide.with(this).load(avatar).into(imageView);

            }



        }


}

