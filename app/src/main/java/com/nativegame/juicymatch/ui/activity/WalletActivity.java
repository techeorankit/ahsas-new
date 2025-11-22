package com.nativegame.juicymatch.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.nativegame.juicymatch.databinding.ActivityMyBooingBinding;
import com.nativegame.juicymatch.ui.adapters.WalletHistoryAdapter;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.User;
import com.nativegame.juicymatch.ui.models.WalletHistoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {
    ActivityMyBooingBinding binding;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyBooingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderSlipActivity.class));
            }
        });
        binding.backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        user = new User(getApplicationContext());
        GridLayoutManager manager = new GridLayoutManager(this, 1);
        binding.recAddWallet.setLayoutManager(manager);
        GridLayoutManager manager1 = new GridLayoutManager(this, 1);
        binding.recWithdrawal.setLayoutManager(manager1);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    binding.recAddWallet.setVisibility(View.VISIBLE);
                    binding.recWithdrawal.setVisibility(View.GONE);


                }if(tab.getPosition()==1) {
                    binding.recAddWallet.setVisibility(View.GONE);
                    binding.recWithdrawal.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        walletHistory();
        walletAmt();
    }
    private void walletHistory() {
        Call<List<WalletHistoryModel>> call = apicontroller.getapi().walletUserHistory(user.getUserid());
        call.enqueue(new Callback<List<WalletHistoryModel>>() {
            @Override
            public void onResponse(Call<List<WalletHistoryModel>> call, Response<List<WalletHistoryModel>> response) {
                List<WalletHistoryModel> data = response.body();
                if (data.get(0).getMessage() == 1) {
                    WalletHistoryAdapter walletHistoryAdapter = new WalletHistoryAdapter(data);
                    binding.recAddWallet.setAdapter(walletHistoryAdapter);
//                    binding.recWithdrawal.setAdapter(walletHistoryAdapter);
                }
                if (data.get(0).getMessage() == 0) {

                }


            }

            @Override
            public void onFailure(Call<List<WalletHistoryModel>> call, Throwable t) {


            }
        });

    }
    private void walletAmt() {

        Call<List<LoginModels>> call = apicontroller.getapi().logIn(user.getUserphone());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();

                if (data.get(0).getMessage() !=null) {
                    if (data.get(0).getMessage().equals("1")) {
                        binding.walletAmtTv.setText(data.get(0).getU_wallet());
//                    Toast.makeText(WalletTransection.this,data.get(0).getU_wallet() , Toast.LENGTH_SHORT).show();
                    }

                }

            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {


            }
        });

    }


}