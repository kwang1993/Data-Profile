package tk.kaicheng.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "feature")
public class Feature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "feature_id")
    private int id;

    @Column(name = "feature_name")
    private String featureName;

    @OneToMany(mappedBy = "feature")
    private Set<ProfileFeature> profileFeatures;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public Set<ProfileFeature> getProfileFeatures() {
        return profileFeatures;
    }

    public void setProfileFeatures(Set<ProfileFeature> profileFeatures) {
        this.profileFeatures = profileFeatures;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
