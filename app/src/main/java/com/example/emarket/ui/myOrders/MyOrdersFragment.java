package com.example.emarket.ui.myOrders;

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

import com.example.emarket.Adapter.MyOrderAdapter;
import com.example.emarket.Model.MyOrderItemModel;
import com.example.emarket.R;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersFragment extends Fragment {

//    private GalleryViewModel galleryViewModel;

    public MyOrdersFragment() {
        //Required empty public constructor
    }

    private RecyclerView myOrderRecycerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
//        final TextView textView = view.findViewById(R.id.text_gallery);
//        galleryViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        myOrderRecycerView = view.findViewById(R.id.my_orders_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myOrderRecycerView.setLayoutManager(layoutManager);

        List<MyOrderItemModel> myOrderItemModelList = new ArrayList<>();
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobilephone,2, "Pixel 2XL Black", "Delivered on Mon, 1st June 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobilephone,0, "iPhone 11 pro max", "Delivered on Tomorrow 29th May 2020"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobilephone,4, "OnePlus 6T", "Cancelled"));
        myOrderItemModelList.add(new MyOrderItemModel(R.drawable.mobilephone,1, "Realme 7 pro", "Delivered on Sat, 31st May 2020"));

        MyOrderAdapter myOrderAdapter = new MyOrderAdapter(myOrderItemModelList);
        myOrderRecycerView.setAdapter(myOrderAdapter);
        myOrderAdapter.notifyDataSetChanged();

        return view;
    }
}