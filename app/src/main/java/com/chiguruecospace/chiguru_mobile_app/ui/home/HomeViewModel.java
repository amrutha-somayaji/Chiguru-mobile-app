package com.chiguruecospace.chiguru_mobile_app.ui.home;



public class HomeViewModel{

    public String title;
    public String desc;
    public String imagepath;

    public HomeViewModel(){}

    public HomeViewModel(String title, String desc, String imagepath) {
        this.title = title;
        this.desc = desc;
        this.imagepath = imagepath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}