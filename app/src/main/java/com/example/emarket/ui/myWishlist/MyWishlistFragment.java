package com.example.emarket.ui.myWishlist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emarket.DBqueries;
import com.example.emarket.Model.WishlistModel;
import com.example.emarket.ProductDetailsActivity;
import com.example.emarket.R;
import com.example.emarket.Adapter.WishlistAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyWishlistFragment extends Fragment {

    public MyWishlistFragment() {
        //Required empty public constructor
    }

    //    private ShareViewModel shareViewModel;
    private RecyclerView wishlistRecyclerview;
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        shareViewModel =
//                ViewModelProviders.of(this).get(ShareViewModel.class);
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);
//        final TextView textView = root.findViewById(R.id.text_share);
//        shareViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ///////Loading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.slider_background));
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();
        ///////Loading Dialog

        wishlistRecyclerview = view.findViewById(R.id.my_wishlist_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerview.setLayoutManager(linearLayoutManager);

        if (DBqueries.wishlistModelList.size() == 0) {
            DBqueries.wishList.clear();
            DBqueries.loadWishList(getContext(), loadingDialog, true);
        } else {
            loadingDialog.dismiss();
        }

        wishlistAdapter = new WishlistAdapter(DBqueries.wishlistModelList, true);
        wishlistRecyclerview.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();

        return view;
    }
}