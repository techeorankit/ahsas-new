package com.nativegame.juicymatch.ui.activity.voiceCallAgora;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.nativegame.juicymatch.ui.config.apicontroller.shop_logo_url;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityCallInviteBinding;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.User;
import com.zegocloud.uikit.ZegoUIKit;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.RoomStateChangedListener;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import im.zego.zegoexpress.constants.ZegoRoomStateChangedReason;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallInviteActivity extends AppCompatActivity {
    User user;
    ActivityCallInviteBinding binding;
    int time = 5;
    int coin = 0;
    private String receiver_id = "";
    private long callStartTime = 0;
    private long callEndTime = 0;
    private boolean isVideoCall = false; // Track call type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCallInviteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Log.d("receiver_id1122", receiver_id);
        user = new User(this);
        receiver_id = getIntent().getStringExtra("receiver_id");
        binding.name.setText(getIntent().getStringExtra("name"));
        binding.time.setText("( " + 5 + " Coins / mins )");
        Glide.with(getApplicationContext())
                .load(shop_logo_url + getIntent().getStringExtra("image")).placeholder(R.drawable.logo).into(binding.profileImage);
        Glide.with(getApplicationContext())
                .load(shop_logo_url + getIntent().getStringExtra("image")).placeholder(R.drawable.logo).into(binding.profileImage1);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initVoiceCallButton();
        initVideoCallButton();
        walletFetch();
        get_status();
    }

    private void get_status() {
        String url = apicontroller.url+"get_status.php?userid=" + user.getUserid();
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        String status = response.getString("video_status");
                        handleVideoStatus(status);
                    } catch (Exception e) {
                    }
                },
                error -> {
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void handleVideoStatus(String status) {
        if ("1".equals(status)) {
            binding.videocl.setVisibility(VISIBLE);
        } else {
            binding.videocl.setVisibility(GONE);

        }
    }


    @Override
    protected void onStart() {
        Log.d("receiver_id", receiver_id);
        super.onStart();
    }

    private void countdata() {
        new CountDownTimer(time * 60 * 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                binding.imageView5.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));
            }

            public void onFinish() {
                binding.imageView5.setText("END");
                ZegoUIKitPrebuiltCallService.unInit();
                finish();
            }
        }.start();
    }

    private void walletFetch() {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().logIn(user.getUserphone());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();

                if (data != null && !data.isEmpty()) {
                    String message = data.get(0).getMessage();

                    if (message.equals("1")) {

                        String walletStr = data.get(0).getU_wallet();

                        if (walletStr != null && !walletStr.isEmpty()) {
                            double walletValue = Double.parseDouble(walletStr);

                            if (walletValue > 4) {
                                // If coin must be int
                                coin = (int) walletValue;
                            } else {
                                Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    } else if (message.equals("0")) {
                        Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                finish();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void initVoiceCallButton() {
        ZegoSendCallInvitationButton voiceCallButton = findViewById(R.id.new_voice_call);
        voiceCallButton.setIsVideoCall(false); // Voice call
        try {
            hideZegoIcon(voiceCallButton);
        } catch (Exception e) {
            Log.e("VoiceCall", "Cannot modify Zego icon: " + e.getMessage());
        }
        voiceCallButton.setOnClickListener(v -> {
            Log.d("receiver_id", receiver_id);
            isVideoCall = false;
            if (coin <= 4) {
                Toast.makeText(getApplicationContext(), "Insufficient coins. Please recharge.", Toast.LENGTH_SHORT).show();
                return;
            }
            String targetUserID = receiver_id;
            String[] split = targetUserID.split(",");
            List<ZegoUIKitUser> users = new ArrayList<>();
            for (String userID : split) {
                String userName = userID + "_name";
                users.add(new ZegoUIKitUser(userID, userName));
            }
            voiceCallButton.setInvitees(users);
            timeDurationEvent();
            binding.callStatus.setText("Initiating voice call...");
            Log.d("CallType", "Voice Call initiated");
        });
    }
    private void hideZegoIcon(ZegoSendCallInvitationButton button) {
        try {
            Method setIconMethod = button.getClass().getMethod("setIcon", int.class);
            setIconMethod.invoke(button, android.R.color.transparent);
        } catch (Exception e) {
            Log.d("ZegoIcon", "setIcon method not available");
        }

        try {
            Method setIconTintMethod = button.getClass().getMethod("setIconTint", int.class);
            setIconTintMethod.invoke(button, Color.TRANSPARENT);
        } catch (Exception e) {
            Log.d("ZegoIcon", "setIconTint method not available");
        }
    }
    private void initVideoCallButton() {
        ZegoSendCallInvitationButton videoCallButton = findViewById(R.id.new_video_call);
        videoCallButton.setIsVideoCall(true);
        videoCallButton.setOnClickListener(v -> {
            Log.d("receiver_id", receiver_id);
            isVideoCall = true;
            String targetUserID = receiver_id;
            String[] split = targetUserID.split(",");
            List<ZegoUIKitUser> users = new ArrayList<>();
            for (String userID : split) {
                String userName = userID + "_name";
                users.add(new ZegoUIKitUser(userID, userName));
            }
            if (coin <= 4) {
                Toast.makeText(getApplicationContext(), "Insufficient coins. Please recharge.", Toast.LENGTH_SHORT).show();
                return;
            }
            videoCallButton.setInvitees(users);
            timeDurationEvent();
                Log.d("CallType", "Video Call initiated");
        });
    }

    private void callTransaction(String duration, String totalDuration) {

        // Call type information add करें
        String callType = isVideoCall ? "video" : "voice";

        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().callTransaction(
                user.getSender_id(),
                receiver_id,
                duration,
                totalDuration,callType
        );

        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(data);
                Log.d("CallEvent", json.toString());

                if (Integer.parseInt(data.get(0).getMessage()) == 1) {
                    Log.d("CallEvent", "Call transaction successful - " + callType + " call");
                }
                if (Integer.parseInt(data.get(0).getMessage()) == 0) {
                    Log.d("CallEvent", "Call transaction failed");
                }
            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void timeDurationEvent() {

        ZegoUIKit.addRoomStateChangedListener(new RoomStateChangedListener() {
            @Override
            public void onRoomStateChanged(String roomID,
                                           ZegoRoomStateChangedReason reason,
                                           int errorCode,
                                           JSONObject extendedData) {
                Log.d("CallEvent", "roomID: " + roomID);
                Log.d("CallEvent", "reason: " + reason.name());
                Log.d("CallEvent", "Call Type: " + (isVideoCall ? "Video" : "Voice"));

                switch (reason) {
                    case LOGINING:
                        Log.d("CallEvent", "Connecting to the room...");
                        break;

                    case LOGINED:
                        Log.d("CallEvent", "Connected to the room.");
                        callStartTime = System.currentTimeMillis();
                        break;

                    case LOGOUT:

                        Log.d("CallEvent", "Disconnected from the room.");
                        callEndTime = System.currentTimeMillis();

                        if (callStartTime != 0) {
                            long durationMillis = callEndTime - callStartTime;
                            long seconds = durationMillis / 1000;
                            String durationStr = formatDuration(seconds);
                            long minutes = (long) Math.ceil(seconds / 60.0);

                            Log.d("CallEvent", "Call duration: " + minutes);
                            Log.d("CallEvent", "Call type: " + (isVideoCall ? "Video" : "Voice"));

                            binding.imageView5.setText(durationStr + "\nCall Duration Time");
                            Toast.makeText(getApplicationContext(),
                                    "Call End",
                                    Toast.LENGTH_LONG).show();
                            callTransaction(String.valueOf(minutes), durationStr);
                            callStartTime = 0;
                            callEndTime = 0;
                        }
                        break;

                    default:
                        Log.d("CallEvent", "Other state: " + reason.name());
                        break;
                }
            }
        });
    }

    private String formatDuration(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}