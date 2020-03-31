package com.chiguruecospace.chiguru_mobile_app;


import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ScannedItemDisplay extends AppCompatActivity {

    private static final String TAG = "ScannedItemDisplay";
    private static final String KEY_TITLE = "Name";
    private static final String KEY_DESCRIPTION = "Description";
    private TextView textViewData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String QRRESULT = Scanner.getQRdata();
    private String path = "items/"+QRRESULT;
    private DocumentReference noteRef = db.document(path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanned_item_display);
        textViewData = findViewById(R.id.textView2);
        loadNote();
    }

    public void loadNote() {
        noteRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String name = documentSnapshot.getString(KEY_TITLE);
                            String description = documentSnapshot.getString(KEY_DESCRIPTION);

                            //Map<String, Object> note = documentSnapshot.getData();

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
