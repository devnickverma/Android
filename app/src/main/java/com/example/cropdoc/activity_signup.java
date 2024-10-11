package com.example.cropdoc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class activity_signup extends AppCompatActivity {

    EditText edFullName, edEmail, edPassword, edConfirmPassword;
    Button btnSignup;
    TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize UI components
        edFullName = findViewById(R.id.Textinputfullname);
        edEmail = findViewById(R.id.input_email);
        edPassword = findViewById(R.id.input_password);
        edConfirmPassword = findViewById(R.id.input_confirm_password);
        btnSignup = findViewById(R.id.btn_signup);
        tvLogin = findViewById(R.id.txt_login);

        // Redirect to Login activity when clicking the login text
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity_signup.this, activity_login.class));
            }
        });

        // Sign Up button action
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edFullName.getText().toString().trim();
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPassword = edConfirmPassword.getText().toString().trim();

                Database db = new Database(getApplicationContext(), "cropdoc", null, 1);

                // Validate input fields
                if (username.isEmpty()) {
                    Toast.makeText(activity_signup.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty()) {
                    Toast.makeText(activity_signup.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else if (db.isEmailExists(email)) {
                    Toast.makeText(activity_signup.this, "Email is already registered", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(activity_signup.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(activity_signup.this, "Password must be at least 8 characters, include an uppercase letter, a number, and a special character", Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(activity_signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Proceed with registration
                    db.register(username, email, password);
                    Toast.makeText(activity_signup.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    // Redirect to home activity after successful signup
                    Intent intent = new Intent(activity_signup.this, activity_home.class);
                    startActivity(intent);
                }
            }
        });
    }

    // Function to validate the password
    private boolean isValidPassword(String password) {
        // Regex to check password strength (8 characters, at least 1 upper case, 1 digit, and 1 special character)
        Pattern PASSWORD_PATTERN = Pattern.compile(
                "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$"
        );
        return PASSWORD_PATTERN.matcher(password).matches();
    }
}
