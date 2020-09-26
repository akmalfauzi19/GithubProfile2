package com.example.githubprofile;

import android.os.Parcel;
import android.os.Parcelable;

public class Profile implements Parcelable {
    private int avatar;
    private String  Imgavatar, name, username, location, company, repository, followers, following;

    public Profile(){

    }

    protected Profile(Parcel in) {
        avatar = in.readInt();
        repository = in.readString();
        followers = in.readString();
        following = in.readString();
        name = in.readString();
        username = in.readString();
        location = in.readString();
        company = in.readString();
        Imgavatar = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public String getImgavatar() {
        return Imgavatar;
    }

    public void setImgavatar(String imgavatar) {
        Imgavatar = imgavatar;
    }
    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(avatar);
        dest.writeString(repository);
        dest.writeString(followers);
        dest.writeString(following);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(location);
        dest.writeString(company);
        dest.writeString(Imgavatar);
    }

}
