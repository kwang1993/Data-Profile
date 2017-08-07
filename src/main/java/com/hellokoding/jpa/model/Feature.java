package com.hellokoding.jpa.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Feature {
    private int id;
    private String name;
    private Set<ProfileFeature> profileFeatures;

    public Feature(){

    }

    public Feature(String name){
        this.name = name;
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

    @OneToMany(mappedBy = "feature")
    public Set<ProfileFeature> getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(Set<ProfileFeature> profileFeatures) {
        this.profileFeatures = profileFeatures;
    }
}
