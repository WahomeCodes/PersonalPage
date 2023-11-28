package com.example.personalpage;

public class User {
    private String userName;
    private String userEmail;
//    URL
    private String profilePicture;

    public User(String userName, String userEmail, String profilePicture) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.profilePicture = profilePicture;
    }

//    Empty constructor for the URL
    public User() {
    }

//    Getters and setters

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
