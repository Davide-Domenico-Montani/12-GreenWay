package it.unimib.greenway.model;

import android.graphics.Bitmap;

public class User {
    String userId;
    String name;
    String surname;
    String email;
    String password;
    String photoUrl;
    String photoUrlGoogle;
    double kmCar;
    double kmTransit;
    double kmWalk;

    double coSavedCar;
    double coSavedTransit;
    double coSavedWalk;

    int point;





    //User normale

    public User(String userId, String name, String surname, String email, String password, String photoUrl, double kmCar, double kmTransit, double kmWalk, double coSavedCar, double coSavedTransit, double coSavedWalk, int point) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.coSavedCar = coSavedCar;
        this.coSavedTransit = coSavedTransit;
        this.coSavedWalk = coSavedWalk;
        this.point = point;
    }


    //User Google

    public User(String userId, String name, String surname, String email, String photoUrlGoogle, double kmCar, double kmTransit, double kmWalk, double coSavedCar, double coSavedTransit, double coSavedWalk, int point) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoUrlGoogle = photoUrlGoogle;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.coSavedCar = coSavedCar;
        this.coSavedTransit = coSavedTransit;
        this.coSavedWalk = coSavedWalk;
        this.point = point;
    }
    //Costruttore per login

    public User(String userId, String email, String password){
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
    //Costruttore per getLoggedUser

    public User(String userId, String email){
        this.userId = userId;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getPhotoUrlGoogle() {
        return photoUrlGoogle;
    }

    public void setPhotoUrlGoogle(String photoUrlGoogle) {
        this.photoUrlGoogle = photoUrlGoogle;
    }

    public double getKmCar() {
        return kmCar;
    }

    public void setKmCar(double kmCar) {
        this.kmCar = kmCar;
    }

    public double getKmTransit() {
        return kmTransit;
    }

    public void setKmTransit(double kmTransit) {
        this.kmTransit = kmTransit;
    }


    public double getKmWalk() {
        return kmWalk;
    }

    public void setKmWalk(double kmWalk) {
        this.kmWalk = kmWalk;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getCoSavedCar() {
        return coSavedCar;
    }

    public void setCoSavedCar(double coSavedCar) {
        this.coSavedCar = coSavedCar;
    }

    public double getCoSavedTransit() {
        return coSavedTransit;
    }

    public void setCoSavedTransit(double coSavedTransit) {
        this.coSavedTransit = coSavedTransit;
    }

    public double getCoSavedWalk() {
        return coSavedWalk;
    }

    public void setCoSavedWalk(double coSavedWalk) {
        this.coSavedWalk = coSavedWalk;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}

