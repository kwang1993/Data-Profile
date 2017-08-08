package com.hellokoding.jpa.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "profile")
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProfileFeature> profileFeatures;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Profile() {
    }
    public Profile(String name) {
        this.name = name;
        profileFeatures = new HashSet<>();
    }
    public Profile(String name, User user) {
        this.name = name;
        profileFeatures = new HashSet<>();
        this.user = user;
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


    // if profile.profileFeatures doesn't contain a profilepuslisher, then not show in profilepublisher table
    public Set<ProfileFeature>   getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(Set<ProfileFeature> profileFeatures) {
        this.profileFeatures = profileFeatures;
    }


    public User getUser() {
        return user;
    }

    public void setUserId(User user) {
        this.user = user;
    }
}
