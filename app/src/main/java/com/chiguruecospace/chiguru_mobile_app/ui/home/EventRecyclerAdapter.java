package com.chiguruecospace.chiguru_mobile_app.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.chiguruecospace.chiguru_mobile_app.MainActivity;
import com.chiguruecospace.chiguru_mobile_app.R;
import com.chiguruecospace.chiguru_mobile_app.ScannedItemDisplay;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {

    public List<HomeViewModel> eventlist;
    private Context mContext;

    public EventRecyclerAdapter(Context context, List<HomeViewModel> eventlist){

        this.eventlist = eventlist;
        this.mContext = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String descdata = eventlist.get(position).getDescription();
        final String titledata = eventlist.get(position).getTitle();
        final String imagedata = eventlist.get(position).getImagepath();

        holder.parentlayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(mContext, EventActivity.class);
                    intent.putExtra("descdata", descdata);
                    intent.putExtra("titledata", titledata);
                    intent.putExtra("imagedata", imagedata);
                    mContext.startActivity(intent);
                }catch (Exception e){

                    Log.println(1,"change",""+e);
                    Toast.makeText(mContext, "Error: "+e, Toast.LENGTH_SHORT).show();

                }
            }
        });

        holder.setDescText(descdata);
        holder.setTitleText(titledata);
        holder.setEventImage(imagedata);

    }

    @Override
    public int getItemCount() {
        return eventlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView descview;
        private TextView heading;
        private ImageView image;
        private View mView;
        ConstraintLayout parentlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
            parentlayout = itemView.findViewById(R.id.parentlayout);

        }

        public void setDescText(String text){

            descview = mView.findViewById(R.id.eventdesctext);
            descview.setText(text);

        }

        public void setTitleText(String text){

            heading = mView.findViewById(R.id.eventheadingtext);
            heading.setText(text);

        }

        public void setEventImage(String text){

            FirebaseStorage storage = FirebaseStorage.getInstance();
            // Create a reference to a file from a Google Cloud Storage URI
            StorageReference gsReference = storage.getReferenceFromUrl(text);

            image = (ImageView) mView.findViewById(R.id.eventimage);

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

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
