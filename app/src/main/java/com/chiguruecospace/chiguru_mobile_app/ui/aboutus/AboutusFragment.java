package com.chiguruecospace.chiguru_mobile_app.ui.aboutus;

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
import com.chiguruecospace.chiguru_mobile_app.ui.aboutus.AboutusModel;

public class AboutusFragment extends Fragment {

    private AboutusModel aboutusViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        aboutusViewModel =
                ViewModelProviders.of(this).get(AboutusModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        return root;
    }
}