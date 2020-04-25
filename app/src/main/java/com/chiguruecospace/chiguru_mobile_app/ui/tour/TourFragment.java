package com.chiguruecospace.chiguru_mobile_app.ui.tour;

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

import com.chiguruecospace.chiguru_mobile_app.R;

public class TourFragment extends Fragment {

    private TourViewModel tourViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        tourViewModel =
                ViewModelProviders.of(this).get(TourViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tour, container, false);

        return root;
    }
}