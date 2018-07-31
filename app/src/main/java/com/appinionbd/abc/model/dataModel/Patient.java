package com.appinionbd.abc.model.dataModel;

public class Patient {
    int id;
    String name;
    String age;
    String relationship;

    public Patient() {
    }

    public Patient(int id, String name, String age, String relationship) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.relationship = relationship;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
}
