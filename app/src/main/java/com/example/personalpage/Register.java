package com.example.personalpage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.personalpage.ui.login.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public FirebaseAuth firebaseAuth;
    public FirebaseUser firebaseUser;
    public Firebase firebase;
    public FirebaseDatabase firebaseDatabase;
    public FirebaseFirestore firebaseFirestore;
    public Button register;
    public String email,password,skills,phone,bio,gender,name,userID;
    public EditText username;
    public EditText userEmail;
    public EditText userPassword;
    public EditText userBio;
    public EditText userPhone;
    public EditText userSkills;
    public Uri userImage;
    public TextView gotoLogin;
    public RadioButton male,female;
    public ImageView profileImage;
    public ProgressBar progressBar;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        Binding
        register = findViewById(R.id.ButtonRegister);
        firebase = Firebase.INSTANCE;
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userPassword = findViewById(R.id.userPassword);
        userEmail = findViewById(R.id.userEmail);
        username = findViewById(R.id.username);
        profileImage = findViewById(R.id.userProfile);
        progressBar = findViewById(R.id.progress);
        userBio = findViewById(R.id.userBio);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        userPhone = findViewById(R.id.userPhone);
        userSkills = findViewById(R.id.userSkills);
//        ******
        userImage = imageUri;

        gotoLogin = findViewById(R.id.gotoLogin);


        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        register.setOnClickListener(view ->{
            email = String.valueOf(userEmail.getText());
            password = String.valueOf(userPassword.getText());
            skills = String.valueOf(userSkills.getText());
            phone = String.valueOf(userPhone.getText());
            bio = String.valueOf(userBio.getText());
            name = String.valueOf(username.getText());
            gender = "";


            if (email.equals("")){
                userEmail.setError("Email cannot be empty");
                userEmail.requestFocus();
            }
            if (password.equals("")){
                userPassword.setError("Email cannot be empty");
                userPassword.requestFocus();
            }
            if (male.isChecked()){
                gender = "Male";
            }
            if (female.isChecked()){
                gender = "Female";
            }

            progressBar.setVisibility(View.VISIBLE);
            register.setVisibility(View.INVISIBLE);

           firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if (task.isSuccessful()){
//                       Let us get user reference
//                       Profile picture is initially an empty string
        //               Every user is unique hence path must contain unique ID of each user
                       FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(username.getText().toString(),userEmail.getText().toString(),""));

//             We create a map
                       Map<String,Object> user = new HashMap<>();
                       user.put("Email",email);
                       user.put("Password",password);
                       user.put("Skills",skills);
                       user.put("Gender",gender);
                       user.put("Username",name);
                       user.put("Phone",phone);
                       user.put("Bio",bio);
                       user.put("Image",imageUri);

                       firebaseFirestore.collection("user")
                                       .add(user)
                                               .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                   @Override
                                                   public void onSuccess(DocumentReference documentReference) {
                                                       Toast.makeText(Register.this, "User successfully added to Database", Toast.LENGTH_SHORT).show();
                                                   }
                                               }).addOnFailureListener(new OnFailureListener() {
                                   @Override
                                   public void onFailure(@NonNull Exception e) {
                                       Toast.makeText(Register.this, "User could not be added to database", Toast.LENGTH_SHORT).show();
                                   }
                               });

                       Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
//                       *****
//                       userID = firebaseAuth.getCurrentUser().getUid();
////                       Collect data of the given user
//                       DocumentReference documentReference = firebaseFirestore.collection("users").document(userID);
////                       Listen to data changes
//                       documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                           @Override
//                           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                           }
//                       });
                       Intent intent = new Intent(getApplicationContext(), UserPage.class);
                       startActivity(intent);

                   }else{
                       Toast.makeText(Register.this, "Failed", Toast.LENGTH_SHORT).show();
                   }
               }
           });


        });

        gotoLogin.setOnClickListener(view ->{
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        });

       profileImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               openGallery();
           }
       });

    }
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
        }
    }
}