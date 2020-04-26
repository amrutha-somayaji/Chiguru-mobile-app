package com.chiguruecospace.chiguru_mobile_app.ui.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.chiguruecospace.chiguru_mobile_app.R;

public class AccountFragment extends Fragment {

    private AccountViewModel accountViewModel;
    private Button accountdetails;
    private Button accountusername;
    private Button accountpassword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_account, container, false);

        accountdetails = root.findViewById(R.id.accountsettings);
        accountusername = root.findViewById(R.id.changeusername);
        accountpassword = root.findViewById(R.id.changepassword);

        accountdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),accountdetailsActivity.class));
            }
        });

        accountusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),accountusernameActivity.class));
            }
        });

        accountpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),accountPasswordActivity.class));
            }
        });

        return root;
    }
}