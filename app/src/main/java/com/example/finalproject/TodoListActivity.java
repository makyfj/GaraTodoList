package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    // Create members variables
    private List<Todo> todoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TodoAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTodo);
        adapter = new TodoAdapter(todoList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // query
        queryDatabase();
    }

    private void queryDatabase() {
        db.collection("Todo")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        Todo t1;

                        String taskTodo;
                        boolean checked;
                        long difficultyLong;
                        int difficultInt;

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){

                                taskTodo = document.getString("task");
                                checked = document.getBoolean("checked");
                                difficultyLong = document.getLong("difficulty");

                                difficultInt = (int)difficultyLong;

                                t1 = new Todo(taskTodo, checked, difficultInt);

                                todoList.add(t1);
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}