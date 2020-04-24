package com.chiguruecospace.chiguru_mobile_app.ui.home;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chiguruecospace.chiguru_mobile_app.R;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView eventslistview;
    private List<HomeViewModel> eventlist;

    private FirebaseFirestore firebasefirestore;
    private EventRecyclerAdapter eventRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        eventlist = new ArrayList<>();
        eventslistview = root.findViewById(R.id.eventslist);

        eventRecyclerAdapter = new EventRecyclerAdapter(eventlist);
        eventslistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        eventslistview.setAdapter(eventRecyclerAdapter);

        firebasefirestore = FirebaseFirestore.getInstance();
        firebasefirestore.collection("events").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        HomeViewModel event = doc.getDocument().toObject(HomeViewModel.class);
                        eventlist.add(event);

                        eventRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        return root;
    }
}