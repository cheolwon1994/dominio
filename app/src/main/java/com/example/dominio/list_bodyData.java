package com.example.dominio;


public class list_bodyData {

    private int image_user;
    private String user_id;
    private String user_height;
    private String user_weight;
    private String user_muscle;
    private String user_fat;
    private String user_date;

    public int getImage_user() {
        return image_user;
    }

    public void setImage_user(int image_user) {
        this.image_user = image_user;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_height() {
        return user_height;
    }

    public void setUser_height(String user_height) {
        this.user_height = user_height;
    }

    public String getUser_weight() {
        return user_weight;
    }

    public void setUser_weight(String user_weight) {
        this.user_weight = user_weight;
    }

    public String getUser_muscle() {
        return user_muscle;
    }

    public void setUser_muscle(String user_muscle) {
        this.user_muscle = user_muscle;
    }

    public String getUser_fat() {
        return user_fat;
    }

    public void setUser_fat(String user_fat) {
        this.user_fat = user_fat;
    }

    public String getUser_date() {
        return user_date;
    }

    public void setUser_date(String user_date) {
        this.user_date = user_date;
    }

    public list_bodyData(int image_user, String user_id, String user_height, String user_weight, String user_muscle, String user_fat, String user_date) {
        this.image_user = image_user;
        this.user_id = user_id;
        this.user_height = user_height;
        this.user_weight = user_weight;
        this.user_muscle = user_muscle;
        this.user_fat = user_fat;
        this.user_date = user_date;
    }
}
