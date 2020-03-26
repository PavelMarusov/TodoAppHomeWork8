package com.example.todoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todoapp.models.Work;
import com.example.todoapp.ui.firebase.FirebaseFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FormActivity extends AppCompatActivity {

    /*FormActivity
    добавляем туда ImageView для выбора картины из галерии
    Нажимаем сохранить
    - загрузить картинки в Storage
    - получаете url картинки
    - сохраняете work String imageUrl
    - отправляете work в Firestore
    Показывать в FirestoreFragment*/


    private EditText editTitle;
    private EditText editDesc;
    private ImageView imageView;
    private Uri image_uri;
    private SharedPreferences.Editor editor;
    Work work = new Work();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        imageView = findViewById(R.id.img_fa);
        editTitle = findViewById(R.id.editTitle);
        editDesc = findViewById(R.id.editDesc);
        getIncomingIntent();
    }

    public void onClick(View view) {

        String text = editTitle.getText().toString().trim();
        String desk = editDesc.getText().toString().trim();
        Work work = new Work();
        work.setTitle(text);
        work.setDescription(desk);
        work.setImage(String.valueOf(image_uri));

        App.getDataBase().workDao().insert(work);// запись в базу данных
        saveInFirebase(work);
        Intent intent = new Intent();
        intent.putExtra("title", text);
        intent.putExtra("work", work);
        setResult(RESULT_OK, intent);
        Toast.makeText(this, "Task is saved", Toast.LENGTH_LONG).show();
        finish();
        Log.d("pop", "Form on Clic");

    }

    private void saveInFirebase(Work work) {
        FirebaseFirestore.getInstance().collection("works").add(work)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FormActivity.this, "Успешно FB", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void getIncomingIntent() {
        Log.d("pop", "getIncomingIntent");
        Intent intent = getIntent();
        work = (Work) intent.getSerializableExtra("key");
        work = (Work) intent.getSerializableExtra("work");
        if (work != null) {
            editTitle.setText(work.getTitle());
            editDesc.setText(work.getDescription());
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            image_uri = data.getData();
            upload_image(data.getData());
            Log.e("ololo", "onActivityResult: " + data.getData() );
            Glide.with(this).load(data.getData()).into(imageView);
        }
    }

    private void upload_image(final Uri data) {
        String userId = FirebaseAuth.getInstance().getUid();
        StorageReference storageReference =
                FirebaseStorage.getInstance().getReference().child("avatars/" + userId);
        final UploadTask task = storageReference.putFile(image_uri);

    }
}

