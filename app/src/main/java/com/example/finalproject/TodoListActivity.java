package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity {

    // Create members variables
    private List<ShowerThought> showerThoughtList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TodoAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTodo);
        adapter = new TodoAdapter(showerThoughtList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        // query
        queryDatabase();
    }

    // Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id){
            case R.id.addShowerThought:
                launchAddShowerThought();
                return true;
            case R.id.deleteUser:
                deleteUser();
                return true;
            case R.id.updatePassword:
                launchUpdatePassword();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchUpdatePassword() {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

    private void deleteUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        assert user != null;

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Account deleted", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(getApplicationContext(), "Couldn't delete account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void launchAddShowerThought() {
    }

    private void queryDatabase() {

        db.collection("ShowerThoughts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        ShowerThought st1;

                        String thought;
                        String username;
                        float rating;
                        long ratingLong;

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){

                                thought = document.getString("thought");
                                username = document.getString("username");
                                ratingLong = document.getLong("rating");

                                rating = (float)ratingLong;

                                st1 = new ShowerThought(thought, username, rating);

                                showerThoughtList.add(st1);
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}