package com.bimasakti.diskominfosan.store_session;

import android.content.Context;
import android.content.SharedPreferences;

import com.bimasakti.diskominfosan.User.ModelUser;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";

    private static SharedPrefManager mInstance;
    private Context mCtx;


        private SharedPrefManager(Context mCtx) {
            this.mCtx = mCtx; }

    public static synchronized SharedPrefManager getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    public void saveUser(ModelUser user) {

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("nik", user.getNik());
        editor.putString("nama", user.getNama_lengkap());
        editor.putString("password", user.getPassword());
        editor.putString("foto", user.getFoto());
        editor.putString("no", user.getNohp());
        editor.putString("alamat", user.getAlamat());
        editor.putString("kecamatan", user.getKecamatan());
        editor.putString("kelurahan_desa", user.getKelurahan_desa());

        editor.apply();

    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("id", -1) != -1;
    }

    public ModelUser getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ModelUser(
                sharedPreferences.getInt("id", -1),
                sharedPreferences.getString("nik", null),
                sharedPreferences.getString("nama", null),
                sharedPreferences.getString("password", null),
                sharedPreferences.getString("foto", null),
                sharedPreferences.getString("no", null),
                sharedPreferences.getString("alamat", null),
                sharedPreferences.getString("kecamatan", null),
                sharedPreferences.getString("kelurahan_desa", null)
        );


    }

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
