package com.hitechwebdesign.yeasin.recipesqlite.model;

/**
 * Created by NM on 5/8/2017.
 */

public class UsersModel {

    private int id;
    private String un;
    private String uemail;
    private String password;
    private String uImg;
    private String uPhn;
    private String uStatus;


    public UsersModel(int id, String un, String uemail, String password, String uPhn, String uStatus, String uImg) {
        this.id = id;
        this.un = un;
        this.uemail = uemail;
        this.password = password;
        this.uPhn = uPhn;
        this.uStatus = uStatus;
        this.uImg = uImg;
    }

    public UsersModel(int id, String un, String uemail, String uImg) {
        this.id = id;
        this.un = un;
        this.uemail = uemail;
        this.password = password;
        this.uImg = uImg;
    }
    public UsersModel(int id, String un, String uemail) {
        this.id = id;
        this.un = un;
        this.uemail = uemail;
        this.password = password;
        this.uImg = uImg;
    }
    public UsersModel(String uemail, String password) {
        this.uemail = uemail;
        this.password = password;
    }

    public UsersModel(String un, String uemail, String password, String uPhn, String uStatus) {
        this.un = un;
        this.uemail = uemail;
        this.password = password;
        this.uPhn = uPhn;
        this.uStatus = uStatus;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUn() {
        return un;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUPhn() {
        return uPhn;
    }

    public void setUPhn(String uPhn) {
        this.uPhn = uPhn;
    }

    public String getuStatus() {
        return uStatus;
    }

    public void setuStatus(String uStatus) {
        this.uStatus = uStatus;
    }


    public String getUImg() {
        return uImg;
    }

    public void setUImg(String uImg) {
        this.uImg = uImg;
    }

}