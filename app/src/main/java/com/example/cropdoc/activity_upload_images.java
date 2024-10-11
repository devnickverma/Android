package com.example.cropdoc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class activity_upload_images extends AppCompatActivity {

    private static final int PICK_IMAGES = 1;
    private ArrayList<Uri> imageUris;
    private TextView fileNamesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_images);

        Button selectImagesButton = findViewById(R.id.btn_select_images);
        fileNamesText = findViewById(R.id.txt_file_names);
        imageUris = new ArrayList<>();

        // Action for selecting images
        selectImagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES && resultCode == RESULT_OK) {
            assert data != null;
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                if (count > 5) {
                    Toast.makeText(this, "Please select up to 5 images", Toast.LENGTH_SHORT).show();
                } else {
                    imageUris.clear();
                    StringBuilder fileNames = new StringBuilder();
                    for (int i = 0; i < count; i++) {
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);
                        fileNames.append(imageUri.getLastPathSegment()).append("\n");
                    }
                    fileNamesText.setText(fileNames.toString());
                }
            } else if (data.getData() != null) {
                imageUris.add(data.getData());
                fileNamesText.setText(data.getData().getLastPathSegment());
            }
        }
    }
}