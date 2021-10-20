package com.example.emarket.ui.myAccount;

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

import com.example.emarket.DeliveryActivity;
import com.example.emarket.MyAddressesActivity;
import com.example.emarket.R;

public class MyAccountFragment extends Fragment {
    public MyAccountFragment() {
    }

    private Button viewAllAddressBtm;
    public static final int MANAGE_ADDRESS = 1;

    //    private SendViewModel sendViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        sendViewModel =
//                ViewModelProviders.of(this).get(SendViewModel.class);
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
//        final TextView textView = root.findViewById(R.id.text_send);
//        sendViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        viewAllAddressBtm = view.findViewById(R.id.view_all_addresses_btn);
        viewAllAddressBtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MyAddressesActivity.class);
                intent.putExtra("MODE", MANAGE_ADDRESS);
                startActivity(intent);
            }
        });

        return view;
    }
}