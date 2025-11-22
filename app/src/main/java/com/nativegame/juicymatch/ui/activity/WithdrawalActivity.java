package com.nativegame.juicymatch.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityWithdrawalBinding;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.MessageModels;
import com.nativegame.juicymatch.ui.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawalActivity extends AppCompatActivity {
ActivityWithdrawalBinding binding;
    TextInputEditText name, phone, email, city, address, pincode;
    TextView usename, submit;
    User user;
    LinearLayout linearLayout;
    String userid;
    ImageView backarrow;
    String path;
    MultipartBody.Part body;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWithdrawalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = new User(getApplicationContext());

        binding.myprofilesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .setAutoZoomEnabled(true)
////                        .setCropShape(CropImageView.CropShape.RECTANGLE)
////                        .setAspectRatio(160, 160)
//                        .start(WithdrawDetail.this);

            }
        });
        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });

        backarrow = findViewById(R.id.backarrow);
        name = findViewById(R.id.profilename);
        phone = findViewById(R.id.profilephone);
        email = findViewById(R.id.profileemail);
        city = findViewById(R.id.profilecity);
        linearLayout = findViewById(R.id.linearaa);
        address = findViewById(R.id.profileaddress);
        pincode = findViewById(R.id.profilepincode);
        usename = findViewById(R.id.usename);
        submit = findViewById(R.id.submit);
        userid = user.getUserid();
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateAccount();
            }
        });
    }
    private void updateAccount() {
        Call<List<MessageModels>> call = apicontroller.getInstance().getapi()
                .withdraw_bank_insert(user.getUserid(), name.getText().toString(), address.getText().toString()
                , email.getText().toString(), city.getText().toString(), pincode.getText().toString(), phone.getText().toString());
        call.enqueue(new Callback<List<MessageModels>>() {
            @Override
            public void onResponse(Call<List<MessageModels>> call, Response<List<MessageModels>> response) {
                List<MessageModels> data = response.body();

                if (data.get(0).getMessage().equals("1")) {
                    Toast.makeText(getApplicationContext(), "Request updated success.", Toast.LENGTH_SHORT).show();
                    finish();

                }

                if (data.get(0).getMessage().equals("2")) {
                    Toast.makeText(getApplicationContext(), "Insufficient Wallet Amount.", Toast.LENGTH_SHORT).show();
                }

                if (data.get(0).getMessage().equals("3")) {
                    Toast.makeText(getApplicationContext(), "Invalid User Detail.", Toast.LENGTH_SHORT).show();
                }

                if (Integer.parseInt(data.get(0).getMessage()) == 0) {

                    Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<MessageModels>> call, Throwable t) {
            }
        });
    }






}