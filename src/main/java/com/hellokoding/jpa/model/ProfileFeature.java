package com.hellokoding.jpa.model;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "profile_feature")
public class ProfileFeature implements Serializable{
    private Profile profile;
    private Feature feature;
    private String featureValue;

    @Id
    @ManyToOne
    @JoinColumn(name = "profile_id")
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id")
    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    @Column(name = "feature_value")
    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }
}
