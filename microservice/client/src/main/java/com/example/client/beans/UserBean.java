package com.example.client.beans;

public class UserBean {

    private String name;
    private String password;

    public UserBean(){}

    public UserBean(String n, String p){
        name=n;
        password=p;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
