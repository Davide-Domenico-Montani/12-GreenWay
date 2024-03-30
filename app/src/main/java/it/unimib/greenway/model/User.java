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

    //User normale
    public User(String userId, String name, String surname, String email, String password, String photoUrl, double kmCar, double kmTrain, double kmBus,double kmWalk) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.kmCar = kmCar;
        this.kmTrain = kmTrain;
        this.kmBus = kmBus;
        this.kmWalk = kmWalk;
    }
    //User Google
    public User(String userId, String name, String surname, String email, String photoUrlGoogle, double kmCar, double kmTrain, double kmBus,double kmWalk) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoUrlGoogle = photoUrlGoogle;
        this.kmCar = kmCar;
        this.kmTrain = kmTrain;
        this.kmBus = kmBus;
        this.kmWalk = kmWalk;
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

    public void setNome(String name) {
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

    public double getKmTrain() {
        return kmTrain;
    }

    public void setKmTrain(double kmTrain) {
        this.kmTrain = kmTrain;
    }

    public double getKmBus() {
        return kmBus;
    }

    public void setKmBus(double kmBus) {
        this.kmBus = kmBus;
    }

    public double getKmWalk() {
        return kmWalk;
    }

    public void setKmWalk(double kmWalk) {
        this.kmWalk = kmWalk;
    }

    double kmTrain;
    double kmBus;
    double kmWalk;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

