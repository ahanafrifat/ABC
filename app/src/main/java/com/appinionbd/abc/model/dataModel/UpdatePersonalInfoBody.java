package com.appinionbd.abc.model.dataModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdatePersonalInfoBody {

    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("height")
    @Expose
    private Integer height;
    @SerializedName("weight")
    @Expose
    private Integer weight;
    @SerializedName("gender")
    @Expose
    private Integer gender;

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

}
