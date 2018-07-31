package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonitorsPatientList {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_email")
    @Expose
    private String userEmail;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("user_ref_code")
    @Expose
    private String userRefCode;

    public MonitorsPatientList() {
    }

    public MonitorsPatientList(String userId, String userName, String userEmail, String dob, String height, String weight, String gender, String userRefCode) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.dob = dob;
        this.height = height;
        this.weight = weight;
        this.gender = gender;
        this.userRefCode = userRefCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUserRefCode() {
        return userRefCode;
    }

    public void setUserRefCode(String userRefCode) {
        this.userRefCode = userRefCode;
    }

}
