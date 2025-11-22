package com.nativegame.juicymatch.ui.activity;

import static com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE;
import static com.nativegame.juicymatch.ui.config.apicontroller.url;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityMainBinding;
import com.nativegame.juicymatch.ui.adapters.DoctorFetchAdapter;
import com.nativegame.juicymatch.ui.adapters.HomeSliderAdapter;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.DoctorDetailModel;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.SliderModels;
import com.nativegame.juicymatch.ui.models.User;
import com.smarteist.autoimageslider.SliderView;
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    NavigationView navigationView;
    View header;
    ActionBarDrawerToggle toggle;
    TextView nameFetch, mobileFetch;
    SliderView sliderView;
    User user;
    ImageView sharelogo;
    KProgressHUD hud;
    int MY_REQUEST_CODE = 9;
    AppUpdateManager appUpdateManager;
    int REQUEST_CODE_PERMISSION = 123;
    boolean doubleBackToExitPressedOnce = false;
    private DatabaseReference userStatusRef, userOnlineRef;
    long appID = 1072673437;
    String appSign = "7a25fd4c82dbc43b4449e6a43430b3e31629d43b0bc2f3a9dc1d95acf5d9df8c";
    private int currentStatus = 0;
    private ToggleButton toggleButton;
    private RequestQueue requestQueue;
    // Updated initCallInviteService method with proper notification config
    public void initCallInviteService(long appID, String appSign, String userID, String userName) {
        Log.d("CallEvent", "Initializing call service for: " + userID);
        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
//      callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.channelID = "ZegoUIKit_Call";
        notificationConfig.channelName = "Call Notifications";
        notificationConfig.sound = "default"; // Use default system sound
        callInvitationConfig.notificationConfig = notificationConfig;
        ZegoUIKitPrebuiltCallService.init(
                getApplication(),
                appID,
                appSign,
                userID,
                userName,
                callInvitationConfig
        );
        Log.d("CallEvent", "Call service initialized successfully for user: " + userName);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        getSupportActionBar().setTitle(null);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.white));
        checkInternet();
        updateApp();

        // Initialize user and call service
        user = new User(getApplicationContext());

        // Initialize Zego call service - ADD THIS LINE
        initCallInviteService(appID, appSign, user.getSender_id(), user.getUsername());

        binding.appBarMain.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), OrderSlipActivity.class));
            }
        });

        GridLayoutManager manager1 = new GridLayoutManager(this, 1);
        binding.appBarMain.recHomeAds1.setLayoutManager(manager1);

        bindingStart();
        sliderData();
        process();

        OnlineStatus();
        initializeToggleButton();
    }
    private void initializeToggleButton() {
        toggleButton = findViewById(R.id.toggleButton);
        requestQueue = Volley.newRequestQueue(this);
        getStatusFromAPI();
        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentStatus = isChecked ? 1 : 0;
            updateStatusToAPI(currentStatus);
            updateUI();
        });
    }
    private void getStatusFromAPI() {
        String url = apicontroller.url + "getStatus.php?user_id=" + user.getUserid();
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int status = jsonResponse.getInt("status");

                            currentStatus = status;
                            updateUI();

                        } catch (JSONException e) {
                            try {
                                currentStatus = Integer.parseInt(response.trim());
                                updateUI();
                            } catch (NumberFormatException ex) {
                                Toast.makeText(MainActivity.this, "Invalid response: " + response, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(stringRequest);
    }
    private void updateStatusToAPI(int status) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                apicontroller.url + "update_status.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Status updated to: " + currentStatus, Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        getStatusFromAPI();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("status", String.valueOf(status));
                params.put("user_id", user.getUserid());
                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void updateUI() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                toggleButton.setChecked(currentStatus == 1);

                // Get the background as a StateListDrawable (selector)
                StateListDrawable drawable = (StateListDrawable) toggleButton.getBackground();
                // Extract current drawable
                Drawable current = drawable.getCurrent();

                if (current instanceof GradientDrawable) {
                    GradientDrawable gradient = (GradientDrawable) current.mutate();
                    if (currentStatus == 1) {
                        gradient.setColor(ContextCompat.getColor(MainActivity.this, R.color.green));
                    } else {
                        gradient.setColor(ContextCompat.getColor(MainActivity.this, R.color.red));
                    }
                }
            }
        });
    }


    public void setStatus(int status) {
        this.currentStatus = status;
        updateStatusToAPI(status);
        updateUI();
    }
    @Override
    protected void onStart() {
        walletFetch();
        Log.d("receiver_id","receiver_id33");
        super.onStart();
    }

    public void process() {
        hud = KProgressHUD.create(MainActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();

        Call<List<DoctorDetailModel>> call = apicontroller.getapi().all_doctor_fetch(user.getUserid());

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

                    DoctorFetchAdapter adsVendorProfileAdapter = new DoctorFetchAdapter(data);
                    binding.appBarMain.recHomeAds1.setAdapter(adsVendorProfileAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<DoctorDetailModel>> call, Throwable t) {
                hud.dismiss();
            }
        });
    }

    private void walletFetch() {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().logIn(user.getUserphone());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, retrofit2.Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                if (Integer.parseInt(data.get(0).getMessage()) == 1) {
                    user.setUserid(data.get(0).getU_id());
                    binding.appBarMain.balance.setText(data.get(0).getU_wallet()+" Coins");
                }
                if (Integer.parseInt(data.get(0).getMessage()) == 0) {
                    Toast.makeText(getApplicationContext(), "Number Not Registered.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                }
            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void share() {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Title", null);
        Uri imageUri = Uri.parse(path);
        share.putExtra(Intent.EXTRA_TEXT, "Welcome To " + "Ahsas Chat" + "\n" + "Download App Now" + "\n" + "https://play.google.com/store/apps/details?id=" + getPackageName());
        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, "Send"));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                share();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }
    }

    private void updateApp() {
        appUpdateManager = AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE)) {
                try {
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, this, AppUpdateOptions.newBuilder(IMMEDIATE).setAllowAssetPackDeletion(true).build(), MY_REQUEST_CODE);
                } catch (IntentSender.SendIntentException e) {
                }
            }
        });
    }

    public void checkInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            connected = true;
        } else {
            connected = false;
        }

        if (connected == false) {
            startActivity(new Intent(getApplicationContext(), CheckInternet.class));
            Toast.makeText(this, "Internet connection error !", Toast.LENGTH_SHORT).show();
        }
    }

    public void bindingStart() {
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        header = navigationView.getHeaderView(0);
        toggle = new ActionBarDrawerToggle(this, drawer, binding.appBarMain.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.homeActivity) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (id == R.id.passbook) {
                    // startActivity(new Intent(getApplicationContext(), Passbook.class));
                } else if (id == R.id.history) {
                    startActivity(new Intent(getApplicationContext(), History.class));

                } else if (id == R.id.Themes) {
                    Intent intent = new Intent(getApplicationContext(), WithdrawalActivity.class);
                    startActivity(intent);
                } else if (id == R.id.manage) {
                    // Intent intent = new Intent(getApplicationContext(), Wallet.class);
                    // intent.putExtra("type", "1");
                    // startActivity(intent);
                } else if (id == R.id.result) {
                    // startActivity(new Intent(getApplicationContext(), ResultChart.class));
                } else if (id == R.id.helpMain) {
                    startActivity(new Intent(getApplicationContext(), HelpUs.class));
                } else if (id == R.id.winners) {
                    // startActivity(new Intent(getApplicationContext(), Winners.class));
                } else if (id == R.id.gameRate) {
                    // startActivity(new Intent(getApplicationContext(), GameRate.class));
                } else if (id == R.id.howToPlay) {
                    // startActivity(new Intent(getApplicationContext(), HowToPlay.class));
                } else if (id == R.id.mybooking) {
                    startActivity(new Intent(getApplicationContext(), WalletActivity.class));
                } else if (id == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), MyProfile.class));
                } else if (id == R.id.logout) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Are You Sure ");
                    builder.setMessage("Do you want to Logout");
                    builder.setNegativeButton("No", (dialog, i) -> {
                        dialog.dismiss();
                    }).setPositiveButton("Yes", (dialog, i) -> {
                        User user = new User(MainActivity.this);
                        user.setFirstTimeLaunch(true);
                        // Uninitialize call service on logout
                        ZegoUIKitPrebuiltCallService.unInit();
                        startActivity(new Intent(MainActivity.this, LogIn.class));
                        finishAffinity();
                        dialog.dismiss();
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                } else if (id == R.id.feedback) {
                    Uri urlstr = Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName());
                    Intent urlintent = new Intent();
                    urlintent.setData(urlstr);
                    urlintent.setAction(Intent.ACTION_VIEW);
                    startActivity(urlintent);
                } else if (id == R.id.privacy) {
                    Uri urlstr = Uri.parse(url+"privacy.php");
                    Intent urlintent = new Intent();
                    urlintent.setData(urlstr);
                    urlintent.setAction(Intent.ACTION_VIEW);
                    startActivity(urlintent);
                } else if (id == R.id.shareapp) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
                    } else {
                        share();
                    }
                } else if (id == R.id.settings) {
                    startActivity(new Intent(getApplicationContext(), MyProfile.class));
                }
                return false;
            }
        });

        View headerview = navigationView.getHeaderView(0);
        nameFetch = headerview.findViewById(R.id.name);
        mobileFetch = headerview.findViewById(R.id.mobile);
        nameFetch.setText(user.getUsername());
        mobileFetch.setText(user.getUserphone());

        sliderView = findViewById(R.id.sliderHome);
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderView.setScrollTimeInSec(2);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
    }

    public void sliderData() {
        Call<List<SliderModels>> call = apicontroller.getInstance().getapi().banners_matka(user.getUsercity(), "0");
        call.enqueue(new Callback<List<SliderModels>>() {
            @Override
            public void onResponse(Call<List<SliderModels>> call, retrofit2.Response<List<SliderModels>> response) {
                List<SliderModels> data = response.body();
                if (data.get(0).getMessage() == 1) {
                    HomeSliderAdapter homeSliderAdapter1 = new HomeSliderAdapter(data);
                    sliderView.setSliderAdapter(homeSliderAdapter1);
                }
                if (data.get(0).getMessage() == 0) {
                }
            }
            @Override
            public void onFailure(Call<List<SliderModels>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE) {
            if (resultCode != RESULT_OK) {
            } else {
            }
        }
    }

    private void OnlineStatus() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userStatusRef = database.getReference("users").child(user.getSender_id()).child("online");
        userOnlineRef = database.getReference(".info/connected");
        userOnlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    userStatusRef.setValue(true);
                    userStatusRef.onDisconnect().setValue(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userStatusRef.setValue(true); // Set online when app is active
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userStatusRef.setValue(false); // Set offline when app goes to the background
        ZegoUIKitPrebuiltCallService.unInit();
        Log.d("receiver_id","receiver_id44");
    }
}