package com.nativegame.juicymatch.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityCallStatusBinding;
import com.nativegame.juicymatch.databinding.ActivityMainBinding;
import com.nativegame.juicymatch.ui.adapters.CallFetchAdapter;
import com.nativegame.juicymatch.ui.adapters.DoctorFetchAdapter;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.DoctorDetailModel;
import com.nativegame.juicymatch.ui.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class CallStatusActivity extends AppCompatActivity {
    KProgressHUD hud;
    User user;
ActivityCallStatusBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_status);
        user = new User(getApplicationContext());
        binding = ActivityCallStatusBinding.inflate(getLayoutInflater());

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(CallStatusActivity.this,R.color.white));
        GridLayoutManager manager1 = new GridLayoutManager(this, 1);

        binding.recHomeAds1.setLayoutManager(manager1);


        process();
    }

    public void process() {
        hud = KProgressHUD.create(CallStatusActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();

        Call<List<DoctorDetailModel>> call = apicontroller.getapi().call_fetch(user.getSender_id());

        call.enqueue(new Callback<List<DoctorDetailModel>>() {
            @Override
            public void onResponse(Call<List<DoctorDetailModel>> call, retrofit2.Response<List<DoctorDetailModel>> response) {
                List<DoctorDetailModel> data = response.body();
                hud.dismiss();
                if (String.valueOf(response.body()).contains("null")) {
                    Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                } else {
                    Gson gson = new Gson();
                    String d= gson.toJson(data);
                    Log.d("dhytfdgfghfcgf",d);

                    CallFetchAdapter adsVendorProfileAdapter = new CallFetchAdapter(data);
                    binding.recHomeAds1.setAdapter(adsVendorProfileAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DoctorDetailModel>> call, Throwable t) {
                hud.dismiss();
            }
        });
    }



}