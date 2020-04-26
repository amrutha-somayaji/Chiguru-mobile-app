package com.chiguruecospace.chiguru_mobile_app.ui.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.chiguruecospace.chiguru_mobile_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class accountdetailsActivity extends AppCompatActivity {

    private TextView username;
    private TextView email;
    private TextView userUID;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdetails);

        username = findViewById(R.id.accountdetailsname);
        email = findViewById(R.id.accountdetailsemail);
        userUID = findViewById(R.id.accountdetailsUID);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        final String userUIDtext = mAuth.getCurrentUser().getUid();

        db.collection("users").document(userUIDtext).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("accountdetails", "DocumentSnapshot data: " + document.getData());
                        final String usernametext = document.getString("Username");
                        final String emailtext = document.getString("Email");

                        username.setText(usernametext);
                        email.setText(emailtext);
                        userUID.setText(userUIDtext);

                    } else {
                        Log.d("accountdetails", "No such document");
                    }
                } else {
                    Log.d("accountdetails", "get failed with ", task.getException());
                }

            }
        });

    }
}
