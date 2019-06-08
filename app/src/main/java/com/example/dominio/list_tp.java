package com.example.dominio;


public class list_tp {

    private int image_tp;
    private String tp_name;
    private String tp_trainTime;
    private String tp_success;


        public int getImage_tp() {
            return image_tp;
        }

        public void setImage_tp(int image_tp) {
            this.image_tp = image_tp;
        }

        public String getTp_name() {
            return tp_name;
        }

        public void setTp_name(String tp_name) {
            this.tp_name = tp_name;
        }

        public String getTp_trainTime() {
            return tp_trainTime;
        }

        public void setTp_trainTime(String tp_trainTime) {
            this.tp_trainTime = tp_trainTime;
        }

        public String getTp_success() {
            return tp_success;
        }

        public void setTp_success(String tp_success) {
            this.tp_success = tp_success;
        }

    public list_tp(int image_tp, String tp_name, String tp_trainTime, String tp_success) {
        this.image_tp = image_tp;
        this.tp_name = tp_name;
        this.tp_trainTime = tp_trainTime;
        this.tp_success = tp_success;
    }
}
