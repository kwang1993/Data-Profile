package com.hellokoding.jpa.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "feature")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name = "id")
    private Set<ProfileFeature> profileFeatures;

    public Feature(){
    }

    public Feature(String name){
        this.name = name;
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

    public Set<ProfileFeature> getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(Set<ProfileFeature> profileFeatures) {
        this.profileFeatures = profileFeatures;
    }
}
