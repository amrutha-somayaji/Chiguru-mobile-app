package com.chiguruecospace.chiguru_mobile_app;

public class User {

    public String username, email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(){}

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
