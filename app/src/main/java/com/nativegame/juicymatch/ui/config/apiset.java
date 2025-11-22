package com.nativegame.juicymatch.ui.config;

import com.google.gson.JsonObject;
import com.nativegame.juicymatch.ui.models.DoctorDetailModel;
import com.nativegame.juicymatch.ui.models.JodiModel;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.MessageModels;
import com.nativegame.juicymatch.ui.models.PaymentModel;
import com.nativegame.juicymatch.ui.models.SliderModels;
import com.nativegame.juicymatch.ui.models.UpiModel;
import com.nativegame.juicymatch.ui.models.WalletHistoryModel;
import com.nativegame.juicymatch.ui.models.WinnerModel;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface apiset {

    @Multipart
    @POST("change_shop_logo.php")
    Call<List<MessageModels>> logoChange(
            @Part("u_id") RequestBody u_id,
            @Part MultipartBody.Part logo

    );

    @GET("winners_fetch.php")
    Call<List<WinnerModel>> winners_fetch();

    @Multipart
    @POST("payment_check.php")
    Call<List<MessageModels>> payment_upload(
            @Part("u_id") RequestBody u_id,
            @Part("desc") RequestBody desc,
            @Part("d_id") RequestBody type,
            @Part MultipartBody.Part logo

    );

    @GET("game_category.php")
    Call<List<GameCatModel>> game_category();

    @GET("live_result.php")
    Call<List<GameCatModel>> live_result();

    @GET("upi_fetch.php")
    Call<List<UpiModel>> upi_fetch();

    @FormUrlEncoded
    @POST("login.php")
    Call<List<LoginModels>> logIn(
            @Field("mobile") String mobile
    );


    @GET("call_transaction.php")
    Call<List<LoginModels>> callTransaction(
            @Query("sender_id") String sender_id,
            @Query("receiver_id") String receiver_id,
            @Query("duration") String duration,
            @Query("totalDuration") String totalDuration,
            @Query("callType") String callType
    );

    @GET("chat_transaction.php")
    Call<List<LoginModels>> chat_transaction(
            @Query("sender_id") String sender_id,
            @Query("receiver_id") String mobfdile,
            @Query("duration") String mofsbile,
            @Query("totalDuration") String totalDuration

    );

    @GET("user_wallet_history.php")
    Call<List<WalletHistoryModel>> walletUserHistory(
            @Query("u_id") String u_id
    );

    @FormUrlEncoded
    @POST("login_otp.php")
    Call<List<LoginModels>> otpSend(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("otp") String otp
    );

    @FormUrlEncoded
    @POST("result_history.php")
    Call<List<GameCatModel>> result(
            @Field("s_date") String s_date,
            @Field("e_date") String e_date
    );

    @FormUrlEncoded
    @POST("jodi_fetch.php")
    Call<List<JodiModel>> jodi_fetch(
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("date") String date
    );

    @FormUrlEncoded
    @POST("harup_fetch.php")
    Call<List<JodiModel>> harup_fetch(
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("date") String date
    );

    @GET("shop_d_data.php")
    Call<List<LoginModels>> vendorProfileOnly(
            @Query("shop_id") String shop_id,
            @Query("u_id") String u_id
    );

    @GET("category_fetch.php")
    Call<JsonObject> categoryFetch();

    @GET("time_slot_fetch.php")
    Call<JsonObject> time_slot_fetch();


    @GET("user_wallet_history.php")
    Call<List<GameCatModel>> user_wallet_history(

            @Query("u_id") String u_id
    );

    @GET("all_doctor_fetch.php")
    Call<List<DoctorDetailModel>> all_doctor_fetch(
            @Query("u_id") String u_id
    );

    @GET("user_time.php")
    Call<List<LoginModels>> user_time(

            @Query("sender_id") String u_id
    );

    @GET("my_doctor_booking.php")
    Call<List<DoctorDetailModel>> my_doctor_booking(

            @Query("u_id") String u_id

    );

    @GET("vendor_Profile_fetch.php")
    Call<List<LoginModels>> vendorProfile(
            @Query("u_id") String u_id
    );

    @FormUrlEncoded
    @POST("user_profile_update.php")
    Call<List<MessageModels>> user_profile_update(
            @Field("u_id") String u_id,
            @Field("name") String name,
            @Field("email") String email,
            @Field("city") String city,
            @Field("addreass") String addreass,
            @Field("pincode") String pincode

    );


    @FormUrlEncoded
    @POST("withdraw_bank_insert.php")
    Call<List<MessageModels>> withdraw_bank_insert(


            @Field("u_id") String u_id,
            @Field("name") String name,
            @Field("account") String email,
            @Field("ifsc") String city,
            @Field("bank") String addreass,
            @Field("amount") String pincode,
            @Field("upi") String mobile

    );

    @GET("banners.php")
    Call<List<SliderModels>> getSliderHome(
            @Query("city") String city,
            @Query("place_id") String place_id
    );

    @GET("banners_matka.php")
    Call<List<SliderModels>> banners_matka(
            @Query("city") String city,
            @Query("place_id") String place_id
    );

    @FormUrlEncoded
    @POST("register_device_id.php")
    Call<List<LoginModels>> otpToken(
            @Field("u_id") String name,
            @Field("device_id") String device_id
    );

    @FormUrlEncoded
    @POST("registration.php")
    Call<List<MessageModels>> signIn(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("city") String city,
            @Field("addreass") String addreass,
            @Field("device_id") String device_id,
            @Field("age") String age,
            @Field("country") String country,
            @Field("gender") String gender,
            @Field("language1") String language1,
            @Field("language2") String language2,
            @Field("language3") String language3


    );

    @FormUrlEncoded
    @POST("jodi_insert.php")
    Call<List<MessageModels>> jodi_insert(
            @Field("total_amount") String total_amount,
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("game_id") String game_id,
            @Field("s_time") String s_time,
            @Field("s_date") String s_date,
            @Field("e_time") String e_time,
            @Field("e_date") String e_date,
            @Field("0") String j0,
            @Field("1") String j1,
            @Field("2") String j2,
            @Field("3") String j3,
            @Field("4") String j4,
            @Field("5") String j5,
            @Field("6") String j6,
            @Field("7") String j7,
            @Field("8") String j8,
            @Field("9") String j5sfgdd,
            @Field("10") String jfsd,
            @Field("11") String jfdgds,
            @Field("12") String jgffdsh,
            @Field("13") String jfsfds,
            @Field("14") String jfdshdh,
            @Field("15") String jdhsfshs,
            @Field("16") String jhdshh,
            @Field("17") String jhsdshh,
            @Field("18") String jaa,
            @Field("19") String jhhdh,
            @Field("20") String jsnfdnd,
            @Field("21") String jfnds,
            @Field("22") String j45he,
            @Field("23") String jh55h,
            @Field("24") String j5hs5e5,
            @Field("25") String jhs55h,
            @Field("26") String jhs5e5,
            @Field("27") String jhshb,
            @Field("28") String jhs5,
            @Field("29") String jbss5,
            @Field("30") String jbsr55,
            @Field("31") String js3ebs,
            @Field("32") String jbse5,
            @Field("33") String jbdf,
            @Field("34") String jbdsdbfs,
            @Field("35") String jbfdr,
            @Field("36") String jfbbfrh,
            @Field("37") String jbbvtr,
            @Field("38") String jbbvtrgh,
            @Field("39") String jcvncvny6,
            @Field("40") String jhgnb6665,
            @Field("41") String jhbbg6,
            @Field("42") String jfgffg456,
            @Field("43") String jbfbf4e34,
            @Field("44") String jgg5454,
            @Field("45") String jhhg545,
            @Field("46") String jgg5445,
            @Field("47") String jdfbgf54,
            @Field("48") String jghm,
            @Field("49") String jm6m67m,
            @Field("50") String jm67767m,
            @Field("51") String jm67mmm,
            @Field("52") String jbvbvcv56,
            @Field("53") String jfddfg56,
            @Field("54") String jdbdf5,
            @Field("55") String jgdbb56,
            @Field("56") String jfdfd54,
            @Field("57") String jdfvbfv,
            @Field("58") String jxcb54,
            @Field("59") String jbfdv,
            @Field("60") String jfbgdcv,
            @Field("61") String jdbvgfv,
            @Field("62") String jbdvcvv,
            @Field("63") String jdfvcdfv,
            @Field("64") String jtynhbgfv,
            @Field("65") String jdfg,
            @Field("66") String jhnb,
            @Field("67") String jnhb,
            @Field("68") String jtnhb,
            @Field("69") String jtyngb,
            @Field("70") String jnhtb,
            @Field("71") String jntgb,
            @Field("72") String jgbfdv,
            @Field("73") String jytrgf,
            @Field("74") String jyhtrg,
            @Field("75") String jythgr,
            @Field("76") String jyngb,
            @Field("77") String jynbgr,
            @Field("78") String jkhbh,
            @Field("79") String jjgvygvc,
            @Field("80") String jjgy,
            @Field("81") String jjgvfgy,
            @Field("82") String jhgfcy,
            @Field("83") String jjgfvtycf,
            @Field("84") String jjuhgh,
            @Field("85") String jhgcgfyc,
            @Field("86") String jfdsdrs,
            @Field("87") String jjhtfytf,
            @Field("88") String jjhgvuvfu,
            @Field("89") String jhgfguf,
            @Field("90") String jjfggyfc,
            @Field("91") String jjuhgfuv,
            @Field("92") String jrewsaew,
            @Field("93") String jhighigih,
            @Field("94") String jihgihvih,
            @Field("95") String juhguygf,
            @Field("96") String jhjgyufy,
            @Field("97") String jiuhi,
            @Field("98") String jhgcfycx,
            @Field("99") String jugfgucu


    );

    @FormUrlEncoded
    @POST("harup_insert.php")
    Call<List<MessageModels>> harup_insert(
            @Field("total_amount") String total_amount,
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("game_id") String game_id,
            @Field("s_time") String s_time,
            @Field("s_date") String s_date,
            @Field("e_time") String e_time,
            @Field("e_date") String e_date,
            @Field("0") String j0,
            @Field("1") String j1,
            @Field("2") String j2,
            @Field("3") String j3,
            @Field("4") String j4,
            @Field("5") String j5,
            @Field("6") String j6,
            @Field("7") String j7,
            @Field("8") String j8,
            @Field("9") String j5sfgdd,
            @Field("b0") String jfsd,
            @Field("b1") String jfdgds,
            @Field("b2") String jgffdsh,
            @Field("b3") String jfsfds,
            @Field("b4") String jfdshdh,
            @Field("b5") String jdhsfshs,
            @Field("b6") String jhdshh,
            @Field("b7") String jhsdshh,
            @Field("b8") String jadffffffa,
            @Field("b9") String jaa


    );


    @FormUrlEncoded
    @POST("cross_insert.php")
    Call<List<MessageModels>> cross_insert(
            @Field("total_amount") String total_amount,
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("game_id") String game_id,
            @Field("s_time") String s_time,
            @Field("s_date") String s_date,
            @Field("e_time") String e_time,
            @Field("e_date") String e_date,
            @Field("data") String data,
            @Field("price") String price

    );

    @FormUrlEncoded
    @POST("number_insert.php")
    Call<List<MessageModels>> number_insert(
            @Field("total_amount") String total_amount,
            @Field("u_id") String u_id,
            @Field("gc_id") String gc_id,
            @Field("game_id") String game_id,
            @Field("s_time") String s_time,
            @Field("s_date") String s_date,
            @Field("e_time") String e_time,
            @Field("e_date") String e_date,
            @Field("data") String data

    );

    @FormUrlEncoded
    @POST("wallet_add.php")
    Call<List<MessageModels>> wallet_add(
            @Field("u_id") String u_id,
            @Field("amount") String gc_id,
            @Field("status") String game_id,
            @Field("type") String type


    );

    @FormUrlEncoded
    @POST("upi-gateway/index.php")
    Call<List<PaymentModel>> payment(
            @Field("u_id") String u_id,
            @Field("amount") String name


    );
    @FormUrlEncoded
    @POST("upi-gateway/createOrder.php")
    Call<List<PaymentModel>> createOrder(
            @Field("u_id") String u_id,
            @Field("amount") String name


    );

    @FormUrlEncoded
    @POST("upi-gateway/redirect_page.php")
    Call<List<MessageModels>> redirect_page(
            @Field("u_id") String u_id,
            @Field("amount") String gc_id,
            @Field("client_txn_id") String client_txn_id,
            @Field("txn_id") String type


    );

    @FormUrlEncoded
    @POST("upi-gateway/payment_confirm.php")
    Call<List<MessageModels>> payment_confirm(
            @Field("u_id") String u_id,
            @Field("amount") String gc_id,
            @Field("client_txn_id") String client_txn_id


    );


    @FormUrlEncoded
    @POST("login_password.php")
    Call<List<LoginModels>> login_password(
            @Field("u_id") String u_id,
            @Field("password") String pass
    );

    @FormUrlEncoded
    @POST("doctor_booking.php")
    Call<List<LoginModels>> doctor_booking(
            @Field("u_id") String u_id,
            @Field("d_id") String pass,
            @Field("date") String pasgs,
            @Field("time") String dfpasgs
    );

    @FormUrlEncoded
    @POST("insert_help.php")
    Call<List<SliderModels>> helpUs(
            @Field("u_id") String u_id,
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("message") String refer_id

    );

    @FormUrlEncoded
    @POST("update_password.php")
    Call<List<LoginModels>> update_password(
            @Field("u_id") String u_id,
            @Field("password") String password,
            @Field("mobile") String jhyg
    );

    @FormUrlEncoded
    @POST("withdraw_wallet.php")
    Call<List<MessageModels>> withdraw_wallet(
            @Field("u_id") String u_id,
            @Field("amount") String gc_id


    );

}
