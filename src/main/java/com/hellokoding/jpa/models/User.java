package com.hellokoding.jpa.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangkaicheng on 2017/8/4.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;// This password requires further processing

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Profile> profiles;

    public User(){
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.profiles = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }
}
