package com.example.emarket;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.emarket.Adapter.ProductSpecificationAdapter;
import com.example.emarket.Model.ProductSpecificationModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductSpecificationFragment extends Fragment {


    public ProductSpecificationFragment() {
        // Required empty public constructor
    }

    private RecyclerView productSpecificationRecyclerView;
    public List<ProductSpecificationModel> productSpecificationModelList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_specification, container, false);

        productSpecificationRecyclerView = view.findViewById(R.id.product_speecification_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);

        productSpecificationRecyclerView.setLayoutManager(linearLayoutManager);
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "General"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"RAM", "6GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Operating System", "Android v9.0 (Pie)"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Sim Slots", "Dual SIM, GSM+GSM"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Custom Ui", "Oxygen OS"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "Storage"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Internal Memory", "128GB"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Expendable Memory", "No"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "Camera"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Front Camera", "8 MP"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Rear Camera", "16 MP + 20 MP"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Camera Features", "Fixed Focus"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Image Resolution", "4616 X 3464 pixels"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Video Recording", "1920x1080 @ 30 fps, 1280x720 @ 30 fps"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "Performance"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Battery Size", "3700 mAh"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Processor", "Octa core (2.8 GHz, Quad core, Kryo 385 + 1.8 GHz, Quad core, Kryo 385"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Fingerprint Sensor Position", "On-screen"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Chipset", "Qualcomm Snapdragon 845"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Graphics", "Adreno 630"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Architecture", "64 bit"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "Display"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Display Type", "Optic AMOLED"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Screen Protection", "Corning Gorilla Glass v6"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Screen Size", "6.41 inches (16.28 cm)"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Screen Resolution", "1080 x 2280 pixels"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Touch Screen", "Yes Capacitive Touchscreen, Multi-touch"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Pixel Density", "394 ppi"));
//        productSpecificationModelList.add(new ProductSpecificationModel(0, "Network Connectivity"));
//        productSpecificationModelList.add(new ProductSpecificationModel(1,"Wifi", "Yes Wi-Fi 802.11, a/ac/b/g/n/n 5GHz, MIMO"));

        ProductSpecificationAdapter productSpecificationAdapter = new ProductSpecificationAdapter(productSpecificationModelList);
        productSpecificationRecyclerView.setAdapter(productSpecificationAdapter);
        productSpecificationAdapter.notifyDataSetChanged();

        return view;
    }

}
