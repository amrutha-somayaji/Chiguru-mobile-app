package com.chiguruecospace.chiguru_mobile_app.ui.shop;

import android.os.Bundle;
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
import com.chiguruecospace.chiguru_mobile_app.ui.home.EventRecyclerAdapter;
import com.chiguruecospace.chiguru_mobile_app.ui.home.HomeViewModel;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ShopFragment extends Fragment {

    private RecyclerView shoplistview;
    private List<ShopViewModel> shoplist;

    private FirebaseFirestore firebasefirestore;
    private ShopitemRecyclerAdapter shopRecyclerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        shoplist = new ArrayList<>();
        shoplistview = root.findViewById(R.id.shopitemslist);

        shopRecyclerAdapter = new ShopitemRecyclerAdapter(this.getActivity(), shoplist);
        shoplistview.setLayoutManager(new LinearLayoutManager(getActivity()));
        shoplistview.setAdapter(shopRecyclerAdapter);

        firebasefirestore = FirebaseFirestore.getInstance();
        firebasefirestore.collection("shop").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange doc: documentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        ShopViewModel shopitem = doc.getDocument().toObject(ShopViewModel.class);
                        shoplist.add(shopitem);

                        shopRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });

        return root;
    }
}