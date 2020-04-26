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

public class loginactivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText loginemailtext;
    private EditText loginpasswordtext;
    private Button loginbtn;
    private TextView createaccount;
    private TextView forgotpassword;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        mAuth = FirebaseAuth.getInstance();

        loginemailtext = (EditText) findViewById(R.id.emailtext);
        loginpasswordtext = (EditText) findViewById(R.id.passtext);
        loginbtn = (Button) findViewById(R.id.logbutton);
        createaccount = (TextView) findViewById(R.id.textView7);
        forgotpassword = (TextView) findViewById(R.id.textView6);
        progressBar = (ProgressBar) findViewById(R.id.logprogressBar);

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this,registeractivity.class));
                finish();
            }
        });

        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivity.this,forgotpasswordActivity.class));
                finish();
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email  = loginemailtext.getText().toString();
                String password  = loginpasswordtext.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){

                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                startActivity(new Intent(loginactivity.this,MainActivity.class));
                                finish();

                            }else{

                                String e = task.getException().getMessage();
                                Toast.makeText(loginactivity.this, "Error: "+e, Toast.LENGTH_SHORT).show();

                            }

                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });

                }

            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            startActivity(new Intent(loginactivity.this,MainActivity.class));
            finish();
        }

    }

}

