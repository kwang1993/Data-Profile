package com.hellokoding.jpa.model;

import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Profile{
    private int id;
    private String name;
    private Set<ProfileFeature> profileFeatures;
    private User user;

    public Profile() {
    }

    public Profile(String name, User user) {
        this.name = name;
        profileFeatures = new HashSet<>();
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)

    // if profile.profileFeatures doesn't contain a profilepuslisher, then not show in profilepublisher table
    public Set<ProfileFeature>   getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(Set<ProfileFeature> profileFeatures) {
        this.profileFeatures = profileFeatures;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }
}
