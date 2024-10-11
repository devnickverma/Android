package com.example.cropdoc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log; // For logging
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class activity_check_diseases extends AppCompatActivity {

    private static final int PICK_IMAGES_REQUEST_CODE = 100; // Request code for permissions
    private LinearLayout selectedImagesLayout; // Layout for displaying selected images
    private final ArrayList<Uri> selectedImageUris = new ArrayList<>(); // List to hold selected image URIs
    private ActivityResultLauncher<Intent> imagePickerLauncher; // Launcher for picking images

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_diseases); // Ensure this matches your XML layout file

        Button uploadButton = findViewById(R.id.btn_upload);
        Button checkDiseasesButton = findViewById(R.id.btn_check_disease);
        selectedImagesLayout = findViewById(R.id.selected_images_layout);

        // Initialize the image picker launcher
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        if (data.getClipData() != null) {
                            // If multiple images are selected
                            int count = data.getClipData().getItemCount();
                            for (int i = 0; i < count; i++) {
                                Uri imageUri = data.getClipData().getItemAt(i).getUri();
                                selectedImageUris.add(imageUri);
                                addImageToLayout(imageUri);
                            }
                        } else if (data.getData() != null) {
                            // If only a single image is selected
                            Uri imageUri = data.getData();
                            selectedImageUris.add(imageUri);
                            addImageToLayout(imageUri);
                        }
                    }
                });

        // Upload images button
        uploadButton.setOnClickListener(v -> requestGalleryPermission());

        // Check diseases button
        checkDiseasesButton.setOnClickListener(v -> {
            if (selectedImageUris.isEmpty()) {
                Toast.makeText(activity_check_diseases.this, "Please upload images first!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(activity_check_diseases.this, activity_view_reports.class);
                intent.putParcelableArrayListExtra("image_paths", selectedImageUris);
                startActivity(intent);
            }
        });
    }

    private void requestGalleryPermission() {
        // Check for permission to read external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.d("CheckDiseases", "Requesting gallery permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGES_REQUEST_CODE);
        } else {
            Log.d("CheckDiseases", "Gallery permission already granted");
            openImagePicker(); // Permission is already granted
        }
    }

    private void openImagePicker() {
        // Open the image picker to select images
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        imagePickerLauncher.launch(intent);
    }

    private void addImageToLayout(Uri imageUri) {
        // Create ImageView for the selected image and add it to the layout
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(150, 150)); // Set image size
        imageView.setPadding(8, 8, 8, 8);
        imageView.setImageURI(imageUri); // Set the image URI

        // Add the ImageView to the layout
        selectedImagesLayout.addView(imageView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PICK_IMAGES_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("CheckDiseases", "Permission granted");
                openImagePicker();
            } else {
                Log.d("CheckDiseases", "Permission denied");
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
