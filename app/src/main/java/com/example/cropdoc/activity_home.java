package com.example.cropdoc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        Button checkDiseasesButton = findViewById(R.id.btn_check_diseases);
        Button viewReportsButton = findViewById(R.id.btn_view_reports);
        Button logoutButton = findViewById(R.id.btn_logout);

        // Check Diseases button action
        checkDiseasesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Check Diseases Activity
                startActivity(new Intent(activity_home.this, activity_check_diseases.class));
            }
        });

        // View Reports button action
        viewReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to View Reports Activity
                startActivity(new Intent(activity_home.this, activity_view_reports.class));
            }
        });

        // Logout button action
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout
                startActivity(new Intent(activity_home.this, MainActivity.class)); // Redirect to Login
                finish(); // Close the Home Activity
            }
        });
    }
}