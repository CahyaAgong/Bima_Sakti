package com.bimasakti.diskominfosan.tablayout2.tab;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.response.ServerResponse;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
import com.bimasakti.diskominfosan.tablayout2.Detail_diskusi;
import com.bimasakti.diskominfosan.tablayout2.list_diskusi.Adapter_diskusi;
import com.bimasakti.diskominfosan.tablayout2.list_diskusi.Model_diskusi;
import com.bimasakti.diskominfosan.tablayout2.spinner.SpinnerKategori_model;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;


public class Tab1diskusi extends Fragment {
    FloatingActionButton add_diskusi;
    private RecyclerView recyclerView;
    private Adapter_diskusi adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Model_diskusi> ds;
    private ApiInterface apiInterface;

    private EditText judul, isi;
    private Spinner kategori;
    Button posting_button;
    private DateFormat dateFormat;
    private Date current;
    private Dialog dialog;
    ModelUser user;

    private List<SpinnerKategori_model> item_category;
    private ArrayList<String> hasil_kategori;
    private ArrayList<Integer> hasil_kategori_id;
    String getCategory;
    int CategoryID;

    private Button pick_img;
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private String mediaPath;
    private String postPath;
    private ImageView gambar_diskusi;
    TextView stats;
    public int counter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1_diskusi,container,false);

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.layout_add_diskusi);
        add_diskusi = (FloatingActionButton) v.findViewById(R.id.posting_diskusi);

        judul               = (EditText) dialog.findViewById(R.id.txt_judul_diskusi);
        kategori            = (Spinner) dialog.findViewById(R.id.spinner_kategori);
        isi                 = (EditText) dialog.findViewById(R.id.txt_deskripsi_diskusi);
        posting_button      = (Button) dialog.findViewById(R.id.btn_posting_diskusi);
        pick_img            = (Button) dialog.findViewById(R.id.choose_image);
        stats               = (TextView) dialog.findViewById(R.id.status_pick);

        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        current = new Date();
        user = SharedPrefManager.getInstance(getActivity()).getUser();

        //list diskusi
        recyclerView = (RecyclerView) v.findViewById(R.id.post_diskusi_rec);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        fetch_diskusi("");

        getKategori();
        kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getCategory = parent.getItemAtPosition(position).toString();
                CategoryID = hasil_kategori_id.get(position);
//                String idd = Integer.toString(CategoryID);
//                Toast.makeText(getContext(), idd, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pick_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EasyPermissions.hasPermissions(getActivity(), galleryPermissions)) {
                    selectImage();
                } else {
                    EasyPermissions.requestPermissions(getActivity(), "Access for storage",
                            101, galleryPermissions);
                }
            }
        });

        add_diskusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        posting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posting();
                uploadFile();
            }
        });
        new CountDownTimer(10000, 500){
            public void onTick(long millisUntilFinished){
                counter++;
            }
            public  void onFinish(){
                fetch_diskusi("");
                start();
            }
        }.start();


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    final int position = rv.getChildAdapterPosition(child);

                    Intent data_diskusi = new Intent(getContext(), Detail_diskusi.class);
                    data_diskusi.putExtra("id_diskusi", ds.get(position).getId_diskusi());
                    data_diskusi.putExtra("judul_diskusi", ds.get(position).getJudul());
                    data_diskusi.putExtra("kategori_diskusi_id", ds.get(position).getId_kategori());
                    data_diskusi.putExtra("kategori_diskusi", ds.get(position).getNama_kategori());
                    data_diskusi.putExtra("tanggal_diskusi", ds.get(position).getCreated_date());
                    data_diskusi.putExtra("id_penulis", ds.get(position).getUserid());
                    data_diskusi.putExtra("penulis_diskusi", ds.get(position).getId_user());
                    data_diskusi.putExtra("isi_diskusi", ds.get(position).getDeskripsi());
                    data_diskusi.putExtra("nama_gambar", ds.get(position).getGambar());
                    startActivityForResult(data_diskusi, 3);

                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        return v;
    }


    public void fetch_diskusi(String key){

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<Model_diskusi>> call = apiInterface.getDiskusi(key);
        call.enqueue(new retrofit2.Callback<List<Model_diskusi>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Model_diskusi>> call, retrofit2.Response<List<Model_diskusi>> response) {
                ds = response.body();
                adapter = new Adapter_diskusi(ds, getContext());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(retrofit2.Call<List<Model_diskusi>> call, Throwable t) {
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getKategori(){
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        retrofit2.Call<List<SpinnerKategori_model>> call = apiInterface.getCategory();
        call.enqueue(new retrofit2.Callback<List<SpinnerKategori_model>>() {
            @Override
            public void onResponse(retrofit2.Call<List<SpinnerKategori_model>> call, retrofit2.Response<List<SpinnerKategori_model>> response) {

                item_category = response.body();
                hasil_kategori = new ArrayList<>();
                hasil_kategori_id = new ArrayList<>();
                for (int i = 0; i < item_category.size(); i++) {
                    hasil_kategori.add(item_category.get(i).getNama_kategori());
                    hasil_kategori_id.add(item_category.get(i).getId_kategori());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, hasil_kategori);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                kategori.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<List<SpinnerKategori_model>> call, Throwable t) {
                Toast.makeText(getContext(), "Error\n"+t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void posting(){
        final String title_diskusi     = judul.getText().toString().trim();
        String deskripsi_diskusi = isi.getText().toString().trim();
        String created_date      = dateFormat.format(current);
        int user_id               = user.getId();

        if (judul.length() < 1 || isi.length() < 1){
            Toast.makeText(getActivity(), "Isi Form Dengan Lengkap!", Toast.LENGTH_LONG).show();
        }else {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd_");
            apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ResponseBody> call = apiInterface.createDiskusi(CategoryID, user_id, title_diskusi, deskripsi_diskusi, created_date, dateFormat.format(current) + title_diskusi);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String s = response.body().string();
                        fetch_diskusi("");
                        judul.setText("");
                        isi.setText("");
                        stats.setText("Status");
                        dialog.dismiss();
                        Toast.makeText(getContext(), "Berhasil Posting Diskusi !!", Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void selectImage() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if (data != null) {
                    // Get the Image from data
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getApplicationContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    assert cursor != null;
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    mediaPath = cursor.getString(columnIndex);
                    // Set the Image in ImageView for Previewing the Media
                    stats.setText("Image Selected!");
//                    pre.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
                    cursor.close();

                    postPath = mediaPath;
                }
            }
        }
        if (resultCode == RESULT_FIRST_USER){
            if (data.getBooleanExtra("delete", true)){
                fetch_diskusi("");
            }
        }
    }


    private void uploadFile() {
        if (postPath == null || postPath.equals("")) {
            Toast.makeText(getContext(), "please select an image ", Toast.LENGTH_LONG).show();
        } else {
            String imgName = judul.getText().toString().trim();
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Map is used to multipart the file using okhttp3.RequestBody
            Map<String, RequestBody> map = new HashMap<>();
            File file = new File(postPath);

            // Parsing any Media type file
            RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
//            Toast.makeText(this, user.getNik(), Toast.LENGTH_LONG).show();
            map.put("file\"; filename=\"" + dateFormat.format(current) + "_" + imgName + "\"", requestBody);
            ApiInterface getResponse = ApiClient.getApiClient().create(ApiInterface.class);
            Call<ServerResponse> call = getResponse.uploadImgDiskusi("token", map);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    if (response.isSuccessful()){
                        if (response.body() != null){
                            ServerResponse serverResponse = response.body();
//                            Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_LONG).show();
//                            Toast.makeText(getContext(), "Gambar Berhasil Di Upload!", Toast.LENGTH_LONG).show();
                            Log.e("log", response.message());
                        }
                    }else {
                        Toast.makeText(getContext(), "problem uploading image", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Log.v("Response gotten is", t.getMessage());
                    Toast.makeText(getContext(), "Cek Koneksi Internet Anda!", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}