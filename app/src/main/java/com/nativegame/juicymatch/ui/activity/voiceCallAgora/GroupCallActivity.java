package com.nativegame.juicymatch.ui.activity.voiceCallAgora;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityGroupCallBinding;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.MessageModel;
import com.nativegame.juicymatch.ui.models.User;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallConfig;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupCallActivity extends AppCompatActivity {
    User user;
    private String receiver_id = "";
    ActivityGroupCallBinding binding;
    long appID = 301281619;
    ArrayList<MessageModel> messageModels;
    int coin = 0;
    String appSign = "18a9a4356a65adfe46b1a1e6274e3685630887cf4ce43009a485f69aea4bff5e";
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupCallBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        user = new User(this);
        receiver_id = getIntent().getStringExtra("receiver_id");
        database = FirebaseDatabase.getInstance();
        messageModels = new ArrayList<>();
        walletFetch();
        addFragment();
        liveGroupFetch();
    }
    private void walletFetch () {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().logIn(user.getUserphone());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                if (Integer.parseInt(data.get(0).getMessage()) == 1) {
                    if (!data.get(0).getU_wallet().isEmpty()) {
                        if (Integer.parseInt(data.get(0).getU_wallet()) > 4) {
                            coin = Integer.parseInt(data.get(0).getU_wallet());
                        } else {
                            Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                if (Integer.parseInt(data.get(0).getMessage()) == 0) {
                    Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                finish();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void liveGroupFetch() {
        database.getReference().child("group_call_live")
                .child(receiver_id)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) {
                            finish();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


    @Override
    protected void onStop() {
        super.onStop();
        disConnectGroup();

    }

    private void disConnectGroup() {
        database.getReference().child("group_call_live")
                .child(user.getSender_id())
                .removeValue();


    }


    public void addFragment() {

        String userID = user.getSender_id();
        String userName = user.getUsername();
        String callID = receiver_id;

        ZegoUIKitPrebuiltCallConfig config = ZegoUIKitPrebuiltCallConfig.groupVoiceCall();
        ZegoUIKitPrebuiltCallFragment fragment = ZegoUIKitPrebuiltCallFragment.newInstance(appID, appSign, userID,
                userName, callID, config);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commitNow();
    }

}