package com.chiguruecospace.chiguru_mobile_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registeractivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private EditText regemailtext;
    private EditText regpasswordtext;
    private EditText confirmpasswordtext;
    private Button registerbtn;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeractivity);


        mAuth = FirebaseAuth.getInstance();

        regemailtext = (EditText) findViewById(R.id.newemailtext);
        regpasswordtext = (EditText) findViewById(R.id.newpasstext);
        confirmpasswordtext = (EditText) findViewById(R.id.confirmpass);
        registerbtn = (Button) findViewById(R.id.regbutton);
        progressBar = (ProgressBar) findViewById(R.id.regprogressBar);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email  = regemailtext.getText().toString();
                String password  = regpasswordtext.getText().toString();
                String confirm = confirmpasswordtext.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirm)){

                    if(password.equals(confirm)){

                        progressBar.setVisibility(View.VISIBLE);

                        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                    startActivity(new Intent(registeractivity.this,MainActivity.class));
                                    finish();

                                }else{

                                    String e = task.getException().getMessage();
                                    Toast.makeText(registeractivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }else{

                        Toast.makeText(registeractivity.this, "Passwords don't match.", Toast.LENGTH_SHORT).show();

                    }

                    progressBar.setVisibility(View.INVISIBLE);
                }

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(registeractivity.this,MainActivity.class));
            finish();
        }

    }

}
