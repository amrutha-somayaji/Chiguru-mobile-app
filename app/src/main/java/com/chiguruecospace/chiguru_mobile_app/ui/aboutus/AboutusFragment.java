package com.chiguruecospace.chiguru_mobile_app.ui.aboutus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chiguruecospace.chiguru_mobile_app.R;
import com.chiguruecospace.chiguru_mobile_app.ui.aboutus.AboutusModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import io.grpc.okhttp.internal.Platform;

public class AboutusFragment extends Fragment {

    private AboutusModel aboutusViewModel;
    private FirebaseFirestore db;

    private ImageView aboutuscoverimage;
    private TextView aboutusheader;
    private TextView aboutusdesc;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutusViewModel =
                ViewModelProviders.of(this).get(AboutusModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        db = FirebaseFirestore.getInstance();

        aboutuscoverimage = root.findViewById(R.id.aboutusimage);
        aboutusheader = root.findViewById(R.id.aboutusheadingtext);
        aboutusdesc = root.findViewById(R.id.aboutusdesctext);

        DocumentReference docRef = db.collection("chiguruinfo").document("aboutus");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Username", "DocumentSnapshot data: " + document.getData());
                        final String imagepath = document.getString("Imagepath");
                        final String title = document.getString("Title");
                        final String desc = document.getString("Description");

                        aboutusheader.setText(title);
                        aboutusdesc.setText(desc);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        // Create a reference to a file from a Google Cloud Storage URI
                        StorageReference gsReference = storage.getReferenceFromUrl(imagepath);

                        try {
                            final File file = File.createTempFile("image","jpg");
                            gsReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    aboutuscoverimage.setImageBitmap(bitmap);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Log.d("Username", "No such document");
                    }
                } else {
                    Log.d("Username", "get failed with ", task.getException());
                }
            }
        });
        return root;
    }
}