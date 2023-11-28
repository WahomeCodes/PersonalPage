package com.example.personalpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalpage.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class UserPage extends AppCompatActivity {
    // Declare UI elements
    public TextView name, phone, gender, bio, skills, textView, logOut;

//    Declare text collected
    public String user_name,user_phone,user_gender,user_bio,user_skills;

    // Firebase instances
    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    public FirebaseUser fUser;
    public String userID;
    public String x;
    private String loggedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        // Initialize UI elements
        textView = findViewById(R.id.seeMore);
        logOut = findViewById(R.id.logout);
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        gender = findViewById(R.id.gender);
        bio = findViewById(R.id.bio);
        skills = findViewById(R.id.skill);

        // Initialize Firebase instances
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        assert fUser != null;
        userID = fUser.getEmail(); // Get the user's UID
        x = String.valueOf(FirebaseAuth.getInstance().getCurrentUser());

        textView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), Users.class);
            startActivity(intent);
        });

        logOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish(); // Finish current activity
        });

//
//        firebaseFirestore.collection("users").document(loggedEmail).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()){
//                    DocumentSnapshot documentSnapshot = task.getResult();
//                    if (documentSnapshot != null && documentSnapshot.exists()){
//                        user_name = documentSnapshot.getString("Username");
//
//                        name.setText(user_name);
//                    }
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(UserPage.this, "Error", Toast.LENGTH_SHORT).show();
//                    }
//                });

    }
}
