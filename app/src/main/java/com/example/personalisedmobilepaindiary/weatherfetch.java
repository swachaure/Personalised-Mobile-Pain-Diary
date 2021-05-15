package com.example.personalisedmobilepaindiary;

import com.google.gson.annotations.SerializedName;

public class weatherfetch {
    @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
