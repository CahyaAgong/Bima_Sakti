package com.bimasakti.diskominfosan.running_text;

import com.google.gson.annotations.SerializedName;

public class Running_text_model {

    @SerializedName("id") private int id;
    @SerializedName("Running_text") private String Running;

    public int getId() {
        return id;
    }

    public String getRunning() {
        return Running;
    }
}
