package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

import static com.google.firebase.firestore.FieldValue.delete;

public class ShowerThoughtListActivity extends AppCompatActivity {

    // Create members variables
    private List<ShowerThought> showerThoughtList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ShowerThoughtAdapter adapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    // Textview Title
    private TextView titleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewTodo);
        adapter = new ShowerThoughtAdapter(showerThoughtList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Initialize Firebase Firestore
        db = FirebaseFirestore.getInstance();

        Context context;
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ShowerThoughtListActivity.this);

        String textSize = sp.getString("textSize", "19");

        Toast.makeText(ShowerThoughtListActivity.this, "Current text size: " + textSize, Toast.LENGTH_SHORT).show();

        titleTextView = findViewById(R.id.textViewTodoList);

        float textSizeFloat = Float.parseFloat(textSize);

        titleTextView.setTextSize(1, textSizeFloat);

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
            case R.id.filterByRating:
                launchFilterByRating();
                return true;
            case R.id.clearFilter:
                queryDatabase();
                return true;
            case R.id.deleteUser:
                deleteUser();
                return true;
            case R.id.updatePassword:
                launchUpdatePassword();
                return true;
            case R.id.userInfo:
                launchUserInfo();
                return true;
            case R.id.appInfo:
                launchAppInfo();
                return true;
            case R.id.settings:
                launchSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void launchSettings() {
        Intent i = new Intent(this, UserPreferenceActivity.class);
        startActivity(i);
    }

    private void launchAppInfo() {
        Intent intent = new Intent(this, AppInfoActivity.class);
        startActivity(intent);
    }

    private void launchUserInfo() {
        Intent intent = new Intent(this, UserInfoActivity.class);
        startActivity(intent);
    }

    private void launchAddShowerThought() {
        Intent intent = new Intent(this, AddShowerThoughtActivity.class);
        startActivityForResult(intent, 0);
    }

    private void launchFilterByRating() {
        Intent intent = new Intent(this, GetRatingActivity.class);
        startActivityForResult(intent, 1);
    }

    private void launchUpdatePassword() {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        startActivity(intent);
    }

    private void deleteUser() {

        Context context;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteAccount();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Your account wouldn't be deleted", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void deleteAccount() {

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Return addThoughtActivity
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                queryDatabase();
            }
        }

        // Return this activity code for GetRatingActivity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int rating = data.getIntExtra("result", 0);
                queryDatabase(rating);
            }

            if(resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Result failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void queryDatabase() {

        // Clear when new data is added
        showerThoughtList.clear();

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

    private void queryDatabase(int rating){

        showerThoughtList.clear();

        db.collection("ShowerThoughts")
                .whereGreaterThanOrEqualTo("rating", rating)
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

    @Override
    protected void onPause() {
        super.onPause();
        queryDatabase();
    }
}