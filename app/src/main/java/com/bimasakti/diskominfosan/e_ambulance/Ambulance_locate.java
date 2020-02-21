package com.bimasakti.diskominfosan.e_ambulance;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bimasakti.diskominfosan.R;
import com.bimasakti.diskominfosan.User.ModelUser;
import com.bimasakti.diskominfosan.e_ambulance.order.data_order;
import com.bimasakti.diskominfosan.e_ambulance.order.data_order_get;
import com.bimasakti.diskominfosan.location.getLocation;
import com.bimasakti.diskominfosan.retrofit.ApiClient;
import com.bimasakti.diskominfosan.retrofit.ApiInterface;
import com.bimasakti.diskominfosan.store_session.SharedPrefManager;
//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationListener;
//import com.google.android.gms.location.LocationRequest;
//import com.google.android.gms.location.LocationServices;
//import com.google.android.gms.maps.CameraUpdate;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.CameraPosition;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
//import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.ResponseBody;
import okhttp3.internal.cache.DiskLruCache;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ambulance_locate extends AppCompatActivity implements OnMapReadyCallback {
    TextView tv, addressget, aa, ba, ca, plat, instansi, jarak_detail;
    Button get_order, tunggu;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    ImageView foto_driver;

    private final String URL1 = "https://kamismart.sukabumikab.go.id/bmadmin/images/driver/";
    ModelUser user;
    ProgressDialog pDialog;
    String Keys;

    public int counter;

    private ArrayList<data_order> order;
    private ArrayList<data_order_get> orderG;
    DatabaseReference reference;
    private ApiInterface apiInterface;

    int detik, menit, jam, hari, bulan, bulan1, tahun;
    GregorianCalendar date = new GregorianCalendar();
    String namabulan[] = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    String datee;
//    private LatLng pickUpLatLng = new LatLng(-6.175110, 106.865039); // Jakarta
//    private LatLng locationLatLng = new LatLng(-6.197301, 106.795951); // Cirebon
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ambulance_locate);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

        user = SharedPrefManager.getInstance(Ambulance_locate.this).getUser();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        initDialog();

        tv = (TextView) findViewById(R.id.txt_tanggal_waktu);
        get_order = findViewById(R.id.get_order_am);
        tunggu = findViewById(R.id.tunggu_ambulan);
        addressget = findViewById(R.id.getAdress);
        plat = findViewById(R.id.txt_plat_driver);
        instansi = findViewById(R.id.txt_instansi_driver);
        jarak_detail = findViewById(R.id.txt_jarak_detail);
        foto_driver = findViewById(R.id.gambar_driver);

        aa = (TextView) findViewById(R.id.lok_driver);
        ba = (TextView)findViewById(R.id.lok_pemesan);
        ca = (TextView)findViewById(R.id.txt_nm_driver);

        detik = date.get(Calendar.SECOND);
        menit = date.get(Calendar.MINUTE);
        jam = date.get(Calendar.HOUR_OF_DAY);
        hari = date.get(Calendar.DAY_OF_MONTH);
        bulan = date.get(Calendar.MONTH);
        bulan1 = date.get(Calendar.MONTH) + 1;
        tahun = date.get(Calendar.YEAR);

        tv.setText(hari + " " + namabulan[bulan] + " " + tahun + ", " + jam + ":" + menit);
        datee = tahun+"/"+bulan1+"/"+hari+" "+jam+":"+menit;

        get_order.setVisibility(View.INVISIBLE);
        get_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Mendapatkan Instance dari Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference getReference;

                String orderer = user.getNama_lengkap();
                String driver = "";
                String no_orderer = user.getNohp();
                String no_driver = "";
                String lokal = addressget.getText().toString();
                String lokhir = "";
                final String jarak = datee;
                String stats = "1";
                String plat = "1";
                String inst = "1";

                String id = Integer.toString(user.getId());
                getReference = database.getReference(); // Mendapatkan Referensi dari Database
                Keys = getReference.push().getKey();
                getReference.child("Order_Ambulance").child(id).child(Keys)
                        .setValue(new data_order(orderer, driver, no_driver, no_orderer, lokal, lokhir, jarak, stats, plat, inst))
                        .addOnSuccessListener(Ambulance_locate.this, new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //Peristiwa ini terjadi saat user berhasil menyimpan datanya kedalam Database
                                /* Buat pesanan */
                                sendAmbulanNotif();
                                final CountDownTimer c = new CountDownTimer(20000, 1000){
                                    public void onTick(long millisUntilFinished){
                                        counter++;
                                    }
                                    public  void onFinish(){
                                        onDeleteData();
                                        hidepDialog();
                                        Toast.makeText(Ambulance_locate.this, "Tidak Ada Driver Saat Ini!", Toast.LENGTH_LONG).show();
                                    }
                                }.start();

                                /* pesanan diterima */
                                final String id = Integer.toString(user.getId());
                                getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("2")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    ActionBar menu = getSupportActionBar();
                                                    menu.setDisplayShowHomeEnabled(false);
                                                    menu.setDisplayHomeAsUpEnabled(false);

                                                    c.cancel();
                                                    hidepDialog();
                                                    GetData();
                                                    get_order.setVisibility(View.INVISIBLE);
                                                    tunggu.setVisibility(View.VISIBLE);

                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                              /*
                                                Kode ini akan dijalankan ketika ada error dan
                                                pengambilan data error tersebut lalu memprint error nya
                                                ke LogCat
                                               */
                                                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                                                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                                            }
                                        });

                                /* pesanan selesai */
                                getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("3")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(final DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    orderG = new ArrayList<>();

                                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                                        //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                                                        data_order_get r  = snapshot.getValue(data_order_get.class);
                                                        //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                                                        r.setKey(snapshot.getKey());
                                                        orderG.add(r);
                                                    }
                                                    if (orderG.get(0).getKey().equals(Keys)){
                                                        FirebaseDatabase.getInstance().getReference("Order_Ambulance").child(id).child(Keys).removeValue();
                                                        FirebaseDatabase.getInstance().getReference("Order_Ambulance_Selesai").
                                                          child(Keys).setValue(new data_order(orderG.get(0).getPemesan(),
                                                                orderG.get(0).getDriver(),
                                                                orderG.get(0).getNo_driver(),
                                                                orderG.get(0).getNo_pemesan(),
                                                                orderG.get(0).getLokasi_awal(),
                                                                orderG.get(0).getLokasi_akhir(),
                                                                orderG.get(0).getJarak(),
                                                                orderG.get(0).getStatus()+"||"+orderG.get(0).getKey(),
                                                                orderG.get(0).getPlat(),
                                                                orderG.get(0).getInstansi()
                                                                ));
                                                        get_order.setVisibility(View.VISIBLE);
                                                        tunggu.setVisibility(View.GONE);
                                                        aa.setText("-");
                                                        ba.setText("-");
                                                        ca.setText("-");
                                                        jarak_detail.setText("0 KM");
                                                        Picasso.get()
                                                                .load(R.drawable.ambul)
                                                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                                                .networkPolicy(NetworkPolicy.NO_CACHE)
                                                                .into(foto_driver);

                                                        ActionBar menu = getSupportActionBar();
                                                        menu.setDisplayShowHomeEnabled(true);
                                                        menu.setDisplayHomeAsUpEnabled(true);
                                                        Toast.makeText(Ambulance_locate.this, "PANGGILAN TELAH SELESAI..", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                                                Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                                            }
                                        });
                            }
                        });
            }
        });
        tunggu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ambulance_locate.this, "Tunggu Ambulan!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference getReference;
        getReference = database.getReference();
        final String id = Integer.toString(user.getId());
        getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("2")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            ActionBar menu = getSupportActionBar();
                            menu.setDisplayShowHomeEnabled(false);
                            menu.setDisplayHomeAsUpEnabled(false);

                            GetData();
                            get_order.setVisibility(View.INVISIBLE);
                            tunggu.setVisibility(View.VISIBLE);
                        } else{
                            ActionBar menu = getSupportActionBar();
                            menu.setDisplayShowHomeEnabled(true);
                            menu.setDisplayHomeAsUpEnabled(true);

                            aa.setText("-");
                            ba.setText("-");
                            ca.setText("-");
                            plat.setText("");
                            instansi.setText("");
                            Picasso.get()
                                    .load(R.drawable.ambul)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .into(foto_driver);

                            get_order.setVisibility(View.VISIBLE);
                            tunggu.setVisibility(View.INVISIBLE);

                            Log.d("TDK ADA DATA - ", "TIDAK ADA DATA");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });

        /*cek pesanan selesai apa belum*/
        getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("3")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            orderG = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                //Mapping data pada DataSnapshot ke dalam objek mahasiswa
                                data_order_get r  = snapshot.getValue(data_order_get.class);
                                //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                                r.setKey(snapshot.getKey());
                                orderG.add(r);
                            }
                            if (!orderG.get(0).getKey().equals(null)){
                                FirebaseDatabase.getInstance().getReference("Order_Ambulance").child(id).child(orderG.get(0).getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference("Order_Ambulance_Selesai").
                                        child(orderG.get(0).getKey()).setValue(new data_order(orderG.get(0).getPemesan(),
                                        orderG.get(0).getDriver(),
                                        orderG.get(0).getNo_driver(),
                                        orderG.get(0).getNo_pemesan(),
                                        orderG.get(0).getLokasi_awal(),
                                        orderG.get(0).getLokasi_akhir(),
                                        orderG.get(0).getJarak(),
                                        orderG.get(0).getStatus()+"||"+orderG.get(0).getKey(),
                                        orderG.get(0).getPlat(),
                                        orderG.get(0).getInstansi()
                                        ));
                                get_order.setVisibility(View.VISIBLE);
                                tunggu.setVisibility(View.GONE);
                                aa.setText("-");
                                ba.setText("-");
                                ca.setText("-");
                                Picasso.get()
                                        .load(R.drawable.ambul)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .into(foto_driver);
                                ActionBar menu = getSupportActionBar();
                                menu.setDisplayShowHomeEnabled(true);
                                menu.setDisplayHomeAsUpEnabled(true);
                                Toast.makeText(Ambulance_locate.this, "PANGGILAN TELAH SELESAI..", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
    }

    @Override
    public void onResume(){
        super.onResume();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference getReference;
        getReference = database.getReference();
        final String id = Integer.toString(user.getId());

        getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("2")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            ActionBar menu = getSupportActionBar();
                            menu.setDisplayShowHomeEnabled(false);
                            menu.setDisplayHomeAsUpEnabled(false);

                            GetData();
                            get_order.setVisibility(View.INVISIBLE);
                            tunggu.setVisibility(View.VISIBLE);
                        } else{
                            ActionBar menu = getSupportActionBar();
                            menu.setDisplayShowHomeEnabled(true);
                            menu.setDisplayHomeAsUpEnabled(true);

                            aa.setText("-");
                            ba.setText("-");
                            ca.setText("-");
                            plat.setText("");
                            instansi.setText("");
                            Picasso.get()
                                    .load(R.drawable.ambul)
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .into(foto_driver);

                            get_order.setVisibility(View.VISIBLE);
                            tunggu.setVisibility(View.INVISIBLE);

                            Log.d("TDK ADA DATA - ", "TIDAK ADA DATA");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });

        /*cek pesanan selesai apa belum*/
        getReference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("3")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            orderG = new ArrayList<>();
                            Toast.makeText(Ambulance_locate.this, "ADA DATA", Toast.LENGTH_LONG).show();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                data_order_get r  = snapshot.getValue(data_order_get.class);
                                r.setKey(snapshot.getKey());
                                orderG.add(r);
                            }
                            if (!orderG.get(0).getKey().equals(null)){
                                FirebaseDatabase.getInstance().getReference("Order_Ambulance").child(id).child(orderG.get(0).getKey()).removeValue();
                                FirebaseDatabase.getInstance().getReference("Order_Ambulance_Selesai").
                                        child(orderG.get(0).getKey()).setValue(new data_order(orderG.get(0).getPemesan(),
                                        orderG.get(0).getDriver(),
                                        orderG.get(0).getNo_driver(),
                                        orderG.get(0).getNo_pemesan(),
                                        orderG.get(0).getLokasi_awal(),
                                        orderG.get(0).getLokasi_akhir(),
                                        orderG.get(0).getJarak(),
                                        orderG.get(0).getStatus()+"||"+orderG.get(0).getKey(),
                                        orderG.get(0).getPlat(),
                                        orderG.get(0).getInstansi()
                                ));
                                get_order.setVisibility(View.VISIBLE);
                                tunggu.setVisibility(View.GONE);
                                aa.setText("-");
                                ba.setText("-");
                                ca.setText("-");
                                Picasso.get()
                                        .load(R.drawable.ambul)
                                        .memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE)
                                        .into(foto_driver);

                                ActionBar menu = getSupportActionBar();
                                menu.setDisplayShowHomeEnabled(true);
                                menu.setDisplayHomeAsUpEnabled(true);

                                Toast.makeText(Ambulance_locate.this, "PANGGILAN TELAH SELESAI..", Toast.LENGTH_LONG).show();
                            } else{
//                                Toast.makeText(Ambulance_locate.this, orderG.get(0).getKey(), Toast.LENGTH_LONG).show();
//                                Toast.makeText(Ambulance_locate.this, "MOVE NODE FAILED", Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });


    }

    @Override
    public void onBackPressed() {

    }

    protected void initDialog() {

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Tunggu Sebentar....");
        pDialog.setCancelable(false);
    }

    protected void showpDialog() {

        if (!pDialog.isShowing()) pDialog.show();
    }

    protected void hidepDialog() {

        if (pDialog.isShowing()) pDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_main:
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.example.com"));
//
//                startActivity(browserIntent);
//
//                return true;

        }
        return false;
    }

    public void sendAmbulanNotif() {
        showpDialog();
        ApiInterface api = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> sendNotif = api.sendAmbulanNotif(user.getId(), Keys, user.getNama_lengkap(), user.getNohp(), addressget.getText().toString(), datee, user.getNik() + ".jpg");
        sendNotif.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> sendNotif, Response<ResponseBody> response) {
                Toast.makeText(Ambulance_locate.this, "Tunggu Sebentar", Toast.LENGTH_LONG).show();
                Log.e("Send Notif", response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> sendNotif, Throwable t) {
                Toast.makeText(Ambulance_locate.this, "POST API NOTIF GAGAL!", Toast.LENGTH_LONG).show();
                Log.e("Send Notif Failure", t.getMessage());
            }
        });
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_place);
                    supportMapFragment.getMapAsync(Ambulance_locate.this);

                }
            }
        });
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                .title("Here I Am..");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_user_64px));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference getReference;

        getReference = database.getReference();
        getReference.child("Lokasi_Driver")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            ArrayList<getLocation> gL = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                getLocation p  = snapshot.getValue(getLocation.class);
                                p.setKey(snapshot.getKey());
                                gL.add(p);
                            }
                            MarkerOptions options = new MarkerOptions();
                            for (int i = 0; i < gL.size(); i++){
                                options.title("Ambulan Is Here!");
                                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.amb));
                                options.position(new LatLng(gL.get(i).getLatitude(), gL.get(i).getLongitude()));
//                                googleMap.clear();
                                googleMap.addMarker(options);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                      /*
                        Kode ini akan dijalankan ketika ada error dan
                        pengambilan data error tersebut lalu memprint error nya
                        ke LogCat
                       */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
//        Circle circle = googleMap.addCircle(new CircleOptions().center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).radius(r).strokeColor(Color.RED));
//        circle.setVisible(true);

        float result [] = new float[10];
//        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(), gL.get(i).getLatitude(),gL.get(i).getLongitude(), result);
//        if (result[0] > r){
//            Toast.makeText(Ambulance_locate.this, "MAAF, Jarak nya melebihi 800 meter \n Jaraknya : "+ result[0], Toast.LENGTH_LONG).show();
//        } else{
//            Toast.makeText(Ambulance_locate.this, "Jarak Termasuk dalam radius \n Jaraknya : "+ result[0], Toast.LENGTH_LONG).show();
//
//        }
        getAddress(this, currentLocation.getLatitude(), currentLocation.getLongitude());
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
        googleMap.addMarker(markerOptions);
        get_order.setVisibility(View.VISIBLE);
    }

    public String getAddress(Context ctx, Double lat, Double lng) {
        String add = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 1);
            if (addressList.size() > 0) {
                Address address = addressList.get(0);
                add = address.getAddressLine(0);
                addressget.setText(add);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return add;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }

    private void GetData(){
        //Mendapatkan Referensi Database
        reference = FirebaseDatabase.getInstance().getReference();
        String id = Integer.toString(user.getId());
        reference.child("Order_Ambulance").child(id).orderByChild("status").equalTo("2")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Inisialisasi ArrayList
                        orderG = new ArrayList<>();
                        float distance = 0;

                        Location crntLocation=new Location("crntlocation");
                        crntLocation.setLatitude(currentLocation.getLatitude());
                        crntLocation.setLongitude(currentLocation.getLongitude());


                        for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                            data_order_get r  = snapshot.getValue(data_order_get.class);
                            r.setKey(snapshot.getKey());
                            orderG.add(r);
                        }
                        for (int i = 0; i < orderG.size(); i++) {
                            Location newLocation=new Location("newlocation");
                            newLocation.setLatitude(orderG.get(i).getLat());
                            newLocation.setLongitude(orderG.get(i).getLng());

                            aa.setText(orderG.get(i).getLokasi_akhir());
                            ba.setText(orderG.get(i).getLokasi_awal());
                            ca.setText(orderG.get(i).getDriver());
                            plat.setText(orderG.get(i).getPlat());
                            instansi.setText(orderG.get(i).getInstansi());
                            Picasso.get()
                                    .load(URL1 + orderG.get(0).getFoto())
                                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                                    .networkPolicy(NetworkPolicy.NO_CACHE)
                                    .into(foto_driver);

                            distance =crntLocation.distanceTo(newLocation); // in km
                            float a = Float.parseFloat(String.format("%.0f",distance));

                            if (a < 1000){
                                jarak_detail.setText(a+" M");
                            }else{
                                jarak_detail.setText(a / 1000 +" KM");
                            }
                        }

//                        Toast.makeText(Ambulance_locate.this, c.getText().toString(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                      /*
                        Kode ini akan dijalankan ketika ada error dan
                        pengambilan data error tersebut lalu memprint error nya
                        ke LogCat
                       */
                        Toast.makeText(getApplicationContext(),"Data Gagal Dimuat", Toast.LENGTH_LONG).show();
                        Log.e("MyListActivity", databaseError.getDetails()+" "+databaseError.getMessage());
                    }
                });
    }

    public void onDeleteData() {
        reference = FirebaseDatabase.getInstance().getReference();
        String id = Integer.toString(user.getId());
        if(reference != null){
            reference.child("Order_Ambulance")
                    .child(id)
                    .child(Keys)
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {

//                            Toast.makeText(Ambulance_locate.this, "Data Berhasil Dihapus", Toast.LENGTH_SHORT).show();
                            Log.e("HAPUS PANGGILAN", "Berhasil!");
                        }
                    });
        }
    }

}
