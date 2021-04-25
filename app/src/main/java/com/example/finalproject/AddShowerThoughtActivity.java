package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class AddShowerThoughtActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText thoughtEditText;
    private RatingBar addRatingRatingBar;
    private Button addShowerThoughtButton;
    private ShowerThoughtListActivity list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shower_thought);

        db = FirebaseFirestore.getInstance();

        // get current user information
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize edit text and button
        thoughtEditText = findViewById(R.id.editTextAddThought);
        addRatingRatingBar = findViewById(R.id.ratingBarAddRating);
        addShowerThoughtButton = findViewById(R.id.buttonAddShowerThought);

        // Add shower thought
        addShowerThoughtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thought = thoughtEditText.getText().toString();
                float rating = addRatingRatingBar.getRating();

                // get current user information
                String userEmail = "";
                if(user != null){
                    userEmail = user.getEmail();
                }

                Map<String, Object> addShowerThoughtMap = new HashMap<>();
                addShowerThoughtMap.put("thought", thought);
                addShowerThoughtMap.put("username", userEmail);
                addShowerThoughtMap.put("rating", rating);

                String path = "ShowerThoughts";

                db.collection(path).document()
                        .set(addShowerThoughtMap, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Shower thought added!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(), "Failed to add shower thought",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                setResult(RESULT_OK);
                finish();
            }
        });

    }
}