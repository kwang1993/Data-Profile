package tk.kaicheng.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "feature") // feature_id, feature_name, profile_id, entry-features
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feature_id")
    private Integer id;

    @Column(name = "feature_name")
    private String featureName;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile; // foreign key

    @OneToMany(mappedBy = "feature")
    private Set<EntryFeature> entryFeatures; // many to many with extra column featureValue



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Set<EntryFeature> getEntryFeatures() {
        return entryFeatures;
    }

    public void setEntryFeatures(Set<EntryFeature> entryFeatures) {
        this.entryFeatures = entryFeatures;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
