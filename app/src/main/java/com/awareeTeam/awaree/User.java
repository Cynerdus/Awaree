package com.awareeTeam.awaree;

public class User {
    private int ID;
    private String FirstName;
    private String SecondName;
    private String Nickname;
    private String Email;
    private boolean IsEmailVerified;
    private String Password;
    private String Profile;
    private String Series;

    public User() {
    }

    public User(int ID, String firstName, String secondName, String nickname, String email, boolean isEmailVerified, String password, String profile, String series) {
        this.ID = ID;
        FirstName = firstName;
        SecondName = secondName;
        Nickname = nickname;
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

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getSecondName() {
        return SecondName;
    }

    public void setSecondName(String secondName) {
        SecondName = secondName;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
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

    private enum profile{
        CTI, IS;
    }
    private enum series{
        CA, CB, CC, CD, AA, AB, AC, AD;
    }
}