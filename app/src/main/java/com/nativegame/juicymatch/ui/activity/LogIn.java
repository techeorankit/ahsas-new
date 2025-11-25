package com.nativegame.juicymatch.ui.activity;

import static com.nativegame.juicymatch.ui.config.apicontroller.url;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.nativegame.juicymatch.R;
import com.nativegame.juicymatch.databinding.ActivityLogInBinding;
import com.nativegame.juicymatch.ui.config.apicontroller;
import com.nativegame.juicymatch.ui.models.LoginModels;
import com.nativegame.juicymatch.ui.models.User;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity {

    ActivityLogInBinding binding;
    String MobilePattern = "[0-9]{10}";
    User user;
    String token;
    String otprandom;
    KProgressHUD hud;
    TextView login;
    ImageView selectsity;
    EditText city, referalEdt;
    TextView txtlogin;
    EditText mobile, password;
    EditText name;
    RecyclerView recyclerView;
    String n, c, m, p;
String language1 = "English", language2 = "", language3 = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        setSupportActionBar(binding.toolbar);
//        getSupportActionBar().setTitle(null);
//        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        login = findViewById(R.id.login);
        name = findViewById(R.id.ed_name);
        city = findViewById(R.id.city);
        mobile = findViewById(R.id.ed_mobile);
//        selectsity = findViewById(R.id.select_gender);
        password = findViewById(R.id.ed_passswordnew);
//        txtlogin = findViewById(R.id.txt_login);
        referalEdt = findViewById(R.id.ed_refercode);

        binding.term.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri urlstr = Uri.parse(url+"term.php");
                Intent urlintent = new Intent();
                urlintent.setData(urlstr);
                urlintent.setAction(Intent.ACTION_VIEW);
                startActivity(urlintent);
            }
        });
        binding.forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ChangePassword.class));
            }
        });

        CheckUserExistance();

//        binding.skipLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                user.setUserphone("1");
//                overridePendingTransition( 0, 0 );
//                finish();
//            }
//        });

        user = new User(this);
        binding.txtSregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtSlogin.setBackground(getResources().getDrawable(R.drawable.tab2));
                binding.txtSlogin.setTextColor(getResources().getColor(R.color.black));
                binding.txtSregister.setBackground(getResources().getDrawable(R.drawable.tab1));
                binding.txtSregister.setTextColor(getResources().getColor(R.color.white));
                binding.lvlLogin.setVisibility(View.GONE);
                binding.lvlRegister.setVisibility(View.VISIBLE);
            }
        });
        binding.txtSlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.txtSlogin.setBackground(getResources().getDrawable(R.drawable.tab1));
                binding.txtSlogin.setTextColor(getResources().getColor(R.color.white));
                binding.txtSregister.setBackground(getResources().getDrawable(R.drawable.tab2));
                binding.txtSregister.setTextColor(getResources().getColor(R.color.black));
                binding.lvlLogin.setVisibility(View.VISIBLE);
                binding.lvlRegister.setVisibility(View.GONE);
            }
        });

        binding.edRefercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothingcall();
            }
        });
        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.mobile.getText().toString().matches(MobilePattern)) {
                    // Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
                    binding.mobile.setError("Please enter valid 10 digit phone number");
                    return;
                } else {
                    loginPassword();
                }

            }
        });

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                n = name.getText().toString();
                c = city.getText().toString();
                m = mobile.getText().toString();
                p = password.getText().toString();

                if (n.isEmpty()) {
                    name.setError("Enter the name");
                    name.requestFocus();
                    return;
                } else if (!m.matches(MobilePattern)) {
                    // Toast.makeText(getApplicationContext(), "Please enter valid 10 digit phone number", Toast.LENGTH_SHORT).show();
                    mobile.setError("Please enter valid 10 digit phone number");
                    return;

//                } else if (p.length() < 6) {
//                    password.setError("Please enter the password");
//                    password.requestFocus();
//                    return;
                } else {

                    random();
                    otpSend(n, m, otprandom);

                }

            }
        });


    }

    private void clothingcall() {
        final String[] genders = {"Male", "Female"};
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIn.this);
        builder.setTitle("Select Gender");
        builder.setSingleChoiceItems(genders, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //   selectGender=genders[which];

                dialog.dismiss();
                binding.edRefercode.setText(genders[which]);
//                glosserycount = "0";

            }
        });
        builder.show();
    }

    private void loginPassword() {

        hud = KProgressHUD.create(LogIn.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();


        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().login_password(binding.mobile.getText().toString(), binding.password.getText().toString());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                hud.dismiss();

                if (data.get(0).getMessage() != null) {
                    if (data.get(0).getMessage().equals("1")) {
                        user.setUserid(data.get(0).getU_id());
                        user.setSender_id(data.get(0).getSender_id());

                        user.setUsername(data.get(0).getName());
                        user.setPhoto(data.get(0).getPhoto());
                        user.setUsercity(data.get(0).getCity());
                        user.setUseremail(data.get(0).getEmail());
                        user.setAddress(data.get(0).getAddreass());
                        user.setGender(data.get(0).getGender());
                        user.setToken(token);
                        user.setUserphone(data.get(0).getMobile());
                        user.setSuscribe(data.get(0).getPaid_status());
                        startActivity(new Intent(LogIn.this, MainActivity.class));
                        Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                        finish();


                    }
                    if (data.get(0).getMessage().equals("0")) {
                        Toast.makeText(getApplicationContext(), "Invalid ID & Password.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed !", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                hud.dismiss();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    public void CheckUserExistance() {
        SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
        if (sp.contains("userphone")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        } else {

        }
    }

    public void random() {
        Random r = new Random();
        int otp = (int) (Math.random() * 9000) + 1000;
//        int otp = r.nextInt(10000); // no. of zeros depends on the OTP digit
        otprandom = String.valueOf(otp);
    }


    private void login() {
        hud = KProgressHUD.create(LogIn.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setMaxProgress(100)
                .show();


        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().logIn(binding.mobile.getText().toString().trim());
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {
                List<LoginModels> data = response.body();
                hud.dismiss();

                if (Integer.parseInt(data.get(0).getMessage()) == 1) {
                    user.setUserid(data.get(0).getU_id());
                    user.setUsername(data.get(0).getName());
                    user.setPhoto(data.get(0).getPhoto());
                    user.setUsercity(data.get(0).getCity());
                    user.setUseremail(data.get(0).getEmail());
                    user.setAddress(data.get(0).getAddreass());
                    user.setToken(token);

                    random();
                    otpSend(data.get(0).getName(), otprandom);


                }
                if (Integer.parseInt(data.get(0).getMessage()) == 0) {
                    Toast.makeText(getApplicationContext(), "Number Not Registered.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), SignUp.class));
                }


            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {
                hud.dismiss();
                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void otpSend(String name, String mobile, String otprandom) {
        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().otpSend(name, mobile, otprandom);
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {

                if (response.isSuccessful()) {

                    if (binding.language1.isChecked()) {
                        language1 = "English";
                    }
                    if (binding.language2.isChecked()) {
                        language2 = "Hindi";
                    }



                    Toast.makeText(getApplicationContext(), "OTP Send your number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OtpSend.class);
                    intent.putExtra("name", n);
                    intent.putExtra("mobile", m);
                    intent.putExtra("otp", otprandom);
                    intent.putExtra("c", c);
                    intent.putExtra("p", p);
                    intent.putExtra("age", binding.age.getText().toString());
                    intent.putExtra("country", binding.country.getText().toString());
                    intent.putExtra("gender", binding.edRefercode.getText().toString());
                    intent.putExtra("language1", language1);
                    intent.putExtra("language2", language2);
                    intent.putExtra("language3", binding.language3.getText().toString());
                    intent.putExtra("type", "1");
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Failed Send OTP!.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void otpSend(String name, String otprandom) {

        Call<List<LoginModels>> call = apicontroller.getInstance().getapi().otpSend(name, binding.mobile.getText().toString().trim(), otprandom);
        call.enqueue(new Callback<List<LoginModels>>() {
            @Override
            public void onResponse(Call<List<LoginModels>> call, Response<List<LoginModels>> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "OTP Send your number.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), OtpSend.class);
                    intent.putExtra("otp", otprandom);
                    intent.putExtra("name", name);
                    intent.putExtra("type", "0");
                    intent.putExtra("mobile", binding.mobile.getText().toString());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Failed Send OTP!.", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<List<LoginModels>> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        user.setUserphone("1");

    }
}