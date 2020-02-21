package com.bimasakti.diskominfosan.User.Edit_profil;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.response.ServerResponse;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Update_profile_act extends AppCompatActivity {

    private  static final int IMAGE = 100;
    ImageView pre;
    Button selectImg, uploadImg;
    EditText imgTitle;

    private String mediaPath;
    private String postPath;
    ProgressDialog pDialog;
    ApiInterface apiInterface;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile_act);

        initDialog();

        pre = findViewById(R.id.preview);
        selectImg = (Button) findViewById(R.id.selectImg);
        uploadImg = (Button) findViewById(R.id.uploadImg);
        imgTitle = (EditText) findViewById(R.id.imgTitle);
        ModelUser user = SharedPrefManager.getInstance(this).getUser();
        imgTitle.setText("foto_profil/" + user.getNik() + ".jpg");

        selectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(Update_profile_act.this, galleryPermissions)) {
                    selectImage();
                } else {
                    EasyPermissions.requestPermissions(Update_profile_act.this, "Access for storage",
                            101, galleryPermissions);
                }
//                selectImage();
            }
        });

        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
                updateImageNama();
            }
        });
    }

    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    pre.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
    }

    private void uploadFile() {
        if (postPath == null || postPath.equals("")) {
            Toast.makeText(this, "please select an image ", Toast.LENGTH_LONG).show();
            return;
        } else {
            showpDialog();
            ModelUser user = SharedPrefManager.getInstance(this).getUser();

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(postPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            Toast.makeText(this, user.getNik(), Toast.LENGTH_LONG).show();
            map.put("file\"; filename=\"" + user.getNik() + "\"", requestBody);
            ApiInterface getResponse = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ServerResponse> call = getResponse.updateImage("token", map);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            hidepDialog();
                            finish();
                            ServerResponse serverResponse = response.body();
//                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "Gambar Berhasil Di Upload!", Toast.LENGTH_LONG).show();
                            Log.e("log", response.message());
                            pre.setImageBitmap(null);
                        }
                    }else {
                        hidepDialog();
                        Toast.makeText(getApplicationContext(), "problem uploading image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    hidepDialog();
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(Update_profile_act.this, "Cek Koneksi Internet Anda!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateImageNama(){
        ModelUser user = SharedPrefManager.getInstance(this).getUser();
        String get_nik  = imgTitle.getText().toString().trim();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.updateImageName(user.getId(), get_nik);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.d("Edit pRof", "onResponse: berhasil input");
                    String s = response.body().string();
                    //Toast.makeText(editActivity.this, "Berhasil Diedit", Toast.LENGTH_LONG).show();
//                    SharedPrefManager.getInstance(getApplication()).clear();
//                    Intent i = new Intent(Update_profile_act.this, Login_act.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(i);
                    Toast.makeText(Update_profile_act.this, "Anda Berhasil Mengupdate Foto Profil, Silahkan Login Ulang..", Toast.LENGTH_LONG).show();

                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(true);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }
}
