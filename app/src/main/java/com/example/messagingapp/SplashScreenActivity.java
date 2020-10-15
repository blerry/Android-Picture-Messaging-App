package com.example.messagingapp;
//we made a drawable xml file and edited manifest xml
//enabled email/password from FireBase
//we import Firebase Libraries
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    public static Boolean started = false;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //onCreate is a premade function
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance(); //contains info associated with user database table id or email
        if(mAuth.getCurrentUser() != null){
            //if null no one is logged in. Intent to go to MainActivity
            Intent intent = new Intent(getApplication(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear up every activity from previous log in erased on top of activity
            startActivity(intent);
            return;
        }
        else{
            //if true then we go to choose log in
            Intent intent = new Intent(getApplication(), ChooseLoginRegistrationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//clear up every activity from previous log in erased on top of activity
            startActivity(intent);
            finish(); //in case not automatically erased
            return;
        }
    }
}
