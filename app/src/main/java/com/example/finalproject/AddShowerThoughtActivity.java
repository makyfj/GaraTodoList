package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
    private static final String CHANNEL_ID = "AddShowerThought";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shower_thought);

        db = FirebaseFirestore.getInstance();

        // notification
        createNotificationChannel();

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

                String title = "New shower thought added";

                db.collection(path).document()
                        .set(addShowerThoughtMap, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Shower thought added!", Toast.LENGTH_SHORT).show();
                                    notification(title, thought);
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

    private void notification(String title, String context){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(title)
                .setContentText(context)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(this, ShowerThoughtListActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ShowerThoughtListActivity.class);

        stackBuilder.addNextIntent(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.project_id);
            String description = getString(R.string.project_id);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}