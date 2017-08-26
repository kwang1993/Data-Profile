package tk.kaicheng.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profile") // profile_id, profile_name, user_id, features, entries
public class Profile{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_id")
    private Integer id;

    @Column(name = "profile_name")
    private String profileName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;// foreign key

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Feature> features; // one to many relationship

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Entry> entries; // one to many relationship

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Set<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Set<Entry> entries) {
        this.entries = entries;
    }
}
