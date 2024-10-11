package com.example.cropdoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class activity_login extends AppCompatActivity {

    EditText edUsername, edPassword;
    Button btnLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUsername = findViewById(R.id.input_username);  // Assuming your EditText for username is named input_username
        edPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.txt_register);

        // Create an instance of the Database class
        Database db = new Database(this, "cropdoc", null, 1);

        // Handle login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edUsername.getText().toString();
                String password = edPassword.getText().toString();

                if (username.length() == 0 || password.length() == 0) {
                    Toast.makeText(activity_login.this, "Please fill in all details", Toast.LENGTH_SHORT).show();
                } else {
                    // Check login credentials using the Database class
                    int loginResult = db.login(username, password);
                    if (loginResult == 1) {
                        // Login successful
                        Toast.makeText(activity_login.this, "Login successful", Toast.LENGTH_SHORT).show();
                        // Redirect to home screen or another activity
                        startActivity(new Intent(activity_login.this, activity_home.class));
                    } else {
                        // Login failed
                        Toast.makeText(activity_login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Handle registration text click
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_login.this, activity_signup.class));
            }
        });
    }
}
