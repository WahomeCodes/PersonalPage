package com.example.personalpage.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personalpage.R;
import com.example.personalpage.UserPage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Login extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button login;
    private ProgressBar progressBar;
    private EditText userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.buttonLogin);
        progressBar = findViewById(R.id.progress_SignIn);
        userEmail = findViewById(R.id.userEmail); // Replace with your EditText IDs
        userPassword = findViewById(R.id.userPassword); // Replace with your EditText IDs

        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            String email = userEmail.getText().toString().trim();
            String password = userPassword.getText().toString().trim();

            if (email.isEmpty()) {
                userEmail.setError("Email cannot be empty");
                userEmail.requestFocus();
                return;
            }
            if (password.isEmpty()) {
                userPassword.setError("Password cannot be empty");
                userPassword.requestFocus();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);

            // Sign in with email and password
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI
                                Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Login.this, UserPage.class);
                                startActivity(intent);
                                finish(); // Finish current activity to prevent going back to login
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Login.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                login.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        });
    }
}
