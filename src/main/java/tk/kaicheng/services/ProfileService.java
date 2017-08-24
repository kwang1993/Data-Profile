package tk.kaicheng.services;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import tk.kaicheng.models.Profile;
import tk.kaicheng.models.User;

import java.util.List;

/**
 * Created by wangkaicheng on 2017/8/20.
 */
public interface ProfileService {
    Profile save(Profile profile);

    void deleteAll();

    void delete(Integer id);

    void delete(Profile profile);

    Iterable <Profile> findAll();

    Profile findOne(Integer id);

    Profile findByProfileName(String profileName);

    List<Profile> findByUser(User user);

    public void updateProfileName(int profile_id, String newProfileName);

    public void saveProfileFeature(int profile_id, int feature_id, String featureValue);

    public void deleteProfileFeatureById(int profile_id, int feature_id);

    public void updateProfileFeatureByFeatureValue(int profile_id, int feature_id, String newFeatureValue);

    public String findProfileFeatureById(int profile_id, int feature_id);

    public List<Object[]> findProfileFeatureByProfileId(int profile_id);

    public List<Object[]> findAllProfileFeatures();
}
