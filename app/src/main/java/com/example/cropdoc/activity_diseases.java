package com.example.cropdoc;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class activity_diseases extends AppCompatActivity {

    private TextView diseaseResultText;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_diseases);

        Button checkNowButton = findViewById(R.id.btn_check_now_);
        diseaseResultText = findViewById(R.id.txt_disease_results);

        // Action when Check Diseases button is clicked
        checkNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logic to check diseases
                checkForDiseases();
            }
        });
    }

    private void checkForDiseases() {
        // This is where you will add your logic to check diseases based on uploaded images
        // Placeholder logic
        diseaseResultText.setText("Diseases detected: None");
    }
}