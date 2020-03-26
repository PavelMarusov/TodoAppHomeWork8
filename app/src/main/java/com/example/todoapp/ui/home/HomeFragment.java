package com.example.todoapp.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.App;
import com.example.todoapp.FormActivity;
import com.example.todoapp.IOnItemClick;
import com.example.todoapp.R;
import com.example.todoapp.models.Work;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment {
    private WorkAdapter adapter;
    private List<Work> list;
    private Work work;
    int position;
    public static int RESULT_CODE = 12;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        recyclerView.setAdapter(adapter = new WorkAdapter(list));
        App.getDataBase().workDao().getAll().observe(this, new Observer<List<Work>>() {
            @Override
            public void onChanged(List<Work> works) {
                list.clear();
                list.addAll(works);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setiOnItemClick(new IOnItemClick() {
            @Override
            public void onClic(int position) {
                HomeFragment.this.position = position;
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("key", list.get(position));
                startActivityForResult(intent, 200);
                Toast.makeText(getContext(), "Please update", Toast.LENGTH_SHORT).show();
                Log.d("pop","Fragment on setOIClick");


            }




        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            Work work = (Work) data.getSerializableExtra("work");
            App.getDataBase().workDao().delete(list.get(position));
            list.remove(position);
            list.add(position,work);
            App.getDataBase().workDao().update(list.get(position));
            Toast.makeText(getContext(), "It is updated", Toast.LENGTH_LONG).show();
            Log.d("pop","Fragment on Activity Result");
        }
    }
}