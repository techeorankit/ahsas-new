package com.nativegame.juicymatch.ui.adapters;

import static com.nativegame.juicymatch.ui.config.apicontroller.shop_logo_url;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.asset.Colors;
import com.nativegame.juicymatch.ui.activity.ChatRoomActivity;
import com.nativegame.juicymatch.ui.activity.voiceCallAgora.CallInviteActivity;
import com.nativegame.juicymatch.ui.activity.voiceCallAgora.GroupCallFetchActivity;
import com.nativegame.juicymatch.ui.models.DoctorDetailModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class CallFetchAdapter extends RecyclerView.Adapter<CallFetchAdapter.myViewHolder> {

    List<DoctorDetailModel> dataProduct;

    public CallFetchAdapter(List<DoctorDetailModel> dataProduct) {
        this.dataProduct = dataProduct;
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.call_item, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.name.setText(dataProduct.get(position).getName());


        holder.language3.setText(dataProduct.get(position).getLanguage3());
        try {
            // Input format (24-hour)
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            Date date = inputFormat.parse(dataProduct.get(position).getCreate_time());

            // Output format (12-hour with AM/PM)
            SimpleDateFormat outputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
            String time12 = outputFormat.format(date); // "03:43 PM"

            // Set text
            holder.datetime.setText(dataProduct.get(position).getCreate_date() + " - " + time12);

        } catch (Exception e) {
            e.printStackTrace();
            // fallback: show original
            holder.datetime.setText(dataProduct.get(position).getCreate_date() + " - " + dataProduct.get(position).getCreate_time());
        }
        holder.experienceV.setText("18-" + dataProduct.get(position).getAge() + " Years");
        holder.ratings.setText(dataProduct.get(position).getFollow() + "\nFollowers");

        if (dataProduct.get(position).getLanguage2().equals("1")) {
            holder.language3.setVisibility(View.GONE);
            holder.language2.setText("Received Call");

            holder.language2.setTextColor(Color.parseColor("#00D957")); // For red

            holder.language1.setText(dataProduct.get(position).getLanguage1());
        }else{
            holder.language2.setText("Missed Call");
            holder.language1.setVisibility(View.GONE);
            holder.language2.setTextColor(Color.parseColor("#FF0000")); // For red

            holder.language3.setVisibility(View.GONE);
        }


        Glide.with(holder.img.getContext())
                .load(shop_logo_url + dataProduct.get(position).getShop_logo()).placeholder(R.drawable.image).into(holder.img);


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(holder.name.getContext(), DetailActivity.class);
//                intent.putExtra("receiver_id", dataProduct.get(position).getSender_id());
//                intent.putExtra("name", dataProduct.get(position).getName());
//                intent.putExtra("language1", dataProduct.get(position).getLanguage1());
//                intent.putExtra("language2", dataProduct.get(position).getLanguage2());
//                intent.putExtra("language3", dataProduct.get(position).getLanguage3());
//                intent.putExtra("age", dataProduct.get(position).getAge());
//                intent.putExtra("follow", dataProduct.get(position).getFollow());
//                intent.putExtra("image", dataProduct.get(position).getShop_logo());
//                holder.name.getContext().startActivity(intent);
//
//            }
//        });

        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.name.getContext(), CallInviteActivity.class);
                intent.putExtra("receiver_id", dataProduct.get(position).getSender_id());
                intent.putExtra("image", dataProduct.get(position).getShop_logo());
                intent.putExtra("name", dataProduct.get(position).getName());


                holder.name.getContext().startActivity(intent);

            }
        });

        holder.groupCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.name.getContext(), GroupCallFetchActivity.class);
                intent.putExtra("receiver_id", dataProduct.get(position).getSender_id());
                intent.putExtra("image", dataProduct.get(position).getShop_logo());
                intent.putExtra("name", dataProduct.get(position).getName());

                holder.name.getContext().startActivity(intent);

            }
        });


        holder.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(holder.name.getContext(), ChatRoomActivity.class);
                intent.putExtra("receiver_id", dataProduct.get(position).getSender_id());
                intent.putExtra("name", dataProduct.get(position).getName());
                intent.putExtra("image", dataProduct.get(position).getShop_logo());

                holder.name.getContext().startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        if (dataProduct.size() > 0) {
            return dataProduct.size();
        } else {
            return 0;
        }

    }


    class myViewHolder extends RecyclerView.ViewHolder {
        TextView name, experienceV, language1, language2, language3, ratings, datetime, fees, city, address;
        CircleImageView img;
        LinearLayout call, chat,groupCall;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            datetime = itemView.findViewById(R.id.datetime);
            name = itemView.findViewById(R.id.name);
            img = itemView.findViewById(R.id.profile_image);
//            shopV = itemView.findViewById(R.id.expert);
            experienceV = itemView.findViewById(R.id.exp);
            language1 = itemView.findViewById(R.id.language1);
            language2 = itemView.findViewById(R.id.language2);
            language3 = itemView.findViewById(R.id.language3);
            ratings = itemView.findViewById(R.id.ratings);
//            totalProductTxt = itemView.findViewById(R.id.totalProductTxt);
//            booking = itemView.findViewById(R.id.booking);
//            fees = itemView.findViewById(R.id.fees);
//            city = itemView.findViewById(R.id.city);
//            address = itemView.findViewById(R.id.address);
            chat = itemView.findViewById(R.id.chat);
            call = itemView.findViewById(R.id.call);
            groupCall = itemView.findViewById(R.id.groupCall);


        }
    }


}
