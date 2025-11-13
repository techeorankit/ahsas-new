package com.nativegame.juicymatch.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityBookingCategoryBinding;
import com.nativegame.juicymatch.ui.adapters.SubCategoryAdapter;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.ServicelistdataItem;
import com.nativegame.juicymatch.ui.models.Services;
import com.nativegame.juicymatch.ui.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingCategoryActivity extends AppCompatActivity {

    ActivityBookingCategoryBinding binding;
    User user;  KProgressHUD hud;

//hfcfydffy
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle(null);

        user = new User(this);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        processdata();

    }

    public void processdata() {

        hud = KProgressHUD.create(BookingCategoryActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();

        Call<JsonObject> call = apicontroller.getInstance().getapi().categoryFetch();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hud.dismiss();
                Gson gson = new Gson();
                Services services = gson.fromJson(response.body().toString(), Services.class);
                List<ServicelistdataItem> dataList = services.getServicelistdata();

                if (services.getResult().equalsIgnoreCase("true")) {


                    RecyclerView recyclerViewList;
                    if (dataList.size() > 0) {
                        binding.lvlItem.removeAllViews();
                        for (int i = 0; i < dataList.size(); i++) {
                            LayoutInflater inflater = LayoutInflater.from(BookingCategoryActivity.this);
                            View view = inflater.inflate(R.layout.itemcategorylinear, null);
                            TextView itemTitle = view.findViewById(R.id.itemTitle);
                            recyclerViewList = view.findViewById(R.id.recycler_view_list);
                            itemTitle.setText("" + dataList.get(i).getTitle());
                            SubCategoryAdapter itemAdp = new SubCategoryAdapter(dataList.get(i).getServiceList());
                            recyclerViewList.setLayoutManager(new GridLayoutManager(BookingCategoryActivity.this, 4));
                            recyclerViewList.setAdapter(itemAdp);
                            recyclerViewList.smoothScrollToPosition(i);
                            binding.lvlItem.addView(view);
                        }

                    } else {
                        Toast.makeText(BookingCategoryActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(BookingCategoryActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hud.dismiss();
                call.cancel();
                t.printStackTrace();
            }
        });


    }


}