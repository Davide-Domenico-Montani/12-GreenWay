package it.unimib.greenway.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {
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
    double co2Produced;
    double co2Car;
    double co2SavedCar;
    double co2SavedTransit;
    double co2SavedWalk;

    int point;
    double co2Consumed;

    List<StatusChallenge> statusChallengeList;
    List<String> idFriends;
    public User() {
    }


    //User normale
    public User(String userId, String name, String surname, String email, String password, String photoUrl, double kmCar, double kmTransit, double kmWalk, double co2Produced, double co2Car, double co2SavedCar, double co2SavedTransit, double co2SavedWalk, int point, List<StatusChallenge> statusChallengeList, List<String> idFriends, double co2Consumed) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.photoUrl = photoUrl;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.co2Produced = co2Produced;
        this.co2Car = co2Car;
        this.co2SavedCar = co2SavedCar;
        this.co2SavedTransit = co2SavedTransit;
        this.co2SavedWalk = co2SavedWalk;
        this.point = point;
        this.statusChallengeList = statusChallengeList;
        this.idFriends = idFriends;
        this.co2Consumed = co2Consumed;
    }


    //User Google

    public User(String userId, String name, String surname, String email, String photoUrlGoogle, String photoUrl, double kmCar, double kmTransit, double kmWalk, double co2Car, double co2SavedCar, double co2SavedTransit, double co2SavedWalk, int point, List<StatusChallenge> statusChallengeList) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.photoUrlGoogle = photoUrlGoogle;
        this.kmCar = kmCar;
        this.kmTransit = kmTransit;
        this.kmWalk = kmWalk;
        this.co2Produced = co2Produced;
        this.co2Car = co2Car;
        this.co2SavedCar = co2SavedCar;
        this.co2SavedTransit = co2SavedTransit;
        this.co2SavedWalk = co2SavedWalk;
        this.point = point;
        this.photoUrl = photoUrl;
        this.statusChallengeList = statusChallengeList;
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

    public double getCo2Consumed() {
        return co2Consumed;
    }

    public void setCo2Consumed(double co2Consumed) {
        this.co2Consumed = co2Consumed;
    }

    public List<String> getIdFriends() {
        return idFriends;
    }

    public void setIdFriends(List<String> idFriends) {
        this.idFriends = idFriends;
    }

    public List<StatusChallenge> getStatusChallengeList() {
        return statusChallengeList;
    }

    public void setStatusChallengeList(List<StatusChallenge> statusChallengeList) {
        this.statusChallengeList = statusChallengeList;
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

    public double getCo2Produced() {
        return co2Produced;
    }

    public void setCo2Produced(double co2Produced) {
        this.co2Produced = co2Produced;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userId);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.photoUrl);
        dest.writeString(this.photoUrlGoogle);
        dest.writeDouble(this.kmCar);
        dest.writeDouble(this.kmTransit);
        dest.writeDouble(this.kmWalk);
        dest.writeDouble(this.co2Produced);
        dest.writeDouble(this.co2Car);
        dest.writeDouble(this.co2SavedCar);
        dest.writeDouble(this.co2SavedTransit);
        dest.writeDouble(this.co2SavedWalk);
        dest.writeInt(this.point);
        dest.writeDouble(this.co2Consumed);
        dest.writeList(this.statusChallengeList);
        dest.writeStringList(this.idFriends);
    }

    public void readFromParcel(Parcel source) {
        this.userId = source.readString();
        this.name = source.readString();
        this.surname = source.readString();
        this.email = source.readString();
        this.password = source.readString();
        this.photoUrl = source.readString();
        this.photoUrlGoogle = source.readString();
        this.kmCar = source.readDouble();
        this.kmTransit = source.readDouble();
        this.kmWalk = source.readDouble();
        this.co2Produced = source.readDouble();
        this.co2Car = source.readDouble();
        this.co2SavedCar = source.readDouble();
        this.co2SavedTransit = source.readDouble();
        this.co2SavedWalk = source.readDouble();
        this.point = source.readInt();
        this.co2Consumed = source.readDouble();
        this.statusChallengeList = new ArrayList<StatusChallenge>();
        source.readList(this.statusChallengeList, StatusChallenge.class.getClassLoader());
        this.idFriends = source.createStringArrayList();
    }

    protected User(Parcel in) {
        this.userId = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.photoUrl = in.readString();
        this.photoUrlGoogle = in.readString();
        this.kmCar = in.readDouble();
        this.kmTransit = in.readDouble();
        this.kmWalk = in.readDouble();
        this.co2Produced = in.readDouble();
        this.co2Car = in.readDouble();
        this.co2SavedCar = in.readDouble();
        this.co2SavedTransit = in.readDouble();
        this.co2SavedWalk = in.readDouble();
        this.point = in.readInt();
        this.co2Consumed = in.readDouble();
        this.statusChallengeList = new ArrayList<StatusChallenge>();
        in.readList(this.statusChallengeList, StatusChallenge.class.getClassLoader());
        this.idFriends = in.createStringArrayList();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

