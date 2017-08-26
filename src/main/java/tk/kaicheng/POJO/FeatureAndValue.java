package tk.kaicheng.POJO;

import org.springframework.stereotype.Component;

/**
 * Created by wangkaicheng on 2017/8/25.
 */
@Component
public class FeatureAndValue{
//    private Integer profileId;
    private Integer entryId;
    private Integer featureId;
    private String featureName;
    private String featureValue;

    public FeatureAndValue(){}
    public FeatureAndValue( Integer entryId, Integer featureId, String featureName, String featureValue){
//    public FeatureAndValue(Integer profileId, Integer entryId, Integer featureId, String featureName, String featureValue){
//        this.profileId = profileId;
        this.entryId = entryId;
        this.featureId = featureId;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getFeatureValue() {
        return featureValue;
    }

    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }

//    public Integer getProfileId() {
//        return profileId;
//    }
//
//    public void setProfileId(Integer profileId) {
//        this.profileId = profileId;
//    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }
}
