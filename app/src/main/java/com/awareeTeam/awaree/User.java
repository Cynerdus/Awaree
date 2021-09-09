package com.awareeTeam.awaree;

public class User {
    private int ID;
    private String Username;
    private String Email;
    private boolean IsEmailVerified;
    private String Password;
    private String Profile;
    private String Series;

    public User() {
    }

    public User(int ID, String username, String email, boolean isEmailVerified, String password, String profile, String series) {
        this.ID = ID;
        Username = username;
        Email = email;
        IsEmailVerified = isEmailVerified;
        Password = password;
        Profile = profile;
        Series = series;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isEmailVerified() {
        return IsEmailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        IsEmailVerified = emailVerified;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getProfile() {
        return Profile;
    }

    public void setProfile(String profile) {
        Profile = profile;
    }

    public String getSeries() {
        return Series;
    }

    public void setSeries(String series) {
        Series = series;
    }

    private enum profile {
        CTI, IS;
    }

    private enum series {
        CA, CB, CC, CD, AA, AB, AC, AD;
    }
}