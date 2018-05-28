package com.hostelmanager.hosteladmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hostelmanager.hosteladmin.Model.HostelerInfo;

public class HostelLogin extends AppCompatActivity {

    private RelativeLayout rly;
    private TextView email,uid,pass;
    private Button login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_login);

        mAuth = FirebaseAuth.getInstance();

        rly = findViewById(R.id.pdsh);
        email = findViewById(R.id.hloginemail);
        uid = findViewById(R.id.hlluid);
        pass = findViewById(R.id.hlpass);
        login = findViewById(R.id.hloginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s1 = email.getText().toString().trim();
                String s2 = uid.getText().toString().trim();
                String s3 = pass.getText().toString().trim();

                if(s2.equals("")||s3.equals("")){
                    Toast.makeText(HostelLogin.this,"Please fill all details",Toast.LENGTH_SHORT).show();
                    return;
                }
                rly.setVisibility(View.VISIBLE);

                mAuth.signInWithEmailAndPassword(s2, s3).addOnCompleteListener(HostelLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("inside", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            rly.setVisibility(View.GONE);
                            finish();
                            startActivity(new Intent(HostelLogin.this,MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fail", "signInWithEmail:failure", task.getException());
                            Toast.makeText(HostelLogin.this, "SignIn failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}
