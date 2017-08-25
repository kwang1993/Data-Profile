package tk.kaicheng.POJO;

/**
 * Created by wangkaicheng on 2017/8/25.
 */
public class FeatureAndValue{
    private int profileId;
    private int featureId;
    private String featureName;
    private String featureValue;

    public FeatureAndValue(){}

    public FeatureAndValue(int profileId, int featureId, String featureName, String featureValue){
        this.profileId = profileId;
        this.featureId = featureId;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }

    public int getFeatureId() {
        return featureId;
    }

    public void setFeatureId(int featureId) {
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

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }
}
