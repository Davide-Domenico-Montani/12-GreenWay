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

    double co2Car;

    double co2SavedCar;
    double co2SavedTransit;
    double co2SavedWalk;

    int point;

    public User() {
    }


//User normale

    public User(String userId, String name, String surname, String email, String password, String photoUrl, double kmCar, double kmTransit, double kmWalk, double co2Car, double co2SavedCar, double co2SavedTransit, double co2SavedWalk, int point) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.co2Car = co2Car;
        this.co2SavedCar = co2SavedCar;
        this.co2SavedTransit = co2SavedTransit;
        this.co2SavedWalk = co2SavedWalk;
        this.point = point;
    }


    //User Google

    public User(String userId, String name, String surname, String email, String photoUrlGoogle, double kmCar, double kmTransit, double kmWalk, double co2Car, double co2SavedCar, double co2SavedTransit, double co2SavedWalk, int point) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoUrlGoogle = photoUrlGoogle;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.co2Car = co2Car;
        this.co2SavedCar = co2SavedCar;
        this.co2SavedTransit = co2SavedTransit;
        this.co2SavedWalk = co2SavedWalk;
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

    public double getCo2SavedCar() {
        return co2SavedCar;
    }

    public void setCo2SavedCar(double co2SavedCar) {
        this.co2SavedCar = co2SavedCar;
    }

    public double getCo2SavedTransit() {
        return co2SavedTransit;
    }

    public void setCo2SavedTransit(double co2SavedTransit) {
        this.co2SavedTransit = co2SavedTransit;
    }

    public double getCo2SavedWalk() {
        return co2SavedWalk;
    }

    public void setCo2SavedWalk(double co2SavedWalk) {
        this.co2SavedWalk = co2SavedWalk;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public double getCo2Car() {
        return co2Car;
    }

    public void setCo2Car(double co2Car) {
        this.co2Car = co2Car;
    }
}

