package com.nativegame.juicymatch.ui.activity;


import static com.nativegame.juicymatch.ui.config.apicontroller.shop_logo_url;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityChatRoomBinding;
import com.nativegame.juicymatch.ui.adapters.ChatAdapter;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.MessageModel;
import com.nativegame.juicymatch.ui.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatRoomActivity extends AppCompatActivity {

    ActivityChatRoomBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    User user;
    //    String  a_id;
    String path;
int coin=0;
    String receiver_id, name, image;
    int time = 5;
    private Uri filePath;
    String recieverRoom;
    String senderRoom;
    String senderId;
    FirebaseStorage storage;
    StorageReference storageReference;
    private DatabaseReference userStatusRef, userOnlineRef;
    private TextView userStatusTextView;
//    Long timestamp = Long.valueOf("1717661350517");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//      getSupportActionBar().hide();
        user = new User(getApplicationContext());
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        senderId = user.getSender_id();
        receiver_id = getIntent().getStringExtra("receiver_id");
        name = getIntent().getStringExtra("name");
        image = getIntent().getStringExtra("image");
        time = getIntent().getIntExtra("time", 5);
        binding.time.setText("( " + 2 + " Coins / mins )");
        binding.name.setText(name);
        userStatusTextView = findViewById(R.id.online);
        Log.d("onlineOnline","Online"+receiver_id);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );

        Glide.with(getApplicationContext())
                .load(shop_logo_url + image).placeholder(R.drawable.logo).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final ArrayList<MessageModel> messageModels = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        binding.chatRecyclerView.setLayoutManager(layoutManager);


        senderRoom = senderId + receiver_id;
         recieverRoom = receiver_id + senderId;

        database.getReference().child("chats")
                .child(recieverRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messageModels.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            MessageModel model = snapshot1.getValue(MessageModel.class);
                            model.setMessageId(snapshot1.getKey());

                            messageModels.add(model);
                            final ChatAdapter chatAdapter = new ChatAdapter(messageModels, ChatRoomActivity.this, receiver_id);
                            binding.chatRecyclerView.setAdapter(chatAdapter);
                            chatAdapter.notifyDataSetChanged();
                        }
                        astroDataFetch();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        binding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!receiver_id.isEmpty()) {
                    if (binding.etMessage.getText().toString().isEmpty()) {
                        binding.etMessage.getError();
                        return;
                    }

                    if (!user.getSuscribe().equals("1") && !user.getGender().equals("Female")){
                        callTransaction();
                    }

//                    Toast.makeText(ChatRoomActivity.this, "getSuscribe"+user.getGender(), Toast.LENGTH_SHORT).show();


                    String message = binding.etMessage.getText().toString();
                    final MessageModel model = new MessageModel(senderId, message);
                    model.setIsImage(0);
                    model.setTimestamp(new Date().getTime());
                    binding.etMessage.setText("");

                    database.getReference().child("chats")
                            .child(recieverRoom)
                            .push()
                            .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    astroDataFetch();
                                    Log.d("hgvsdfgggh", "onClick: ");
                                database.getReference().child("chats")
                                        .child(senderRoom)
                                        .push()
                                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("hgvsdfgggh", "feailfbf: " + e.getLocalizedMessage());
                                }
                            });
                } else {
                    Toast.makeText(ChatRoomActivity.this, "Please reload the page , We don't find valid Registration ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
        OnlineStatus();
        countdata();
        walletFetch();
        displayUserStatus(receiver_id);


    }
    private void callTransaction() {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().chat_transaction(user.getSender_id(),receiver_id,"duration","totalDuration");
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                Gson gson = new Gson();
                String json = gson.toJson(data);
                Log.d("CallEvent",json.toString());
                if (Integer.parseInt(data.get(0).getMessage()) == 1) {
//                    user.setUserid(data.get(0).getU_id());
//                    binding.appBarMain.balance.setText(data.get(0).getU_wallet()+" Coins");
//                    Toast.makeText(getApplicationContext(), "Success .", Toast.LENGTH_SHORT).show();
                }
                if (Integer.parseInt(data.get(0).getMessage()) == 0) {
//                    Toast.makeText(getApplicationContext(), "Failed ! .", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                }
            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void walletFetch() {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().logIn(user.getUserphone());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                if (Integer.parseInt(data.get(0).getMessage()) == 1) {

                    if (!data.get(0).getU_wallet().isEmpty()){
                        double walletValue = Double.parseDouble(data.get(0).getU_wallet());

                        if (walletValue >= 3) {
                            // If coin must be int
                            coin = (int) walletValue;
                        } else {
                            Toast.makeText(getApplicationContext(), "Please add coins.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }else{
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


    private void displayUserStatus(String receiver_idnew) {
        Log.d("onlineOnline","bbbbOnline"+receiver_idnew);

        DatabaseReference userStatusDisplayRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(receiver_idnew)
                .child("online");

        userStatusDisplayRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean isOnline = snapshot.getValue(Boolean.class);
                    if (isOnline) {
                        Log.d("onlineOnline","Online"+receiver_idnew);
                        userStatusTextView.setText("Online");
                    } else {
                        userStatusTextView.setText("Offline");
                        Log.d("onlineOnline","Offline");
                    }
                } else {
                    Log.d("onlineOnline","Status Unknown");
                    userStatusTextView.setText("Status Unknown");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    private void OnlineStatus() {

        // Initialize Firebase references
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userStatusRef = database.getReference("users").child(user.getSender_id()).child("online");
        userOnlineRef = database.getReference(".info/connected");

        // Monitor connection status
        userOnlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    userStatusRef.setValue(true); // User is online
                    userStatusRef.onDisconnect().setValue(false); // Automatically set offline on disconnect
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }

    public void astroDataFetch() {

        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().user_time(receiver_id);
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();


            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
            }
        });


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
            }
        }.start();

    }

}