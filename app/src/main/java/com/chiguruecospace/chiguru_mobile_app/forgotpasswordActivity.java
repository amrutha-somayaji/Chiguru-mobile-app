package com.chiguruecospace.chiguru_mobile_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText resetpassword;
    private Button resetpasswordbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        auth = FirebaseAuth.getInstance();
        resetpassword = findViewById(R.id.resetpassword);
        resetpasswordbutton = findViewById(R.id.resetpasswordbutton);

        resetpasswordbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String emailAddress = resetpassword.getText().toString();

                auth.sendPasswordResetEmail(emailAddress)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(forgotpasswordActivity.this, "Email sent!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(forgotpasswordActivity.this, loginactivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(forgotpasswordActivity.this, "An error occured. Please try again.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}
