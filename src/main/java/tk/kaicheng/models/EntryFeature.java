package tk.kaicheng.models;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "entry_feature") // entry_id, feature_id, feature_value
public class EntryFeature implements Serializable{
    @Id
    @ManyToOne
    @JoinColumn(name = "entry_id")
    private Entry entry;

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;

    @Column(name = "feature_value")
    private String featureValue;

    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
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
