package com.example.finalproject;

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
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "Example";
    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();

        registerButton = findViewById(R.id.buttonRegister);
        loginButton = findViewById(R.id.buttonLogin);


        // Notification
       // NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
       //         .setSmallIcon(R.drawable.ic_stat_name)
       //         .setContentTitle("New Notification")
       //         .setContentText("Here come the context :)))")
       //         .setPriority(NotificationCompat.PRIORITY_DEFAULT);

       // Intent intent = new Intent(this, MainActivity.class);
       // TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
       // stackBuilder.addParentStack(MainActivity.class);

       // stackBuilder.addNextIntent(intent);
       // PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
       // builder.setContentIntent(pendingIntent);

       // NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

       // notificationManager.notify(0, builder.build());

        // Redirect to register activity
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit intent
                Intent register = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });

        // Redirect to login activity
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Explicit intent
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
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