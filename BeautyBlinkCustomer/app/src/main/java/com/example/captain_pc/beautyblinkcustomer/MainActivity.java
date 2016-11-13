package com.example.captain_pc.beautyblinkcustomer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import static android.R.attr.onClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private String displayname;
    private String personalEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if(mFirebaseUser == null){
            // Not signed in, launch the sign in activity.
            startActivity(new Intent(this, GoogleSignIn.class));
            return;
        }else {
            displayname = mFirebaseUser.getDisplayName();
            personalEmail = mFirebaseUser.getEmail();
        }

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(displayname);
        TextView email = (TextView) findViewById(R.id.email);
        email.setText(personalEmail);
    }



    private void signOut() {
        // Firebase sign out
        FirebaseAuth.getInstance().signOut();
        finish();
        startActivity(getIntent());
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_out_button:
                    signOut();
                    break;
            }
    }
}
