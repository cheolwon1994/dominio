package com.example.dominio;


public class list_ts {

    private int image_ts;
    private String ts_name;

    public int getImage_ts() {
        return image_ts;
    }

    public void setImage_ts(int image_ts) {
        this.image_ts = image_ts;
    }

    public String getTs_name() {
        return ts_name;
    }

    public void setTs_name(String ts_name) {
        this.ts_name = ts_name;
    }

    public list_ts(int image_ts, String ts_name) {
        this.image_ts = image_ts;
        this.ts_name = ts_name;

    }
}
