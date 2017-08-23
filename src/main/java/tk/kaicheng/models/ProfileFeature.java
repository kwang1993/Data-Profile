package tk.kaicheng.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "profile_feature")
public class ProfileFeature implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Column(name = "feature_value")
    private String featureValue;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }
}
