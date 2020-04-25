package com.chiguruecospace.chiguru_mobile_app.ui.home;

public class HomeViewModel{

    private String Description;
    private String Title;
    private String Imagepath;

    public String getImagepath() {
        return Imagepath;
    }

    public void setImagepath(String imagepath) {
        Imagepath = imagepath;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public String getTitle() {
        return Title;
    }

    public HomeViewModel(){}

    public HomeViewModel(String title, String desc) {
        this.Title = title;
        this.Description = desc;
    }

}