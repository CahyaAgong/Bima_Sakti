package com.bimasakti.diskominfosan.tablayout2;

import android.os.CountDownTimer;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
import com.bimasakti.diskominfosan.tablayout2.list_komentar.Adapter_komentar;
import com.bimasakti.diskominfosan.tablayout2.list_komentar.Model_komentar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Komentar_form extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter_komentar adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Model_komentar> ds;
    private ApiInterface apiInterface;

    private TextView nothing;
    private EditText Comment_text;
    private Button send;
    private DateFormat dateFormat;
    private Date current;
    ModelUser user;

    String id_disscuss, id_creator;
    public int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.komentar_form);

        nothing = (TextView) findViewById(R.id.stats_null);
        Comment_text = (EditText) findViewById(R.id.comment_text);
        send = (Button) findViewById(R.id.send_comment);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current = new Date();
        user = SharedPrefManager.getInstance(Komentar_form.this).getUser();
//        System.out.println("TANGGAL SEKARANG "+dateFormat.format(current));

        Bundle extra = getIntent().getExtras();
        id_disscuss = extra.getString("id_diskusi");
        id_creator = extra.getString("id_penulis");

        //list diskusi
        recyclerView = (RecyclerView) findViewById(R.id.rec_komentar);
        layoutManager = new LinearLayoutManager(Komentar_form.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetch_komentar(id_disscuss);
        new CountDownTimer(10000, 500){
            public void onTick(long millisUntilFinished){
                counter++;
            }
            public  void onFinish(){
                fetch_komentar(id_disscuss);
                start();
            }
        }.start();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                send_comment();
            }
        });

    }

    public void fetch_komentar(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Model_komentar>> call = apiInterface.getComment(key);
        call.enqueue(new retrofit2.Callback<List<Model_komentar>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Model_komentar>> call, retrofit2.Response<List<Model_komentar>> response) {
                ds = response.body();
                adapter = new Adapter_komentar(ds, Komentar_form.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Model_komentar>> call, Throwable t) {
//                Toast.makeText(Komentar_form.this, "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
                recyclerView.setVisibility(View.INVISIBLE);
                nothing.setVisibility(View.VISIBLE);
            }
        });
    }

    private void send_comment(){
        String id_diskusi        = id_disscuss;
        int iduser               = user.getId();
        String Comment           = Comment_text.getText().toString().trim();
        String created_date      = dateFormat.format(current);

        if (id_diskusi.length() < 1 || Comment.length() < 1){
            Toast.makeText(Komentar_form.this, "Isi Kolom Komentar dengan Benar!", Toast.LENGTH_LONG).show();
        }else {
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.createComment(id_diskusi, iduser, Comment, created_date);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s = response.body().string();
                        fetch_komentar(id_disscuss);
                        recyclerView.setVisibility(View.VISIBLE);
                        nothing.setVisibility(View.INVISIBLE);
                        Comment_text.setText("");
                        Toast.makeText(Komentar_form.this, "Berhasil Mengirim Komentar !!", Toast.LENGTH_LONG).show();
                        sendNotif();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(Komentar_form.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void sendNotif() {
        ApiInterface api = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> sendNotif = api.sendNotif();
        sendNotif.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> sendNotif, Response<ResponseBody> response) {
//                Toast.makeText(Komentar_form.this, "POST API NOTIF", Toast.LENGTH_LONG).show();
                Log.e("Send Notif", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> sendNotif, Throwable t) {
//                Toast.makeText(Komentar_form.this, "POST API NOTIF GAGAL!", Toast.LENGTH_LONG).show();
                Log.e("Send Notif Failure", t.getMessage());
            }
        });
    }
}
