package tk.kaicheng.models;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by wangkaicheng on 2017/8/26.
 */
@Entity
@Table(name = "entry") // entry_id, entry_name, profile_id, entry-features,
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "entry_id")
    private Integer id;

    @Column(name = "entry_name")
    private String entryName;


    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile; // foreign key


    @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EntryFeature> entryFeatures; // entry feature mapping

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEntryName() {
        return entryName;
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
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
