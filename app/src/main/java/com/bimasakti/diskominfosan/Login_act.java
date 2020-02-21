package com.bimasakti.diskominfosan;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.response.loginResponse;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;

public class Login_act extends AppCompatActivity {

    private static final String TAG1 = "Login";

    private ApiInterface apiInterface;
    TextView forgot;
    EditText nik, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        nik     = (EditText)findViewById(R.id.txt_nik_login);
        password     = (EditText)findViewById(R.id.txt_password_login);

        login     = (Button)findViewById(R.id.bt_login);

        forgot  = (TextView) findViewById(R.id.tvForgot);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://api.whatsapp.com/send?phone=6285720004991&text=Mohon Maaf Admin Saya Lupa Password"));
                startActivity(i);
                }
            });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String get_nik  = nik.getText().toString();
                String get_pass = password.getText().toString();

                if (get_nik.trim().length() > 0 && get_pass.trim().length() > 0) {
                    userlogin(get_nik, get_pass);

                    nik.setText(null);
                    password.setText(null);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(getApplicationContext() ,"Kolom tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void go_reg(View view) {
        Intent i = new Intent(Login_act.this, Register_act.class);
        startActivity(i);
    }

    private void userlogin(final String nik, String password){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<loginResponse> call = apiInterface.login(nik, password);
        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, retrofit2.Response<loginResponse> response) {
                loginResponse loginResponse = response.body();
                if (!loginResponse.isError()){

                    Log.e(TAG1, "Berhasil login");

                    SharedPrefManager.getInstance(Login_act.this)
                            .saveUser(loginResponse.getModelUser());

                    Intent intent = new Intent(Login_act.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    Toast.makeText(Login_act.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else {

                    Log.e(TAG1, "login gagal ");
                    Toast.makeText(Login_act.this, "Gagal Login, Username atau Password Salah"
                            , Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {

                Toast.makeText(Login_act.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                Log.e(TAG1, "gagal login\n " + t.toString());
            }
        });
    }


}
