package com.example.messagingapp;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class RegistrationActivity extends AppCompatActivity {

    private Button mRegistration;
    private EditText mEmail, mPassword, mName;

    //authentication system
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;
    //indicates when user logs in so we know to move on to mainactivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                //this listener is called anytime user logs in "or when state changed",premade function
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                //if user is null go to other page
                if(user != null){
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);//same if from SplashScreen
                    return;
                }
            }
        };
        //importing variables here
        mAuth = FirebaseAuth.getInstance();

        mRegistration = findViewById(R.id.registration);
        mName = findViewById(R.id.name);
        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        //dealing with registration itself
     mRegistration.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String name = mName.getText().toString();
            final String email = mEmail.getText().toString();
            final String password = mPassword.getText().toString();
            //adding the sign info passing email and password then add listener and get results
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Executor) getApplication(), new OnCompleteListener<AuthResult>() {
                //onComplete is from onCompleteListener function
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //notice to user if login not successful
                    if(!task.isSuccessful()){
                        Toast.makeText(getApplication(), "ERROR", Toast.LENGTH_SHORT).show(); //toast is message alert
                    } else{
                        //if registration successful we create child in database for user
                        String userId = mAuth.getCurrentUser().getUid();//getting id from Auth
                        //reference to get child we want
                        DatabaseReference currentUserDb = FirebaseDatabase.getInstance().getReference().child("users").child(userId);

                        Map userInfo = new HashMap<>();
                        userInfo.put("email",email);
                        userInfo.put("name",name);
                        userInfo.put("profileImageUrl","default");
                        //associating with database
                        currentUserDb.updateChildren(userInfo);
                    }
                }
            });
        }
    });
}
    //premade function
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener); //when activity starts we start listener
    }

    @Override
    protected void onStop() {
        super.onStop();
        //when activity stopped we must remove offstate listener
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }


}