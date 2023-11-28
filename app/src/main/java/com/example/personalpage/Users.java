package com.example.personalpage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Users extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public Firebase firebase;
    public FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

//        Binding
        firebase = Firebase.INSTANCE;
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        CollectionReference documentReference = firebaseFirestore.collection("users");
        String item;


    }
}