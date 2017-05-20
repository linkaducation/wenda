package com.zm.model;

/**
 * Created by Ellen on 2017/5/19.
 */
public class User {
    private int id;
    private String password;
    private String headUrl;
    private String salt;
    private String name;

    public User() {
    }

    public User(int id, String name, String password, String headUrl, String salt) {

        this.id = id;
        this.name = name;
        this.password = password;
        this.headUrl = headUrl;
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", headUrl='" + headUrl + '\'' +
                ", salt='" + salt + '\'' +
                '}';
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }


}
