package com.chiguruecospace.chiguru_mobile_app.ui.contactus;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chiguruecospace.chiguru_mobile_app.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ContactusFragment extends Fragment {

    private ContactusViewModel contactusViewModel;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    private ImageView contactuscoverimage;
    private TextView contactusheader;
    private TextView contactusdesc;
    private EditText contactusform;
    private Button contactussubmitbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        contactusViewModel =
                ViewModelProviders.of(this).get(ContactusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_contactus, container, false);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        contactuscoverimage = root.findViewById(R.id.contactusimage);
        contactusheader = root.findViewById(R.id.contactusheadingtext);
        contactusdesc = root.findViewById(R.id.contactusdesctext);
        contactusform = root.findViewById(R.id.contactusform);

        contactussubmitbtn = root.findViewById(R.id.contactussubmitbutton);

        DocumentReference docRef = db.collection("chiguruinfo").document("contactus");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("Contactus", "DocumentSnapshot data: " + document.getData());
                        final String imagepath = document.getString("Imagepath");
                        final String title = document.getString("Title");
                        final String desc = document.getString("Description");

                        contactusheader.setText(title);
                        contactusdesc.setText(desc);

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        // Create a reference to a file from a Google Cloud Storage URI
                        StorageReference gsReference = storage.getReferenceFromUrl(imagepath);

                        try {
                            final File file = File.createTempFile("image","jpg");
                            gsReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                    contactuscoverimage.setImageBitmap(bitmap);
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
                        Log.d("Contactus", "No such document");
                    }
                } else {
                    Log.d("Contactus", "get failed with ", task.getException());
                }
            }
        });

        contactussubmitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = contactusform.getText().toString();
                String userUID = mAuth.getCurrentUser().getUid();

                Map<String, Object> data = new HashMap<>();
                data.put("UserUID", userUID);
                data.put("Message", message);


                db.collection("messages")
                        .add(data)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("messagecontactus", "DocumentSnapshot written with ID: " + documentReference.getId());
                                Toast.makeText(getActivity(), "Message Sent!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("messagecontactus", "Error adding document", e);
                            }
                        });

            }
        });

        return root;
    }
}