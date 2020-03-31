package com.chiguruecospace.chiguru_mobile_app;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class ScannedItemDisplay extends AppCompatActivity {

    private static final String TAG = "ScannedItemDisplay";
    private static final String KEY_TITLE = "Name";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_IMAGE_PATH = "Image-path";
    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String QRRESULT = Scanner.getQRdata();
    private String path = "items/"+QRRESULT;
    private DocumentReference noteRef = db.document(path);
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_item_display);
        textViewData = findViewById(R.id.textView2);
        loadNote();


    public void loadNote() {

        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);
                            String imagepath = documentSnapshot.getString(KEY_IMAGE_PATH);

                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            // Create a reference to a file from a Google Cloud Storage URI
                            StorageReference gsReference = storage.getReferenceFromUrl("gs://chiguru-mobile-app.appspot.com/items-pictures/"+imagepath);

                            image = (ImageView) findViewById(R.id.imageView2);

                            try {
                                final File file = File.createTempFile("image","jpg");
                                gsReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                        image.setImageBitmap(bitmap);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ScannedItemDisplay.this, "Image failed to load", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            String text = "Name: " + name + "\n" + "Description: " + description;
                            textViewData.setText(text);
                        } else {
                            Toast.makeText(ScannedItemDisplay.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ScannedItemDisplay.this, "Error!", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG, e.toString());
                    }
                });
    }

}
