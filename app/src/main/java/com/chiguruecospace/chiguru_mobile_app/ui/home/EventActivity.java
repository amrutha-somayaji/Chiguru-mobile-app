package com.chiguruecospace.chiguru_mobile_app.ui.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.chiguruecospace.chiguru_mobile_app.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class EventActivity extends AppCompatActivity {

    private TextView desctext;
    private TextView titletext;
    private TextView datelabeltext;
    private TextView datetext;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        String descdata = getIntent().getStringExtra("descdata");
        String titledata = getIntent().getStringExtra("titledata");
        String imagedata = getIntent().getStringExtra("imagedata");

        desctext = findViewById(R.id.fulleventdesctext);
        titletext = findViewById(R.id.fulleventheadingtext);
        imageView = findViewById(R.id.fulleventimage);

        desctext.setText(descdata);
        titletext.setText(titledata);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        // Create a reference to a file from a Google Cloud Storage URI
        StorageReference gsReference = storage.getReferenceFromUrl(imagedata);

        try {
            final File file = File.createTempFile("image","jpg");
            gsReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
