package com.chiguruecospace.chiguru_mobile_app.ui.account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chiguruecospace.chiguru_mobile_app.MainActivity;
import com.chiguruecospace.chiguru_mobile_app.R;
import com.chiguruecospace.chiguru_mobile_app.User;
import com.chiguruecospace.chiguru_mobile_app.registeractivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class accountusernameActivity extends AppCompatActivity {

    private TextView currentusername;
    private EditText newusername;
    private Button changeusernamebtn;

    private String usernametext;
    private String emailtext;
    private String newusernametext;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountusername);

        currentusername = findViewById(R.id.currentusername);
        newusername = findViewById(R.id.newusername);
        changeusernamebtn = findViewById(R.id.changeusernamebutton);

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
                        usernametext = document.getString("Username");
                        emailtext = document.getString("Email");

                        currentusername.setText(usernametext);

                    } else {
                        Log.d("accountdetails", "No such document");
                    }
                } else {
                    Log.d("accountdetails", "get failed with ", task.getException());
                }

            }
        });

        changeusernamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newusernametext = newusername.getText().toString();

                if(!TextUtils.isEmpty(emailtext) && !TextUtils.isEmpty(newusernametext)){

                    User user = new User(newusernametext, emailtext);
                    final String userUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Map<String, Object> map = new HashMap<>();
                    map.put("Username", user.username);
                    map.put("Email", user.email);

                    db.collection("users").document(userUID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Toast.makeText(accountusernameActivity.this, "Username Updated!", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(accountusernameActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        }
                    });

                }

            }
        });

    }
}
