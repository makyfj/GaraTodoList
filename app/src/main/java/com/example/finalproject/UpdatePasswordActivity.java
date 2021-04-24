package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    private EditText updatePasswordEditText;
    private Button updatePasswordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        updatePasswordEditText = findViewById(R.id.editTextUpdatePassword);
        updatePasswordButton = findViewById(R.id.buttonUpdatePassword);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = updatePasswordEditText.getText().toString();

                if(newPassword.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please enter new password", Toast.LENGTH_SHORT).show();
                }else{
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "User password updated", Toast.LENGTH_SHORT).show();
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Couldn't update password, try later", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                finish();
            }
        });
    }
}