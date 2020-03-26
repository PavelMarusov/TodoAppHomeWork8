package com.example.todoapp.ui.firebase;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.todoapp.FormActivity;
import com.example.todoapp.IOnItemClick;
import com.example.todoapp.R;
import com.example.todoapp.models.Work;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirebaseFragment extends Fragment {
    List<Work> works;
    RecyclerView recyclerView;
    FirebaseFirestore fb;
    FirebaseAdapter adapter;
    private Work work;
    DocumentSnapshot snapshot;
    int pos;
    public final static int RESULT_COD =111;

    public FirebaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firebase, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        work = (Work) getActivity().getIntent().getSerializableExtra("work");
        recyclerView = view.findViewById(R.id.firebase_rec);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        works = new ArrayList<>();
        adapter = new FirebaseAdapter(works);
        recyclerView.setAdapter(adapter);
        fb = FirebaseFirestore.getInstance();
        fb.collection("works").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {
                                Work work = d.toObject(Work.class);
                                works.add(work);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
        adapter.setiOnItemClick(new IOnItemClick() {
            @Override
            public void onClic(int position) {
                Log.d("pop","onClic FB");
                pos = position;
                Intent  intent = new Intent(getActivity(), FormActivity.class);
                intent.putExtra("work",works.get(position));
                startActivityForResult(intent,RESULT_COD);
            }

        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Log.d("pop","onActivityResult FB");
        if(resultCode==Activity.RESULT_OK&&resultCode==42){
            Work work = (Work) data.getSerializableExtra("fire");
            works.add(work);
        }
    }
}
