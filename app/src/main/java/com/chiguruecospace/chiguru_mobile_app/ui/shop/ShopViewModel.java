package com.chiguruecospace.chiguru_mobile_app.ui.shop;

public class ShopViewModel{

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

    public ShopViewModel(){}

    public ShopViewModel(String title, String desc) {
        this.Title = title;
        this.Description = desc;
    }
}