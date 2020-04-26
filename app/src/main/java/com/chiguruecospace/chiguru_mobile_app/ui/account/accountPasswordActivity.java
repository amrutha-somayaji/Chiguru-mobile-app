package com.chiguruecospace.chiguru_mobile_app.ui.account;

import com.chiguruecospace.chiguru_mobile_app.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chiguruecospace.chiguru_mobile_app.MainActivity;
import com.chiguruecospace.chiguru_mobile_app.R;
import com.chiguruecospace.chiguru_mobile_app.loginactivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class accountPasswordActivity extends AppCompatActivity {

    EditText currpasswordtext;
    EditText newpasswordtext;
    EditText retypepassword;
    private Button changepasswordbtn;

    private String currpassword;
    private String newpassword;
    private String retypenewpassword;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_password);

        currpasswordtext = findViewById(R.id.currentpassword);
        newpasswordtext = findViewById(R.id.newpassword);
        retypepassword = findViewById(R.id.retypepassword);
        changepasswordbtn = findViewById(R.id.passwordchangebutton);

        changepasswordbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currpassword = currpasswordtext.getText().toString();
                newpassword = newpasswordtext.getText().toString();
                retypenewpassword = retypepassword.getText().toString();

                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                String useremail = user.getEmail();

                // Get auth credentials from the user for re-authentication. The example below shows
                // email and password credentials but there are multiple possible providers,
                // such as GoogleAuthProvider or FacebookAuthProvider.
                AuthCredential credential = EmailAuthProvider
                        .getCredential(useremail, currpassword);

                if(newpassword.equals(retypenewpassword)) {
                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newpassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("Password change", "Password updated");
                                                    mAuth.signOut();
                                                    Toast.makeText(accountPasswordActivity.this, "Password Updated! Kindly login again", Toast.LENGTH_LONG).show();
                                                    try {
                                                        startActivity(new Intent(accountPasswordActivity.this, loginactivity.class));
                                                        finish();
                                                    } catch (Exception e) {
                                                        Log.d("Password change", "Intent: " + e);
                                                    }

                                                } else {
                                                    Log.d("Password change", "Error password not updated");
                                                }
                                            }
                                        });
                                    } else {
                                        Log.d("Password change", "Error auth failed");
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(accountPasswordActivity.this, "" + e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(accountPasswordActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
