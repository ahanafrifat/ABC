package com.appinionbd.abc.model.dataHolder;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserInfo extends RealmObject {
    @PrimaryKey
    private String userId;
    private String token;

    public UserInfo() {
    }

    public UserInfo(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
