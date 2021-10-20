package com.example.emarket.ui.myRewards;

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

import com.example.emarket.Adapter.MyRewardsAdapter;
import com.example.emarket.Model.RewardModel;
import com.example.emarket.R;

import java.util.ArrayList;
import java.util.List;

public class MyRewardsFragment extends Fragment {
    public MyRewardsFragment() {
    }

    //    private SlideshowViewModel slideshowViewModel;

    private RecyclerView rewardsRecylerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        slideshowViewModel =
//                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        rewardsRecylerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecylerView.setLayoutManager(linearLayoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback", "till 2nd,Jun 2020", "Get 20% instant CASHBACK on any product above ₹ 500/- and below ₹ 2,500/-"));
        rewardModelList.add(new RewardModel("Discount", "till 31st,Jun 2020", "Get 10% instant DISCOUNT on any product above ₹ 1000/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "till 15th,Jun 2020", "Get 1 product free on shopping of any clothes above ₹ 500/-"));
        rewardModelList.add(new RewardModel("Reward", "till 31st,May 2020", "Get ₹ 2000/- OFF BIGBAZAAR coupon valid on shopping above ₹ 5000/-"));
        rewardModelList.add(new RewardModel("Cashback", "till 2nd,Jun 2020", "Get 20% instant CASHBACK on any product above ₹ 500/- and below ₹ 2,500/-"));
        rewardModelList.add(new RewardModel("Discount", "till 31st,Jun 2020", "Get 10% instant DISCOUNT on any product above ₹ 1000/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "till 15th,Jun 2020", "Get 1 product free on shopping of any clothes above ₹ 500/-"));
        rewardModelList.add(new RewardModel("Reward", "till 31st,May 2020", "Get ₹ 2000/- OFF BIGBAZAAR coupon valid on shopping above ₹ 5000/-"));
        rewardModelList.add(new RewardModel("Cashback", "till 2nd,Jun 2020", "Get 20% instant CASHBACK on any product above ₹ 500/- and below ₹ 2,500/-"));
        rewardModelList.add(new RewardModel("Discount", "till 31st,Jun 2020", "Get 10% instant DISCOUNT on any product above ₹ 1000/-"));
        rewardModelList.add(new RewardModel("Buy 1 Get 1 free", "till 15th,Jun 2020", "Get 1 product free on shopping of any clothes above ₹ 500/-"));
        rewardModelList.add(new RewardModel("Reward", "till 31st,May 2020", "Get ₹ 2000/- OFF BIGBAZAAR coupon valid on shopping above ₹ 5000/-"));

        MyRewardsAdapter myRewardsAdapter = new MyRewardsAdapter(rewardModelList, false);
        rewardsRecylerView.setAdapter(myRewardsAdapter);
        myRewardsAdapter.notifyDataSetChanged();

        return view;
    }
}