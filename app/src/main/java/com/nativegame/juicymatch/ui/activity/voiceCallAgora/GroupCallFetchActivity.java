package com.nativegame.juicymatch.ui.activity.voiceCallAgora;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nativegame.juicymatch.databinding.ActivityGroupCallFetchBinding;
import com.nativegame.juicymatch.ui.adapters.GroupFetchAdapter;
import com.nativegame.juicymatch.ui.models.MessageModel;
import com.nativegame.juicymatch.ui.models.User;

import java.util.ArrayList;
import java.util.Date;

public class GroupCallFetchActivity extends AppCompatActivity {
    FirebaseDatabase database;
    User user;
    String receiver_id;
    ActivityGroupCallFetchBinding binding;
    ArrayList<MessageModel> messageModels;
    GroupFetchAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupCallFetchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();
        user = new User(this);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE
        );
        receiver_id = getIntent().getStringExtra("receiver_id");

        binding.searchicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         messageModels = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recLiveGroup.setLayoutManager(layoutManager);

        binding.goLive.setOnClickListener(v -> {
            GoLiveGroup();
        });

   liveGroupFetch();

    }

    private void liveGroupFetch() {
        database.getReference().child("group_call_live")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        messageModels.clear();

                        if (snapshot.exists()){
                            binding.recLiveGroup.setVisibility(View.VISIBLE);
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                MessageModel model = snapshot1.getValue(MessageModel.class);
                                model.setMessageId(snapshot1.getKey());

                                messageModels.add(model);
                                chatAdapter = new GroupFetchAdapter(messageModels, GroupCallFetchActivity.this, receiver_id);
                                binding.recLiveGroup.setAdapter(chatAdapter);
                                chatAdapter.notifyDataSetChanged();

                            }

                        }else {
                            binding.recLiveGroup.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    private void GoLiveGroup() {
        if (!user.getSender_id().isEmpty()) {
            String message = user.getUsername();
            final MessageModel model = new MessageModel(user.getSender_id(), message);
            model.setIsImage(1);
            model.setMessageId(user.getSender_id());
            model.setTimestamp(new Date().getTime());

            database.getReference().child("group_call_live")
                    .child(user.getSender_id())
                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d("hgvsdfgggh", "onClick: ");

                            Intent intent = new Intent(getApplicationContext(), GroupCallActivity.class);
                            intent.putExtra("receiver_id", user.getSender_id());
                            startActivity(intent);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("hgvsdfgggh", "feailfbf: " + e.getLocalizedMessage());
                        }
                    });

        } else {
            Toast.makeText(GroupCallFetchActivity.this, "Please reload the page , We don't find valid Registration ID", Toast.LENGTH_SHORT).show();
        }


    }


}